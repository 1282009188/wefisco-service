package com.wego.controller;

import com.wego.model.ResultModel;
import com.wego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeanController {

    @Autowired
    UserService userService;

    @PostMapping("bean/transfer")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel transfer(@RequestParam String payerName, @RequestParam String payeeName, @RequestParam int amount) {
        return userService.transfer(payerName, payeeName, amount);
    }
}
