import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Artem on 25.03.2017.
 */
public class Receiver implements Runnable{
    Socket clientSocket;
    ServerGamer myServerGamer;

    public Receiver(Socket clientSocket){
        this.clientSocket = clientSocket;

    }
    public void addListener(ServerGamer serverGamer){
        myServerGamer = serverGamer;
    }

    @Override
    public void run(){
        InputStreamReader inStream = null;
        BufferedReader r = null;
        try {
            inStream = new InputStreamReader(clientSocket.getInputStream());
            r = new BufferedReader(inStream);
            while(true){
                String inputMessage = null;
                if(r!=null){
                    inputMessage = r.readLine();
                    if(inputMessage!=null){
                        Message message = new Message(inputMessage);

                        if(message.getNumberOfCommand()!=-1){
                            myServerGamer.Handler(message);
                        }
                    }
                }
            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
