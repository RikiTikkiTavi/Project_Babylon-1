package com.greenelephant.babylon.controller;

public class Bank {

    private int gold = 0;

    public void setGold(int newGold) {
        gold = newGold;
    }

    public int getGold() {
        return gold;
    }

    public boolean canBuy(int price) {
        return price <= gold;
    }

}

