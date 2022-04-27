package com.wezaam.withdrawal.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "users", schema = "withdrawals")
public class User {
    @Id
    private UUID id;
    @Column(name = "first_name")
    private String firstName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Withdrawal> withdrawals = new HashSet<>();
    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Set<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void addWithdrawal(Withdrawal withdrawal) {
        withdrawals.add(withdrawal);
        withdrawal.setUser(this);
    }

    public void removeWithdrawal(Withdrawal withdrawal) {
        withdrawals.remove(withdrawal);
        withdrawal.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User that = (User) o;

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

        private final User object;

        public Builder() {
            object = new User();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withFirstName(String value) {
            object.firstName = value;
            return this;
        }

        public Builder withWithdrawals(Set<Withdrawal> value) {
            object.withdrawals = value;
            return this;
        }


        public User build() {
            return object;
        }

    }

}
