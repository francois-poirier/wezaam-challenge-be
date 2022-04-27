package com.wezaam.withdrawal.stream.producer;

import com.wezaam.withdrawal.model.Withdrawal;

public interface WithdrawalProducer {

    public void send(String bindingName, Withdrawal withdrawalDto);
}
