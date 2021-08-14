package com.superman.controller;

import com.superman.domain.Role;
import com.superman.domain.User;
import com.superman.service.RoleService;
import com.superman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleSevice;

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) {

        User user = userService.login(username, password);
        if (user != null) {
            // 登陆成功
            session.setAttribute("user", user);
            return "redirect:/index.jsp";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/del/{id}")
    public String delUser(@PathVariable("id") Long id) {
        userService.delUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();

        List<User> userList = userService.list();

        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("user-list");

        return modelAndView;
    }

    @RequestMapping("/saveUI")
    public ModelAndView saveUI() {
        ModelAndView modelAndView = new ModelAndView();

        List<Role> roleList = roleSevice.list();

        modelAndView.addObject("roleList", roleList);
        modelAndView.setViewName("user-add");

        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(User user, Long[] roleIds) {

        userService.save(user, roleIds);

        return "redirect:/user/list";

    }
}
