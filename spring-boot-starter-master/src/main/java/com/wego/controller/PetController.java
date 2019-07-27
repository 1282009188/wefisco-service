package com.wego.controller;

import com.wego.entity.Pet;
import com.wego.model.ResultModel;
import com.wego.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author elizayuan
 * 用于操作宠物的接口
 */
@RestController
public class PetController {
    @Autowired
    PetService petService;

    /**
     * 在商城中查询所有的宠物
     *
     * @return
     */
    @GetMapping("market/pet")
    @ResponseBody
    ResultModel<HashMap<String, List<Pet>>> marketPet() {
        return petService.showMarketPet();
    }

    /**
     * 在商城中查询所有的宠物
     *
     * @return
     */
    @PostMapping("pet/changename")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel changePet(@RequestParam int uid, @RequestParam int pid, @RequestParam String name) {
        return petService.changePet(uid, pid, name);
    }


}
