package com.example.tic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserId implements Serializable {

    @Column(name = "user_id", length = 36)
    private String userId;

    public UserId() {}
    public UserId(String userId) { this.userId = userId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId other)) return false;
        return Objects.equals(userId, other.userId);
    }

    @Override public int hashCode() { return Objects.hash(userId); }
}
