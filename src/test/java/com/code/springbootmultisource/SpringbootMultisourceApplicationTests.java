package com.code.springbootmultisource;

import com.code.springbootmultisource.common.dao.entity.User;
import com.code.springbootmultisource.serive.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMultisourceApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootMultisourceApplicationTests.class);

    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {

        User user = userService.findById(1);
        LOGGER.info("核心数据库user = " + user.getUsername());

        User user1 = userService.findById1(1);
        LOGGER.info("biz数据库user = " + user1.getUsername());
    }

}
