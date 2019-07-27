package com.wego.dao;


import com.wego.entity.UserPet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPetMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(UserPet record);

    UserPet selectByPrimaryKey(Integer pid);

    List<UserPet> selectAll();

    int updateByPrimaryKey(UserPet record);

    /**
     * 根据宠物的id去修改卡路里
     * @param fid
     * @return
     */
    int updateColByPrimaryKey(Integer fid,Integer col);

    /**
     * 根据用户的id去查找宠物信息
     * @param uid
     * @return
     */
    UserPet selectByUid(Integer uid);
}