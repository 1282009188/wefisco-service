package com.wego.service.impl;


import com.wego.bacService.BACManager;
import com.wego.dao.*;
import com.wego.entity.*;
import com.wego.model.ResultModel;
import com.wego.service.SkinServer;
import com.wego.service.UserServer;
import org.fisco.bcos.BAC001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service("SkinServer")
public class SkinServerImpl implements SkinServer {
    @Autowired
    UserskinMapper userskinMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    SkinMapper skinMapper;

    @Autowired
    UserServer userServer;

    @Override
    public ResultModel<Skin> showSkin(int uid) {
        return null;
    }

    @Override
    public ResultModel useSkin(int uid, int pid, int sid) {
        ResultModel resultModel = new ResultModel();
        Userskin userskin1 = userskinMapper.selectByUidUseSkin(uid);
        if (userskin1 != null) {
            userskin1.setState(0);
            userskinMapper.updateByPrimaryKey(userskin1);
        }
        Userskin userskin2 = userskinMapper.selectByUidSid(uid, sid);
        userskin2.setState(1);
        userskinMapper.updateByPrimaryKey(userskin2);

        return resultModel;
    }

    @Override
    public ResultModel buySkin(int uid, int sid) {
        ResultModel resultModel = new ResultModel();
        User user = userMapper.selectByPrimaryKey(uid);
        Skin skin = skinMapper.selectByPrimaryKey(sid);

        //1.查是否已有该皮肤
        Userskin userskin = userskinMapper.selectByUidSid(uid, sid);
        if (userskin != null) {
            resultModel.setCode(1);
            resultModel.setMessage("不能重复购买");
            return resultModel;
        }
        //2.扣钱
        resultModel = userServer.transfer(user.getName(), "wego", skin.getBean());

        //3. 加皮肤
        Userskin us = new Userskin();
        us.setUid(uid);
        us.setSid(sid);
        us.setState(0);
        userskinMapper.insert(us);

        return resultModel;
    }


}
