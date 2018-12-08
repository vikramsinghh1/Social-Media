package io.SampleLogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}