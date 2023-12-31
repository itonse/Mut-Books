package com.ll.mb.domain.product.product.entity;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.global.app.AppConfig;
import com.ll.mb.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Product extends BaseEntity {
    @ManyToOne
    private Member maker;    // 상품을 만든 사람
    private String relTypeCode;   // 상품타입: 책
    private long relId;  // 책 3번
    private String name;
    private int price;

    public Book getBook() {    // product에서 relType ID를 기준으로 book을 얻는다
        return AppConfig.getEntityManager().getReference(Book.class, relId);
    }
}
