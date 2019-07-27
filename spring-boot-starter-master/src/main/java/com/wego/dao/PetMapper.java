package com.wego.dao;

import com.wego.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {

    List<Pet> selectAll();

    Pet selectByPrimaryKey(int pid);

}
