package com.wego.service.impl;


import com.wego.dao.*;
import com.wego.entity.*;
import com.wego.model.ResultModel;
import com.wego.service.SkinService;
import com.wego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Service("SkinServer")
public class SkinServiceImpl implements SkinService {
    @Autowired
    UserskinMapper userskinMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    SkinMapper skinMapper;

    @Autowired
    UserService userService;

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

        resultModel.setMessage("穿戴成功!");
        resultModel.setCode(0);
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
        resultModel = userService.transfer(user.getName(), "wego", skin.getBean());

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
