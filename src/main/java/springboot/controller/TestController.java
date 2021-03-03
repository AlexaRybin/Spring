package springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getList(){
        return ResponseEntity.ok(userService.index());
    }

    @GetMapping("/now")
    public ResponseEntity<User> authUser(Authentication authentication){
        return ResponseEntity.ok((User) authentication.getPrincipal());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<User> saveNewUser(User newUser){
        return ResponseEntity.ok(userService.save(newUser));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/edit")
    public ResponseEntity<User> editUser(User user){
        return ResponseEntity.ok(userService.update(user.getId(), user));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/del")
    public void delUser(Long id){
        userService.delete(id);
    }

}
