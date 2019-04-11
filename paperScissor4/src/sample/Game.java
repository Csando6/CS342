package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class Game{
    ArrayList<Pair<String,String>> playerHand = new ArrayList<>();
    int onePoint=0;
    int twoPoint=0;
    String oneString="";
    String twoString="";
    boolean oneWin = false;
    boolean twoWin = false;


    String nameOne="";//optional, give players a name
    String nameTwo="";

    public void Game(){
        this.init();
    }

    public void init(){
        playerHand = new ArrayList<Pair<String, String>>( );
        onePoint = twoPoint = 0;
        oneString = twoString = "";
        oneWin = twoWin = false;
    }

    public void playerMove(String playerOne, String playerTwo){ //determines who has won
        Pair<String,String> temp = new Pair<>(playerOne, playerTwo);
        this.playerHand.add(temp );

        if(playerOne.equals(playerTwo) ){
            oneWin = twoWin = false;
        }
        else if(playerOne.equals("Paper") ){
            if(playerTwo.equals("Scissor") || playerTwo.equals("Lizard") ){
                oneWin = false;
                twoWin = true;
                this.twoPoint++;
            }
            else{
                oneWin = true;
                twoWin = false;
                this.onePoint++;
            }
        }
        else if(playerOne.equals("Rock") ){
            if(playerTwo.equals("Paper") || playerTwo.equals("Spock") ){
                oneWin = false;
                twoWin = true;
                this.twoPoint++;
            }
            else{
                oneWin = true;
                twoWin = false;
                this.onePoint++;
            }
        }
        else if(playerOne.equals("Scissor") ){
            if(playerTwo.equals("Rock") || playerTwo.equals("Spock") ){
                oneWin = false;
                twoWin = true;
                this.twoPoint++;
            }
            else{
                oneWin = true;
                twoWin = false;
                this.onePoint++;
            }
        }
        else if(playerOne.equals("Lizard") ){
            if(playerTwo.equals("Scissor") || playerTwo.equals("Rock") ){
                oneWin = false;
                twoWin = true;
                this.twoPoint++;
            }
            else{
                oneWin = true;
                twoWin = false;
                this.onePoint++;
            }
        }
        else if(playerOne.equals("Spock") ){
            if(playerTwo.equals("Paper") || playerTwo.equals("Lizard") ){
                oneWin = false;
                twoWin = true;
                this.twoPoint++;
            }
            else{
                oneWin = true;
                twoWin = false;
                this.onePoint++;
            }
        }
        //oneWin = true;
        //twoWin = true;
        //onePoint++;
        //twoPoint++;
    }

    public int requestOne(){
        return this.onePoint;
    }

    public int requestTwo(){
        return this.twoPoint;
    }
    //public
    public ArrayList getMoves(){
        return this.playerHand;
    }

    public Pair getMove(int i){
        return this.playerHand.get(i);
    }

    public void printMoves(){
        for(int i=0;i<this.playerHand.size();i++){
            Pair<String,String> temp = this.playerHand.get(i);
            System.out.printf("%d) %s, %s\n",i ,temp.getKey(),temp.getValue() );
        }
    }

    public String commandOne(){
        return (oneWin?"true":"false") +":"+onePoint+":"+twoPoint;
    }
    public String commandTwo(){
        return (twoWin?"true":"false") +":"+twoPoint+":"+onePoint;
    }
}