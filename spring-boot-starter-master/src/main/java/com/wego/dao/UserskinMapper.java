package com.wego.dao;

import com.wego.entity.Skin;
import com.wego.entity.Userfood;
import com.wego.entity.Userskin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserskinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Userskin record);

    Userskin selectByPrimaryKey(Integer id);

    List<Userskin> selectAll();

    int updateByPrimaryKey(Userskin record);

    List<Userskin> selectByUid(Integer uid);

    Userskin selectByUidSid(@Param("uid") Integer uid,@Param("sid") Integer sid);

    /**
     * 根据用户的id去查找穿戴的
     */
    Userskin selectByUidUseSkin(Integer uid);

}