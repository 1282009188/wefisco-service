package com.wego.service.impl;

import com.wego.dao.PetMapper;
import com.wego.dao.UserMapper;
import com.wego.dao.UserPetMapper;
import com.wego.entity.Pet;
import com.wego.entity.UserPet;
import com.wego.model.ResultModel;
import com.wego.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("PetServer")
public class PetServiceImpl implements PetService {
    @Autowired
    PetMapper petMapper;
    @Autowired
    UserPetMapper userPetMapper;
    @Autowired
    UserMapper userMapper;

    public ResultModel<HashMap<String, List<Pet>>> showMarketPet() {
        ResultModel<HashMap<String, List<Pet>>> resultModel = new ResultModel<>();
        List<Pet> pets = petMapper.selectAll();
        if (pets == null || pets.size() == 0) {
            resultModel.setCode(1);
            resultModel.setMessage("还没有任何宠物，敬请期待");
            return resultModel;
        }
        resultModel.setCode(0);
        resultModel.setMessage("查询成功");
        HashMap<String, List<Pet>> hashMap = new HashMap<>();
        hashMap.put("petlist", pets);
        resultModel.setData(hashMap);
        return resultModel;

    }

    @Override
    public ResultModel changePet(int uid, int pid, String name) {
        ResultModel resultModel = new ResultModel();
        UserPet up = userPetMapper.selectByUidAndPid(uid, pid);
        up.setPname(name);
        userPetMapper.updateByPrimaryKey(up);
        resultModel.setCode(0);
        resultModel.setMessage("修改成功");
        return resultModel;
    }
}
