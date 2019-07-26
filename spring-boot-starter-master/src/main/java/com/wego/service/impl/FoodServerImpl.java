package com.wego.service.impl;

import com.wego.entity.Food;

import java.util.List;

public interface FoodServerImpl {

    //显示用户所拥有的宠物食物
    /**
     * @param uid
     * @return
     */
    List<Food> showUserfoods(int  uid);


    // 使用食物
    /**
     * @param uid   用户id
     * @param pid   宠物id
     * @param fid   食物id
     */
    void  useFood(int uid,int pid,int  fid);


    //购买食物
    /**
     * @param uid 用户id
     * @param fid 食物id
     * @param num  购买数量
     * @return   0，1  0=》健康豆余额不足
     */

    int buyFood(int uid, int  fid,int num);
}
