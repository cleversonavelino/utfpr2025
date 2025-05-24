package br.edu.utfpr.exemplo.controller;

import br.edu.utfpr.exemplo.model.User;
import br.edu.utfpr.exemplo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.utfpr.exemplo.model.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<UserVO> save(@RequestBody UserVO userVO) {
        User user = modelMapper.map(userVO, User.class);
        userService.save(user);
        userVO.setId(user.getId());
        return new ResponseEntity<>(userVO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVO> update(@PathVariable("id") Long id, @RequestBody UserVO userVO) {
        User user = modelMapper.map(userVO, User.class);
        user.setId(id);
        userService.update(user);
        return new ResponseEntity<>(userVO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserVO findById(@PathVariable("id") Long id) {
        return modelMapper.map(userService.findById(id), UserVO.class);
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> findAll() {
        List<User> users = userService.findAll();
        List<UserVO> userVOs = users.stream().map(user -> modelMapper.map(user, UserVO.class)).
                toList();
        return new ResponseEntity<>(userVOs, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
