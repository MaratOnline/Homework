import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Марат on 17.12.13.
 */
public class Main {

    public static void main(String[] args) {

        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/kino";

        String name = "root";
        String password = "admin";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, name, password);

            Statement st = connection.createStatement();
            st.executeUpdate("DROP TABLE IF EXISTS updatedrating;");
            st.executeUpdate("CREATE TABLE updatedrating AS SELECT imdb.name, imdb.year, imdb.rating\n" +
                    "FROM imdb LEFT JOIN kinopoisk ON (imdb.name = kinopoisk.name AND imdb.year = kinopoisk.year)\n"+
                    "WHERE (kinopoisk.name IS NULL)\n"+
                    "UNION SELECT kinopoisk.name, kinopoisk.year, kinopoisk.rating\n" +
                    "FROM kinopoisk LEFT JOIN imdb ON (imdb.name = kinopoisk.name AND imdb.year = kinopoisk.year)");

            st.executeUpdate(
                    "UPDATE updatedrating SET rating = (COALESCE((SELECT rating FROM imdb WHERE name = updatedrating.name AND year = updatedrating.year),0) + (COALESCE((SELECT rating FROM kinopoisk WHERE name = updatedrating.name AND year = updatedrating.year),0)))/2"
            );

            Statement statement = connection.createStatement();
            ResultSet result1 = statement.executeQuery("SELECT * FROM updatedrating ORDER BY rating DESC");

            Scanner sc = new Scanner(System.in);
            int count = 0;

            Statement forcount = connection.createStatement();
            ResultSet resultSet = forcount.executeQuery("SELECT * FROM updatedrating");
            while(resultSet.next()) {
                count++;
            }
            System.out.print("Введите место в Общем рейтинге: ");
            int a = sc.nextInt();
            if(a<1||a>count)
                System.out.print("Выход за пределы таблицы");
            else {
                for(int i = 0; i<a; i++)
                result1.next();
                System.out.print(result1.getString("name")+" ("+ result1.getString("year")+")"+" – "+result1.getString("rating"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
