package com.study.srb.core.controller.admin;


import com.alibaba.excel.EasyExcel;
import com.study.srb.core.pojo.dto.ExcelDictDto;
import com.study.srb.core.pojo.entity.Dict;
import com.study.srb.core.service.DictService;
import com.study.srb.exception.BusinessException;
import com.study.srb.result.R;
import com.study.srb.result.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Api(tags = "数据字典管理")
@Slf4j
// 在gataway里配置了跨域，和@CrossRoigin冲突
// @CrossOrigin
@RestController
@RequestMapping("/admin/core/dict")
public class AdminDictController {
    @Resource
    DictService dictService;

    @ApiOperation("Excel数据的批量导入")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel数据字典文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            dictService.importData(file.getInputStream());
            return R.ok().message("数据字典批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("excel数据的导出")
    @GetMapping("/export")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("mydict", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ExcelDictDto.class).sheet("模板")
                .doWrite(dictService.listDictData());
    }

    @ApiOperation("根据上级id获取节点数据")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@ApiParam(value = "上级节点id", required = true)
                            @PathVariable Long parentId) {
        List<Dict> dictList = dictService.listByParentId(parentId);
        return R.ok().data("list", dictList);
    }
}

