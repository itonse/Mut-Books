package com.ll.mb.domain.cash.cash.service;

import com.ll.mb.domain.cash.cash.entity.CashLog;
import com.ll.mb.domain.cash.cash.repository.CashLogRepository;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.global.jpa.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CashService {
    private final CashLogRepository cashLogRepository;

    @Transactional
    public CashLog addCash(Member member, long price, CashLog.EvenType evenType,
                           BaseEntity relEntity) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .relTypeCode(relEntity.getModelName())
                .relId(relEntity.getId())
                .evenType(evenType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }

}
