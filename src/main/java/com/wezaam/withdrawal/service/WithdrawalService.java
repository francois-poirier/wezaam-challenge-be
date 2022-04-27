package com.wezaam.withdrawal.service;

import com.wezaam.withdrawal.dto.WithdrawalDto;
import com.wezaam.withdrawal.dto.WithdrawalDtoList;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    @Autowired
    public WithdrawalService(final WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    public WithdrawalDtoList findAll(){
        var withdrawalList = withdrawalRepository.findAll();
        return new WithdrawalDtoList.Builder().withWithdrawals(withdrawalList.stream().map(this::mapToDto).collect(Collectors.toList())).build();
    }

    private WithdrawalDto mapToDto(Withdrawal domain){
        return new WithdrawalDto.Builder()
                .withId(domain.getId())
                .withAmount(domain.getAmount())
                .withCreateAt(domain.getCreatedAt())
                .withPaymentMethod(domain.getPaymentMethod().toString())
                .withStatus(domain.getStatus().toString())
                .build();
    }
}
