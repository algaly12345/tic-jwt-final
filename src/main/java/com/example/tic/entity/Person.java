package com.example.tic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_access")
    private String mobileAccess;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "national_id", length = 30)
    private String nationalId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "user_id")
    private String userId;   // ✅ خليها String عشان userId عندك UUID String في JWT

    @Column(name = "user_name")
    private String userName;

    @Column(name = "exit_time")
    private Instant exitTime;

    @Column(name = "return_time")
    private Instant returnTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PersonStatus status = PersonStatus.INSIDE;

    // ===== getters/setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMobileAccess() { return mobileAccess; }
    public void setMobileAccess(String mobileAccess) { this.mobileAccess = mobileAccess; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Instant getExitTime() { return exitTime; }
    public void setExitTime(Instant exitTime) { this.exitTime = exitTime; }

    public Instant getReturnTime() { return returnTime; }
    public void setReturnTime(Instant returnTime) { this.returnTime = returnTime; }


    public PersonStatus getStatus() { return status; }
    public void setStatus(PersonStatus status) { this.status = status; }
}
