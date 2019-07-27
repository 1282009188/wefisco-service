package com.wego.controller;

import com.wego.dao.UserMapper;
import com.wego.entity.User;
import com.wego.model.ResultModel;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class InitController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/deploy")
    @CrossOrigin(origins = "*")
    ResultModel deploy() throws Exception {
        BigInteger gasPrice = new BigInteger("1");
        BigInteger gasLimit = new BigInteger("2100000000");
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        // 获取spring配置文件，生成上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        User wego = userMapper.selectByName("wego");
        //wego发行资产
        BAC001 bac001 = BAC001.deploy(web3j, Credentials.create(wego.getSk()), contractGasProvider, "健康豆资产发布", "health bean", BigInteger.valueOf(1), BigInteger.valueOf(10000)).send();
        wego.setBean(bac001.balance(wego.getAddr()).send().intValue());
        userMapper.updateByPrimaryKey(wego);
        ResultModel model = new ResultModel();
        model.setMessage("发行成功");
        model.setData(bac001.getContractAddress());
        return model;
    }
}
