package com.ll.mb.global.initData;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.book.book.service.BookService;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.member.member.service.MemberService;
import com.ll.mb.domain.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;
    private final MemberService memberService;
    private final BookService bookService;
    private final ProductService productService;

    @Bean
    ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByUsername("admin").isPresent()) return;

        // 여기서 memberUserN 은 저자이다.
        Member memberAdmin = memberService.join("admin", "1234").getData();
        Member memberUser1 = memberService.join("user1", "1234").getData();
        Member memberUser2 = memberService.join("user2", "1234").getData();
        Member memberUser3 = memberService.join("user3", "1234").getData();

        Book book1 = bookService.createBook(memberUser1, "책 제목 1", "책 내용 1", 10000);
        Book book2 = bookService.createBook(memberUser2, "책 제목 2", "책 내용 2", 10000);
        Book book3 = bookService.createBook(memberUser2, "책 제목 3", "책 내용 3", 10000);
        Book book4 = bookService.createBook(memberUser3, "책 제목 4", "책 내용 4", 10000);
        Book book5 = bookService.createBook(memberUser3, "책 제목 5", "책 내용 5", 10000);
        Book book6 = bookService.createBook(memberUser3, "책 제목 6", "책 내용 6", 10000);

        productService.createProduct(book3);
        productService.createProduct(book4);
        productService.createProduct(book5);
    }
}
