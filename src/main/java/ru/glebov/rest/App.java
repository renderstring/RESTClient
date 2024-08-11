package ru.glebov.rest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.glebov.rest.config.MyConfig;
import ru.glebov.rest.model.User;

import java.util.List;

public class App 
{
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> users = communication.getAllUsers();

        for (User user : users) {
            System.out.println(user);
        }

        User user = new User(3L, "James", "Brown", (byte) 20);

        String newUser = communication.addUser(user);

        user.setName("Thomas");
        user.setLastName("Shelby");

        String updatedUser = communication.updateUser(user);

        String deletedUser = communication.deleteUser(3L);

        System.out.println(newUser + updatedUser + deletedUser);
    }
}
