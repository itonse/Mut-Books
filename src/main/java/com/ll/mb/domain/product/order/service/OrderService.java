package com.ll.mb.domain.product.order.service;

import com.ll.mb.domain.member.member.entity.Member;
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

    @Transactional
    public Order createFromCart(Member buyer) {
        // 입력된 회원의 장바구니 아이템들을 전부 가져온다.
        List<CartItem> cartItems = cartService.findItemsByBuyer(buyer);

        return create(buyer, cartItems);
    }

    @Transactional
    public Order create(Member buyer, List<CartItem> cartItems) {
        Order order = Order.builder()
                .buyer(buyer)
                .build();

        cartItems.stream()
                .forEach(cartItem -> order.addItem(cartItem));

       orderRepository.save(order);

       // 장바구니의 아이템들을 삭제
       cartItems.stream()
               .forEach(cartService::delete);

       return order;
    }
}
