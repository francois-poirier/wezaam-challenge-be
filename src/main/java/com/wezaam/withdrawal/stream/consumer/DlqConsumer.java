package com.wezaam.withdrawal.stream.consumer;

import com.wezaam.withdrawal.event.WithdrawalEvent;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.model.WithdrawalStatus;
import com.wezaam.withdrawal.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Component("dlqConsumer")
@Transactional
public class DlqConsumer implements Consumer<WithdrawalEvent>  {

    private final WithdrawalRepository withdrawalRepository;

    @Autowired
    public DlqConsumer(final WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    @Override
    /**
     * this is a third party dlq service simulation
     */
    public void accept(WithdrawalEvent withdrawalEvent) {
      Withdrawal withdrawal = withdrawalRepository.findById(withdrawalEvent.getId()).orElseThrow(() ->new IllegalStateException("Cannot find Withdrawal with id " + withdrawalEvent.getId()));
      withdrawal.setStatus(WithdrawalStatus.CANCELED);
      withdrawalRepository.save(withdrawal);
    }
}
