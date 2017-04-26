/**
 * Created by Artem on 25.03.2017.
 * Класс который инкапсулирует сообщение
 * Сообщение представляет собой [][][]в начале идут 3 цифры означающие код сообщения
 * далее в зависимости от кода идет "декомпрессия" сообщения
 */

public class Message {
    private int numberOfCommand;
    private String message;
    private String login;
    private String loginLength;
    private String password;
    private int coordX;
    private int coordY;

    public Message(String decompressMessage){
        String s = decompressMessage.substring(0,3);
        int number = Integer.parseInt(s);
        switch (number){
            case 101:login = decompressMessage.substring(3); break;
            case 100:{
                int loginLength = Integer.parseInt(decompressMessage.substring(3,5));
                int passwordLength = Integer.parseInt(decompressMessage.substring(5,7));
                login = decompressMessage.substring(7,7+loginLength+1);
                password = decompressMessage.substring(7+loginLength,7+loginLength+passwordLength);
                setLoginLength(loginLength);

            }; break;
            case 102:{
                int loginLength = Integer.parseInt(decompressMessage.substring(3,5));
                login = decompressMessage.substring(5,5+loginLength);
                coordX = Integer.parseInt(decompressMessage.substring(5+loginLength,6+loginLength));
                coordY = Integer.parseInt(decompressMessage.substring(6+loginLength,7+loginLength));
            } break;
            case 200:{
                int loginLength = Integer.parseInt(decompressMessage.substring(3,5));
                login = decompressMessage.substring(5,5+loginLength);
                message = decompressMessage.substring(5+loginLength);

                setLoginLength(loginLength);
            }; break;
            default:number = -1;
        }
        numberOfCommand = number;
    }

    public Message(int numberOfCommand, String login, String message){
        this.numberOfCommand = numberOfCommand;
        this.login = login;
        this.message = message;
        setLoginLength(login.length());
    }
    public Message(int numberOfCommand,String login){
        this.numberOfCommand = numberOfCommand;
        this.login = login;
        setLoginLength(login.length());
    }
    public Message(String login, int coordX,int coordY){
        numberOfCommand = 102;
        this.login = login;
        setLoginLength(login.length());
        this.coordX = coordX;
        this.coordY = coordY;
    }
    public String toString() {
        int numberOfCommand = this.getNumberOfCommand();
        String textMessage = "";
        switch (numberOfCommand) {
            case 101: {
                textMessage += "101";
                textMessage += this.getLogin();
            } break;
            case 102: {
                textMessage += "102";
                textMessage += loginLength;
                textMessage += login;
                textMessage += coordX;
                textMessage += coordY;
            }
            case 200: {
                textMessage += "200";
                textMessage += this.getLoginLength();
                textMessage += this.getLogin();
                textMessage += this.getMessage();
            } break;
        }
        return textMessage;
    }
    public int getNumberOfCommand() {
        return numberOfCommand;
    }
    public String getMessage(){
            return message;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword() {
        return password;
    }
    private void setLoginLength(int loginLength){
        if(loginLength<10){
            this.loginLength = "0" + loginLength;
        }else{
            this.loginLength = "" +loginLength;
        }
    }
    public String getLoginLength() {
        return loginLength;
    }
    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
