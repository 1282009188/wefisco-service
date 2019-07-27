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
import com.wego.service.UserServer;
import org.fisco.bcos.BAC001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Service("SkinServer")
public class SkinServerImpl implements SkinServer {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SkinMapper skinMapper;

    @Autowired
    UserskinMapper userskinMapper;

    @Autowired
    UserServer userServer;

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

    public ResultModel<HashMap<String, List<Skin>>> marketSkin(){
        ResultModel<HashMap<String, List<Skin>>> resultModel=new ResultModel<>();
        List<Skin> skins=skinMapper.selectAll();
        if (skins==null||skins.size()==0){
            resultModel.setCode(1);
            resultModel.setMessage("还没有任何皮肤，敬请期待");
            return resultModel;
        }
        HashMap<String, List<Skin>> hashMap=new HashMap<>();
        hashMap.put("skinlist",skins);
        resultModel.setData(hashMap);
        return resultModel;
    }
}
