package com.wego.dao;

import com.wego.entity.Userfood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserfoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Userfood record);

    Userfood selectByPrimaryKey(Integer id);

    List<Userfood> selectAll();

    int updateByPrimaryKey(Userfood record);
    List<Userfood> selectByUid(Integer uid);
}