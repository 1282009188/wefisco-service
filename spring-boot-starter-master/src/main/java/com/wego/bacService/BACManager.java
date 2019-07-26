package com.wego.bacService;

import com.wego.entity.User;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

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

    private static String contractAddress;

    public static String getContractAddress() {
        return contractAddress;
    }

    public static void setContractAddress(String contractAddress) {
        BACManager.contractAddress = contractAddress;
    }

    public static BAC001 getBAC001(User user) {
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
        return BAC001.load(contractAddress, web3j, Credentials.create(user.getSk()), contractGasProvider);
    }
}
