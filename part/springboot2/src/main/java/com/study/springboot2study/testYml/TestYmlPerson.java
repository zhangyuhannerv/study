package com.study.springboot2study.testYml;

import com.study.springboot2study.bean.Pet;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestYmlPerson
 * @Description 测试yml的person对象
 * @Author Zhangyuhan
 * @Date 2022/3/25
 * @Version 1.0
 */

@Component
@ConfigurationProperties(prefix = "person")
@ToString
@Data
public class TestYmlPerson {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private TestYmlPet pet;
    private String[] interests;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salarys;
    private Map<String, List<Pet>> allPets;
}
