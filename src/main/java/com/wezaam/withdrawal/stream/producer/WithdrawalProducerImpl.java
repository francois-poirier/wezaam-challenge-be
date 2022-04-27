package com.wezaam.withdrawal.stream.producer;

import com.wezaam.withdrawal.event.WithdrawalEvent;
import com.wezaam.withdrawal.model.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalProducerImpl implements WithdrawalProducer {

    private final StreamBridge streamBridge;

    @Autowired
    public WithdrawalProducerImpl(final StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void send(String bindingName, Withdrawal withdrawal) {
        streamBridge.send(bindingName, mapToWithdrawalEvent(withdrawal));
    }

    private WithdrawalEvent mapToWithdrawalEvent(Withdrawal withdrawal) {
       return new WithdrawalEvent.Builder()
               .withId(withdrawal.getId())
               .withAmount(withdrawal.getAmount())
               .withCreateAt(withdrawal.getCreatedAt())
               .withPaymentMethod(withdrawal.getPaymentMethod().toString())
               .withStatus(withdrawal.getStatus().toString())
               .build();
    }
}
