package com.wego.bacService;

import com.wego.entity.User;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.BAC002;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BACManager {
    //创建 SingleObject 的一个对象
    private static BACManager instance = new BACManager();

    //让构造函数为 private，这样该类就不会被实例化
    private BACManager() {
    }

    //获取唯一可用的对象
    public static BACManager getInstance() {
        return instance;
    }


    public static BAC001 getBAC001(User user) throws Exception {
        BigInteger gasPrice = new BigInteger("1");
        BigInteger gasLimit = new BigInteger("2100000000");
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        // 获取spring配置文件，生成上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        try {
            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());

        Path rootLocation = Paths.get("folder");
        Path path = rootLocation.resolve("bac001.txt");
        return BAC001.load(new String(Files.readAllBytes(path)), web3j, Credentials.create(user.getSk()), contractGasProvider);
    }

    @Test
    public static BAC002 getBAC002(User user) throws Exception {
        BigInteger gasPrice = new BigInteger("1");
        BigInteger gasLimit = new BigInteger("2100000000");
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        try {
            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());

        Path rootLocation = Paths.get("folder");
        Path path = rootLocation.resolve("bac002.txt");
        return BAC002.load(new String(Files.readAllBytes(path)), web3j, Credentials.create(user.getSk()), contractGasProvider);
    }
}
