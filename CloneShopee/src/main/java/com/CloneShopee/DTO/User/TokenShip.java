package com.CloneShopee.DTO.User;

import java.io.Serializable;
import java.util.List;

public class TokenShip implements Serializable {
    private String token;
    private String totalShip;
    private String address;

    private List<Integer> items;

    public Boolean checkGenerate(List<Integer> ids) {
        if (true) { // kiểm tra generate Token đúng ko
            return true; // giả sử item là hợp lệ.
        }
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTotalShip() {
        return totalShip;
    }

    public void setTotalShip(String totalShip) {
        this.totalShip = totalShip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

}