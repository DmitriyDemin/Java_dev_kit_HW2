package own.controller;

import own.view.ClientGUI;

public class ChatApp {

    public static void main(String[] args) {


        ServerController server = new ServerController();
        ClientController client1 = new ClientController(server);
        ClientController client2 = new ClientController(server);





    }
}
