package com.ll.mb.domain.product.order.service;

import com.ll.mb.domain.cash.cash.entity.CashLog;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.member.member.service.MemberService;
import com.ll.mb.domain.product.cart.entity.CartItem;
import com.ll.mb.domain.product.cart.service.CartService;
import com.ll.mb.domain.product.order.entity.Order;
import com.ll.mb.domain.product.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {    // 주문 관련
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final MemberService memberService;

    @Transactional
    public Order createFromCart(Member buyer) {
        // 입력된 회원의 장바구니 아이템들을 전부 가져온다.
        List<CartItem> cartItems = cartService.findItemsByBuyer(buyer);

        Order order = Order.builder()
                .buyer(buyer)
                .build();

        cartItems.stream()
                .forEach(order::addItem);

        orderRepository.save(order);

        cartItems.stream()
                .forEach(cartService::delete);

        return order;
    }

    @Transactional
    public void payByCashOnly(Order order) {
        Member buyer = order.getBuyer();
        long restCash = buyer.getRestCash();   // 잔액
        long payPrice = order.calcPayPrice();   // 지불할 금액

        if (payPrice > restCash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        // 고객의 캐시에서 차감
        memberService.addCash(buyer, payPrice * -1, CashLog.EvenType.사용__예치금_주문결제, order);

        payDone(order);   // 주문완료 처리
    }

    private void payDone(Order order) {
        order.setPaymentDone();
    }

    @Transactional
    public void refund(Order order) {
        long payPrice = order.calcPayPrice();   // 환불되는 금액

        memberService.addCash(order.getBuyer(), payPrice, CashLog.EvenType.환불__예치금_주문결제, order);

        order.setCancelDone();    // 환불 할때는 취소도 같이 한다
        order.setRefundDone();
    }
}
