package com.wego.dao;

import com.wego.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Pet record);

    Pet selectByPrimaryKey(Integer pid);

    List<Pet> selectAll();

    int updateByPrimaryKey(Pet record);

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
    Pet selectByUid(Integer uid);
}