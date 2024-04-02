package com.example.security.service;

import com.example.security.model.UserAuth;
import com.example.security.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplement implements IUserService{
    @Autowired
    IUserRepository _iUserRepository;
    @Override
    public UserAuth GetById(Long id) {
        return _iUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserAuth> GetAll() {
        List<UserAuth> userAuths = new ArrayList<>();
        _iUserRepository.findAll().forEach(userAuths::add);
        return userAuths;
    }

    @Override
    public UserAuth Save(UserAuth userAuth) {
        return _iUserRepository.save(userAuth);
    }

    @Override
    public boolean DeleteById(Long id) {
        boolean aux = true;
        try{
            _iUserRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            aux = false;
        }
        return aux;
    }
}
