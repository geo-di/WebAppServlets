package model;

public class PhoneNumber {
    private String phoneNumber;
    private Program program;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
    
    public PhoneNumber(String phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }
}
