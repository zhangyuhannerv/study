package com.study.srb_mybatis_plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入自动填充");
        // 实现填充业务逻辑
        // 在插入的时候把要插入的对象的相应的属性值赋值
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 如果age属性有值，还是会执行下面的代码
        // 虽然经过内部校验最终age的值还是初始值，而不是填充值。但还是执行了一步无用的代码，降低了效率
//        log.info("insert age 属性");
//        this.strictInsertFill(metaObject, "age", Integer.class, 3);
        // 优化，判断属性是否赋值,没有赋值才自动填充
        if (this.getFieldValByName("age", metaObject) == null) {
            log.info("insert age 属性");
            this.strictInsertFill(metaObject, "age", Integer.class, 3);
        }

        // 测试自动填充某一张表的特定字段（对于大多数表来说是不存在的字段）
        // 测试结果是可以正常插入，但是确实执行了下面的代码。下面的代码对于大多数插入来说是无意义的，降低了效率
//        log.info("insert author 属性");
//        this.strictInsertFill(metaObject, "author", String.class, "石头");

        // 因此需要优化：判断数据对象里面有没有特定属性，有才执行
        if (metaObject.hasSetter("author")) {
            log.info("insert author 属性");
            this.strictInsertFill(metaObject, "author", String.class, "石头");
        }

        // 经历上述两个优化：
        // 总结：
        // 自动填充字段最好是所有表都有的，并且需要自动填充的
        // 某些表独有的字段如果想自动填充，那么直接写在业务代码里即可
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新自动填充");
        // 在更新的时候把要更新的对象的相应的属性值赋值
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
