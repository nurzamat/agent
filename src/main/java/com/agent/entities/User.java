package com.agent.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name="users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Pattern(regexp="^[a-z0-9_-]{3,15}$", message="Неверный формат username")
    @Column(name="username",length=20, nullable=false)
    private String userName;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="fullname", length=100)
    private String fullName;

    @Column(name="phone_number", length=20)
    private String phoneNumber;

    @Column(name="is_admin")
    private boolean isAdmin = false;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<TradePoint> tradePoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public void setAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public Set<TradePoint> getTradePoints() {
        return tradePoints;
    }

    public void setTradePoints(Set<TradePoint> tradePoints) {
        this.tradePoints = tradePoints;
    }

}
