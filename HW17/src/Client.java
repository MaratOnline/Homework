import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    int port = 2154;
    String addres = "localhost";
    Socket socket;

    Client(String path){
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(addres);
            socket = new Socket(ipAddress, port);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try{
            DataOutputStream outD = new DataOutputStream(socket.getOutputStream());

            File f = new File(path);

            outD.writeLong(f.length());
            outD.writeUTF(f.getName());

            FileInputStream in = new FileInputStream(f);
            byte [] buffer = new byte[64*1024];
            int count;

            while((count = in.read(buffer)) != -1){
                outD.write(buffer, 0, count);
            }
            System.out.print("Файл принят!");
            outD.flush();
            in.close();

            socket.close();
        }
        catch(IOException e){
            System.out.print("Файл не найден.");
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите путь к файлу и имя файла: ");
        String path = scan.next();
        new Client(path);
    }
}
