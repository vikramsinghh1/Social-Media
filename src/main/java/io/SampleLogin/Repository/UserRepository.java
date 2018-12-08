package io.SampleLogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import io.SampleLogin.Model.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}