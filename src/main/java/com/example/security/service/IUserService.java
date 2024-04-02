package com.example.security.service;

import com.example.security.model.UserAuth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    UserAuth GetById(Long id);
    List<UserAuth> GetAll();
    UserAuth Save(UserAuth userAuth);
    boolean DeleteById(Long id);
}
