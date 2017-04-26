import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Artem on 25.03.2017.
 * в бесконечном цикле подключает клиентов
 */
public class Connector implements Runnable {
    Server myServer;
    public Connector(Server server){
        myServer = server;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            while(true){
                Socket tmp = null;
                tmp = serverSocket.accept();
                if(tmp!=null) {                                         //при подключении создает нового анонимного серверного геймера
                    ServerGamer gamer = new ServerGamer(tmp, myServer); //этому серверному геймеру сразу задается сервер в виде порта и ссылки на сам сервер
                    myServer.addAnonimGamer(gamer);                     //вызывается запрос к серверу на добавление игрока

                    gamer.sendMessage(new Message(101,"Anonim"+myServer.incrementIndexOfAnonim()));
                    myServer.sendMessageToAll(new Message(200,"SERVER!","Подключился новый клиент!"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
