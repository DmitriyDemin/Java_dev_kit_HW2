package own.repositoiy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository implements IRepository {

    private String fileHistoryPath;
    private String fileHistoryPathDefault = "sem2/src/own/repositoiy/log.txt";
//    private List<String> msgHistory = new ArrayList<>();
    private File chatHistory;


    public Repository(String fileHistoryPath) {
        this.fileHistoryPath = fileHistoryPath;
        chatHistory = new File(fileHistoryPath);

    }

    public Repository() {
        chatHistory = new File(fileHistoryPathDefault);

    }

    @Override
    public void writeChatHistory(List<String> msgHistory) {
        if (!chatHistory.exists()){
            try {
                chatHistory.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (chatHistory.canWrite()){
            try (FileWriter fw = new FileWriter(chatHistory, false)){
                for(String item: msgHistory){
                    fw.write(item + "\n");
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<String> sendShatHistory() {
        List<String> msgHistory = new ArrayList<>();
        if (!chatHistory.exists()){
            try {
                chatHistory.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

            if (chatHistory.canRead()){
                try (Scanner scan = new Scanner(chatHistory)){
                    msgHistory.clear();
                    while (scan.hasNext()){
                        msgHistory.add(scan.nextLine());
                    }
                    return msgHistory;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return null;
        }




}




