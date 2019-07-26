package com.wego.service.impl;


import com.wego.bacService.BACManager;
import com.wego.dao.SkinMapper;
import com.wego.dao.UserMapper;
import com.wego.dao.UserskinMapper;
import com.wego.entity.Skin;
import com.wego.entity.User;
import com.wego.entity.Userskin;
import com.wego.model.ResultModel;
import com.wego.service.SkinServer;
import org.fisco.bcos.BAC001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service("SkinServer")
public class SkinServerImpl implements SkinServer {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SkinMapper skinMapper;

    @Autowired
    UserskinMapper userskinMapper;

    @Override
    public ResultModel<Skin> showSkin(int uid) {
        return null;
    }

    @Override
    public void useSkin(int uid, int pid, int sid) {

    }

    @Override
    public ResultModel buySkin(int uid, int sid) {
        ResultModel resultModel = new ResultModel();
        User user = userMapper.selectByPrimaryKey(uid);
        Skin skin = skinMapper.selectByPrimaryKey(sid);
        //1.查用户余额够不够
        if (user.getBean() < skin.getBean()) {
            resultModel.setCode(1);
            resultModel.setMessage("余额不足");
            return resultModel;
        }
        //2.查是否已有该皮肤
        Userskin userskin = userskinMapper.selectByUidSid(uid, sid);
        if (userskin != null) {
            resultModel.setCode(1);
            resultModel.setMessage("不能重复购买");
            return resultModel;
        }
        //3.扣积分，加皮肤
        user.setBean(user.getBean() - skin.getBean());
        userMapper.updateByPrimaryKey(user);
        Userskin us = new Userskin();
        us.setUid(uid);
        us.setSid(sid);
        us.setState(0);
        userskinMapper.insert(us);
        //4.积分转入WeGo账户
        BAC001 bac001 = BACManager.getBAC001(user);
        User wego = userMapper.selectByName("wego");
        try {
            bac001.send(wego.getAddr(), BigInteger.valueOf(skin.getBean()), "交易健康豆").send();
            wego.setBean(wego.getBean() + skin.getBean());
            userMapper.updateByPrimaryKey(wego);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultModel.setCode(0);
        resultModel.setMessage("购买成功");
        return resultModel;
    }
}
