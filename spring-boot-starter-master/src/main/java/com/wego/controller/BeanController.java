package com.wego.controller;

import com.wego.model.ResultModel;
import com.wego.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class BeanController {

    @Autowired
    UserServer userServer;

    @PostMapping("bean/transfer")
    @ResponseBody
    @CrossOrigin(origins = "*")
    ResultModel transfer(@RequestParam String payerName, @RequestParam String payeeName, @RequestParam int amount) {
        return userServer.transfer(payerName, payeeName, amount);
    }
}
