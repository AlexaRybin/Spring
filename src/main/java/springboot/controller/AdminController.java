package springboot.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.model.Role;
import springboot.model.User;
import springboot.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

//    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final UserService userServiceImp;

    public AdminController( UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public String index(ModelMap model, Authentication authentication) {
        System.out.println("herata");
        User user = (User) authentication.getPrincipal();
//        String role = user.getRoleStr(user);
//        createFirstUser();
        model.addAttribute("userNow", user);
        model.addAttribute("users", userServiceImp.index());
        model.addAttribute("newUser", new User());
        model.addAttribute("str", new String());
        Set<Role> roleList = new HashSet<>();
        roleList.add(userServiceImp.getRoleFromId(1));
        roleList.add(userServiceImp.getRoleFromId(2));
        model.addAttribute("roleList", roleList);
        return "/admin/index";
    }

    @GetMapping("/user_page")
    public String showActiveUser(ModelMap modelMap, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        modelMap.addAttribute("active", user);
        return "admin/show";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Long id, ModelMap model){
//        model.addAttribute("user", userServiceImp.getUserFromId(id));
//        return "/admin/show";
//    }

    @PostMapping("/add") // was users
    public String create(@ModelAttribute("newUser") User user){
        userServiceImp.save(user);
        return "redirect:/admin";
    }


    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        System.out.println("ne rab");
        userServiceImp.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/del/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println("del");
        userServiceImp.delete(id);
        return "redirect:/admin";
    }

    public void createFirstUser(){
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setLastName("Ltest");
        user.setPassword("test");
        Set<Role> set = new HashSet<>();
        set.add(new Role(1, "ROLE_ADMIN"));
        set.add(new Role(2, "ROLE_USER"));
        user.setRole(set);
        userServiceImp.save(user);
    }
}
