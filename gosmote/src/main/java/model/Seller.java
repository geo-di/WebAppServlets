package model;

import model.Bill;
import model.Call;
import model.PhoneNumber;
import model.Program;

import java.util.List;

public class Seller extends User{

    public Seller(String username, String name, String surname) {
        super(username, name, surname);
        this.type = "seller";
    }

    public Client registerNewClient(User userClient, String afm, PhoneNumber phoneNumber){

        Client client = new Client(userClient,afm);
        System.out.println("User "+username+" created as a client!");

        client.setPhoneNumber(phoneNumber);


        return client;
    }

    public void publishClientBill(Client client, String month, List<Call> calls){

        Program program = client.getPhoneNumber().getProgram();
        Bill bill = new Bill();
        bill.setMonth(month);
        bill.setPhoneNumber(client.getPhoneNumber());
        bill.setCalls(calls);

        int totalMinutes = 0;
        for(Call call : bill.getCalls()){
            totalMinutes += call.getMinutes();
        }
        System.out.println("Printing bill for client with afm: " + client.getAfm());

        float billTotal = program.getPrice();
        if(program.getMinutes()<totalMinutes){
            int extraMinutes = totalMinutes - program.getMinutes();
            System.out.println("You have exceeded your allowed minutes. You will be charged extra for this month");
            billTotal += extraMinutes * program.getExtraMinutesCharge();

        } else {
            System.out.println("Minutes remaining: " + (program.getMinutes() - totalMinutes));
        }
        System.out.println("Your bill for month: "+ month +" is: " + billTotal );


    }

    public Client changeClientProgram(Client client, Program program){
        client.getPhoneNumber().setProgram(program);
        System.out.println("Changed client's program successfully!" );
        return client;
    }
}
