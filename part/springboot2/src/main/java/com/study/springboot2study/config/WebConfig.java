package com.study.springboot2study.config;

import com.study.springboot2study.convertors.GuiguMessageConvertor;
import com.study.springboot2study.pojo.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.*;

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

            // 自定义内容协商策略，这样浏览器访问的时候加个参数format=gg,就会返回以x-gui协议写出的内容
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

                // 基于参数的内容协商管理器
                Map<String, MediaType> mediaTypes = new HashMap<>();
                // 指定支持解析哪些参数对应的哪些媒体类型
                mediaTypes.put("json", MediaType.APPLICATION_JSON);
                mediaTypes.put("xml", MediaType.APPLICATION_XML);
                mediaTypes.put("gg", MediaType.parseMediaType("application/x-guigu"));
                ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
                // parameterStrategy.setParameterName("ff");// 默认是format,可以手动更改基于参数的内容协商的参数名称

                // 基于请求头的内容协商管理器
                // 这里之所以要放这个基于请求头的内容协商管理器，是因为此时自定义的内容协商管理器覆盖了系统默认的。
                // 此时整个容器中若不放基于请求头的内容协商管理器的话，就只剩下了基于参数的内容协商管理器。
                // 那么就会出现无论请求头的accept字段传什么值，都只能找到*/*,即返回默认的json
                HeaderContentNegotiationStrategy headerStrategy = new HeaderContentNegotiationStrategy();

                configurer.strategies(Arrays.asList(parameterStrategy, headerStrategy));
            }
        };
    }
}
