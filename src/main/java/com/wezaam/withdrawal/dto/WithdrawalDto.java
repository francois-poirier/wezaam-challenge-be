package com.wezaam.withdrawal.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.UUID;

public class WithdrawalDto {

    private UUID id;

    private Double amount;

    private LocalDate createdAt;

    private String paymentMethod;

    private String status;

    public UUID getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof WithdrawalDto)) return false;

        WithdrawalDto that = (WithdrawalDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(amount, that.amount)
                .append(createdAt,that.createdAt)
                .append(paymentMethod,paymentMethod)
                .append(status,status)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(amount)
                .append(createdAt)
                .append(paymentMethod)
                .append(status)
                .toHashCode();
    }
    public static final class Builder {

        private final WithdrawalDto object;

        public Builder() {
            object = new WithdrawalDto();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withAmount(Double value) {
            object.amount = value;
            return this;
        }

        public Builder withCreateAt(LocalDate value) {
            object.createdAt = value;
            return this;
        }

        public Builder withPaymentMethod(String value) {
            object.paymentMethod = value;
            return this;
        }

        public Builder withStatus(String value) {
            object.status = value;
            return this;
        }

        public WithdrawalDto build() {
            return object;
        }

    }
}
