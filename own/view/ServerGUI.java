package own.view;

import own.controller.ServerController;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ServerGUI extends JFrame {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int WINDOW_POSX =(screenSize.width - WINDOW_WIDTH) / 2;
    private int WINDOW_POSY = 200;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final String START_BTN_TEXT = "ПУСК";
    private static final String STOP_BTN_TEXT = "СТОП";
    private static final String WINDOW_TITLE = "Чат-сервер";

    ServerController controller;
    JButton startBtn = new JButton(START_BTN_TEXT);
    JButton stopBtn = new JButton(STOP_BTN_TEXT);
    JTextArea logConsole = new JTextArea();


    public ServerGUI(ServerController controller) {
        this.controller = controller;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setTitle(WINDOW_TITLE);
        setResizable(false);
        controller.sendStatusServer();
        JPanel buttonsPnl = new JPanel();

        logConsole.setEditable(false);
        logConsole.setLineWrap(true);
        logConsole.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logConsole);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        startBtn.addActionListener(e -> {
            if (!controller.isServerWork()){
                controller.setServerWork(true);
                consolePrint("Server started successfully.");
                consolePrint("Waiting for clients...");
            }
            else {
                consolePrint("Server already run.");
            }

        });

        stopBtn.addActionListener(e -> {
            if (controller.isServerWork()){
                controller.setServerWork(false);
                consolePrint("Server stopped.");
                controller.sendToAll("Сервер завершил свою работу");
                controller.getUserList().clear();
                controller.saveChatHistory();
            }
        });

        buttonsPnl.setLayout(new GridLayout(1, 2));
        buttonsPnl.add(startBtn);
        buttonsPnl.add(stopBtn);
        add(buttonsPnl, BorderLayout.SOUTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void consolePrint(String msg){
        logConsole.append(controller.getDateFormat().format(new Date()) + ": " + msg + "\n");
    }

}
