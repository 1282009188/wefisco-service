package org.fisco.bcos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @RequestMapping("/index")
    public String index() {
        return "success";
    }


    @RequestMapping("/getMap")
    public Map<String, Object> getMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errorCode", "200");
        result.put("errorMsg", "成功");
        return result;
    }

    @RequestMapping("/testName")
    @ResponseBody
    public Map<String, Object> testName(String username) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("username", username);
        return result;
    }



}
