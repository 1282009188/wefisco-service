package com.wego.dao;

import com.wego.entity.Skin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkinMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Skin record);

    Skin selectByPrimaryKey(Integer sid);

    List<Skin> selectAll();

    int updateByPrimaryKey(Skin record);
}