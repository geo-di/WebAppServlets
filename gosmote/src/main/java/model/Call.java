package model;

public class Call {
    private final int minutes;
    private final PhoneNumber phoneNumber;
    private final String month;

    public Call(int minutes, PhoneNumber phoneNumber, String month) {
        this.minutes = minutes;
        this.phoneNumber = phoneNumber;
        this.month = month;
    }

    public int getMinutes() {
        return minutes;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getMonth() {
        return month;
    }
}
