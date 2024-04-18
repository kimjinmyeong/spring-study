package org.spring.study.data.repository;

import org.spring.study.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users getByUid(String uid);

}
