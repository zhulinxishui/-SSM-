package com.stu.user.service;

import com.stu.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenkexuan on 2017/4/27.
 */

/**
 * edited and modified by dengxionghui on 2018/6/21
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户登陆
     * @param role
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> userLogin(HttpServletRequest request, String role, String username, String password) {
        Map<String,Object> result = new HashMap<String,Object>();
        String adminDisplayName=null;

        if("manager".equals(role)){
            //登陆用户为管理员
            Map<String,Object>data = new HashMap<>();
            data=userDao.adminLogin(username,password);

            if(data!=null && data.get("id")!=null){
                //登陆成功
                request.getSession().setAttribute("adminUserName",data.get("username"));
                request.getSession().setAttribute("adminName",data.get("name"));
                request.getSession().setAttribute("adminId",data.get("id"));
                result.put("code",true);
                result.put("url","/index.jsp");
                return result;
            }else{
                result.put("code",false);
                result.put("msg","管理员用户名或密码错误！");
                return result;
            }
        }
        else{
            //学生登陆
            //从学生表中查找该用户名的学生

            int stuId1= Integer.parseInt(username);
             Map<String,Object> stu = userDao.selectId(stuId1);
 //           Map<String,Object> stu = userDao.stuLogin(username);
            if(stu!=null && stu.get("reset").equals("0")){
                //有该用户，并且还没有重新设置过密码
                if(password.equals("123456")){
                    //登陆成功
                    request.getSession().setAttribute("userName",stu.get("name"));
                    request.getSession().setAttribute("userId",stu.get("id"));
                    request.getSession().setAttribute("defaultPass",true);
                    result.put("code",true);
                    result.put("url","/stuMsg.jsp");
                    return result;
                }else{
                    result.put("code",false);
                    result.put("msg","用户名或密码错误！");
                    return result;
                }
            }else if(stu!=null && stu.get("reset")!=("0")){
                //学生已经重置密码
//                int j = userDao.studentLogin((String) stu.get("name"),password);
                int j = userDao.studentLogin2(stuId1,password);
                if(j>0){
                    //登陆成功
                    result.put("code",true);
                    request.getSession().setAttribute("userName",stu.get("name"));
                    request.getSession().setAttribute("userId",stu.get("id"));
                    result.put("url","/stuMsg.jsp");
                    return result;
                }else{
                    result.put("code",false);
                    result.put("msg","用户名或密码错误！");
                    return result;
                }
            }else if(stu==null){
                //从user表中找
                Map<String,Object> stu1 = userDao.studentLogin1(username,password);
                if(stu1==null) {
                    result.put("code", false);
                    result.put("msg", "用户名或密码错误！");
                    return result;
                }else{
                    int stuId = (Integer)stu1.get("stuId");
                    //查找用户名
                    String name = userDao.getUsername(stuId);
                    result.put("code", true);
                    result.put("url","/stuMsg.jsp");
                    request.getSession().setAttribute("userName",name);
                    request.getSession().setAttribute("userId",stuId);
                    return result;
                }
            }else{
                result.put("code", false);
                result.put("msg", "用户名或密码错误！");
                return result;
            }
        }
    }

    public void UserLogout(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session=request.getSession();

        if(session.getAttribute("userName")!=null)
        {
            session.removeAttribute("userName");
            session.invalidate();
        }
        response.sendRedirect("/login.jsp");
    }

    public void AdminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session=request.getSession();
        if(session.getAttribute("adminName")!=null)
        {
            session.removeAttribute("adminName");
            session.invalidate();
        }
        response.sendRedirect("/login.jsp");
    }

    public void updateAdminPassword(String adminId,String newPasswd)
    {
        userDao.updateAdminPassword(adminId,newPasswd);
    }


    /*王鑫蕊
    * */
    public Map<String,Object> UserSignUp( String username, String password, int stuId)  {
        System.out.println(username+password+"--"+stuId);
        Map<String,Object> result = new HashMap<>();
        String a = stuId+"";
        if(username!=null && password!=null && a.length()==2){
            userDao.stuSignUp(stuId,username, "1");
            userDao.UserSignUp( username, password, stuId);
            result.put("code", true);
            result.put("msg","注册成功");
            result.put("url","/login.jsp");
            return result;
        }
        else{
            result.put("code", false);
            result.put("msg1","注册失败");
            result.put("url","/signup.jsp");
            return result;
    }

    }

   /* public void stuSignUp(int id){
        userDao.stuSignUp(id);
    }
*/

   public boolean upReset(String password,String reset, String username){

       userDao.upReset("123456","0",username);
       Map<String,Object> stu = userDao.stuLogin(username);
       System.out.println(stu.get("reset"));
       if(stu.get("reset").equals("0")){
           return true;
       }else{
           return false;
       }

   }

}
