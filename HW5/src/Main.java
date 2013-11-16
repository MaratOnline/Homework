/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 13.10.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void Serialize(Object object, String fileName){
        try{
            FileOutputStream file = new FileOutputStream(fileName); // Файловый поток
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject(object);
            output.flush();
            output.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void UserSer(String fileName,String username, String pasword){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss,dd/MM/yyyy");
        Serialize(username + " " + pasword + " " + df.format(date), fileName + username + ".out");
    }

    public static Object UserDes(String fileName){
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream object = new ObjectInputStream(file);
            return object.readObject();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void UP(String fileName){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя пользователя: ");
        String username = sc.next();
        Pattern patternUSER = Pattern.compile("\\w{2,9}[A-Z a-z 0-9]");
        Pattern patternPASWORD = Pattern.compile("\\w{3,39}");
        Matcher matcherUSER = patternUSER.matcher(username);
        if (matcherUSER.matches() == true){
            File file = new File(fileName + username + ".out");
            boolean exists = file.exists();
            if (exists == true) {
                System.out.println("Введите пароль: ");
                String pasword = sc.next();
                Matcher matcherPASWORD = patternPASWORD.matcher(pasword);
                if (matcherPASWORD.matches() == true) {
                    String UsPas = (String) UserDes(fileName + username + ".out");
                    String UPD [] = UsPas.split(" ");
                    if (UPD[1].equals(pasword) & matcherPASWORD.matches() == true ) {
                        for (int i = 0; i < 3; i++) {
                            System.out.println(UPD[i]);
                            UserSer(fileName, username, pasword);
                        }
                    } else {
                        System.out.println("Введен неверный пароль! Повторите еще раз.");
                        System.out.println("Длина пароля должна быть от 3 до 40 символов");
                        System.out.println("Можно использовать буквы русского и латинского алфавита, а так же знак подчеркивания");
                        UP("C:/HW/");
                    }
                }
            } else {
                System.out.println("Пользователя с таким логином не существует, был создан новый пользователь: " + username);
                System.out.println("Введите пароль для нового пользователя: ");
                String pasword = sc.next();
                UserSer(fileName, username, pasword);
                System.out.println("Пользователь был успешно создан!");
            }
        } else {
            System.out.println("В логине могут присутствовать только латинские буквы!");
            System.out.println("Попробуйте еще раз.");
            UP("C:/HW/");
        }
    }

    public static void main(String[] args) throws IOException {
        UP("C:/HW/");
    }
}