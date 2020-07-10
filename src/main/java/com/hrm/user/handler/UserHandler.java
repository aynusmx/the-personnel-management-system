package com.hrm.user.handler;


import com.hrm.commons.beans.User;
import com.hrm.user.service.IUserService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHandler {

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    //HttpSession 不识别，需要再pom.xml文件中引入tomcat的jar包 servlet-api
    public String login(User user, HttpSession session, Model model){
        User login_user = userService.findUserByLoginUser(user);
//        System.out.println(login_user);
        if (login_user != null){
            session.setAttribute("login_user",login_user);
            return "/jsp/main.jsp";
        }else {
            model.addAttribute("login_error","用户名或密码错误，请重新登录");
            return "/index.jsp";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session,Model model){
        session.removeAttribute("loginuser");
        model.addAttribute("login_error","退出成功，请重新登陆！");
        return "/index.jsp";
    }

    //用户查询
    @RequestMapping("/findUser")
    public String findUser(User user,@RequestParam(defaultValue = "1") int pageIndex,Model model){
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);

        int recordCount = userService.finUserCount(user);
        pageModel.setRocordCount(recordCount);
        List<User> users = userService.findUser(user,pageModel);
//        for (User u:users){
//            System.out.println(u);
//        }
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("users",users);
        //将user封装到model里，为了进行用户名搜索时，将用户信息传给分页标签
        model.addAttribute("user",user);
        return "/jsp/user/user.jsp";
    }

    //用户修改
    @RequestMapping("/modifyUser")
    public String modifyUser(User user,Model model,String flag,int pageIndex){
        if(flag == null){
            user = userService.findUserById(user.getId());
            model.addAttribute("user",user);
            model.addAttribute("pageIndex",pageIndex);
            return "/jsp/user/showUpdateUser.jsp";
        }else{
            int rows = userService.modifyUser(user);//rows接收数据库受影响的条目数
            if(rows>0){
                return "redirect:/user/findUser?pageIndex="+pageIndex;
            }else {
                model.addAttribute("fail","用户信息修改失败!");
                return "/jsp/fail.jsp";
            }
        }

    }

    //用户添加
    @RequestMapping("/addUser")
    public String addUser(User user,Model model){

        int rows = userService.addUser(user);
        if(rows>0){
            PageModel pageModel = new PageModel();
            int recordCount = userService.finUserCount(null);
            pageModel.setRocordCount(recordCount);
            pageModel.getTotalSize();
            return "redirect:/user/findUser?pageIndex="+pageModel.getTotalSize();
        }else {
            model.addAttribute("fail","用户信息添加失败！");
            return "/jsp/fail.jsp";
        }
    }

    //用户删除
    @RequestMapping("/removeUser")
    public String removeUser(Integer[] ids,Model model,HttpSession session){
        //获取当前登录用户
        User login_user = (User) session.getAttribute("login_user");
        for (Integer id:ids){
            if(login_user.getId().equals(id)){
                model.addAttribute("fail","不能删除当前登录用户！");
                return "/jsp/fail.jsp";
            }
        }


        try {
            int rows = userService.removeUser(ids);
            if (rows == ids.length){
                return "redirect:/user/findUser";
            }else{
                model.addAttribute("fail","用户信息删除失败！");
                return "/jsp/fail.jsp";
            }
        }catch (DataIntegrityViolationException e){
            model.addAttribute("fail","当前用户有发布公告或文档，请先删除公告或文档！");
            return "/jsp/fail.jsp";
        }

    }
    //用户添加检查
    @RequestMapping("/checkLoginname")
    @ResponseBody
    public String checkLoginname(String loginname){
        //System.out.println(loginname);
        User user = userService.findLoginname(loginname);
        if (user!=null){
            return "EXIST";
        }else{
            return "OK";
        }

    }
}
