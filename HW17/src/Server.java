import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void server(){
        String outPath = "C:\\Users\\Марат\\Desktop\\";
        int port = 2154;

        try {
            boolean notDone = true;
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Wait connect...");

            while(notDone){
                Socket soket = ss.accept();

                InputStream in = soket.getInputStream();
                DataInputStream din = new DataInputStream(in);

                System.out.print("Прием файла: \n");

                long fileSize = din.readLong();

                String fileName = din.readUTF();
                System.out.print("Имя файла: " + fileName+"\n");
                System.out.print("Размер файла: " + fileSize + " байт\n");

                byte[] buffer = new byte[64*1024];
                FileOutputStream outF = new FileOutputStream(outPath+fileName);
                int count, total = 0;

                while ((count = din.read(buffer)) != -1){
                    total += count;
                    outF.write(buffer, 0, count);
                    if(total == fileSize){
                        break;
                    }
                }
                outF.flush();
                outF.close();
                System.out.print("Файл принят\n");
                notDone = false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] arg){
        server();
    }
}
