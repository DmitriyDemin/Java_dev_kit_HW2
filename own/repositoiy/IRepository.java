package own.repositoiy;

import java.util.List;

public interface IRepository {

//    void createHistoryFile();
    void writeChatHistory(List<String> msgHistory);
    List<String> sendShatHistory();




}
