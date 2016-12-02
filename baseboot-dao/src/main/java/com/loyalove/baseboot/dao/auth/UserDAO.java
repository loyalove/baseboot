package com.loyalove.baseboot.dao.auth;

import com.loyalove.baseboot.common.model.Pager;
import com.loyalove.baseboot.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Title: UserDAO.java
 * Description: UserDAO
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 13:03
 */
@Mapper
@Repository
public interface UserDAO {

    /**
     * 根据用户ID查询对应角色
     * @param userId
     * @return
     */
    Set<String> queryRoleByUserId(Integer userId);

    /**
     * 根据用户ID查询对应权限
     * @param userId
     * @return
     */
    Set<String> queryPermissionsByUserId(Integer userId);

    /**
     * 查询用户列表
     * @param pager
     * @return
     */
    List<UserPO> queryUsers(@Param("pager") Pager pager);
}
