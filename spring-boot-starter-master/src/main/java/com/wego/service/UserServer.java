package com.wego.service;

import com.wego.entity.Food;
import com.wego.entity.Skin;
import com.wego.entity.User;
import com.wego.model.ResultModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    //健康豆转账
    ResultModel transfer(String payerName, String payeeName, int amount);
    /***
     * 查询用户信息，同时更新等级
     * @param integer
     * @return
     */
    ResultModel<User> showInfo(Integer integer);

    /**
     * 根据用户id去查找食物id
     * @param uid
     * @return
     */
    ResultModel<List<Food>> showFood(Integer uid);

    /***
     * 根据用户id去查找皮肤id
     * @param uid
     * @return
     */
    ResultModel< List<Skin>> showSkin(Integer uid);

    /**
     * 给食物喂食
     * @param uid
     * @param pid
     * @param fid
     * @return
     */
    ResultModel useFood(Integer uid,Integer pid,Integer fid);
}
