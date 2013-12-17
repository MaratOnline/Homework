import java.util.regex.*;

/**
 * Created by Марат on 14.12.13.
 */
public class Main {
    static String numberPhone = null;
    private static void example(String number){
        Pattern pattern;
        pattern = Pattern.compile("\\+\\d{1,3}\\(\\d{2,8}\\)\\d{1,6}\\-\\d{1,6}\\-\\d{1,6}");
        Matcher matcher = pattern.matcher(number);

        if(matcher.find()){
            numberPhone = matcher.group();
            System.out.println("Задание №1: " + numberPhone); // Выводим всю строку
        }else{
            System.out.println("Задание №1: Номер не прошел проверку!");
        }
    }
    private static void standart(String number) {
        example(number);
        if (numberPhone!=null) {
            System.out.println("Задание №2: " + numberPhone.replaceAll("\\(", "").replaceAll("\\)","").replaceAll("-", ""));
            numberPhone=null;
        } else {
            System.out.println("Задание №2: Номер не прошел проверку в первом задании!");
        }
        System.out.println("*****************************");
    }

    public static void main(String [] args) {
        standart("+7(969)622-84-49");   // подходящий номер
        standart("+3223(475)69-664-65"); // не подходящий номер
        standart("+3(475)69-664-65");   // подходящий номер
        standart("+3(475233213)69-664-65"); //не подходящий номер
    }
}
