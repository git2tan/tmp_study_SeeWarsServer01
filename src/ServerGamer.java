import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Artem on 25.03.2017.
 * серверный геймер
 * этот объект содержит в себе поток
 */
public class ServerGamer {
    private boolean isAnonim;
    private Socket toClientSocket;
    private String login;
    private String password;
    private int id;
    private Server server;
    private PrintWriter toClient;

    public ServerGamer(Socket socket, Server server){
        isAnonim = true;
        login = null;
        password = null;
        this.server = server;
        toClientSocket =socket;
        try{
            toClient = new PrintWriter(toClientSocket.getOutputStream(), true);
        }catch(IOException e){
            e.printStackTrace();
        }

        Receiver myReceiver = new Receiver(socket);
        myReceiver.addListener(this);
        Thread myReceiverThread = new Thread(myReceiver);
        myReceiverThread.start();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    //обработчик сообщений из потока
    public void Handler(Message message){
        int numberOfCommand = message.getNumberOfCommand();

        switch(numberOfCommand){
            case -1:;break;
            case 100:{
                server.findGamer(message.getLogin(),message.getPassword(),this);
            };break;
            case 101:{
                login = message.getLogin();
                //sendMessage(message);
            };break;
            case 102:{
                server.sendMessageToAll(new Message(200, message.getLogin(),"Стрельнул по " + message.getCoordX() + message.getCoordY()));
            } break;
            case 200:{
                server.sendMessageToAll(message);
            };break;
        }
    }

    public boolean checkLoginAndPassword(String login, String password){
        if(this.login.equals(login)&&this.password.equals(password))
            return true;
        else
            return false;
    }

    public void activate(ServerGamer serverGamer){
        isAnonim = false;
        login = new String(serverGamer.getLogin());
        password = new String(serverGamer.getPassword());
        id = serverGamer.getId();
    }

    public void sendMessage(Message message){
        int numberOfCommand = message.getNumberOfCommand();
        switch(numberOfCommand){
            case 101:toClient.println(message);
            case 200:{
                toClient.println(message);
            };break;

        }
    }
    public void sendText(String text){
        toClient.println(text);
    }
    public boolean isExist(String login){
        if(this.getLogin().compareTo(login)==1){
            return true;
        }else{
            return false;
        }
    }
}
