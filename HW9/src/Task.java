/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 01.11.13
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task {
    public static void Serialize(Object object, String fileName) {

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

    public static void UserSer(String fileName,String username, String pasword, String gender){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss,dd.MM.yyyy");
        Serialize(username + " " + pasword + " " + gender + " "  + df.format(date), fileName + username + ".out");
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

    static String getMD5(String inputString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // DSA, RSA, MD5, SHA-1, SHA-256
            byte[] messageDigest = md.digest(inputString.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}