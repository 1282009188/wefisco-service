package com.wego.service.impl;

import com.wego.bacService.BACManager;
import com.wego.dao.*;
import com.wego.entity.*;
import com.wego.model.ResultModel;
import com.wego.model.UserModel;
import com.wego.service.UserService;
import org.fisco.bcos.BAC001;
import org.fisco.bcos.BAC002;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author elizayuan
 * 实现用户的逻辑
 */
@Service("UserServer")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserfoodMapper userfoodMapper;
    @Autowired
    FoodMapper foodMapper;
    @Autowired
    UserskinMapper userskinMapper;
    @Autowired
    SkinMapper skinMapper;
    @Autowired
    UserPetMapper userpetMapper;
    @Autowired
    PetSkinMapper petSkinMapper;
    @Autowired
    PetMapper petMapper;

    /**
     * 用户注册，会自动生成私钥和密钥
     *
     * @param pwd   密码
     * @param name  名字
     * @param email 邮箱
     * @return Map key,1代表注册成功,message:注册成功，key:0表示注册失败,message:用户已经存在
     */
    public ResultModel register(String pwd, String name, String email) throws Exception {
        ResultModel resultModel = new ResultModel();
        if (pwd.equals("") || name.equals("") || email.equals("")) {
            resultModel.setCode(1);
            resultModel.setMessage("用户名，密码和邮箱不能为空");
            return resultModel;
        }

        if (userMapper.selectByName(name) != null && name.equals(userMapper.selectByName(name).getName())) {
            resultModel.setCode(1);
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

        //随机为用户分配一个企鹅
        List<Pet> list = petMapper.selectAll();
        Random random = new Random();
        Pet pet = list.get(random.nextInt(list.size()));
        //插入企鹅数据到userpet表中
        UserPet userPet = new UserPet();
        userPet.setCol(0);
        userPet.setPid(pet.getPid());
        userPet.setPname(pet.getName());
        userPet.setUrl(pet.getUrl());
        userPet.setUid(user.getUid());
        userpetMapper.insert(userPet);

        //生成企鹅资产
        user = userMapper.selectByName(name);
        User wego = userMapper.selectByName("wego");
        BAC002 bac002 = BACManager.getBAC002(wego);
        bac002.issueWithAssetURI(user.getAddr(), BigInteger.valueOf(userPet.getId()),
                "this is a pet information url is www."
                        + pet.getName() + ".com",
                (user.getName() + "get a pet").getBytes()).send();
        resultModel.setCode(0);
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
        ResultModel<Integer> resultModel = new ResultModel<Integer>();
        if (name.equals("") || pwd.equals("")) {
            resultModel.setCode(1);
            resultModel.setMessage("用户名和密码不能为空");
            return resultModel;
        }
        if (userMapper.selectByName(name) == null) {
            resultModel.setCode(1);
            resultModel.setMessage("用户不存在");
            return resultModel;
        }
        if (userMapper.selectByName(name) != null && !pwd.equals(userMapper.selectByName(name).getPwd())) {
            resultModel.setCode(1);
            resultModel.setMessage("密码错误");
            return resultModel;
        }
        resultModel.setCode(0);
        resultModel.setMessage("登录成功");
        resultModel.setData(userMapper.selectByName(name).getUid());
        return resultModel;
    }

    /**
     * 查询用户信息，同时更新等级
     *
     * @param uid
     * @return 用户信息
     */
    public ResultModel<User> showInfo(Integer uid) {
        ResultModel<User> resultModel = new ResultModel<User>();
        if (uid == null) {
            resultModel.setCode(1);
            resultModel.setMessage("不能为空");
            return resultModel;
        }
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            resultModel.setCode(1);
            resultModel.setMessage("该账户不存在");
            return resultModel;
        }
        int level = 0;
        if (user.getBean() != 0) {
            int temp = user.getBean() / 1000;
            level = (int) (Math.log(temp) / Math.log(10)) + 1;
        }

        user.setLevel(level);
        userMapper.updateByPrimaryKey(user);
        user.setSk(null);
        user.setPk(null);
        user.setAddr(null);
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(user);
        return resultModel;
    }

    @Override
    public ResultModel<User> getMechnismBeanByAccount(String account) {
        ResultModel<User> resultModel = new ResultModel<User>();
        if (account == null) {
            resultModel.setCode(1);
            resultModel.setMessage("不能为空");
            return resultModel;
        }
        User user = userMapper.selectByName(account);
        if (user == null) {
            resultModel.setCode(1);
            resultModel.setMessage("该账户不存在");
            return resultModel;
        }
        int level = 0;
        if (user.getBean() != 0) {
            int temp = user.getBean() / 1000;
            level = (int) (Math.log(temp) / Math.log(10)) + 1;
        }

        user.setLevel(level);
        userMapper.updateByPrimaryKey(user);
        user.setSk(null);
        user.setPk(null);
        user.setAddr(null);
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(user);
        return resultModel;
    }


    /**
     * 查询该用户有多少食物
     *
     * @param uid
     * @return
     */
    public ResultModel<List<Skin>> showSkin(Integer uid) {
        ResultModel<List<Skin>> resultModel = new ResultModel<>();
        if (uid == null) {
            resultModel.setCode(1);
            resultModel.setMessage("用户id为空");
            return resultModel;
        }
        if (userMapper.selectByPrimaryKey(uid) == null) {
            resultModel.setCode(1);
            resultModel.setMessage("该账户不存在");
            return resultModel;
        }
        List<Userskin> userskins = userskinMapper.selectByUid(uid);
        if (userskins == null || userskins.size() == 0) {
            resultModel.setCode(1);
            resultModel.setMessage("无皮肤");
            resultModel.setData(null);
            return resultModel;
        }
        List<Skin> skins = new ArrayList<>();
        for (Userskin userskin : userskins) {
            Integer sid = userskin.getSid();
            skins.add(skinMapper.selectByPrimaryKey(sid));

        }
        resultModel.setCode(0);
        resultModel.setMessage("查询皮肤成功");
        resultModel.setData(skins);
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
        BAC001 bac001 = null;
        try {
            bac001 = BACManager.getBAC001(payer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bac001.send(payee.getAddr(), BigInteger.valueOf(amount), "交易健康豆").send();
            int payerBean = bac001.balance(payer.getAddr()).send().intValue();
            int payeeBean = bac001.balance(payee.getAddr()).send().intValue();
            payer.setBean(payerBean);
            payee.setBean(payeeBean);
            userMapper.updateByPrimaryKey(payer);
            userMapper.updateByPrimaryKey(payee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultModel.setCode(0);
        resultModel.setMessage("交易成功");
        return resultModel;
    }

    public ResultModel<UserModel> getImage(Integer uid) {
        ResultModel<UserModel> resultModel = new ResultModel<>();
        UserModel userModel = new UserModel();
        if (uid == null) {
            resultModel.setCode(1);
            resultModel.setMessage("用户id为空");
            return resultModel;
        }
        User user = userMapper.selectByPrimaryKey(uid);
        userModel.setLevel(user.getLevel());
        userModel.setBean(user.getBean());

        //去查找用户的id去查找宠物信息,目前只能有一只
        UserPet pet = userpetMapper.selectByUid(uid);
        if (pet == null) {
            resultModel.setCode(1);
            resultModel.setMessage("宠物为空");
            return resultModel;
        }
        //查找已经穿戴好的皮肤
        Userskin userskin = userskinMapper.selectByUidUseSkin(uid);
        userModel.setUrl(pet.getUrl());
        userModel.setPetname(pet.getPname());
        userModel.setPid(pet.getPid());
        if (userskin == null) {
            userModel.setPid(pet.getPid());
            userModel.setUrl(pet.getUrl());
        } else {

            //根据宠物和皮肤去确定唯一的穿戴
            PetSkin petSkin = petSkinMapper.selectByPidAndSid(pet.getPid(), userskin.getSid());
            userModel.setUrl(petSkin.getUrl());
        }
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(userModel);
        return resultModel;
    }
}
