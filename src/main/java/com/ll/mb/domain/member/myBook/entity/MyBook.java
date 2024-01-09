package com.ll.mb.domain.member.myBook.entity;

import com.ll.mb.domain.book.book.entity.Book;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class MyBook extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
