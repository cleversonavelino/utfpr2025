package br.edu.utfpr.exemplo.controller;

import br.edu.utfpr.exemplo.exception.BusinessException;
import br.edu.utfpr.exemplo.exception.NotFoundException;
import br.edu.utfpr.exemplo.model.Address;
import br.edu.utfpr.exemplo.model.User;
import br.edu.utfpr.exemplo.model.vo.AddressVO;
import br.edu.utfpr.exemplo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<UserVO> save(@RequestBody UserVO userVO) throws BusinessException {
        User user = modelMapper.map(userVO, User.class);

        if (userVO.getAddresses() == null) {
            BusinessException ex = new BusinessException();
            ex.setMessage("O Usuário precisa ter endereço.");
            ex.setCodeDescription("ADDRESS_REQUIRED");
            throw ex;
        }

        user.setAddresses(userVO.getAddresses().stream().
                map(address -> modelMapper.map(address, Address.class)).
                toList());

        userService.save(user);
        userVO.setId(user.getId());
        return new ResponseEntity<>(userVO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVO> update(@PathVariable("id") Long id, @RequestBody UserVO userVO) throws BusinessException {
        User user = modelMapper.map(userVO, User.class);
        user.setId(id);
        userService.update(user);
        return new ResponseEntity<>(userVO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404",  description = "Not found - The user was not found")
    })
    public ResponseEntity<UserVO> findById(@PathVariable("id") Long id) throws NotFoundException {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException();
        }

        UserVO userVO = modelMapper.map(user, UserVO.class);
        userVO.setAddresses(user.getAddresses().stream().
                map(address -> modelMapper.map(address, AddressVO.class)).
                toList());

        return new ResponseEntity<>(userVO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
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
