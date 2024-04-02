package com.example.security.repository;

import com.example.security.model.UserAuth;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<UserAuth,Long> {
}
