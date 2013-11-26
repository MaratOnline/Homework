/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 26.11.13
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GUI extends JFrame {
    // Создаем GET запрос

    public static void GET(String transl, String languageTR){
        Get get = new Get();
        String httpLA = "https://translate.yandex.net/api/v1.5/tr.json/detect?";
        String textLA = "text="+transl;
        String httpTR = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        String key = ""; // Здесь нужно прописать значение API Key!!!!!!!!!!!!!!!!(на конце не нужно добавлять "&")
        String text = "&text="+transl+"&";
        String lang = "lang="+languageTR+"&";
        String format = "format=plain&";
        String option = "option=1&";
        String callback ="calback=5";
        String responce2 = get.executeGet(httpLA+key+textLA);
        String response = get.executeGet(httpTR+key+text+lang+format+option+callback);

        Pattern patternLA = Pattern.compile("\"[a-z]{2}\"");
        Pattern pattern = Pattern.compile("\\[.*]");
        Matcher matcher = pattern.matcher(response);
        Matcher matcherLA = patternLA.matcher(responce2);
        int ind = 0;
        if (matcher.find()&matcherLA.find()) {
            for (int i=0;i<a.length;i++) {
                if (a[i].equals(matcherLA.group().replace("\"",""))) {
                    ind = i;
                    if (language1.getSelectedIndex()==ind) {
                        getText.setText(matcher.group().replace("\"","").replace("]","").replace("[",""));
                        Message.setText("Перевод завершен!");
                    } else {
                        Message.setForeground(Color.RED);
                        Message.setText("Выбран неправильный исходный язык!");
                    }
                }
            }
        }
    }
    static String[] a = {"az","be","bg","ca","cs","da","de","el","en","es","et","fi","fr","hr","hu","hy","it","lt","lv","mk","nl","no","pl","pt","ro","ru","sk","sl","sq","sr","sv","tr","uk"};
    static enum LaguageMA {Азербайджанский,Белорусский,Болгарский,Каталанский,Чешский,Датский,Немецкий,Греческий,Английский,Испанский,Эстонский,Финский,Французский,Хорватский,Венгерский,Армянский,Итальянский,Литовский,Латышский,Македонский,Голландский,Норвежский,Польский,Португальский,Румынский,Русский,Словацкий,Словенский,Албанский,Сербский,Шведский,Турецкий,Украинский}
    static JTextArea setText;
    static JTextArea getText;
    static JComboBox language1 = new JComboBox(LaguageMA.values());
    static JComboBox language2 = new JComboBox(LaguageMA.values());
    static JLabel Message = new JLabel("Пожалуйста, выберите языки для перевода!");
    GUI() {
        super("Переводчик");// Заголовок окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 300, 300, 300); // Размеры окна
        // Задаем стиль
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Throwable thrown) {
            thrown.printStackTrace();
        }

        // Создания окон для ввода
        Box box1 = Box.createHorizontalBox();
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(new TitledBorder(new EtchedBorder(),"Введите текст"));
        setText = new JTextArea(4,25);
        setText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scroll = new JScrollPane(setText,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
        setText.setLineWrap(true);
        setText.setWrapStyleWord(true);
        setText.setFont(setText.getFont().deriveFont(15f));
        //Создание окна вывода перевода
        JPanel middlePanel2 = new JPanel();
        middlePanel2.setBorder(new TitledBorder(new EtchedBorder(),"Перевод"));
        getText = new JTextArea(4,25);
        getText.setLineWrap(true);
        getText.setWrapStyleWord(true);
        getText.setFont(setText.getFont().deriveFont(15f));
        getText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getText.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(getText,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));

        // Добавление в рамки и на новый слой Box
        middlePanel.add(scroll);
        middlePanel2.add(scroll2);
        box1.add(middlePanel);
        box1.add(middlePanel2);

        //Создание списков языков
        language1.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
        language2.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
        Box boxLanguage = Box.createHorizontalBox();
        language1.setFocusable(false);
        language2.setFocusable(false);
        boxLanguage.add(Box.createHorizontalStrut(2));
        boxLanguage.add(language1);
        boxLanguage.add(Box.createHorizontalStrut(5));
        boxLanguage.add(language2);
        boxLanguage.add(Box.createHorizontalStrut(2));

        //Вывод подсказок
        Box boxMessage = Box.createHorizontalBox();
        boxMessage.add(Message);

        // Создание окна с кнопками
        Box box3 = Box.createHorizontalBox();
        JButton translate = new JButton("Перевести");
        translate.setFocusPainted(false);
        JButton Exit = new JButton("Выйти");
        Exit.setFocusPainted(false);
        box3.add(Box.createHorizontalGlue());
        box3.add(translate);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(Exit);


        /*
        * Обработчики событий
         */
        translate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message.setForeground(Color.GREEN.darker());
                int ind = language2.getSelectedIndex();
                String str2 = a[ind];
                try {
                    GET(URLEncoder.encode(setText.getText(), "UTF-8"),str2);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /*
        * Обработчики событий
         */

        setLayout(new BorderLayout());
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,5,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(boxMessage);
        mainBox.add(Box.createVerticalStrut(14));
        mainBox.add(boxLanguage);
        mainBox.add(Box.createVerticalStrut(17));
        mainBox.add(box3);
        mainBox.setPreferredSize(new Dimension(550,250));
        setVisible(true);
        setContentPane(mainBox);
        pack();
        setResizable(false);
    }
}




