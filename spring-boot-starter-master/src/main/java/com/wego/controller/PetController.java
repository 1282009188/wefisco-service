package com.wego.controller;

import com.wego.entity.Pet;
import com.wego.model.ResultModel;
import com.wego.service.PetServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author elizayuan
 * 用于操作宠物的接口
 */
@RestController
public class PetController {
    @Autowired
    PetServer petServer;

    /**
     * 在商城中查询所有的宠物
     * @return
     */
    @GetMapping("market/pet")
    @ResponseBody
    ResultModel<HashMap<String,List<Pet>>> marketPet(){
        return petServer.showMarketPet();
    }
}
