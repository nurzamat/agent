package com.agent.services;

import com.agent.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    Page<User> listAllByPage(Pageable pageable, boolean isExist);

    Iterable<User> listAllUsers();

    Iterable<User> listAgents();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(Integer id);

    boolean checkUserForUsername(User user);
}
