package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    //boolean isServer = true;
    boolean isServer = false;
    ServerGame myServerGame;
    Client myClient;

    int sceneWidth=500;
    int sceneHight=500;

    @Override
    public void start(Stage primaryStage) throws Exception{
        if(this.isServer){
            myServerGame = new ServerGame();
            serverScene(primaryStage);
        }
        else{
            myClient = new Client();
            clientStart(primaryStage);
        }
    }

    public void serverScene(Stage primaryStage) {
        BorderPane scene = new BorderPane();
        VBox centerScene = new VBox();
        HBox titleBox = new HBox();
        HBox titleBox2 = new HBox();

        Text titleScene = new Text("Enter port to open:");
        TextField insertText = new TextField();
        Button submit = new Button("Submit");
        Button refresh = new Button("refresh");

        titleBox.getChildren().add(titleScene);
        titleBox2.getChildren().addAll(insertText, submit);
        centerScene.getChildren().addAll(titleBox, titleBox2);
        scene.setCenter(centerScene);
        scene.setRight(displayUserPoint(primaryStage));

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String userInput = "";
                Integer userNum;
                userInput += insertText.getCharacters();
                userNum = Integer.parseInt(userInput);
                System.out.println(userInput);
                System.out.println(userNum);
                myServerGame.init(userNum);
                insertText.clear();
            }
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                serverScene(primaryStage);
            }
        });

        primaryStage.setScene(new Scene(scene, sceneWidth,sceneHight));
        primaryStage.show();
    }

    public HBox displayUserPoint(Stage primaryStage){
        HBox center = new HBox();
        VBox refresh = new VBox();
        VBox left = new VBox();
        VBox right = new VBox();
        Button refreshButton = new Button("Refresh");
        refresh.getChildren().addAll(refreshButton );
        Label oneLabel = new Label("player: One");
        Label twoLabel = new Label("player: Two");
        Label pointOne = new Label(Integer.toString(myServerGame.onePoint()));
        Label pointTwo = new Label(Integer.toString(myServerGame.twoPoint()));
        left.getChildren().addAll(oneLabel,pointOne);
        right.getChildren().addAll(twoLabel,pointTwo);
        center.getChildren().addAll(refresh,left,right );

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                serverScene(primaryStage);
            }
        });

        return center;
    }

    public void clientStart(Stage primaryStage){
        BorderPane scene = new BorderPane();
        VBox centerScene = new VBox();
        Button startButton = new Button("Start");

        //centerScene.getChildren().addAll(startButton);
        scene.setCenter(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientScene(primaryStage);
            }
        });

        primaryStage.setScene(new Scene(scene, sceneWidth,sceneHight));
        primaryStage.show();

    }

    public void clientScene(Stage primaryStage){
        BorderPane scene = new BorderPane();
        VBox centerBox = new VBox();
        VBox ipBox = new VBox();
        VBox portBox = new VBox();
        HBox topRow = new HBox();
        HBox submitBox = new HBox();

        Text title = new Text("Client Submit port");
        Text ipTitle = new Text("Server IP:");
        Text portTitle = new Text("port");
        TextField userIP = new TextField();
        TextField userPort = new TextField();
        Button submit = new Button("Submit");

        ipBox.getChildren().addAll(ipTitle,userIP);
        submitBox.getChildren().addAll(userPort,submit);
        portBox.getChildren().addAll(portTitle,submitBox);
        topRow.getChildren().addAll(ipBox,portBox);
        centerBox.getChildren().addAll(topRow);
        scene.setCenter(centerBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String getIP = "";
                String getPort = "";
                Integer getPortInt;

                getIP += userIP.getCharacters();
                getPort += userPort.getCharacters();
                getPortInt = Integer.parseInt(getPort );
                myClient.init(getIP,getPortInt);
                myClient.start();

                //FIXME: only transition when socket is successful
                clientWaiting(primaryStage);
            }
        });
        scene.setBottom(displayScore() );
        primaryStage.setScene(new Scene(scene, sceneWidth,sceneHight) );
        primaryStage.show();
    }

    public void clientWaiting(Stage primaryStage) {

        BorderPane scene = new BorderPane();
        VBox centerScene = new VBox();
        Text sceneTitle = new Text("Waiting for players ...");
        Button clickToContinue = new Button("Click to Continue");

        clickToContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //add Game transition only when two players are on
                if(myClient.readyToStart() )
                    clientPlaying(primaryStage);

            }
        });

        centerScene.getChildren().addAll(sceneTitle, clickToContinue);
        scene.setCenter(centerScene);

        scene.setBottom(displayScore() );
        primaryStage.setScene(new Scene(scene, sceneWidth, sceneHight));
        primaryStage.show();

    }


    public void clientPlaying(Stage primaryStage){
        BorderPane scene = new BorderPane();
        HBox horizScene = new HBox();
        //VBox centerScene = new VBox();
        VBox leftBar = new VBox();
        VBox rightBar = new VBox();
        Button[] userButton = new Button[5];
        String name[] = {"Rock","Paper","Scissor","Lizard","Spock"};
        userButton[0] = new Button(name[0]);
        userButton[1] = new Button(name[1]);
        userButton[2] = new Button(name[2]);
        userButton[3] = new Button(name[3]);
        userButton[4] = new Button(name[4]);
        //userButton.setGraphic(setCardImage("Paper"));

        ArrayList<Button> userButtons = new ArrayList<>();
        for(int i=0;i<5;i++){
            Button temp = new Button(name[i]);
            temp.setGraphic(setCardImage(name[i]) );
            userButtons.add(temp);

        }



//        for(Button elem : userButtons){
//            centerScene.getChildren().add(elem);
//        }
        for(int i=0;i<3;i++){
            leftBar.getChildren().add(userButtons.get(i));
        }
        for(int i=3;i<5;i++){
            rightBar.getChildren().add(userButtons.get(i));
        }
        horizScene.getChildren().addAll(leftBar,rightBar);

        //centerScene.getChildren().add(elem);
        scene.setCenter(horizScene);

        for(int i=0;i<5 ;i++){
            int finalI = i;
            userButton[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String temp = name[finalI];
                    System.out.println("Main client T1: "+temp);
                    myClient.sendMessage(temp);
                    clientWaiting2(primaryStage );
                    //System.out.println("Button: "+temp+" has been clicked!");
                }
            });
        }

        scene.setBottom(displayScore() );
        primaryStage.setScene(new Scene(scene,sceneWidth,sceneHight));
        primaryStage.show();
    }

    public void clientWaiting2(Stage primaryStage) {

        BorderPane scene = new BorderPane();
        VBox centerScene = new VBox();
        Text sceneTitle = new Text("Waiting for players ...");
        Button clickToContinue = new Button("Click to Continue");

        clickToContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //add Game transition only when two players are on
                if(myClient.getPoints() )
                    clientDisplayScore(primaryStage);

            }
        });

        centerScene.getChildren().addAll(sceneTitle, clickToContinue);
        scene.setCenter(centerScene);

        scene.setBottom(displayScore() );
        primaryStage.setScene(new Scene(scene, sceneWidth, sceneHight));
        primaryStage.show();

    }

    public void clientDisplayScore(Stage primaryStage){
        BorderPane scene = new BorderPane();
        HBox rowScene = new HBox();
        VBox centerLeft = new VBox();
        VBox center = new VBox();
        VBox centerRight = new VBox();
        Button playAgain = new Button("Click to Continue");

        center.getChildren().addAll(playAgain);
        rowScene.getChildren().addAll(centerLeft,center,centerRight);
        scene.setCenter(rowScene);

        playAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientPlaying(primaryStage);
            }
        });

        scene.setBottom(displayScore() );
        primaryStage.setScene(new Scene(scene,sceneWidth,sceneHight) );
        primaryStage.show();

    }

    public HBox displayScore(){
        HBox display = new HBox();
        VBox playerOne = new VBox();
        VBox playerTwo = new VBox();
        VBox message = new VBox();
        VBox messageBox = new VBox();
        Text playerOneTitle = new Text("player One:");
        Text playerTwoTitle = new Text("player Two: ");
        Text messageTitle = new Text("Message: ");
        int myNum = this.myClient.onePoint;
        int myNum2 = this.myClient.twoPoint;
        String temp = myClient.copyMessage();
        Text playerOnePoint = new Text(Integer.toString(myNum) );
        Text playerTwoPoint = new Text(Integer.toString(myNum2));
        Text clientMessage = new Text(temp);

        playerOne.getChildren().addAll(playerOneTitle,playerOnePoint );
        playerTwo.getChildren().addAll(playerTwoTitle,playerTwoPoint );
        message.getChildren().addAll(messageTitle,messageBox);
        messageBox.getChildren().addAll(clientMessage );
        display.getChildren().addAll(playerOne,playerTwo,message );

        return display;
    }

    public ImageView setCardImage(String myString){
        String tempStr = "/PNG/" + myString + ".jpeg";
        //SSystem.out.printf("Card: %s\n", tempStr);
        Image picture = new Image(tempStr);
        ImageView vie = new ImageView(picture);
        vie.setFitWidth(75);
        vie.setPreserveRatio(true);
        return vie;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
