package com.Controller;


import com.Entity.User;
import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component("userAction")
public class UserAction {

    @Autowired
    private UserService userService;


    /**
     * 登录接口 /user_login
     * @param user_name 用户名
     * @param user_password 密码
     * @return 视图
     */
    @RequestMapping("/user_login")
    public String user_login(String user_name, String user_password,Model model){
        if(!userService.LoginCheck(user_name, user_password)){
            model.addAttribute("LoginError","账号或密码错误");
            return "login";
        }
        User u  = userService.LoginType(user_name);
        if("Ordinary".equals(u.getUser_type())){
            return "admin";
        }
        else {
            return "admin";
        }
    }

    /**
     * 普通用户注册接口 /user_regist
     * @param user_name 用户名
     * @param user_password 密码
     * @param email 邮箱
     * @param model 错误信息
     * @return 视图
     */
    @RequestMapping("/user_register")
    public String user_register(String user_name, String user_password, String email,Model model){
        if(userService.Regist_DuplicateChecking(user_name,email)){
            userService.RegistUser(user_name,user_password,email,"Ordinary");
            model.addAttribute("RegisterSuccess","注册成功");
            return "login";
        }else{
            model.addAttribute("RegisterError","用户名或邮箱已经存在");
            return "login";
        }
    }

    /**
     * 管理户注册接口 /admin_register
     * @param user_name 用户名
     * @param user_password 密码
     * @param email 邮箱
     * @param model 错误信息
     * @return 视图
     */
    @RequestMapping("/admin_register")
    public String admin_regist(String user_name, String user_password, String email,Model model){
        if(userService.Regist_DuplicateChecking(user_name,email)){
            userService.RegistUser(user_name,user_password,email,"Administrator");
            return "login";
        }else{
            model.addAttribute("AdminRegisterError","用户名或邮箱已经存在");
            return "register";
        }
    }




    /**
     * 修改用户密码接口  /update_user_password
     * @param user_name 用户名
     * @param user_password 密码
     */
    @RequestMapping("/update_user_password")
    public void Update_user_password(String user_name,String user_password){
        userService.Update_user_password(user_name, user_password);
    }
}
