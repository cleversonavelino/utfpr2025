package br.edu.utfpr.exemplo.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.utfpr.exemplo.model.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private List<UserVO> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<UserVO> save(@RequestBody UserVO user) {
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public UserVO update(@PathVariable("id") Long id, @RequestBody UserVO user) {

        return user;
    }

    @GetMapping("/{id}")
    public UserVO findById(@PathVariable("id") Long id) {
        return users.stream().
                filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> list() {
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @param user
     * @return
     */
    @DeleteMapping("/{id}")
    public UserVO delete(@PathVariable("id") Long id, @RequestBody UserVO user) {
        return user;
    }

}
