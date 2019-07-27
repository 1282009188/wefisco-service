package com.wego.service.impl;

import com.wego.dao.*;
import com.wego.entity.Food;
import com.wego.entity.UserPet;
import com.wego.entity.Userfood;
import com.wego.model.ResultModel;
import com.wego.service.FoodServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service("FoodServer")
public class FoodServerImpl implements FoodServer {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserfoodMapper userfoodMapper;
    @Autowired
    FoodMapper foodMapper;
    @Autowired
    UserskinMapper userskinMapper;
    @Autowired
    SkinMapper skinMapper;
    @Autowired
    UserPetMapper petMapper;

    /**
     * 查询该用户有多少食物
     *
     * @param uid
     * @return
     */
    public ResultModel<List<Food>> showFood(Integer uid) {
        ResultModel<List<Food>> resultModel = new ResultModel<>();
        if (uid == null) {
            resultModel.setCode(1);
            resultModel.setMessage("用户id为空");
            return resultModel;
        }
        if (userMapper.selectByPrimaryKey(uid) == null) {
            resultModel.setCode(1);
            resultModel.setMessage("该账户不存在");
            return resultModel;
        }
        List<Userfood> userfood = userfoodMapper.selectByUid(uid);
        if (userfood == null || userfood.size() == 0) {
            resultModel.setCode(1);
            resultModel.setMessage("无食物");
            resultModel.setData(null);
            return resultModel;
        }
        List<Food> foods = new ArrayList<>();
        for (Userfood food : userfood) {
            Integer fid = food.getFid();
            foods.add(foodMapper.selectByPrimaryKey(fid));

        }
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(foods);
        return resultModel;
    }

    public ResultModel useFood(Integer uid, Integer pid, Integer fid) {
        ResultModel resultModel = new ResultModel();
        if (uid == null || pid == null || fid == null) {
            resultModel.setCode(1);
            resultModel.setMessage("用户id,宠物id，食物id不能为空");
            return resultModel;
        }
        //根据用户id，和食物id去查找用户所属的食物
        Userfood userfood = userfoodMapper.selectByUidAndFid(uid, fid);
        if (userfood == null) {
            resultModel.setCode(1);
            resultModel.setMessage("无食物");
            return resultModel;
        }
        if (userfood.getNum() == 0) {
            resultModel.setCode(1);
            resultModel.setMessage("食物数量为0");
            return resultModel;
        }
        //根据食物id去查找食物的卡路里
        Food food = foodMapper.selectByPrimaryKey(fid);
        int col = food.getCol();
        //修改宠物的col
        UserPet pet = petMapper.selectByPrimaryKey(pid);
        if (pet == null) {
            resultModel.setCode(1);
            resultModel.setMessage("宠物不存在");
            return resultModel;
        }
        pet.setCol(col + pet.getCol());
        petMapper.updateByPrimaryKey(pet);
        //减少食物的数量
        userfood.setNum(userfood.getNum() - 1);
        userfoodMapper.updateByPrimaryKey(userfood);
        //返回成功
        resultModel.setCode(0);
        resultModel.setMessage("喂食成功");
        return resultModel;
    }

    /**
     * 获取所有的商城食物列表
     *
     * @return
     */

    public ResultModel<List<Food>> getAllFood() {
        ResultModel<List<Food>> resultModel = new ResultModel<>();
        List<Food> foodList = foodMapper.selectAll();
        if(foodList==null||foodList.size()==0){
            resultModel.setCode(1);
            resultModel.setMessage("该商场还没有任何的食物，敬请期待");
            return resultModel;
        }
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(foodList);
        return resultModel;
    }

}
