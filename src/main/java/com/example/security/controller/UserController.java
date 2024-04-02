package com.example.security.controller;

import com.example.security.model.UserAuth;
import com.example.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    IUserService _iUserService;
    @GetMapping("/{id}")
    public ResponseEntity<UserAuth> GetUserById(@PathVariable Long id){
        return new ResponseEntity<UserAuth>(_iUserService.GetById(id), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserAuth>> GetAllUser(){
        return  new ResponseEntity<List<UserAuth>>(_iUserService.GetAll(),HttpStatus.OK);
    }

    @GetMapping("/index")
    public String Index(){
        return  "Hola mundo";
    }

    @PostMapping("/create")
    public ResponseEntity<UserAuth> PostUser(@RequestBody UserAuth userAuth){
        return new ResponseEntity<UserAuth>(_iUserService.Save(userAuth),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeleteUser(@PathVariable Long id){
        return new ResponseEntity<Boolean>(_iUserService.DeleteById(id),HttpStatus.NO_CONTENT);
    }


}
