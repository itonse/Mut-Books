package com.ll.mb.domain.member.member.entity;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.member.myBook.entity.MyBook;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;
    private long restCash;

    @OneToMany(mappedBy = "owner", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<MyBook> myBooks = new ArrayList<>();

    public void addMyBook(Book book) {
        MyBook myBook = MyBook.builder()
                .owner(this)
                .book(book)
                .build();
        myBooks.add(myBook);
    }

    public void removeMyBook(Book book) {
        myBooks.removeIf(myBook -> myBook.getBook().equals(book));
    }

    public boolean hasBook(Book book) {    // 해당 책을 보유하고 있는지 확인(구체적)
        return myBooks
                .stream()
                .anyMatch(myBook -> myBook.getBook().equals(book));
    }

    public boolean has(Product product) {    // 상품을 보유하고 있는지 확인
        return switch (product.getRelTypeCode()) {
            case "book" -> hasBook(product.getBook());   // 상품이 책인 경우
            default -> false;    // 기본값
        };
    }
}
