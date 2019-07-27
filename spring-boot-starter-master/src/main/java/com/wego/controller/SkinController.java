package com.wego.controller;

import com.wego.model.ResultModel;
import com.wego.service.SkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    SkinService skinService;

    @PostMapping("skin/buyskin")
    @CrossOrigin(origins = "*")
    ResultModel buyskin(@RequestParam Integer uid, @RequestParam Integer sid) {
        return skinService.buySkin(uid, sid);
    }

    @PostMapping("skin/useSkin")
    @CrossOrigin(origins = "*")
    ResultModel useskin(@RequestParam Integer uid, @RequestParam Integer pid, @RequestParam Integer sid) {
        return skinService.useSkin(uid, pid,sid);
    }
}
