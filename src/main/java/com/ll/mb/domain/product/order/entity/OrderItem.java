package com.ll.mb.domain.product.order.entity;

import com.ll.mb.domain.product.product.entity.Product;
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
public class OrderItem extends BaseEntity {    // 주문한 품목
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;

    public long getPayPrice() {
        return product.getPrice();
    }

    public void setPaymentDone() {    // 결제가 완료 되면 나의 책 목록에 책 추가
        AppConfig.getEntityManager().flush();
        switch (product.getRelTypeCode()) {
            case "book" -> order.getBuyer().addMyBook(product.getBook());
        }
    }

    public void setCancelDone() {
    }

    public void setRefundDone() {    // 환불이 완료 되면 나의 책 목록 에서 책 제거
        switch (product.getRelTypeCode()) {
            case "book" -> order.getBuyer().removeMyBook(product.getBook());
        }
    }
}
