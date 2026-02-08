package com.example.tic.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "national_id", length = 20, unique = true)
    private String nationalId;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "email", nullable = false, length = 200, unique = true)
    private String email;

    @ColumnDefault("1")
    @Column(name = "is_active", nullable = false)
    private Integer isActive;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "signature_image", length = 255)
    private String signatureImage;

    public UserId getId() { return id; }
    public void setId(UserId id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getSignatureImage() { return signatureImage; }
    public void setSignatureImage(String signatureImage) { this.signatureImage = signatureImage; }
}
