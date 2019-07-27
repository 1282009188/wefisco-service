package com.wego.service;

import com.wego.entity.Food;
import com.wego.model.ResultModel;

import java.util.HashMap;
import java.util.List;

public interface FoodService {

    /**
     * 根据用户id去查找食物id
     * @param uid
     * @return
     */
    ResultModel<HashMap<String,List<Food>>> showFood(Integer uid);


    /**
     * 给食物喂食
     * @param uid
     * @param pid
     * @param fid
     * @return
     */
    ResultModel useFood(Integer uid,Integer pid,Integer fid);

    /**
     * 用于显示所有的食物
     * @return
     */
    ResultModel<HashMap<String,List<Food>>>getAllFood();

    ResultModel buyFood(int uid, int fid,int num);

}
