import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import sample.Game;

public class GameTest {
    @Test
    @DisplayName("1 Testing the game")
    public void testingGame(){
        Game myGame = new Game();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
    }

    @Test
    @DisplayName("2 Players point")
    public void testingPlayermoves(){
        Game myGame = new Game();
        myGame.playerMove("Rock","Rock"); //0,0
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);

        myGame.playerMove("Paper","Scissor");//0,1
        myGame.playerMove("Scissor","Lizard");//1,1
        myGame.playerMove("Lizard","Spock");//2,1
        assertEquals(myGame.requestOne(),2);
        assertEquals(myGame.requestTwo(),1);
    }

    @Test
    @DisplayName("3 reset Players")
    public void testingEmptyPlayer(){
        Game myGame = new Game();
        myGame.playerMove("Rock","Rock"); //0,0
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);

        myGame.playerMove("Paper","Scissor");//0,1
        myGame.playerMove("Scissor","Lizard");//1,1
        myGame.playerMove("Lizard","Spock");//2,1
        assertEquals(myGame.requestOne(),2);
        assertEquals(myGame.requestTwo(),1);

        myGame.init();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
    }

    @Test
    @DisplayName("4 get Players String")
    public void testingPlayerString(){
        Game myGame = new Game();
        myGame.playerMove("Rock","Rock"); //0,0
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);

        myGame.playerMove("Paper","Scissor");//0,1
        myGame.playerMove("Scissor","Lizard");//1,1
        myGame.playerMove("Lizard","Spock");//2,1
        assertEquals(myGame.requestOne(),2);
        assertEquals(myGame.requestTwo(),1);

        assertEquals(myGame.commandOne(),"true:2:1");
        assertEquals(myGame.commandTwo(),"false:1:2");
    }

    @Test
    @DisplayName("5 Brutal Test")
    public void brutalTest(){
        Game myGame = new Game();
        myGame.playerMove("Rock","Rock"); //0,0
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);

        for(int i=0;i<100;i++)
            myGame.playerMove("Paper","Scissor");//0,100
        for(int i=0;i<200;i++)
            myGame.playerMove("Scissor","Lizard");//200,100
        for(int i=0;i<25;i++)
            myGame.playerMove("Lizard","Spock");//225,100

        assertEquals(myGame.requestOne(),225);
        assertEquals(myGame.requestTwo(),100);
        assertEquals(myGame.commandOne(),"true:225:100");
        assertEquals(myGame.commandTwo(),"false:100:225");
    }

    @Test
    @DisplayName("6 Brutal Test with empty")
    public void brutalTestwithEmpty(){
        Game myGame = new Game();
        myGame.playerMove("Rock","Rock"); //0,0
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);

        for(int i=0;i<100;i++)
            myGame.playerMove("Paper","Scissor");//0,100
        for(int i=0;i<200;i++)
            myGame.playerMove("Scissor","Lizard");//200,100
        for(int i=0;i<25;i++)
            myGame.playerMove("Lizard","Spock");//225,100

        assertEquals(myGame.requestOne(),225);
        assertEquals(myGame.requestTwo(),100);
        assertEquals(myGame.commandOne(),"true:225:100");
        assertEquals(myGame.commandTwo(),"false:100:225");

        myGame.init();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
        assertEquals(myGame.commandOne(),"false:0:0");
        assertEquals(myGame.commandOne(),"false:0:0");
    }

    @Test
    @DisplayName("7 Run test within a tests")
    public void moreTesting(){
        Game myGame = new Game();
        for(int j=0;j<100;j++){
            myGame.init();
            myGame.playerMove("Rock","Rock"); //0,0
            assertEquals(myGame.requestOne(),0);
            assertEquals(myGame.requestTwo(),0);

            for(int i=0;i<100;i++)
                myGame.playerMove("Paper","Scissor");//0,100
            for(int i=0;i<200;i++)
                myGame.playerMove("Scissor","Lizard");//200,100
            for(int i=0;i<25;i++)
                myGame.playerMove("Lizard","Spock");//225,100

            assertEquals(myGame.requestOne(),225);
            assertEquals(myGame.requestTwo(),100);
            assertEquals(myGame.commandOne(),"true:225:100");
            assertEquals(myGame.commandTwo(),"false:100:225");
        }
        myGame.init();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
        assertEquals(myGame.commandOne(),"false:0:0");
        assertEquals(myGame.commandOne(),"false:0:0");
    }

    @Test
    @DisplayName("8 Testing all the options")
    public void biasedWinner(){
        Game myGame = new Game();
        String []myString = {"Paper","Scissor","Rock","Lizard","Spock"};
        myGame.playerMove(myString[0],myString[1]);//0,1
        myGame.playerMove(myString[0],myString[2]);//1,1
        myGame.playerMove(myString[0],myString[3]);//1,2
        myGame.playerMove(myString[0],myString[4]);//2,2

        assertEquals(myGame.requestOne(),2);
        assertEquals(myGame.requestTwo(),2);
        myGame.init();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
    }

    @Test
    @DisplayName("9 Testing all the options 2")
    public void biasedWinner2(){
        Game myGame = new Game();
        String []myString = {"Paper","Scissor","Rock","Lizard","Spock"};
        for(int i=0;i<100;i++) {
            myGame.playerMove(myString[0], myString[1]);//0,1
            myGame.playerMove(myString[0], myString[2]);//1,1
            myGame.playerMove(myString[0], myString[3]);//1,2
            myGame.playerMove(myString[0], myString[4]);//2,2
        }
        assertEquals(myGame.requestOne(),200);
        assertEquals(myGame.requestTwo(),200);
        myGame.init();
        assertEquals(myGame.requestOne(),0);
        assertEquals(myGame.requestTwo(),0);
    }

    @Test
    @DisplayName("10 One more test")
    public void moreMoves(){
        Game myGame = new Game();
        for(int i=0;i<100;i++) {
            myGame.playerMove("Rock", "Rock"); //0,0
        }
        assertEquals(myGame.requestOne(), 0);
        assertEquals(myGame.requestTwo(), 0);
        myGame.init();
        assertEquals(myGame.requestOne(), 0);
        assertEquals(myGame.requestTwo(), 0);

        String []myString = {"Paper","Scissor","Rock","Lizard","Spock"};
        for(int i=0;i<100;i++){
            myGame.playerMove(myString[0], myString[1]);//0,1
            myGame.playerMove(myString[0], myString[2]);//1,1
            myGame.playerMove(myString[0], myString[3]);//1,2
            myGame.playerMove(myString[0], myString[4]);//2,2
        }
        assertEquals(myGame.requestOne(), 200);
        assertEquals(myGame.requestTwo(), 200);
        myGame.init();
        assertEquals(myGame.requestOne(), 0);
        assertEquals(myGame.requestTwo(), 0);
    }




}
