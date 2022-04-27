package com.wezaam.withdrawal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.wezaam.withdrawal.dto.UserDto;
import com.wezaam.withdrawal.dto.UserDtoList;
import com.wezaam.withdrawal.dto.WithdrawalDto;
import com.wezaam.withdrawal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserControllerTest {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final UUID WITHDRAWAL_ID = UUID.randomUUID();
    public static final String FAKE_CREDIT_CARD = "CREDIT_CARD";
    public static final double FAKE_AMOUNT = 2000.0;
    public static final String FAKE_STATUS = "PENDING";
    public static final String FAKE_FIRST_NAME = "firstName";
    private UserService userService;
    private UserController sut;

    @BeforeEach
    public void setUp() {
        this.userService = Mockito.mock(UserService.class);
        this.sut = new UserController(userService);
    }

    @Test
    void shouldBeReturnUserDtoList() {
        // given
        UserDtoList result = createUserDtoList();
        when(userService.findAll()).thenReturn(result);
        // when
        ResponseEntity<UserDtoList> response = this.sut.users();
        // then
        verify(userService).findAll();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getBody(), result);
    }

    @Test
    void shouldBeReturnUserDto() {
        // given
        UserDto result = createUser();
        when(userService.find(USER_ID)).thenReturn(Optional.of(result) );
        // when
        ResponseEntity<UserDto> response = this.sut.user(USER_ID);
        // then
        verify(userService).find(USER_ID);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getBody(), result);
    }

    @Test
    void shouldBeCreateWithdrawal() {
        // given
        WithdrawalDto dto = createWithdrawal();
        doNothing().when(userService).createWithdrawal(USER_ID, dto);
        // when
        ResponseEntity<Void> response = this.sut.create(USER_ID, dto);
        // then
        verify(userService).createWithdrawal(USER_ID,dto);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }
    private UserDto createUser() {
        return new UserDto.Builder()
                .withId(USER_ID)
                .withFirstName(FAKE_FIRST_NAME)
                .build();
    }
    private List<UserDto> createUsers() {
      return List.of(createUser() );
    }

    private UserDtoList createUserDtoList() {
        return new UserDtoList.Builder()
                              .withUsers(createUsers())
                              .build();
    }

    private WithdrawalDto createWithdrawal() {
        return new WithdrawalDto.Builder()
                .withId(WITHDRAWAL_ID)
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(FAKE_CREDIT_CARD)
                .withAmount(FAKE_AMOUNT)
                .withStatus(FAKE_STATUS)
                .build();
    }

}
