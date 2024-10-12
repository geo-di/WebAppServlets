package model;

import model.PhoneNumber;

public class Client extends User{

    private final String afm;
    private PhoneNumber phoneNumber;

    public Client(User user, String afm) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.type = "client";
        this.afm = afm;
    }

    public String getAfm() {
        return afm;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
