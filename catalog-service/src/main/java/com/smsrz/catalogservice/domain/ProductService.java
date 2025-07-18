package com.smsrz.catalogservice.domain;


import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepo repo;
    private final ApplicationProperties properties;

    public ProductService(ProductRepo repo, ApplicationProperties properties) {
        this.repo = repo;
        this.properties = properties;
    }

    public PagedResult<Product> getproducts(int pageNo){
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo-1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize());
        Page<Product> productPage =  repo.findAll(pageable).map(ProductMapper::toProduct);
        return new PagedResult<>(
                productPage.getContent(),
                productPage.getTotalElements(),
                productPage.getNumber()+1,
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious()
        );
    }
    public Optional<Product> getProductByCode(String code){
        return repo.findByCode(code).map(ProductMapper::toProduct);
    }
}
