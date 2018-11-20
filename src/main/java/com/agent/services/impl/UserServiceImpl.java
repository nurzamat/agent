package com.agent.services.impl;

import com.agent.entities.User;
import com.agent.repositories.UserRepository;
import com.agent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> listAllByPage(Pageable pageable, boolean isExist) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> listAgents() {
        return userRepository.findByIsAdmin(false);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public User saveUser(User user) {
        if(user.getId() == null)
           user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(user.getRole().getName().equals("ROLE_ADMIN"))
            user.setAdmin(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }


    @Override
    public boolean checkUserForUsername(User user)
    {
        User u = userRepository.findByUserName(user.getUserName());
        if(u != null)
        {
            //check for edit mode
            if(u.getId().equals(user.getId()))
            {
                return false;
            }
            return true;
        }

        return false;
    }
}
