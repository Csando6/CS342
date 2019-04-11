package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    ServerSocket ss;

    ArrayList<ClientHandler> myClients = new ArrayList<>();

    public boolean stringReady(int index){
        if(index >= myClients.size()){
            return false;
        }
        return this.myClients.get(index).readReady();
    }
    public String getString(int clientIndex){
        if(clientIndex < myClients.size() ){
            return this.myClients.get(clientIndex).readData( );
        }
        else{
            return "";
        }
    }

    public void setString(int clientIndex, String dataString){
        if(clientIndex < myClients.size() ){//check if user is in client
            myClients.get(clientIndex).sendData(dataString);
        }
    }
    public void setStringAll(String data){
        for(ClientHandler elem: myClients){
            elem.sendData(data);
        }
    }


    public boolean init(int port){
        try{
            ss = new ServerSocket(port);
            System.out.println("Server has been initialized: "+ss.getLocalPort() );
            start();
            return true;

        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run(){
        try {
            while(true){
                Socket s = null;

                try{
                    s = ss.accept();
                    System.out.println("New Client connected: "+s);
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    ClientHandler temp = new ClientHandler(s,dis,dos);//add clientHandler to arraylist
                    Thread t = temp;
                    myClients.add(temp);
                    if(myClients.size() >= 2)
                        setStringAll("players:2");
                    else
                        setStringAll("players:1");


                    t.start();

                }catch(Exception e){

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
