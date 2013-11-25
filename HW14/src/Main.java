import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 24.11.13
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */

public class Main
{
    static ArrayList<City> listLink = new ArrayList<City>();

    static class Sort implements Comparator<City> {
        public int compare(City a, City b) {
            return Integer.parseInt(a.getYear()) - Integer.parseInt(b.getYear());
        }
    }
    static class City
    {
        String name;
        String year;
        City(String name, String year)
        {
            this.name = name;
            this.year = year;
        }
        String getName()
        {
            return name;
        }
        String getYear()
        {
            return year;
        }
        void setYear(String year)
        {
            this.year = year;
        }
        public String toString()
        {
            return name + " (" + year + ")";
        }
    }

    static int SIZE = 0;
    static class SearchCity extends Thread
    {
        int size, first;
        String l;
        SearchCity(int size, int first)
        {
            this.size = size;
            this.first = first;
        }
        public void run()
        {
            while(first<size)
            {
                try {
                    Document doc = Jsoup.connect(listLink.get(first).getYear()).get();
                    Elements year = doc.select("table > tbody > tr > td:containsOwn(Первое) + td > div > a[href]," +
                                               "table > tbody > tr > td:containsOwn(Основан) + td > div > a[href]," +
                                               "table > tbody > tr > td:containsOwn(Основан) + td > div > span[title]," +
                                               "table > tbody > tr > td:containsOwn(Город) + td > div > a[href]");
                    l = year.toString();
                    Pattern pattern = Pattern.compile("[0-9]{3,4}");
                    Matcher matcher = pattern.matcher(l);
                    if(matcher.find())
                    {
                        listLink.get(first).setYear(matcher.group());
                        System.out.println("Считывание " + listLink.get(first).getName());
                    }
                    first++;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MainT extends Thread
    {
        public void run()
        {
            Document doc;
            try
            {
                doc = Jsoup.connect("http://ru.m.wikipedia.org/wiki/Список_городов_России_с_населением_более_100_тысяч_жителей").get();
                Elements links = doc.getElementsByTag("td");
                Elements links2 = links.select("[href]");
                Elements links3 = links2.select("[title]");
                Pattern pattern = Pattern.compile("\"/wiki/.*?\" ");
                Matcher matcher;
                String l;
                for(Element link : links3)
                {
                    if (!link.text().equals("Сарапул"))
                    {
                        l = link.toString();
                        matcher = pattern.matcher(l);
                        if (matcher.find())
                        {
                            l = "http://ru.m.wikipedia.org" + matcher.group().replaceAll("\"","");
                            listLink.add(new City(link.text(),l));
                        }
                    } else
                    {
                        l = link.toString();
                        matcher = pattern.matcher(l);
                        if (matcher.find())
                        {
                            l = "http://ru.m.wikipedia.org" + matcher.group().replaceAll("\"","");
                            listLink.add(new City(link.text(),l));
                        }
                        break;
                    }
                }

                SIZE = listLink.size()/3;
                Thread one = new SearchCity(SIZE, 0);
                Thread two = new SearchCity(SIZE*2, SIZE);
                Thread three = new SearchCity(listLink.size(), SIZE*2);
                one.start();
                two.start();
                three.start();
                while (one.isAlive()|two.isAlive()|three.isAlive())
                {
                    sleep(1000);
                }
                System.out.println("----Поиск завершен----");
                System.out.println(" ");
                System.out.println("----Вывод городов----");
                Collections.sort(listLink, new Sort());
                for (int i=0; i<10;i++)
                {
                    System.out.print((i+1)+") "+listLink.get(i));
                    System.out.println("");
                }
                System.out.println("---------------------");
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException
    {
        Thread Main = new MainT();
        Main.start();
        System.out.println("----Начало поиска----");
    }
}

