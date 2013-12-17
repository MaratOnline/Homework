import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Марат on 17.12.13.
 */
public class Main {
    static Connection connection = null;
    static Statement statement = null;
    public static void main(String[] args) {
        String DBname = "hwP";
        String url = "jdbc:postgresql://localhost:5432/"+DBname;
        String name = "postgres";
        String passwordsql = "1994";
        try {
            connection = DriverManager.getConnection(url, name, passwordsql);
            statement = connection.createStatement();
        } catch (SQLException e) {e.printStackTrace();}

        try {
            System.out.println("Создание базы данных...");
            String query = "CREATE TABLE USERS (login varchar(255) primary key," +
                                               "pass varchar(255),"              +
                                               "gender varchar(255),"            +
                                               "last_day varchar(255)"           +
                            ")";
            statement.executeUpdate(query);
            System.out.println("Таблица создана в базе данных \""+DBname+"\", начинаю работу программы...");
            new Tool();
        } catch (SQLException e) {
            System.out.println("ERROR!\nДанная таблица уже существует в базе данных \""+DBname+"\", начинаю работу основной программы...");
            new Tool();
        }
    }
}
