package model;

import model.Program;

public class Admin extends User{
    public Admin(String username, String name, String surname) {
        super(username, name, surname);
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.type = "admin";
        System.out.println("User "+username+" created as an admin!");
    }

    public Seller createNewSeller(String username,String name,String surname){

        Seller seller = new Seller(username,name,surname);
        System.out.println("User "+seller.getUsername()+" created as a seller!");
        return seller;

    }
    public void deleteSeller(Seller seller){
        System.out.println("Seller deleted");
    }
    public User createNewUser(String username,String name,String surname){

        return new User(username,name,surname);

    }
    public void deleteUser(User user){
        System.out.println("User deleted");
    }
    public Program createNewProgram(int id, String name, int minutes,int data,float price, float extraMinutesCharge,float extraDataCharge){
        Program program = new Program(id, name, data, data, extraDataCharge, extraDataCharge, extraDataCharge);
        System.out.println("New program created");
        return program;
    }
    public void deleteProgram(Program program){
        System.out.println("Program deleted");
    }



}
