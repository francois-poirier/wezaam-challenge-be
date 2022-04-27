package com.wezaam.withdrawal.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "withdrawals", schema = "withdrawals")
public class Withdrawal {

    @Id
    private UUID id;
    @Column(name = "amount")
    private Double amount;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WithdrawalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public UUID getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public WithdrawalStatus getStatus() {
        return status;
    }

    public void setStatus(WithdrawalStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Withdrawal)) return false;

        Withdrawal that = (Withdrawal) o;

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

        private final Withdrawal object;

        public Builder() {
            object = new Withdrawal();
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

        public Builder withPaymentMethod(PaymentMethod value) {
            object.paymentMethod = value;
            return this;
        }

        public Builder withStatus(WithdrawalStatus value) {
            object.status = value;
            return this;
        }

        public Withdrawal build() {
            return object;
        }
    }
}
