package com.wezaam.withdrawal.service;

import com.wezaam.withdrawal.dto.WithdrawalDtoList;
import com.wezaam.withdrawal.model.PaymentMethod;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.model.WithdrawalStatus;
import com.wezaam.withdrawal.repository.WithdrawalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawalServiceTest {

    public static final UUID FAKE_ID = UUID.randomUUID();
    public static final PaymentMethod FAKE_CREDIT_CARD = PaymentMethod.CREDIT_CARD;
    public static final double FAKE_AMOUNT = 2000.0;
    public static final WithdrawalStatus FAKE_STATUS = WithdrawalStatus.PROCESSING;
    private WithdrawalRepository withdrawalRepository;
    private WithdrawalService sut;

    @BeforeEach
    public void setUp() {
        this.withdrawalRepository = Mockito.mock(WithdrawalRepository.class);
        this.sut = new WithdrawalService(withdrawalRepository);
    }

    @Test
    void shouldBeReturnWithdrawalDtoList() {
        // given
        List<Withdrawal> result = createWithdrawals();
        when(withdrawalRepository.findAll()).thenReturn(result);
        // when
        WithdrawalDtoList response = this.sut.findAll();
        // then
        verify(withdrawalRepository).findAll();
        assertNotNull(response);
    }

    private Withdrawal createWithdrawal() {
        return new Withdrawal.Builder()
                .withId(FAKE_ID)
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(FAKE_CREDIT_CARD)
                .withAmount(FAKE_AMOUNT)
                .withStatus(FAKE_STATUS)
                .build();
    }
    private List<Withdrawal> createWithdrawals() {
        return List.of(createWithdrawal() );
    }


}
