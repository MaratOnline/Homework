/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 21.10.13
 * Time: 0:50
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Main extends JFrame {
    JTextField loginField;
    JPasswordField passwordField;
    Main() {
        super("Рейтинг фильмов");// Заголовок окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(600, 300, 300, 300); // Размеры окна
        // Задаем стиль
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Throwable thrown) {
            thrown.printStackTrace();
        }

        // Создания окна название фильма
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Название фильма:");
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(19));
        box1.add(loginField);



        // Создание окна с кнопками
        Box box3 = Box.createHorizontalBox();
        JButton Rating = new JButton("Рейтинг");
        JButton Exit = new JButton("Выйти");
        box3.add(Box.createHorizontalGlue());
        box3.add(Rating);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(Exit);

        // Создание поля вывода сообщений
        final JLabel Message = new JLabel();
        /*
        * Обработчики событий
         */
        Rating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        Message.setHorizontalTextPosition(JLabel.CENTER);
        Box box4 = Box.createHorizontalBox();
        box4.add(Box.createVerticalStrut(45));
        box4.add(Message);
        setLayout(new BorderLayout());
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainBox.add(Box.createVerticalStrut(0));
        mainBox.add(box4);
        mainBox.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box3);
        setVisible(true);
        setContentPane(mainBox);
        pack();
        setResizable(false);
    }

   public static void main(String[] args) {
       new Main();
   }
}