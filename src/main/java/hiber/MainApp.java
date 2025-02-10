package hiber;

import hiber.config.AppConfig;
import hiber.model.*;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User User1 = new User("User1", "Lastname1", "user1@mail.ru");
        User User2 = new User("User2", "Lastname2", "user2@mail.ru");
        User User3 = new User("User3", "Lastname3", "user3@mail.ru");
        User User4 = new User("User4", "Lastname4", "user4@mail.ru");

        Car Haval = new Car("Haval", 7);
        Car Audi = new Car("Bmv", 3);
        Car Porsche = new Car("Porsche", 911);
        Car Nissan = new Car("Volvo", 60);

        userService.add(User1.setCar(Haval).setUser(User1));
        userService.add(User2.setCar(Audi).setUser(User2));
        userService.add(User3.setCar(Porsche).setUser(User3));
        userService.add(User4.setCar(Nissan).setUser(User4));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        User user = userService.getUserByCar("Volvo", 60);
        System.out.println(user.toString());

        try {
            User notFoundUser = userService.getUserByCar("Kia", 5);
        } catch (NoResultException e) {
            System.out.println("Машина не найдена");
        }

        context.close();
    }
}
