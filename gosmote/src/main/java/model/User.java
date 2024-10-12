package model;

public class User {
    protected String username;
    protected String name;
    protected String surname;
    protected String type;
    private static int usersCounter = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int getUsersCounter() {
        return usersCounter;
    }

    public User() {
    }

    public User(String username, String name, String surname) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        usersCounter++;
        System.out.println("New User Created");
    }

    public void register() {
        System.out.println("Registered Successfully!");
    }

    public void login() {
        System.out.println("Signed in Successfully!");
    }

    public void logout() {
        System.out.println("Signed out Successfully!");
    }

}
