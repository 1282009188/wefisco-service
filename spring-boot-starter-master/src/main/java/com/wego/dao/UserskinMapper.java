package com.wego.dao;

import com.wego.entity.Skin;
import com.wego.entity.Userfood;
import com.wego.entity.Userskin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserskinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Userskin record);

    Userskin selectByPrimaryKey(Integer id);

    List<Userskin> selectAll();

    int updateByPrimaryKey(Userskin record);

    List<Userskin> selectByUid(Integer uid);
}