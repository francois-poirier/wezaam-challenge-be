package com.wezaam.withdrawal.controller;

import com.wezaam.withdrawal.dto.WithdrawalDto;
import com.wezaam.withdrawal.dto.WithdrawalDtoList;
import com.wezaam.withdrawal.service.WithdrawalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawalControllerTest {

    public static final UUID FAKE_ID = UUID.randomUUID();
    public static final String FAKE_CREDIT_CARD = "CREDIT_CARD";
    public static final double FAKE_AMOUNT = 2000.0;
    public static final String FAKE_STATUS = "PENDING";
    private WithdrawalService withdrawalService;
    private WithdrawalController sut;

    @BeforeEach
    public void setUp() {
        this.withdrawalService = Mockito.mock(WithdrawalService.class);
        this.sut = new WithdrawalController(withdrawalService);
    }

    @Test
    void shouldBeReturnWithdrawalDtoList() {
        // given
        WithdrawalDtoList result = createWithdrawalDtoList();
        when(withdrawalService.findAll()).thenReturn(result);
        // when
        ResponseEntity<WithdrawalDtoList> response = this.sut.withdrawals();
        // then
        verify(withdrawalService).findAll();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getBody(), result);
    }

    private WithdrawalDto createWithdrawal() {
        return new WithdrawalDto.Builder()
                .withId(FAKE_ID)
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(FAKE_CREDIT_CARD)
                .withAmount(FAKE_AMOUNT)
                .withStatus(FAKE_STATUS)
                .build();
    }
    private List<WithdrawalDto> createWithdrawals() {
        return List.of(createWithdrawal() );
    }

    private WithdrawalDtoList createWithdrawalDtoList() {
        return new WithdrawalDtoList.Builder()
                .withWithdrawals(createWithdrawals())
                .build();
    }

}
