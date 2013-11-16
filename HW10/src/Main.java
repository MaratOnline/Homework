import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 03.11.13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    static String PASSWORD;
    static Thread onlyabc;
    static Thread onlynum;
    static Thread numabc;
    static Thread potok;
    static int buf = 0;


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

    static class MainT extends Thread {
        public void run() {
            System.out.print("Введите хеш запрос: ");
            Scanner sc = new Scanner(System.in);
            PASSWORD = sc.next();
            onlyabc = new one();
            onlynum = new two();
            numabc = new three();
            onlyabc.run();
            onlynum.run();
            numabc.run();
        }
    }

    static class one extends Thread {
        String abc = "";
        String fir1,t1,tr1,four1;
        public void run() {
            for (int a = 97; a <=122 & buf == 0; a++) {
                fir1 = String.valueOf((char)a);
                for (int b = 97; b <=122 & buf == 0; b++) {
                    t1 = String.valueOf((char)b);
                    for (int c = 97; c <=122 & buf == 0; c++) {
                        tr1 = String.valueOf((char)c);
                        for (int d = 97; d <=122 & buf == 0; d++) {
                            four1 = String.valueOf((char)d);
                            abc = fir1 +  t1 + tr1 + four1;
                            String abcMD5 = getMD5(abc);
                            if (abcMD5.equals(PASSWORD)) {
                                System.out.println("Ваш пароль: " + abc);
                                buf = 1;
                            }
                        }
                    }
                }
            }
        }
    }

    static class two extends Thread {
        String abc = "";
        String fir1,t1,tr1,four1;
        public void run() {
            for (int a=48; a<=57 & buf == 0; a++) {
                fir1 = String.valueOf((char)a);
                for (int b=48; b<=57 & buf == 0; b++) {
                    t1 = String.valueOf((char)b);
                    for (int c=48; c<=57 & buf == 0; c++) {
                        tr1 = String.valueOf((char)c);
                        for (int d=48; d<=57 & buf == 0; d++) {
                            four1 = String.valueOf((char)d);
                            abc = fir1 +  t1 + tr1 + four1;
                            String abcMD5 = getMD5(abc);
                            if (abcMD5.equals(PASSWORD)) {
                                System.out.println("Ваш пароль: " + abc);
                                buf = 1;
                            }
                        }
                    }
                }
            }
        }
    }

    static class three extends Thread {
        String abc = "";
        String fir1,t1,tr1;
        public void run() {
            for (int a=48; a <=90 & buf == 0; a++) {
                if((a>=48 & a<=57) | (a>=65 & a<=90)) {
                    fir1 = String.valueOf((char)a);
                    for (int b=48; b <=90 & buf == 0; b++) {
                        if((b>=48 & b<=57) | (b>=65 & b<=90)) {
                            t1 = String.valueOf((char)b);
                            for (int c=48; c <=90 & buf == 0; c++) {
                                if((c>=48 & c<=57) | (c>=65 & c<=90)) {
                                    tr1 = String.valueOf((char)c);
                                    abc = fir1 +  t1 + tr1;
                                    String abcMD5 = getMD5(abc);
                                    if (abcMD5.equals(PASSWORD)) {
                                        System.out.println("Ваш пароль: " + abc);
                                        buf = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static class ResultPotok extends Thread {
        public void run() {
            potok = new MainT();
            potok.run();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (potok.isAlive()) {
               run();
            } else if (buf == 0) {
                System.out.println("Совпадений не найдено!");
            }
        }
    }

    public static void main(String[] args){
        ResultPotok RP = new ResultPotok();
        RP.run();
    }
}
