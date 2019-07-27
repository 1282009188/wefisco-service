package com.wego.dao;

import com.wego.entity.PetSkin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetSkinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PetSkin record);

    PetSkin selectByPrimaryKey(Integer id);

    List<PetSkin> selectAll();

    int updateByPrimaryKey(PetSkin record);

    PetSkin selectByPidAndSid(@Param("pid") Integer pid, @Param("sid") Integer sid);

    PetSkin selectByPid(int pid);
}