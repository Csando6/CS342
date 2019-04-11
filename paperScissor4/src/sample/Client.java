package sample;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    String ipAddress;
    int port;

    Scanner scn = new Scanner(System.in );
    InetAddress ip;
    Socket S;
    DataInputStream dis;
    DataOutputStream dos;

    String txData="";
    String rxData="";
    public int onePoint = 0;
    public int twoPoint = 0;
    public boolean oneWinner = false;

    boolean SConnected = false;

    void init(String ipAddress,int port){
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void sendMessage(String message){
        txData = message;
    }
    public String readMessage(){
        String temp = rxData;
        rxData = "";
        return temp;
    }

    public String copyMessage(){
        return rxData;
    }

    public boolean readyToStart(){
        if(rxData.equals("players:2") ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean getPoints(){
        String[] myData = rxData.split(":");
        if(myData.length != 3){
            System.out.println("Client,getPoint: "+myData.length );
            return false;
        }

        if(myData[0].equals("false") ){
            oneWinner = false;
        }else if(myData[0].equals("true") ){
            oneWinner = true;
        }
        else{
            return false;
        }

        if(isDigit(myData[1]) ){
            onePoint = Integer.parseInt(myData[1]);
        }
        else{
            return false;
        }

        if(isDigit(myData[2]) ){
            twoPoint = Integer.parseInt(myData[2] );
        }
        else{
            return false;
        }
        rxData = "";
        return true;
    }

    public boolean isDigit(String data){
        int myNum;
        try{
            myNum = Integer.parseInt(data);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void run(){
        try {
            ip = InetAddress.getByName(ipAddress);
            S = new Socket(ip,port);
            dis = new DataInputStream(S.getInputStream() );
            dos = new DataOutputStream(S.getOutputStream() );

            while (true) {
                //System.out.println("Hello from Client");
                if(!txData.isEmpty() ){//if there is a message to send
                    dos.writeUTF(txData);
                    txData = "";
                }
                if(dis.available()>0){//if there is a message to receive, read it
                    rxData = dis.readUTF();
                    System.out.println(rxData);
                }

                if(rxData.equals("Exit")){
                    S.close();
                    break;
                }
            }
            scn.close();
            dis.close();
            dos.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
