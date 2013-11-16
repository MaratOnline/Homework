/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 01.11.13
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Enum;


public class Tool extends JFrame {
    JTextField loginField;
    JPasswordField passwordField;
    enum Gender { Выбрать,Мужской, Женский }
    Tool() {
        super("Авторизация");// Заголовок окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(600, 300, 300, 300); // Размеры окна
        // Задаем стиль
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Throwable thrown) {
            thrown.printStackTrace();
        }

        // Создания окна пользователя
        Box box1 = Box.createHorizontalBox();
        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField(15);
        box1.add(loginLabel);
        box1.add(Box.createHorizontalStrut(19));
        box1.add(loginField);

        // Создание окна пароля
        Box box2 = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField(15);
        box2.add(passwordLabel);
        box2.add(Box.createHorizontalStrut(12));
        box2.add(passwordField);

        // Создание окна с кнопками
        Box box3 = Box.createHorizontalBox();
        JButton ok = new JButton("Войти");
        JButton Exit = new JButton("Выйти");
        box3.add(Box.createHorizontalGlue());
        box3.add(ok);
        box3.add(Box.createHorizontalStrut(12));
        box3.add(Exit);

        // Создание поля вывода сообщений
        final JLabel Message = new JLabel();

        //Создание окна с полом
        Box box5 = Box.createHorizontalBox();
        final JLabel genderLabel = new JLabel("Пол:          ");
        final JComboBox editComboBox = new JComboBox(Gender.values());
        box5.add(genderLabel);
        box5.add(editComboBox);
        editComboBox.setEnabled(false);

        /*
        * Обработчики событий
         */
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message.removeAll();
                int ErrorPasword1 = 0;
                int CompleteUser = 0;
                int ErrorPasword2 = 0;
                int ErrorUser1 = 0;
                int ErrorUser2 = 0;
                int ErrorGender = 0;
                String fileName = "C:/HW/";
                String username = loginField.getText();
                String pasword = passwordField.getText();
                Pattern patternUSER = Pattern.compile("\\w{2,9}[A-Z a-z 0-9]");
                Pattern patternPASWORD = Pattern.compile("\\w{3,39}");
                Matcher matcherUSER = patternUSER.matcher(username);
                Matcher matcherPASWORD = patternPASWORD.matcher(pasword);
                String gender = editComboBox.getSelectedItem().toString();
                pasword = Task.getMD5(pasword);
                if (username.length() >= 3) {
                    if (matcherUSER.matches() == true){
                        if (matcherPASWORD.matches() == true) {
                            File file = new File(fileName + username + ".out");
                            boolean exists = file.exists();
                            if (exists == true) {
                                String UsPas = (String) Task.UserDes(fileName + username + ".out");
                                String UPD [] = UsPas.split(" ");
                                if (UPD[1].equals(pasword) & matcherPASWORD.matches() == true) {
                                    Message.setForeground(Color.BLUE);
                                    gender =  UPD[2];
                                    Message.setText("<html>Последний вход: <br>" + UPD[3] + "<br> <html>Пол: " + UPD[2]);
                                    Task.UserSer(fileName, username, pasword, gender);
                                } else {
                                    ErrorPasword1 = 1;
                                }
                            } else {
                                editComboBox.setEnabled(true);
                                if (gender.equals("Мужской") | gender.equals("Женский")) {
                                    CompleteUser = 1;
                                    Task.UserSer(fileName, username, pasword, gender);
                                } else {
                                    ErrorGender = 1;
                                }
                            }
                        } else {
                            ErrorPasword2 = 1;
                        }
                    } else {
                        ErrorUser1 = 1;
                    }
                } else {
                    ErrorUser2 = 1;
                }
                if (ErrorGender ==1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Для продолжения регистрации укажите пол!");
                } else if (ErrorUser2 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Логин должен быть больше 3-х символов!");
                } else if (ErrorPasword1 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Введен неверный пароль!");
                } else if (CompleteUser == 1) {
                    editComboBox.setSelectedIndex(0);
                    editComboBox.setEnabled(false);
                    Message.setForeground(Color.green);
                    Message.setText("<html>Пользователь был успешно создан!");
                } else if (ErrorPasword2 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Длина пароля должна быть от 3 до 40 символов!");
                } else if (ErrorUser1 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>В логине могут присутствовать только латинские буквы!");
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
        Message.setHorizontalTextPosition(JLabel.CENTER);
        Box box4 = Box.createHorizontalBox();
        box4.add(Box.createVerticalStrut(45));
        box4.add(Message);

        setLayout(new BorderLayout());
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12,12,12,12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box5);
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
}
