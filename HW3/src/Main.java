/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 02.10.13
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.util.regex.*;

public class Main {
    public static void search(String fileName, String Type){
        Pattern p = Pattern.compile(".*\\." + Type);
        Matcher m = p.matcher(fileName);
        File f = new File(fileName);
        if (f.exists()) {
            if(f.isFile() & m.matches()){
                System.out.println(f.length() + " " + f.getAbsolutePath());
            }else if(f.isDirectory()){
                for (File file: f.listFiles()){
                   search(file.toString(), Type);
                }
            }
        }else System.out.println("File does not exist");
    }

    public static void main(String[] args) {
        search("C:/HW", "txt");
        search("../HW3/f", "txt");
    }

}

