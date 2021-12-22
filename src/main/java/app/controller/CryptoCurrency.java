package app.controller;

public class CryptoCurrency {
    String code;
    double rate;
    long volume;
    long cap;

    void CryptoCurrency(String name, double value, long volume, long cap) {
        this.code = name;
        this.rate = value;
        this.volume = volume;
        this.cap = cap;
    }

    public double getRate() {
        return rate;
    }

    public long getCap() {
        return cap;
    }

    public long getVolume() {
        return volume;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setCap(long cap) {
        this.cap = cap;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Crypto [code=" + code + ", rate=" + rate + "]";
    }
}