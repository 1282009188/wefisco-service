package com.wego.dao;

import com.wego.entity.PetSkin;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PetSkinMapper {
    PetSkin selectByPidAndSid(Integer pid,Integer sid);
}
