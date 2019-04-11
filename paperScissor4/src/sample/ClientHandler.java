package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    String txData = "";//sending
    String rxData = "";//receiving

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(Socket mySocket,DataInputStream Din, DataOutputStream Dout){
        this.s = mySocket;
        this.dis = Din;
        this.dos = Dout;
    }

    public void sendData(String data){
        txData = data;
    }

    public String readData(){
        String temp = rxData;
        rxData = "";
        return temp;
    }

    boolean readReady(){
        if(rxData.isEmpty() ){
            return false;
        }
        return true;
    }

    @Override
    public void run(){
        while(true){
            try{
                if(!txData.isEmpty()){
                    dos.writeUTF(txData);
                    txData = "";
                }
                if(dis.available()>0){
                    rxData = dis.readUTF();
                    System.out.println(rxData);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
