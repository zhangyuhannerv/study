package com.study.spring5_transaction.test;

import com.study.spring5_transaction.config.TxConfig;
import com.study.spring5_transaction.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestAccount
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/9
 * @Version 1.0
 */
public class TestAccount {
    @Test
    public void testTransferAccount() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("com/study/spring5_transaction/bean.xml");
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.transferMoney();
    }

    @Test
    public void testTransferAccountAllByXml() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("com/study/spring5_transaction/transactionBean.xml");
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.transferMoney();
    }

    @Test
    public void testTransferAccountAllByAno() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(TxConfig.class);
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.transferMoney();
    }
}
