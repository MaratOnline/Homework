/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 16.10.13
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task {
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
        DateFormat df = new SimpleDateFormat("HH:mm:ss,dd.MM.yyyy");
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
}
