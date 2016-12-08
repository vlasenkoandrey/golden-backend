package ru.vlasenko.model;

/**
 * Created by andrew on 08/12/16.
 */
public class Device {
    private String token;
    private String arn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    @Override
    public String toString() {
        return "Device{" +
                ", token='" + token + '\'' +
                ", arn='" + arn + '\'' +
                '}';
    }
}
