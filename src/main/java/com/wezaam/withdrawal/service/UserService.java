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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    public static final String BINDING_NAME = "withdrawalPendingConsumer-out-0";
    private final UserRepository userRepository;
    private final WithdrawalProducer withdrawalProducer;

    @Autowired
    public UserService(final UserRepository userRepository, WithdrawalProducer withdrawalProducer) {
        this.userRepository = userRepository;
        this.withdrawalProducer = withdrawalProducer;
    }
    public UserDtoList findAll(){
      var userList = userRepository.findUsers();
      return new UserDtoList.Builder().withUsers(userList.stream().map(this::mapToUserDto).collect(Collectors.toList())).build();
    }

    public Optional<UserDto> find(UUID userUuid) {
        Optional<User> optionalUser = userRepository.findByPk(userUuid);
        return optionalUser.map(user -> Optional.ofNullable(mapToUserDto(user))).orElse(null);
    }

    public void createWithdrawal(UUID userUuid, WithdrawalDto withdrawalDto) {
        Optional<User> optionalUser = userRepository.findByPk(userUuid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Withdrawal withdrawal = mapToWithdrawal(withdrawalDto, WithdrawalStatus.PENDING);
            user.addWithdrawal(withdrawal);
            userRepository.save(user);
            withdrawalProducer.send(BINDING_NAME, withdrawal);
        }
        else throw new UserNotFoundException("User does not exists", userUuid.toString());
    }
    private UserDto mapToUserDto(User domain){
        return new UserDto.Builder()
                .withId(domain.getId())
                .withFirstName(domain.getFirstName())
                .withWithdrawals(new HashSet<>(domain.getWithdrawals().stream().map(this::mapToWithdrawalDto).collect(Collectors.toList())))
                .build();
    }

    private WithdrawalDto mapToWithdrawalDto(Withdrawal domain) {
        return  new WithdrawalDto.Builder()
                .withId(domain.getId())
                .withAmount(domain.getAmount())
                .withCreateAt(domain.getCreatedAt())
                .withPaymentMethod(domain.getPaymentMethod().toString())
                .withStatus(domain.getStatus().toString())
                .build();
    }

    private Withdrawal mapToWithdrawal(WithdrawalDto withdrawalDto, WithdrawalStatus withdrawalStatus) {
        return new Withdrawal.Builder()
                .withId(UUID.randomUUID())
                .withAmount(withdrawalDto.getAmount())
                .withCreateAt(LocalDate.now())
                .withPaymentMethod(PaymentMethod.valueOf(withdrawalDto.getPaymentMethod()))
                .withStatus(withdrawalStatus)
                .build();
    }
}
