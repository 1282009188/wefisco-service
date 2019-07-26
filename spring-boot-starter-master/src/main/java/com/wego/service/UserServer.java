package com.wego.service;

import com.wego.model.ResultModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author elizayuan
 * 主要用于对用户的操作
 */

public interface UserServer {
    //用户注册，输入密码，用户名，邮箱
    ResultModel register(String pwd, String name, String email);

    //用户名
    ResultModel login(String name, String pwd);

    //健康豆转账
    ResultModel transfer(String payerName, String payeeName, int amount);
}
