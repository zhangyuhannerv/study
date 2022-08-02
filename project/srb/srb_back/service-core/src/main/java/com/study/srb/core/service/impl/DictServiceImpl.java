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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", parentId);
        List<Dict> list = dictMapper.selectList(dictQueryWrapper);
        // 填充hasChildren字段
        list.forEach(dict -> {
            // 判断当前节点是否有子节点，找到当前dict的下级有没有子节点
            dict.setHasChildren(hasChildren(dict.getId()));
        });
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
