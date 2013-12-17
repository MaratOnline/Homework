import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Марат on 16.12.13.
 */
public class Main {
    public static String test = "test.docx";
    private static String inPos = "word/document.xml";
    public static void ZipAnaliz(File zipFile, InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile(zipFile.getName(), null);
        tempFile.delete();
        boolean renameOk=zipFile.renameTo(tempFile);
        if (!renameOk){
            throw new RuntimeException("Ошибка переименования файла "+zipFile.getAbsolutePath()+" на "+tempFile.getAbsolutePath());
        }
        byte[] buf = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            if(!entry.getName().equals(inPos))  {
                out.putNextEntry(new ZipEntry(name));
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        zin.close();
        out.putNextEntry(new ZipEntry(inPos));
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        inputStream.close();
        out.close();
        tempFile.delete();
    }
    static String Newdoc(String inName, String outName) throws IOException {
        int len;
        byte[] buf = new byte[1024];
        String output = outName+".docx";

        InputStream in = new FileInputStream(inName);
        OutputStream out = new FileOutputStream(output);
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
        return output;
    }
    static Document getterXML(String source) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        ZipEntry entry;
        Document doc = null;
        ZipInputStream in = new ZipInputStream(new FileInputStream(source));
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        while((entry = in.getNextEntry())!=null){
            if(entry.getName().equals(inPos))    {
                doc = db.parse(new UIS(in));
                break;
            }
        }
        in.close();
        return doc;
    }
    static InputStream changerXML(Document doc, String name, String surname) throws TransformerException, IOException {
        Node N;
        int i = 0;
        while((N = doc.getElementsByTagName("w:t").item(i))!=null) {
            if(N.getTextContent().equals("template_name"))
                N.setTextContent(name);
            if(N.getTextContent().equals("template_surname"))
                N.setTextContent(surname);
            i++;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(outputStream);
        TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    public static class UIS extends InputStream {
        private final InputStream input;

        public UIS(InputStream input) {
            this.input = input;
        }
        @Override
        public void close() throws IOException {}
        @Override
        public int read() throws IOException {
            return input.read();
        }
    }
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        Scanner sc = new Scanner(System.in);
        boolean bool = true;
        System.out.println("*******Генерация документов*******");
        while (bool) {
            System.out.println("Введите фамилию: ");
            String surname = sc.next();
            System.out.println("Введите имя: ");
            String name = sc.next();
            System.out.println("Начинаю работу с документом...");
            Document xml = getterXML(test);
            InputStream in = changerXML(xml,name,surname);
            String out = Newdoc(test, surname  + " " + name);
            ZipAnaliz(new File(out), in);
            System.out.print("Работа завршена!\nПродолжить генерацию документов?(y/n): ");
            String buf = sc.next();
            if ("y".equals(buf)) {
                System.out.println("**********************************");
                bool = true;
            } else if ("n".equals(buf)) {
                System.out.println("Завершение работы программы...");
                System.out.println("**********************************");
                bool = false;
            } else {
                System.out.println("Введено неверное значение. Завершение работы программы...");
                System.out.println("**********************************");
                bool = false;
            }
        }
    }
}
