package own.view;

import own.controller.ClientController;
import own.controller.ServerController;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ClientGUI extends JFrame {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final String WINDOW_TITLE = "Чат-клиент";
    private static final String LOGIN_BTN_TEXT = "Enter chat";
    private static final String SENDMSG_BTN_TEXT = "Send >>";
    private String servStatusText = "default";
    private static int counter;
    private int WINDOW_POSX = 150 + counter * (WINDOW_WIDTH + 50);

   public JTextArea chatConsole = new JTextArea();
   public JButton loginBtn = new JButton(LOGIN_BTN_TEXT);
   public JTextField messageTF = new JTextField();
   public JButton sendMsgBtn = new JButton(SENDMSG_BTN_TEXT);
   public JLabel statusServer = new JLabel(servStatusText);
   public JTextField loginTF = new JTextField();
   public JTextField passwordTF = new JTextField();


    ClientController controller;

    public DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public JPanel loginPnl = new JPanel(new GridLayout(2 , 3));

    public ClientGUI(ClientController controller) {
        this.controller = controller;
        counter ++;

//        JTextArea chatConsole = new JTextArea();
        JTextField serverIpTF = new JTextField(controller.getServerIp());
        JTextField serverSocketTF = new JTextField(controller.getServerSocket());
        loginTF = new JTextField(controller.getLogin());
        passwordTF = new JTextField(controller.getPassword());





        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, 500);
        setTitle(WINDOW_TITLE);
        setResizable(false);

        chatConsole.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (!controller.isServerWork()){
                    loginPnl.setVisible(true);
                }
            }
        });

        JPanel messagePnl = new JPanel(new BorderLayout());

        loginBtn.addActionListener(e-> {
            if (loginTF.getText().isEmpty()) {
                loginTF.requestFocus(true);
            } else if (passwordTF.getText().isEmpty()) {
                passwordTF.requestFocus(true);
            } else {
                controller.connectToServer();
                messageTF.requestFocus(true);
            }
        });


        sendMsgBtn.addActionListener(e -> {
            if (controller.isMsgValid()) controller.sendMsg();
        });


        messageTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && controller.isMsgValid()) {
                    controller.sendMsg();
                }
            }
        });

        loginPnl.add(serverIpTF);
        loginPnl.add(serverSocketTF);
        loginPnl.add(statusServer);//не обновляется?
        loginPnl.add(loginTF);
        loginPnl.add(passwordTF);
        loginPnl.add(loginBtn);
        add(loginPnl, BorderLayout.NORTH);


        chatConsole.setEditable(false);

        chatConsole.setLineWrap(true);
        chatConsole.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatConsole);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(scrollPane, BorderLayout.CENTER);


        messagePnl.add(messageTF, BorderLayout.CENTER);
        messagePnl.add(sendMsgBtn, BorderLayout.EAST);
        add(messagePnl, BorderLayout.SOUTH);

        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnectFromServer();
                setVisible(false);
            }
        });
    }

    public void consolePrintMsg(String sysMsgText){
        chatConsole.append("(" + timeFormat.format(new Date()) + ") " + sysMsgText + "\n");
    }

    public void setServStatusText(String servStatusText) {
        this.servStatusText = servStatusText;
    }

}
