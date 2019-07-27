package com.wego.controller;

import com.wego.entity.Skin;
import com.wego.entity.User;
import com.wego.model.ResultModel;
import com.wego.model.UserModel;
import com.wego.service.FoodService;
import com.wego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author elizayuan
 * 主要用于用户的接口
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FoodService foodService;

    /**
     * 注册
     *
     * @param pwd
     * @param name
     * @param email
     * @return Map key,1代表注册成功,message:注册成功，key:0表示注册失败,message:用户已经存在
     */
    @PostMapping("user/register")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<Integer> register(@RequestParam String pwd, @RequestParam String name, @RequestParam String email) throws Exception{
        return userService.register(pwd, name, email);
    }

    /**
     * 用户登录，登录成功后返回uid
     *
     * @param name
     * @param pwd
     * @return
     */
    @PostMapping("user/login")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel login(@RequestParam String name, @RequestParam String pwd) {
        return userService.login(name, pwd);
    }

    /***
     * 查看用户信息，同时更新数据库的等级
     *
     */
    @GetMapping("user/viewinfo")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<User> viewInfo(Integer uid) {
        return userService.showInfo(uid);
    }


    @GetMapping("user/showskin")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<List<Skin>> showskin(Integer uid) {
        return userService.showSkin(uid);
    }

    /**
     * 显示主页用户以及形象信息
     *
     * @param uid
     * @return
     */
    @GetMapping("user/showimage")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel<UserModel> showImage(Integer uid) {
        return userService.getImage(uid);
    }

}
