package com.loyalove.baseboot.dao.base;

import com.loyalove.baseboot.pojo.UserExample;
import com.loyalove.baseboot.pojo.UserPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * 该接口（类）由系统生成，请勿修改
 *
 生成时间 2016/12/02
 */
@Mapper
@Repository
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    List<UserPO> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    List<UserPO> selectByExample(UserExample example);

    UserPO selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserExample example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);
}