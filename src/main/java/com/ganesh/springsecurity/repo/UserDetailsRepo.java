package com.ganesh.springsecurity.repo;

import com.ganesh.springsecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User,Long> {

  User  findByUserName(String userName);

}
