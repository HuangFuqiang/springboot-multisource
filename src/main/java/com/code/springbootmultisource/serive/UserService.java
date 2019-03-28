package com.code.springbootmultisource.serive;

import com.code.springbootmultisource.common.annotation.DataSource;
import com.code.springbootmultisource.common.dao.entity.User;
import com.code.springbootmultisource.common.dao.repository.UserMapper;
import com.code.springbootmultisource.common.multidatasource.DSEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    public User findById1(Integer id) {
        return userMapper.selectById(id);
    }

}
