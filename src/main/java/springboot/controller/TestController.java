package springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springboot.model.User;
import springboot.service.UserService;

import java.util.List;

@RequestMapping("/users")
@RestController
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getList(){
        List<User> arr = userService.index();
        return arr;
    }

    @GetMapping("/now")
    public User authUser(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public User saveNewUser(User newUser){
        return userService.save(newUser);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/edit")
    public  User editUser(User user){
        return userService.update(user.getId(), user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/del")
    public void delUser(Long id){
        userService.delete(id);
    }

}
