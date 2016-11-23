package com.cmpay.cashier.dao;

import com.cmpay.cashier.model.TestUser;

public interface TestUserMapper {
    int insert(TestUser record);

    int insertSelective(TestUser record);
}