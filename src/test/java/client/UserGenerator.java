package client;

import models.User;

import java.util.Random;

public class UserGenerator {


    public static String getEmailRandom(){
        String email="testsk4"+new Random().nextInt(50)+"@yandex.ru";

        return email;
    };
    public static User getUserWithAllCredentialsRandom(){
        User user =new User(getEmailRandom(),"1234","testsk");

        return user;
    };
    public static User getUserWithoutEmailRandom(){
        User user =new User("","1234","testsk");

        return user;
    };
    public static User getUserWithoutPasswordRandom(){
        User user =new User(getEmailRandom(),"","testsk");

        return user;
    };
    public static User getUserWithoutNameRandom(){
        User user =new User(getEmailRandom(),"1234","");

        return user;
    };
}
