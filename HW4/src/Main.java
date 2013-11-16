/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 04.10.13
 * Time: 1:06
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void realAccess(String fileNameRead, String fileNameWrite){
        try{
            RandomAccessFile f = new RandomAccessFile(fileNameRead, "rwd");
            RandomAccessFile f2 = new RandomAccessFile(fileNameWrite, "rws");
            String str = f.readLine() ;
            Pattern p = Pattern.compile("^[A-Z]" + ".*\\S");
            Matcher m;
            String[] words = str.split("\\W");
            for (String buf: words) {
                m = p.matcher(buf);
                if (m.matches()==true){
                    f2.writeChars(buf + '\n');
                }
            }
            f2.close();
            f.close();
        }catch (IOException e){
            System.out.println("Some I/O error");
        }
    }
    public static void main(String[] args) {
        realAccess("C:/HW/Test.txt","C:/HW/Result.txt");
    }
}
