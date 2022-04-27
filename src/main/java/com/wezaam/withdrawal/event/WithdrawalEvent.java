package com.wezaam.withdrawal.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class WithdrawalEvent {
    @NotNull
    private UUID id;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDate createdAt;

    @NotNull
    private String paymentMethod;

    @NotNull
    private String status;

    public UUID getId() {return id;}

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

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof WithdrawalEvent)) return false;

        WithdrawalEvent that = (WithdrawalEvent) o;

        return new EqualsBuilder()
                .append(id,that.id)
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

        private final WithdrawalEvent object;

        public Builder() {
            object = new WithdrawalEvent();
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

        public WithdrawalEvent build() {
            return object;
        }

    }
}
