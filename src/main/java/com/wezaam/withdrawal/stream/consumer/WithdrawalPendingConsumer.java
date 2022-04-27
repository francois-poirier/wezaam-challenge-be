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

@Component("withdrawalPendingConsumer")
@Transactional
public class WithdrawalPendingConsumer implements Consumer<WithdrawalEvent> {

    private static final Double MAX_ACCOUNT= 10000.0;
    public static final String BINDING_NAME = "withdrawalProcessingConsumer-out-0";

    private final WithdrawalProducer withdrawalProducer;
    private final WithdrawalRepository withdrawalRepository;

    @Autowired
    public WithdrawalPendingConsumer(final WithdrawalProducer withdrawalProducer, final WithdrawalRepository withdrawalRepository) {
        this.withdrawalProducer = withdrawalProducer;
        this.withdrawalRepository = withdrawalRepository;
    }

    /**
     * checking if withdrawal amount it's > 10000.
     * this is a third party account service simulation
     */
    @Override
    public void accept(WithdrawalEvent withdrawalEvent) {

        if (WithdrawalStatus.PENDING.toString().equals(withdrawalEvent.getStatus())) {
            Withdrawal withdrawal = withdrawalRepository.findById(withdrawalEvent.getId()).orElseThrow(() ->new IllegalStateException("Cannot find Withdrawal with id " + withdrawalEvent.getId()));
            if (MAX_ACCOUNT < withdrawalEvent.getAmount()) {
                throw new IllegalStateException("Amount greater than allowed " + MAX_ACCOUNT);
            }
            withdrawal.setStatus(WithdrawalStatus.PROCESSING);
            withdrawalRepository.save(withdrawal);
            withdrawalProducer.send(BINDING_NAME, withdrawal);
        }
    }
}
