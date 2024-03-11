package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;
public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

       //Создаю пользователей с машинами
      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      Car car1 = new Car("Toyota Corolla", 170);
      user1.setUserCar(car1);

      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      Car car2 = new Car(" Ford Mustang ", 550);
      user2.setUserCar(car2);

      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      Car car3 = new Car("BMW 3 Series", 20);
      user3.setUserCar(car3);

      User user4 = new User("User4", "Lastname4", "user4@mail.ru");
      Car car4 = new Car("Honda Civic", 307);
      user4.setUserCar(car4);

      //Добавляю пользователей в базу данных
       userService.add(user1);
       userService.add(user2);
       userService.add(user3);
       userService.add(user4);

      //Получаю пользователя и базы данных
       List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+ user.getUserCar());
         System.out.println();
      }

      //Получаю пользователя по серии и модели
      try {
         User foundUser = userService.getUserByCar(" Ford Mustang ", 550);
         System.out.println("Пользователь у которого есть указанный автомобиль: "+foundUser.getFirstName() +" " +foundUser.getLastName()+" "+
                 foundUser.getEmail());
      }catch (NoResultException e){
         System.out.println("Не найден пользователь с указанным автомобилем");
      }


      context.close();
   }
}
