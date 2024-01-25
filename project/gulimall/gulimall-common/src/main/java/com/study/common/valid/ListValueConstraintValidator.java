package com.study.common.valid;

import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

@Data
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    private Set<Integer> set = new HashSet<>();

    // 初始化方法
    @Override
    public void initialize(ListValue constraintAnnotation) {
        // 注解上标明的范围数据
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            this.set.add(val);
        }
    }

    // 判断是否校验成功

    /**
     * @param value   object to validate 需要校验的值
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (set.size() > 0) {
            return set.contains(value);
        } else {
            // 此时注解没标明范围，则任何值都校验通过
            return true;
        }
    }
}
