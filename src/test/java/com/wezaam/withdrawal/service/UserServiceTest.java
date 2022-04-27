package com.wezaam.withdrawal.service;

import com.wezaam.withdrawal.dto.UserDto;
import com.wezaam.withdrawal.dto.UserDtoList;
import com.wezaam.withdrawal.dto.WithdrawalDto;
import com.wezaam.withdrawal.exception.UserNotFoundException;
import com.wezaam.withdrawal.model.PaymentMethod;
import com.wezaam.withdrawal.model.User;
import com.wezaam.withdrawal.model.Withdrawal;
import com.wezaam.withdrawal.model.WithdrawalStatus;
import com.wezaam.withdrawal.repository.UserRepository;
import com.wezaam.withdrawal.stream.producer.WithdrawalProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    public static final UUID USER_ID = UUID.randomUUID();

    public static final UUID USER_NOT_FOUND_ID = UUID.randomUUID();
    public static final UUID WITHDRAWAL_ID = UUID.randomUUID();
    public static final String FAKE_CREDIT_CARD = "CREDIT_CARD";
    public static final double FAKE_AMOUNT = 2000.0;
    public static final String FAKE_STATUS = "PENDING";
    public static final String FAKE_FIRST_NAME = "firstName";
    private UserRepository userRepository;
    private WithdrawalProducer withdrawalProducer;
    private UserService sut;

    @BeforeEach
    public void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.withdrawalProducer = Mockito.mock(WithdrawalProducer.class);
        this.sut = new UserService(userRepository, withdrawalProducer);
    }

    @Test
    void shouldBeReturnUserDtoList() {
        // given
        List<User> result = createUsers();
        when(userRepository.findUsers()).thenReturn(result);
        // when
        UserDtoList response = this.sut.findAll();
        //then
        verify(userRepository).findUsers();
        assertNotNull(response);
    }

    @Test
    void shouldBeReturnUserDto() {
        // given
        User user = createUser();
        when(userRepository.findByPk(USER_ID)).thenReturn(Optional.of(user));
        // when
        Optional<UserDto> response = this.sut.find(USER_ID);
        // then
        verify(userRepository).findByPk(USER_ID);
        assertTrue(response.isPresent());
        assertNotNull(response.get());
    }

    @Test
    void shouldBeCreateWithdrawal() {
        // given
        User user = createUser();
        WithdrawalDto withdrawalDto = createWithdrawalDto();
        Withdrawal withdrawal = createWithdrawal();
        when(userRepository.findByPk(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(withdrawalProducer).send(UserService.BINDING_NAME,withdrawal);
        // when
        this.sut.createWithdrawal(USER_ID,withdrawalDto);
        // then
        verify(userRepository).findByPk(USER_ID);
        verify(userRepository).save(user);
    }
    @Test
    void shouldNotBeCreateWithdrawal() {

        // given
        WithdrawalDto withdrawalDto = createWithdrawalDto();
        when(userRepository.findById(USER_NOT_FOUND_ID)).thenReturn(Optional.empty());
        // when
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> { this.sut.createWithdrawal(USER_NOT_FOUND_ID,withdrawalDto); }
        );
    }

    private User createUser() {
        return new User.Builder()
                .withId(USER_ID)
                .withFirstName(FAKE_FIRST_NAME)
                .build();
    }
    private List<User> createUsers() {
        return List.of(createUser() );
    }

    private WithdrawalDto createWithdrawalDto() {
        return new WithdrawalDto.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(FAKE_CREDIT_CARD)
                .withAmount(FAKE_AMOUNT)
                .withStatus(FAKE_STATUS)
                .build();
    }

    private Withdrawal createWithdrawal() {
        return new Withdrawal.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(PaymentMethod.CREDIT_CARD)
                .withAmount(FAKE_AMOUNT)
                .withStatus(WithdrawalStatus.PENDING)
                .build();
    }
}
