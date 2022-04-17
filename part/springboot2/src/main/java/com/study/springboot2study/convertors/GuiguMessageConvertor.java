package com.study.springboot2study.convertors;

import com.study.springboot2study.pojo.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @ClassName GuiguMessageConvertor
 * @Description 自定义的MessageConvertor
 * @Author Zhangyuhan
 * @Date 2022/4/18
 * @Version 1.0
 */
public class GuiguMessageConvertor implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // 读这里先不管，目前只关注写
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        // isAssignableFrom()判断某个类是不是参数类的父类（包括参数类本身）
        return clazz.isAssignableFrom(Person.class);
    }

    /**
     * 服务器要关注所有MessageConvertor能写出哪些内容类型
     * 获取该MessageConvertor所有支持的媒体类型
     *
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        // 自定义一个或多个该Convertor支持的协议类型
        return MediaType.parseMediaTypes("application/x-guigu");
    }

    @Override
    public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // 读这里先不管，目前只关注写
        return null;
    }

    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 自定义协议的写出
        String data = person.getUserName() + ";" + person.getAge() + ";" + person.getBirth();

        // 写出去
        // body是一个写出的输出流
        OutputStream body = outputMessage.getBody();
        body.write(data.getBytes());

    }
}
