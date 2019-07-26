package com.wego.controller;

import com.wego.model.ResultModel;
import com.wego.service.SkinServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author elizayuan
 * 主要用于用户的接口
 */
@RestController
public class SkinController {
    @Autowired
    SkinServer skinServer;

    /**
     * 根据用户的id，宠物id，食物id喂食，同时增在宠物的col
     * @param uid
     * @return
     */
    @PostMapping("skin/buyskin")
    ResultModel buyskin(@RequestParam Integer uid, @RequestParam Integer sid) {
        return skinServer.buySkin(uid,sid);
    }
}
