package com.smitcoderx.jomkes;

public class JokesModelClass {

    private String jokesText;
    private String setup;
    private String delivery;

    public JokesModelClass(String jokesText, String setup, String delivery) {
        this.jokesText = jokesText;
        this.setup = setup;
        this.delivery = delivery;
    }

    public String getJokesText() {
        return jokesText;
    }

    public String getSetup() {
        return setup;
    }

    public String getDelivery() {
        return delivery;
    }
}
