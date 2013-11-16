/**
 * Created with IntelliJ IDEA.
 * User: Марат
 * Date: 09.11.13
 * Time: 9:22
 * To change this template use File | Settings | File Templates.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class Tools extends JFrame {
    int sec = 0;
    int min = 0;
    static Thread time;
    static Thread time2;
    static boolean bool = false;
    static int buf = 0;

    Tools() {
        super("Pomodoro");// Заголовок окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(600, 300, 300, 300); // Размеры окна
        // Задаем стиль
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Throwable thrown) {
            thrown.printStackTrace();
        }

        // Фон приложения
        class ImagePanel extends JPanel {
            private Image image;
            public Image getImage() {
                return image;
            }
            public void setImage(Image image) {
                this.image = image;
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(image != null){
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                }
            }
        }

        // Создание окна с кнопками
        Box box3 = Box.createHorizontalBox();
        final JButton work = new JButton();
        work.setIcon(new ImageIcon("button\\work_button.png"));
        work.setRolloverIcon(new ImageIcon("button\\work_another.png"));
        work.setPressedIcon(new ImageIcon("button\\work_pressed.png"));
        work.setFocusPainted(false);
        work.setBorderPainted(false);
        work.setContentAreaFilled(false);
        final JButton relax = new JButton();
        relax.setIcon(new ImageIcon("button\\relax_button.png"));
        relax.setRolloverIcon(new ImageIcon("button\\relax_another.png"));
        relax.setPressedIcon(new ImageIcon("button\\relax_pressed.png"));
        relax.setFocusPainted(false);
        relax.setBorderPainted(false);
        relax.setContentAreaFilled(false);
        relax.setEnabled(false);
        box3.add(work);
        box3.add(Box.createHorizontalStrut(30));
        box3.add(relax);

        //Табло со временем
        final JLabel Message = new JLabel("WELCOME!");
        final Font font = new Font("Showcard Gothic", Font.PLAIN, 40);
        Message.setForeground(Color.getHSBColor(155,90,90));
        Message.setFont(font);
        Message.setHorizontalTextPosition(JLabel.CENTER);
        Box box4 = Box.createHorizontalBox();
        box4.add(Message);

        //Создаем таймер
        final Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            switch (buf) {
            case 1:
                if (bool == false) {
<<<<<<< HEAD
                    if (sec<=59 & min != 25) {
=======
                    if (sec<=59 & sec != 10) {
>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        if (min>=10 & sec >=10) {
                            Message.setText(String.valueOf(min)+ ":" + String.valueOf(sec));
                            sec++;
                        } else if (min<10 & sec <10){
                            Message.setText("0"+String.valueOf(min)+ ":0" + String.valueOf(sec));
                            sec++;
                        } else if (min<10 & sec >=10) {
                            Message.setText("0"+String.valueOf(min)+ ":" + String.valueOf(sec));
                            sec++;
                        } else if (min>=10 & sec <10) {
                            Message.setText(String.valueOf(min)+ ":0" + String.valueOf(sec));
                            sec++;
                        }
<<<<<<< HEAD
                    } else if (sec>=60 & min != 25){
=======
                    } else if (sec>=60 & sec !=10){
>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        min++;
                        if (min>=10) {
                            Message.setText(String.valueOf(min)+ ":00");
                        } else {
                            Message.setText("0"+String.valueOf(min)+ ":00");
                        }
                        sec = 1;

<<<<<<< HEAD
                    } else if (min == 25) {
=======
                    } else if (sec == 10) {

>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        Message.setForeground(Color.getHSBColor(1,1,1));
                        Message.setText("Relax!");
                        Message.setForeground(Color.getHSBColor(1,1,1));
                        relax.setEnabled(true);
                        bool = true;
                    }
                }
                break;
            case 2:
                if (bool == true) {
                    Message.setFont(font);
<<<<<<< HEAD
                    if (sec<=59 & min != 5) {
=======
                    if (sec<=59 & sec != 10) {
>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        if (min>=10 & sec >=10) {
                            Message.setText(String.valueOf(min)+ ":" + String.valueOf(sec));
                            sec++;
                        } else if (min<10 & sec <10){
                            Message.setText("0"+String.valueOf(min)+ ":0" + String.valueOf(sec));
                            sec++;
                        } else if (min<10 & sec >=10) {
                            Message.setText("0"+String.valueOf(min)+ ":" + String.valueOf(sec));
                            sec++;
                        } else if (min>=10 & sec <10) {
                            Message.setText(String.valueOf(min)+ ":0" + String.valueOf(sec));
                            sec++;
                        }
<<<<<<< HEAD
                    } else if (sec>=60 & min != 5){
=======
                    } else if (sec>=60 & sec !=10){
>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        min++;
                        if (min>=10) {
                            Message.setText(String.valueOf(min)+ ":00");
                        } else {
                            Message.setText("0"+String.valueOf(min)+ ":00");
                        }
                        sec = 1;
<<<<<<< HEAD
                    } else if (min == 5) {
=======
                    } else if (sec ==10) {

>>>>>>> d00a842c35c1a25659e4fcb1cf3fd8d18759c542
                        Message.setForeground(Color.getHSBColor(316, -20, 150));
                        Message.setText("Start work!");
                        Message.setForeground(Color.getHSBColor(316, -20, 150));
                        work.setEnabled(true);
                        bool = false;
                    }
                }
                break;
                }
            }
        });
        /*
        * Обработчики событий
         */

        work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sec = 0;
                min = 0;
                buf = 1;
                work.setEnabled(false);
                class potok extends Thread {
                    public void run() {
                        synchronized (timer) {
                            if (bool == false) {
                                timer.start();
                            } else if (bool == true) {

                                timer.stop();
                            }
                        }
                    }
                }
                time = new potok();
                time.run();
            }
        });

        relax.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sec = 0;
                min = 0;
                buf = 2;
                relax.setEnabled(false);
                class potok2 extends Thread {
                    public void run() {
                        synchronized (timer) {
                            if (bool == true) {
                                timer.start();
                            } else if (bool == false) {
                                timer.stop();
                            }
                        }
                    }
                }
                time2 = new potok2();
                time2.run();
            }
        });
        /*
        * Обработчики событий
         */
        // Создание панели для фона приложения
        ImagePanel pp = new ImagePanel();
        pp.setLayout(new BorderLayout());
        try {
            pp.setImage(ImageIO.read(new File("main.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Создаем главную панель и вставляем основной фон с кнопками
        setLayout(new BorderLayout());
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(16,12,12,12));
        mainBox.add(box3);
        mainBox.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainBox.add(box4);
        pp.add(mainBox);
        pp.setPreferredSize(new Dimension(300, 310));
        setVisible(true);
        setContentPane(pp);
        pack();
        setResizable(true);
    }
}
