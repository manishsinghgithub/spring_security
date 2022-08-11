package com.app.my_security.repository;

import com.app.my_security.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserApplicationRepository extends JpaRepository<AppUsers, String> {

    public AppUsers findByEmail(String email);
}
