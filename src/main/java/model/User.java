package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String username, fullName, password, email, accessLevel, address, phoneNumber;
    private Float inventory;

    public User(String username, String fullName, String password, String email, String accessLevel) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.accessLevel = accessLevel;
    }

    public User(String username, String fullName, String password, String email, Float inventory, String accessLevel) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.inventory = inventory;
        this.accessLevel = accessLevel;
    }

    public User(String username, String fullName, String password, String email, String accessLevel, String address, Float inventory, String phoneNumber) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.inventory = inventory;
        this.accessLevel = accessLevel;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getInventory() {
        return inventory;
    }

    public void setInventory(Float inventory) {
        this.inventory = inventory;
    }
}
