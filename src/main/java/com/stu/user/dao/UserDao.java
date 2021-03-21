package com.stu.user.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by chenkexuan on 2017/4/27.
 */

/**
 * edited and modified by dengxionghui on 2018/6/21
 */
@Repository
public interface UserDao {

    Map<String,Object> adminLogin(String username, String password);

    String getAdminName(String username); //根据登录名返回用户名

    Map<String,Object> stuLogin(String username);

    int studentLogin(String username, String password);

    Map<String,Object> studentLogin1(String username, String password);

    String getUsername(int stuId);

    int UserSignUp(String username, String password, int stuId);

    void updateAdminPassword(String adminId, String newPassword);

    int stuSignUp(int id);
}
