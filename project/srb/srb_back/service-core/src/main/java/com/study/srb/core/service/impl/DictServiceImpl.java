package com.study.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.listener.ExcelDictDTOListener;
import com.study.srb.core.mapper.DictMapper;
import com.study.srb.core.pojo.dto.ExcelDictDto;
import com.study.srb.core.pojo.entity.Dict;
import com.study.srb.core.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DictMapper dictMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDto.class, new ExcelDictDTOListener(dictMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    @Override
    public List<ExcelDictDto> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        // 创建ExcelDictDto列表，将dict列表转成excelDictDto列表
        List<ExcelDictDto> excelDictDtoList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDto excelDictDto = new ExcelDictDto();
            BeanUtils.copyProperties(dict, excelDictDto);
            excelDictDtoList.add(excelDictDto);
        });
        return excelDictDtoList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        String redisKey = "srb:core:dictList:" + parentId;
        // 查询缓存
        try {
            // 首先查询redis中是否存在数据列表
            log.info("从redis中取值");
            List<Dict> dictList = (List<Dict>) redisTemplate.opsForValue().get(redisKey);
            // 如果存在，则从redis中直接返回数据列表
            if (dictList != null) {
                return dictList;
            }
        } catch (Exception e) {
            log.error("redis服务器异常：{}", ExceptionUtils.getStackTrace(e));
        }


        // 如果不存在，则查询数据
        log.info("从数据库中取值");
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", parentId);
        List<Dict> list = dictMapper.selectList(dictQueryWrapper);
        // 填充hasChildren字段
        list.forEach(dict -> {
            // 判断当前节点是否有子节点，找到当前dict的下级有没有子节点
            dict.setHasChildren(hasChildren(dict.getId()));
        });


        // 更新一下缓存
        try {
            log.info("向redis中存值");
            redisTemplate.opsForValue().set(redisKey, list, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("redis服务器异常：{}", ExceptionUtils.getStackTrace(e));
        }

        return list;
    }

    /**
     * 判断当前id所在的节点下是否有子节点
     *
     * @param id
     * @return
     */
    private Boolean hasChildren(Long id) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(dictQueryWrapper);
        return count > 0;
    }
}
