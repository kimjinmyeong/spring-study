package org.spring.study.data.repository.support;

import org.spring.study.data.entity.Product;
import org.spring.study.data.entity.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findByName(String name) {
        QProduct product = QProduct.product;

        List<Product> productList = from(product).where(product.name.eq(name)).select(product).fetch();
        return productList;
    }
}
