package com.cmpay.service.weixin.dao;

import com.cmpay.service.weixin.model.TestUser;

public interface TestUserMapper {
    int insert(TestUser record);

    int insertSelective(TestUser record);
}