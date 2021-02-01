package springboot.controller;


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

    private final UserService userServiceImp;

    public AdminController(UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

//    @GetMapping(value = "/login")
//    public String getLoginPage() {
//        return "/admin/login";
//    }

    @GetMapping
    public String index(ModelMap model) {
        System.out.println("herata");
        model.addAttribute("users", userServiceImp.index());
        return "/admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("user", userServiceImp.getUserFromId(id));
        return "/admin/show";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model){
        model.addAttribute("user", new User());
        model.addAttribute("role", new String());
        return "/admin/new";
    }

    @PostMapping("/add") // was users
    public String create(@ModelAttribute("user") User user){
        userServiceImp.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        Set<Role> roleList = new HashSet<>();
        roleList.add(userServiceImp.getRoleFromId(1));
        roleList.add(userServiceImp.getRoleFromId(2));
        model.addAttribute("user", userServiceImp.getUserFromId(id));
        model.addAttribute("roleList", roleList);
        return "/admin/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id, @RequestParam(value = "setRoles", required = false) String roles) {
        System.out.println("ne rab");
        userServiceImp.update(id, user, roles);
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
        user.setPassword("test");
        Set<Role> set = new HashSet<>();
        set.add(new Role(1, "ROLE_ADMIN"));
        user.setRole(set);
        userServiceImp.save(user);
    }
}
