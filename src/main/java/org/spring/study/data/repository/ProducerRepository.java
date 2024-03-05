package org.spring.study.data.repository;

import org.spring.study.data.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
