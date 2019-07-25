package org.fisco.bcos.dao;

import org.apache.ibatis.annotations.Mapper;
import org.fisco.bcos.model.WegoUser;

/**
 * @author elizayuan
 * 用于对用户数据表的操作
 */
@Mapper
public interface userDao {
     // 查询所有用户
     WegoUser getALLUser();
}
