package com.wego.service.impl;

import com.wego.dao.*;
import com.wego.entity.*;
import com.wego.model.ResultModel;
import com.wego.service.UserServer;
import jnr.ffi.annotations.In;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author elizayuan
 * 实现用户的逻辑
 */
@Service("UserServer")
public class UserServerImpl implements UserServer {

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
    PetMapper petMapper;

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

        userMapper.updateByPrimaryKey(user);
        user.setLevel(level);
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
    public ResultModel<List<Food>> showFood(Integer uid) {
        ResultModel<List<Food>> resultModel = new ResultModel<>();
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
        List<Userfood> userfood = userfoodMapper.selectByUid(uid);
        if (userfood == null || userfood.size() == 0) {
            resultModel.setCode(1);
            resultModel.setMessage("无食物");
            resultModel.setData(null);
            return resultModel;
        }
        List<Food> foods = new ArrayList<>();
        for (Userfood food : userfood) {
            Integer fid = food.getFid();
            foods.add(foodMapper.selectByPrimaryKey(fid));

        }
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        resultModel.setData(foods);
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

    public ResultModel useFood(Integer uid, Integer pid, Integer fid){
        ResultModel resultModel=new ResultModel();
        if (uid==null||pid==null||fid==null){
            resultModel.setCode(1);
            resultModel.setMessage("用户id,宠物id，食物id不能为空");
            return resultModel;
        }
        //根据用户id，和食物id去查找用户所属的食物
        Userfood userfood=userfoodMapper.selectByUidAndFid(uid,fid);
        if(userfood==null){
            resultModel.setCode(1);
            resultModel.setMessage("无食物");
            return resultModel;
        }
        if(userfood.getNum()==0){
            resultModel.setCode(1);
            resultModel.setMessage("食物数量为0");
            return resultModel;
        }
        //根据食物id去查找食物的卡路里
        Food food=foodMapper.selectByPrimaryKey(fid);
        int col=food.getCol();
        //修改宠物的col
        Pet pet=petMapper.selectByPrimaryKey(pid);
        if(pet==null){
            resultModel.setCode(1);
            resultModel.setMessage("宠物不存在");
            return resultModel;
        }
        pet.setCol(col+pet.getCol());
        petMapper.updateByPrimaryKey(pet);
        //减少食物的数量
        userfood.setNum(userfood.getNum()-1);
        userfoodMapper.updateByPrimaryKey(userfood);
        //返回成功
        resultModel.setCode(0);
        resultModel.setMessage("喂食成功");
        return resultModel;
    }
}
