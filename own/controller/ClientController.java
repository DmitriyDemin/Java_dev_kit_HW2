package own.controller;

import own.view.ClientGUI;

import java.util.Random;

public class ClientController {

    public String serverIp = "127.0.0.1";
    public String serverSocket = "12345";
    public String password = "********";
    public String login = "user" + new Random().nextInt(100,1000);
    public ServerController serv;
    public boolean serverWork;

    ClientGUI view = new ClientGUI(this);

    ClientController(ServerController serv){
        this.serv = serv;
        serverWork = serv.isServerWork();
    }

        public boolean connectToServer(){
        login = view.loginTF.getText();
        password = view.passwordTF.getText();
        if (serv.isServerWork()){
            serv.addUser(this);
            view.loginPnl.setVisible(true);
            return true;
        } else {
            view.consolePrintMsg("Нет соединения с сервером. Попробуйте ещё раз.");
        }
        return false;
    }

    public boolean disconnectFromServer() {
        serv.removeUser(this);
        return true;
    }


    public boolean isMsgValid(){
        String message = view.messageTF.getText();
        return  (!message.isEmpty() && message.length() < 255);
    }

    public void sendMsg(){
        serv.getUserMessage(this, view.messageTF.getText());
        view.messageTF.setText("");
    }

    public String getLogin() {
        return login;
    }


    public String getServerIp() {
        return serverIp;
    }

    public String getServerSocket() {
        return serverSocket;
    }

    public String getPassword() {
        return password;
    }

    public boolean isServerWork() {
        return serverWork;
    }


}
