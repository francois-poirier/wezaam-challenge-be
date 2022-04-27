package com.wezaam.withdrawal.stream.consumer;

import com.wezaam.withdrawal.event.WithdrawalEvent;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.model.WithdrawalStatus;
import com.wezaam.withdrawal.repository.WithdrawalRepository;
import com.wezaam.withdrawal.stream.producer.WithdrawalProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Component("withdrawalProcessingConsumer")
@Transactional
public class WithdrawalProcessingConsumer implements Consumer<WithdrawalEvent> {

    public static final String BINDING_NAME = "withdrawalSuccess-out-0";
    private final WithdrawalProducer withdrawalProducer;

    private final WithdrawalRepository withdrawalRepository;

    @Autowired
    public WithdrawalProcessingConsumer(final WithdrawalProducer withdrawalProducer, final WithdrawalRepository withdrawalRepository) {
        this.withdrawalProducer = withdrawalProducer;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Override
    /**
     * this is a third party payment service simulation
     */
    public void accept(WithdrawalEvent withdrawalEvent) {
        if (WithdrawalStatus.PROCESSING.toString().equals(withdrawalEvent.getStatus())) {
            Withdrawal withdrawal = withdrawalRepository.findById(withdrawalEvent.getId()).orElseThrow(() ->new IllegalStateException("Cannot find Withdrawal with id " + withdrawalEvent.getId()));
            withdrawal.setStatus(WithdrawalStatus.SUCCESS);
            withdrawalRepository.save(withdrawal);
            withdrawalProducer.send(BINDING_NAME, withdrawal);
        }
    }
}
