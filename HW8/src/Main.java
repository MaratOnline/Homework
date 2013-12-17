import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Марат on 14.12.13.
 */
public class Main {
    public static void Convert(String number){
        Pattern patternDollar = Pattern.compile("(\\$[0-9]{0,}(.|,)[0-9]{1,2})|(\\$[0-9]{0,})");
        Pattern patternEuro = Pattern.compile("(\\€[0-9]{0,}(.|,)[0-9]{1,2})|(\\€[0-9]{0,})");
        Matcher matcherDollar = patternDollar.matcher(number);
        Matcher matcherEuro = patternEuro.matcher(number);
        String flag;
        double num = 0;
        if (matcherDollar.find()) {
            flag = matcherDollar.group().replace("$","");
            System.out.println(flag);
            num = Double.parseDouble(flag);
            num = num*32.87;
            num = Math.rint(100.0*num)/100.0;
        } else if (matcherEuro.find()) {
            flag = matcherEuro.group().replace("€","");
            num = Double.parseDouble(flag);
            num = num*45.19;
            num = Math.rint(100.0*num)/100.0;
        }
        System.out.println(num);

    }
    public static void main(String[] args) {
        System.out.print("Введите число в долларах или евро: ");
        Scanner sc = new Scanner(System.in);
        String num=sc.next();
        Convert(num);
    }
}
