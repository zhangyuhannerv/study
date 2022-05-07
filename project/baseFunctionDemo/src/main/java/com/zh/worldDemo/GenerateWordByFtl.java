package com.zh.worldDemo;

import org.junit.Test;
import freemarker.core.ParseException;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GenerateWordByFtl
 * @Description 通过ftl生成world文件
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */
@Slf4j
public class GenerateWordByFtl {
    private final Configuration config;

    {
        config = new Configuration(Configuration.VERSION_2_3_26);
        config.setDefaultEncoding("utf-8");
    }

    /**
     * @param t            数据
     * @param templateName 模板名称
     * @param response     响应
     */
    public <T> void createWord(T t, String templateName, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>();
        try {
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.getType().equals(Date.class)) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatDate = sdf.format(field.get(t));
                    map.put(field.getName(), formatDate);
                } else {
                    map.put(field.getName(), field.get(t));
                }


            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //加载模板(路径)数据
        config.setClassForTemplateLoading(this.getClass(), "/wordTemplate");
        //设置异常处理器 这样的话 即使没有属性也不会出错 如：${list.name}...不会报错
        config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        Template template = null;
        if (templateName.endsWith(".ftl")) {
            templateName = templateName.substring(0, templateName.indexOf(".ftl"));
        }
        try {
            template = config.getTemplate(templateName + ".ftl");
        } catch (TemplateNotFoundException e) {
            log.error("模板文件未找到", e);
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            log.error("模板类型不正确", e);
            e.printStackTrace();
        } catch (ParseException e) {
            log.error("解析模板出错，请检查模板格式", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO读取失败", e);
            e.printStackTrace();
        }

        // 写入某个文件
        // File file = new File("d://a.doc");
        // try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

        // 写入响应的输出流
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8))) {
            template.process(map, writer);
            // 日志记录转成成功
            log.info("由模板文件：" + templateName + ".ftl" + "导出成功");

        } catch (TemplateException e) {
            log.error("填充模板时异常", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO读取时异常", e);
            e.printStackTrace();
        }

    }
}
