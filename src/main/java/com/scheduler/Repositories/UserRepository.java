package com.scheduler.Repositories;

import com.scheduler.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int ownerId);
}
