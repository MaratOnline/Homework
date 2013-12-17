import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

/**
 * Created by Марат on 17.12.13.
 */
public class Tool extends JFrame {
    static Connection connection = null;
    Statement statement = null;
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
        ok.setFocusPainted(false);
        JButton Exit = new JButton("Выйти");
        Exit.setFocusPainted(false);
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
                String url = "jdbc:postgresql://localhost:5432/hwP";
                String name = "postgres";
                String passwordsql = "1994";

                Date date = new Date();
                DateFormat df = new SimpleDateFormat("HH:mm:ss,dd.MM.yyyy");
                Message.removeAll();
                int ErrorPasword1 = 0;
                int CompleteUser = 0;
                int ErrorPasword2 = 0;
                int ErrorUser1 = 0;
                int ErrorUser2 = 0;
                int ErrorGender = 0;
                String username = loginField.getText();
                String password = passwordField.getText();
                Pattern patternUSER = Pattern.compile("\\w{2,9}[A-Z a-z 0-9]");
                Pattern patternPASWORD = Pattern.compile("\\w{3,39}");
                Matcher matcherUSER = patternUSER.matcher(username);
                Matcher matcherPASWORD = patternPASWORD.matcher(password);
                String gender = editComboBox.getSelectedItem().toString();
                password = Task.getMD5(password);
                String query = null;
                String MESSAGE = null;
                try {
                    connection = DriverManager.getConnection(url, name, passwordsql);
                    statement = connection.createStatement();
                } catch (SQLException e1) {e1.printStackTrace();}
                if (username.length() >= 3) {
                    if (matcherUSER.matches() == true){
                        try {
                            query = "SELECT * FROM users WHERE login='"+username+"'";
                            ResultSet result = statement.executeQuery(query);
                            while (result.next()) {
                                if (result.getString("login").equals(username)) {
                                    query=null;
                                }
                            }
                            if (query==null) {
                                if (matcherPASWORD.matches() == true) {
                                    query = "SELECT pass FROM users WHERE login='"+username+"'";
                                    ResultSet result2 = statement.executeQuery(query);
                                    while (result2.next()) {
                                        if (result2.getString("pass").equals(Task.getMD5(matcherPASWORD.group()))) {
                                            query=null;
                                        } else {
                                            ErrorPasword1=1;
                                        }
                                    }
                                    if (query==null) {
                                        query = "SELECT gender FROM users WHERE login='"+username+"'";
                                        ResultSet result3 = statement.executeQuery(query);
                                        while (result3.next()) {
                                            //if (result3.getString("gender").equals("Мужской")|result3.getString("gender").equals("Женский")) {
                                                MESSAGE="Пол: " + result3.getString("gender");
                                            //}
                                        }
                                        query = "SELECT last_day FROM users WHERE login='"+username+"'";
                                        ResultSet result4 = statement.executeQuery(query);
                                        while (result4.next()) {
                                            MESSAGE=MESSAGE + ". Дата последнего захода: " + result4.getString("last_day");
                                        }
                                        Date dateNEW = new Date();
                                        query = "UPDATE users SET last_day='"+df.format(dateNEW)+"' WHERE login='"+username+"'";
                                        statement.executeUpdate(query);
                                        Message.setForeground(Color.BLUE);
                                        Message.setText("<html>"+MESSAGE);
                                    }
                                } else {
                                    ErrorPasword2 = 1;
                                }
                            } else if (matcherPASWORD.matches() == true) {
                                if ("Мужской".equals(gender) | "Женский".equals(gender)) {
                                    try {
                                        CompleteUser = 1;
                                        query =  "INSERT INTO users (login,pass,gender,last_day) " +
                                                "VALUES ('" +username+ "','"
                                                +password+ "','"
                                                +gender+ "','"
                                                +df.format(date)+
                                                "');";
                                        statement.executeUpdate(query);
                                    }catch (SQLException e1) {e1.printStackTrace();}
                                } else {
                                    ErrorGender = 1;
                                }
                            } else {
                                ErrorPasword2 = 1;
                            }
                        } catch (SQLException e1) {e1.printStackTrace();}
                    } else {
                        ErrorUser1 = 1;
                    }
                } else {
                    ErrorUser2 = 1;
                }
                if (ErrorGender ==1) {
                    Message.setForeground(Color.RED);
                    editComboBox.setEnabled(true);
                    Message.setText("<html>Для продолжения регистрации укажите пол!");
                } else if (ErrorUser2 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Логин должен быть больше 3-х символов!");
                } else if (ErrorPasword1 == 1) {
                    Message.setForeground(Color.RED);
                    Message.setText("<html>Введен неверный пароль!");
                } else if (CompleteUser == 1) {
                    Message.setForeground(Color.green);
                    editComboBox.setSelectedIndex(0);
                    editComboBox.setEnabled(false);
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
