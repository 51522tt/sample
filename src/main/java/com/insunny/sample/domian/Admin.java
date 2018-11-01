package com.insunny.sample.domian;


import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private long total;


    public Integer getAccounts() {
        return accounts;
    }

    public void setAccounts(Integer accounts) {
        this.accounts = accounts;
    }

    public Integer getSpeciesNumber() {
        return speciesNumber;
    }

    public void setSpeciesNumber(Integer speciesNumber) {
        this.speciesNumber = speciesNumber;
    }

    private Integer accounts;
    private Integer speciesNumber;
    private Integer level;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", total=" + total +
                ", accounts=" + accounts +
                ", speciesNumber=" + speciesNumber +
                ", level=" + level +
                '}';
    }
}
