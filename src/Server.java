import java.util.ArrayList;

/**
 * Created by Artem on 25.03.2017.
 */
public class Server {
    ArrayList<ServerGamer> anonimGamers;            //
    ArrayList<ServerGamer> registeredGamers;
    ArrayList<ServerGamer> activeGamers;
    int indexOfAnonim;
    //Connector myConnector;

    public Server(){
        indexOfAnonim =0;
        anonimGamers = new ArrayList<ServerGamer>();
        registeredGamers = new ArrayList<ServerGamer>();
        activeGamers = new ArrayList<ServerGamer>();
    }

    public void work(){
        Connector myConnector = new Connector(this);    //создает постоянного слушателя порта который при подключении  извне создает
        Thread myThread = new Thread(myConnector);
        myThread.start();
    }

    public void findGamer(String login, String password, ServerGamer gamer){
        for (ServerGamer one:registeredGamers){
            if(one.checkLoginAndPassword(login,password)){
                gamer.activate(one);
                activeGamers.add(gamer);
            }
        }
    }
    public void addAnonimGamer(ServerGamer gamer){
        anonimGamers.add(gamer);
    }
    public void sendTextToAll(String text){
        for(ServerGamer one: activeGamers)
            one.sendText(text);
        for(ServerGamer one:anonimGamers)
            one.sendText(text);
    }

    public void sendTextToOne(String text, String login){
        for(ServerGamer one:activeGamers){
            if(one.isExist(login))
                one.sendText(text);
        }
    }
    public void sendMessageToAll(Message message){
        for(ServerGamer one: activeGamers)
            one.sendMessage(message);
        for(ServerGamer one:anonimGamers)
            one.sendMessage(message);
    }

    public int incrementIndexOfAnonim(){
        int tmp = indexOfAnonim;
        ++indexOfAnonim;
        return tmp;
    }
}
