package com.ll.mb.domain.product.order.entity;

import com.ll.mb.domain.global.exception.GlobalException;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.cart.entity.CartItem;
import com.ll.mb.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
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
@Table(name = "order_")
public class Order extends BaseEntity {
    @ManyToOne
    private Member buyer;    // 구매자
    private LocalDateTime payDate;    // 결제일
    private LocalDateTime cancelDate;    // 취소일
    private LocalDateTime refundDate;    // 환불일

    @Builder.Default   // 빌터패턴을 사용할 때 기본값 설정(객체 생성 시 초기화 보장)
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)   // cascade = ALL: Order 엔티티에 변화가 생겼을 때, 그와 관련된 OrderItem 엔티티들에도 동일한 변화가 적용되도록 설정
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addItem(CartItem cartItem) {
        if (buyer.has(cartItem.getProduct())) {
            throw new GlobalException("400-1", "이미 구매한 상품입니다.");
        }

        OrderItem orderItem = OrderItem.builder()
                .order(this)
                .product(cartItem.getProduct())
                .build();



        orderItems.add(orderItem);
    }

    public long calcPayPrice() {    // 지불할 금액
        return orderItems.stream()
                .mapToLong(OrderItem::getPayPrice)
                .sum();
    }

    public void setPaymentDone() {
        payDate = LocalDateTime.now();    // 결제일 지정

        orderItems.stream()
                .forEach(OrderItem::setPaymentDone);
    }

    public void setCancelDone() {
        cancelDate = LocalDateTime.now();    // 결제취소일 지정

        orderItems.stream()
                .forEach(OrderItem::setCancelDone);
    }

    public void setRefundDone() {
        refundDate = LocalDateTime.now();    // 환불일 지정

        orderItems.stream()
                .forEach(OrderItem::setRefundDone);
    }
}
