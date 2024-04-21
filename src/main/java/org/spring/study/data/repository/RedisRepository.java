package org.spring.study.data.repository;

import org.spring.study.data.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<BlackList, String> {

}
