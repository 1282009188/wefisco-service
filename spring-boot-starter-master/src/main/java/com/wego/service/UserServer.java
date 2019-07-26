package com.wego.service;

import com.wego.entity.User;
import com.wego.model.ResultModel;

/**
 * @author elizayuan
 * 主要用于对用户的操作
 */

public interface UserServer {
    //用户注册，输入密码，用户名，邮箱

    /**
     * 注册
     * @param pwd
     * @param name
     * @param email
     * @return
     */
    ResultModel register(String pwd, String name, String email);

    /**
     * 登录
     * @param name
     * @param pwd
     * @return
     */
    ResultModel login(String name, String pwd);

    /***
     * 查询用户信息，同时更新等级
     * @param integer
     * @return
     */
    User showInfo(Integer integer);

}
