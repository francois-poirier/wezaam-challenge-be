package com.wezaam.withdrawal.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

public class WithdrawalDtoList {

    @NotNull
    private List<WithdrawalDto> withdrawals;

    public List<WithdrawalDto> getWithdrawals() {
        return withdrawals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof WithdrawalDtoList)) return false;

        WithdrawalDtoList that = (WithdrawalDtoList) o;

        return new EqualsBuilder()
                .append(withdrawals, that.withdrawals)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(withdrawals)
                .toHashCode();
    }

    public static final class Builder {

        private final WithdrawalDtoList object;

        public Builder() {
            object = new WithdrawalDtoList();
        }

        public Builder withWithdrawals(List<WithdrawalDto> value) {
            object.withdrawals = value;
            return this;
        }

        public WithdrawalDtoList build() {
            return object;
        }

    }
}
