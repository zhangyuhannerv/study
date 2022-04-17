package com.study.springboot2study.config;

import com.study.springboot2study.convertors.GuiguMessageConvertor;
import com.study.springboot2study.pojo.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/2
 * @Version 1.0
 */
@Configuration(proxyBeanMethods = false)
// public class WebConfig implements WebMvcConfigurer {
public class WebConfig {
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
        methodFilter.setMethodParam("_m");
        return methodFilter;
    }

    // 重写路径映射规则，开启矩阵变量

    // 第一种写法，重写接口修改默认实现
    // @Override
    // public void configurePathMatch(PathMatchConfigurer configurer) {
    //     UrlPathHelper urlPathHelper = new UrlPathHelper();
    //     // 不移除;后面的内容，此时矩阵变量才是开启的
    //     urlPathHelper.setRemoveSemicolonContent(false);
    //     configurer.setUrlPathHelper(urlPathHelper);
    // }

    // 第二种开启矩阵变量的写法。不是实现接口，而是重定义WebMvcConfigure组件
    // 只要想改SpringMvc的任何功能，都在下面进行配置。这是一个统一的入口
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            // 开启矩阵变量
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                // 不移除;后面的内容，此时矩阵变量才是开启的
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            // 自定义转换器（Converters)，请求
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {
                    @Override
                    public Pet convert(String source) {// 此时source就是页面传过来的值
                        if (!StringUtils.isEmpty(source)) {
                            Pet pet = new Pet();
                            String[] split = source.split(",");
                            pet.setName(split[0]);
                            pet.setAge(Integer.parseInt(split[1]));
                            return pet;
                        }
                        return null;
                    }
                });
            }

            // 自定义messageConvertor，响应
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new GuiguMessageConvertor());
            }
        };
    }
}
