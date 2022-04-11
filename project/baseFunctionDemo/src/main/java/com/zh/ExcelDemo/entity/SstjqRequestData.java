package com.zh.ExcelDemo.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName SstjqRequestData
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/31
 * @Version 1.0
 */

@Data
@ToString
public class SstjqRequestData {
    private String code;
    private String xb;
    private List<String> ids;
    private List<Sstjq> exportList;
}
