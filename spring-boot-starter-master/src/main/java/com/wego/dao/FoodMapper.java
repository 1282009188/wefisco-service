package com.wego.dao;

import com.wego.entity.Food;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodMapper {
    int deleteByPrimaryKey(Integer fid);

    int insert(Food record);

    Food selectByPrimaryKey(Integer fid);

    List<Food> selectAll();

    int updateByPrimaryKey(Food record);
}