package own.controller;

import own.repositoiy.Repository;
import own.view.ServerGUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ServerController {

    private List<ClientController> userList = new ArrayList<>();
    private boolean serverWork;
    private List<String> msgHistory = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");

    Repository repo = new Repository();
    ServerGUI view = new ServerGUI(this);

    public void saveChatHistory(){
        repo.writeChatHistory(msgHistory);
    }

    private List<String> loadChatHistory (){
        return repo.sendShatHistory();
    }

    public boolean addUser(ClientController user){
        userList.add(user);
        view.consolePrint("User " + user.getLogin() + " connected to server.");
        sendToCurrentUser(user, "Соединение установлено. Добро пожаловать, " + user.getLogin() + "!");
        sendToCurrentUser(user, "Сегодня " + dateFormat1.format(new Date()) + " г.");
        sendToAll("Пользователь " + user.getLogin() + " вошёл в чат.");
        String history = loadChatHistory().toString().replaceAll("\\[\\]","");
        history = history.replaceAll("[\\[\\]]","");
        history = history.replaceAll(", ", "\n");
        user.view.consolePrintMsg(
                "История сообщений ============\n"
                + history +
                " =====================================");
        return true;
    }

    public void removeUser(ClientController user) {
        if (userList.contains(user)) {
            userList.remove(user);
            view.consolePrint("User " + user.getLogin() + " disconnected.");
            sendToAll("Пользователь " + user.getLogin() + " покинул чат.");
        }
        else {
            view.consolePrint("Such login (" + user.getLogin() + ") is absent in user list.");
        }
    }

    public void sendToAll(String msg){
        if (!userList.isEmpty()) {
            for (ClientController user : userList) {
                user.view.consolePrintMsg(msg);
            }
        }
    }

    private void sendToCurrentUser(ClientController user, String msg){
         user.view.consolePrintMsg(msg);
    }

    public void getUserMessage(ClientController user, String msgText) {
        view.consolePrint(user.getLogin() + ": " + msgText);
        sendToAll(user.getLogin() + ": " + msgText);
        msgHistory.add("(" + dateFormat.format(new Date()) + ") " + user.getLogin() + ": " + msgText);
        saveChatHistory();
    }

    public void sendStatusServer(){
        if (!userList.isEmpty()) {
            for (ClientController user : userList) {
                if (isServerWork()){
                    user.view.setServStatusText("Server ON");

                }else user.view.setServStatusText("Server OFF");

            }
        }

    }

    public List<ClientController> getUserList() {
        return userList;
    }

    public void setServerWork(boolean serverWork) {
        this.serverWork = serverWork;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }
    public boolean isServerWork() {
        return serverWork;
    }
}
