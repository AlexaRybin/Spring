package springboot.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springboot.model.User;
import springboot.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userServiceImp;

    public AdminController( UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public String index(ModelMap model, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        model.addAttribute("userNow", user);
        model.addAttribute("users", userServiceImp.index());
        model.addAttribute("newUser", new User());
        model.addAttribute("str", new String());
        model.addAttribute("roleList", userServiceImp.getAllRole());
        return "/admin/index";
    }
}
