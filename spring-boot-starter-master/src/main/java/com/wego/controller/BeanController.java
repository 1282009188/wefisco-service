package com.wego.controller;

import com.wego.model.ResultModel;
import com.wego.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class BeanController {

    @Autowired
    UserServer userServer;

    @PostMapping("bean/transfer")
    @ResponseBody
    ResultModel transfer(@RequestParam String payerName, @RequestParam String payeeName, @RequestParam int amount) {
        return userServer.transfer(payerName, payeeName, amount);
    }
}
