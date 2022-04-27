package com.wezaam.withdrawal.stream.consumer;

import com.wezaam.withdrawal.event.WithdrawalEvent;
import com.wezaam.withdrawal.model.PaymentMethod;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.model.WithdrawalStatus;
import com.wezaam.withdrawal.repository.WithdrawalRepository;
import com.wezaam.withdrawal.stream.producer.WithdrawalProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WithdrawalPendingConsumerTest {

    public static final UUID WITHDRAWAL_ID = UUID.randomUUID();
    public static final UUID WITHDRAWAL_NOT_FOUND_ID = UUID.randomUUID();
    public static final String PAYMENT_METHOD = PaymentMethod.CREDIT_CARD.toString();
    public static final String PENDING_STATUS = WithdrawalStatus.PENDING.toString();
    private WithdrawalProducer withdrawalProducer;
    private WithdrawalRepository withdrawalRepository;
    private WithdrawalPendingConsumer sut;

    @BeforeEach
    public void setUp() {
        this.withdrawalProducer = Mockito.mock(WithdrawalProducer.class);
        this.withdrawalRepository = Mockito.mock(WithdrawalRepository.class);
        this.sut = new WithdrawalPendingConsumer(withdrawalProducer,withdrawalRepository);
    }

    @Test
    public void shouldBeAccept() {
        // given
        WithdrawalEvent event = createWithdrawalEvent();
        Withdrawal withdrawal = createWithdrawal();
        when(withdrawalRepository.findById(WITHDRAWAL_ID)).thenReturn(Optional.of(withdrawal));
        doNothing().when(withdrawalProducer).send(anyString(), any());
        // when
        this.sut.accept(event);
        // then
        verify(withdrawalRepository).findById(WITHDRAWAL_ID);
    }

    @Test
    public void shouldBeNotAccept() {
        // given
        WithdrawalEvent event = createWithdrawalEventNotFound();
        Withdrawal withdrawal = createWithdrawalNotFound();
        when(withdrawalRepository.findById(WITHDRAWAL_NOT_FOUND_ID)).thenReturn(Optional.empty());
        // when
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> { this.sut.accept(event);; }
        );
    }

    @Test
    public void shouldBeAmountGreaterThanMax() {
        // given
        WithdrawalEvent event = createWithdrawalEventMax();

        Withdrawal withdrawal = createWithdrawal();
        when(withdrawalRepository.findById(WITHDRAWAL_ID)).thenReturn(Optional.of(withdrawal));
        // when
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> { this.sut.accept(event);; }
        );
    }

    private Withdrawal createWithdrawal() {
        return new Withdrawal.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt( LocalDate.now())
                .withAmount(1000.0)
                .withPaymentMethod(PaymentMethod.CREDIT_CARD)
                .withStatus(WithdrawalStatus.PENDING)
                .build();
    }
    private WithdrawalEvent createWithdrawalEvent() {
        return new WithdrawalEvent.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt( LocalDate.now())
                .withAmount(1000.0)
                .withPaymentMethod(PAYMENT_METHOD)
                .withStatus(PENDING_STATUS)
                .build();
    }

    private Withdrawal createWithdrawalNotFound() {
        return new Withdrawal.Builder()
                .withId(WITHDRAWAL_NOT_FOUND_ID)
                .withCreateAt( LocalDate.now())
                .withAmount(1000.0)
                .withPaymentMethod(PaymentMethod.CREDIT_CARD)
                .withStatus(WithdrawalStatus.PENDING)
                .build();
    }
    private WithdrawalEvent createWithdrawalEventNotFound() {
        return new WithdrawalEvent.Builder()
                .withId(WITHDRAWAL_NOT_FOUND_ID)
                .withCreateAt( LocalDate.now())
                .withAmount(1000.0)
                .withPaymentMethod(PAYMENT_METHOD)
                .withStatus(PENDING_STATUS)
                .build();
    }

    private WithdrawalEvent createWithdrawalEventMax() {
        return new WithdrawalEvent.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt( LocalDate.now())
                .withAmount(100000.0)
                .withPaymentMethod(PAYMENT_METHOD)
                .withStatus(PENDING_STATUS)
                .build();
    }
}
