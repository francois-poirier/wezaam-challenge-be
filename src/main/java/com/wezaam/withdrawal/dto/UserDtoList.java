package com.wezaam.withdrawal.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDtoList {

    @NotNull
    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserDtoList)) return false;

        UserDtoList that = (UserDtoList) o;

        return new EqualsBuilder()
                .append(users, that.users)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(users)
                .toHashCode();
    }

    public static final class Builder {

        private final UserDtoList object;

        public Builder() {
            object = new UserDtoList();
        }

        public Builder withUsers(List<UserDto> value) {
            object.users = value;
            return this;
        }

        public UserDtoList build() {
            return object;
        }

    }
}
