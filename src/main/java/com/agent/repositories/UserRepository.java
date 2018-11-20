package com.agent.repositories;

import com.agent.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUserName(String userName);

    Iterable<User> findByIsAdmin(boolean isAdmin);
}
