package com.example.restapi_crud_hateoas_bank;

public class Amount {

    private float amount;

    public Amount() {
    }

    public Amount(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
