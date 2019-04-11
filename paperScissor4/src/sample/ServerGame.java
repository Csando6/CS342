package sample;

import java.util.ArrayList;

public class ServerGame extends Thread{
    String oneString = "";
    String twoString = "";
    Server myServer = new Server();
    Game myGame = new Game();

    public int onePoint(){
        return myGame.requestOne();
    }
    public int twoPoint(){
        return myGame.requestTwo();
    }
    public ArrayList getMoves(){
        return myGame.getMoves();
    }

    public boolean init(int port){
        oneString = "";
        twoString = "";
        //myGame = new Game();
        boolean temp = myServer.init(port);
        start();
        return temp;
    }

    @Override
    public void run(){
        while(true){
            //System.out.println("Hello fromt SG1:");
            System.out.printf("stringOne: %s stringTwo: %s \n",myServer.stringReady(0),myServer.stringReady(1) );
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(myServer.stringReady(0) && myServer.stringReady(1) ){
                oneString = myServer.getString(0);//get string from playerOne
                twoString = myServer.getString(1);//get string from playerTwo

                System.out.println(oneString );
                System.out.println(twoString );

                this.myGame.playerMove(oneString,twoString);
                myServer.setString(0,myGame.commandOne() );
                myServer.setString(1,myGame.commandTwo() );

                System.out.println("SG1: "+myGame.commandOne() );
                System.out.println("SG2: "+myGame.commandTwo() );
                oneString = "";
                twoString = "";
            }
        }
    }


}
