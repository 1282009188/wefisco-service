package com.wego.service.impl;

import com.wego.bacService.BACManager;
import com.wego.dao.UserMapper;
import com.wego.entity.User;
import com.wego.model.ResultModel;
import com.wego.service.UserServer;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author elizayuan
 * 实现用户的逻辑
 */
@Service("UserServer")
public class UserServerImpl implements UserServer {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户注册，会自动生成私钥和密钥
     *
     * @param pwd   密码
     * @param name  名字
     * @param email 邮箱
     * @return Map key,1代表注册成功,message:注册成功，key:0表示注册失败,message:用户已经存在
     */
    public ResultModel register(String pwd, String name, String email) {
        ResultModel resultModel = new ResultModel();
        if (pwd.equals("") || name.equals("") || email.equals("")) {
            resultModel.setCode(0);
            resultModel.setMessage("用户名，密码和邮箱不能为空");
            return resultModel;
        }

        if (userMapper.selectByName(name) != null && name.equals(userMapper.selectByName(name).getName())) {
            resultModel.setCode(0);
            resultModel.setMessage("用户已经存在");
            return resultModel;
        }
        User user = new User();
        user.setPwd(pwd);
        user.setName(name);
        user.setEmail(email);
        //创建普通账户
        EncryptType.encryptType = 0;
        //创建国密账户，向国密区块链节点发送交易需要使用国密账户
        // EncryptType.encryptType = 1;
        Credentials credentials = GenCredential.create();
        //账户地址
        String address = credentials.getAddress();
        //账户私钥
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //账户公钥
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
        user.setAddr(address);
        user.setPk(publicKey);
        user.setSk(privateKey);
        user.setBean(0);
        user.setLevel(0);
        userMapper.insert(user);
        resultModel.setCode(1);
        resultModel.setMessage("注册成功");
        return resultModel;
    }

    /**
     * 用户登录，输入名字和密码
     *
     * @param name 用户名
     * @param pwd  密码
     * @return 0为空，1为账户不存在，2为密码错误，3为登录成功
     */
    public ResultModel login(String name, String pwd) {
        ResultModel resultModel = new ResultModel();
        if (name.equals("") || pwd.equals("")) {
            resultModel.setCode(0);
            resultModel.setMessage("用户名和密码不能为空");
            return resultModel;
        }
        if (userMapper.selectByName(name) == null) {
            resultModel.setCode(0);
            resultModel.setMessage("用户不存在");
            return resultModel;
        }
        if (userMapper.selectByName(name) != null && !pwd.equals(userMapper.selectByName(name).getPwd())) {
            resultModel.setCode(0);
            resultModel.setMessage("密码错误");
            return resultModel;
        }
        resultModel.setCode(1);
        resultModel.setMessage("登录成功");
        resultModel.setUid(userMapper.selectByName(name).getUid());
        return resultModel;
    }

    public ResultModel transfer(String payerName, String payeeName, int amount) {
        User payer = userMapper.selectByName(payerName);
        User payee = userMapper.selectByName(payeeName);

        ResultModel resultModel = new ResultModel();

        if (payer == null) {
            resultModel.setCode(1);
            resultModel.setMessage("支付方名称错误");
            return resultModel;
        }
        if (payee == null) {
            resultModel.setCode(1);
            resultModel.setMessage("收款方不存在");
            return resultModel;
        }
        if (payer.getBean() < amount) {
            resultModel.setCode(1);
            resultModel.setMessage("余额不足");
            return resultModel;
        }
        //1.支付方减豆，收款方加豆，上链
        payer.setBean(payer.getBean() - amount);
        payee.setBean(payee.getBean() + amount);
        userMapper.updateByPrimaryKey(payer);
        userMapper.updateByPrimaryKey(payee);
        BAC001 bac001 = BACManager.getBAC001(payer);
        try {
            bac001.send(payee.getAddr(), BigInteger.valueOf(amount), "交易健康豆").send();
            System.out.println(payerName + ":" + bac001.balance(payer.getAddr()).send().toString());
            System.out.println(payeeName + ":" + bac001.balance(payee.getAddr()).send().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultModel.setCode(0);
        resultModel.setMessage("转账成功");
        return resultModel;
    }


}
