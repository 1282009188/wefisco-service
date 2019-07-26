package com.wego;

import com.wego.bacService.BACManager;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class WegoCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... var1) throws Exception {
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
        BAC001 bac001 = BAC001.deploy(web3j, Credentials.create("77fda4a212c4f285347b0275527b0f9087e47f010af2fdc680143e8ffa47ac94"), contractGasProvider, "健康豆资产发布", "health bean", BigInteger.valueOf(1), BigInteger.valueOf(1000000000)).send();
        BACManager.getInstance().setContractAddress(bac001.getContractAddress());
        System.out.println("已经发行1000000000健康豆");
        //发给金吉鸟
        bac001.send("0xd7eacedcebbddbd98aed1de894ca0eade4e9eb53", BigInteger.valueOf(20000), "金吉鸟购买健康豆").send();
        System.out.println("已经出售20000健康豆至金吉鸟健身房");
        //发给维比
        bac001.send("0x41374f1a3fe92a753dc0c516b22a9892837c1422", BigInteger.valueOf(30000), "维比购买健康豆").send();
        System.out.println("已经出售30000健康豆至维比健身房");
    }
}
