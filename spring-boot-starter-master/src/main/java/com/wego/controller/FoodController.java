package com.wego.controller;

import com.wego.entity.Food;
import com.wego.model.ResultModel;
import com.wego.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {
    @Autowired
    FoodService foodService;

    /**
     * 根据用户的id，宠物id，食物id喂食，同时增在宠物的col
     *
     * @param uid
     * @param pid
     * @param fid
     * @return
     */
    @PostMapping("user/usefood")
    @CrossOrigin(origins = "*")
    ResultModel usefood(@RequestParam Integer uid, @RequestParam Integer pid, @RequestParam Integer fid) {
        return foodService.useFood(uid, pid, fid);
    }

    /**
     * 查看该用户拥有的食物
     *
     * @param uid
     * @return
     */
    @GetMapping("user/showfood")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<List<Food>> showfood(Integer uid) {
        return foodService.showFood(uid);
    }

    /**
     * 查询所有的食物
     *
     * @return
     */
    @GetMapping("market/food")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<List<Food>> showMarketFood() {
        return foodService.getAllFood();
    }

    /**
     * 根据用户的id，宠物id，食物id喂食，同时增在宠物的col
     *
     * @param uid
     * @return
     */
    @PostMapping("user/buyfood")
    ResultModel buyfood(@RequestParam Integer uid, @RequestParam Integer fid, @RequestParam Integer num) {
        return foodService.buyFood(uid, fid, num);
    }

}
