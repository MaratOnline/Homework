/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 23.09.13
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
import java.util.regex.*;

public class Main {
    private static void example(String number){
        Pattern pattern;
        pattern = Pattern.compile("\\+([0-9]{1,3})\\(([0-9]{2,8})\\)\\d([0-9]{1,6})\\-\\d([0-9]{1,6})\\-\\d([0-9]{1,6})");
        Matcher matcher = pattern.matcher(number);

        System.out.print("Find: ");
        // Поиск соответствия шаблону в строку (поиск подстроки)
        if(matcher.find()){
            System.out.println("matches");
            System.out.println("Задание №1: " + matcher.group()); // Выводим всю строку
        }else{
            System.out.println("Not matched");
        }
    }

    public static void main(String [] args) {
        example("+7(969)622-84-49");   // подходящий номер
        example("+3(475)69-664-65");   // подходящий номер
        example("+3(475233213)69-664-65");     //не подходящий номер
    }
}
