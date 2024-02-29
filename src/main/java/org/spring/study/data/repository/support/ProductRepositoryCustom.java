package org.spring.study.data.repository.support;

import org.spring.study.data.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findByName(String name);

}
