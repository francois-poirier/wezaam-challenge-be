package com.wezaam.withdrawal.dto;

import com.wezaam.withdrawal.model.User;
import com.wezaam.withdrawal.model.Withdrawal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String firstName;

    private Set<WithdrawalDto> withdrawals = new HashSet<>();

    public UUID getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Set<WithdrawalDto> getWithdrawals() {
        return withdrawals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserDto)) return false;

        UserDto that = (UserDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(firstName, that.firstName)
                .append(withdrawals, that.withdrawals)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(withdrawals)
                .toHashCode();
    }
    public static final class Builder {

        private final UserDto object;

        public Builder() {
            object = new UserDto();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withFirstName(String value) {
            object.firstName = value;
            return this;
        }

        public Builder withWithdrawals(Set<WithdrawalDto> value) {
            object.withdrawals = value;
            return this;
        }

        public UserDto build() {
            return object;
        }

    }
}
