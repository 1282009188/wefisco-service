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
}