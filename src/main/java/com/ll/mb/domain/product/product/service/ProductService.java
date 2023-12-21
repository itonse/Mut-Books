package com.ll.mb.domain.product.product.service;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Book book) {
        Product product = Product.builder()
                .maker(book.getAuthor())
                .relTypeCode(book.getModelName())
                .relId(book.getId())
                .name(book.getTitle())
                .price(book.getPrice())
                .price(book.getPrice())
                .build();

        productRepository.save(product);

        return product;
    }
}
