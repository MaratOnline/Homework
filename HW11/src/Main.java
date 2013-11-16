/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 28.10.13
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.util.regex.*;
import java.util.Scanner;

public class Main {
    static String Dir ="";

    static class MainT extends Thread {

        public void run() {
            for (int i=0; i<1; i++) {
                One music = new One();
                Two films = new Two();
                Three pictures = new Three();
                music.start();
                films.start();
                pictures.start();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
        }
    }

    static class One extends Thread {

        public void run() {
            search(Dir, "mp3");
            search(Dir, "mp4");
        }
    }

    static class Two extends Thread {


        public void run() {
            search(Dir, "mkv");
            search(Dir, "avi");

        }
    }

    static class Three extends Thread {

        public void run() {
            for (int i=0; i<1; i++) {
                search(Dir, "jpg");
                search(Dir, "png");
                search(Dir, "gif");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
        }
    }

    public static void search(String fileName, String Type){
        Pattern p = Pattern.compile(".*\\." + Type);
        Matcher m = p.matcher(fileName);
        File f = new File(fileName);
        if (f.exists()) {
            if(f.isFile() & m.matches() & (Type.equals("avi") | Type.equals("mp4") | Type.equals("mkv"))){
                System.out.println("Films: " + f.length() + " " + f.getAbsolutePath());
            }else if (f.isFile() & m.matches() & (Type.equals("mp3") | Type.equals("wav")))  {
                System.out.println("Audio: " + f.length() + " " + f.getAbsolutePath());
            }else if (f.isFile() & m.matches() & (Type.equals("jpg") | Type.equals("png") | Type.equals("gif")))  {
                System.out.println("Pictures: " + f.length() + " " + f.getAbsolutePath());
            }else if(f.isDirectory()){
                for (File file: f.listFiles()){
                    search(file.toString(), Type);
                }
            }
        }else System.out.println("File does not exist");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите директорию: ");
        Dir = sc.next();
        MainT m = new MainT();
        m.start();
    }

}
