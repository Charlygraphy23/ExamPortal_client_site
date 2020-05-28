package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.database.DBHandeller;
import sample.model.Paper;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MainPageController {

    @FXML
    private BorderPane pane;

    @FXML
    private Label timmer;

    @FXML
    private Label questionNumber;

    @FXML
    private Label mQuestions;

    @FXML
    private JFXRadioButton fistAnswar;

    @FXML
    private JFXRadioButton secondAnswar;

    @FXML
    private JFXRadioButton thirdAnswar;

    @FXML
    private JFXRadioButton fourthAnswar;

    @FXML
    private JFXButton nextQ;

    @FXML
    private VBox resultPane;

    @FXML
    private Label scoreBoard;

    @FXML
    private Label failedLabel;

    @FXML
    private Label passedLabel;

    @FXML
    private JFXButton submitA;

    @FXML
    private Separator sbar2;

    @FXML
    private Separator sbar1;

    @FXML
    private Label logOutButton;

    @FXML
    private Label namePlate;


    @FXML
    void close(KeyEvent event) {

    }

    private DBHandeller handeller;
    private ArrayList<Paper> listOfPapers;
    private int c=1;
    private int marks=0;
    private String ans;
    private int inittime=60;
    private int initmin=1;
    private String fName,lName;
    private  Timeline timeline;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        handeller=new DBHandeller();

        //getting Full name of student
        ResultSet s=handeller.getUserDetails();
        while (s.next()){
           fName=s.getString("firstname");
           lName=s.getString("lastname");
        }
        namePlate.setText("Welcome - "+fName+" "+lName);

        System.out.println(LoginPageController.liveTime);

        String t[]=LoginPageController.liveTime.split("\\.");
        inittime=Math.abs(inittime-Integer.parseInt(t[1]));
        initmin=Math.abs(Integer.parseInt(t[0])-initmin);


        // setting the timer to the Exam
       timeline=new Timeline();
       timeline.setCycleCount(Timeline.INDEFINITE);
       KeyFrame frame=new KeyFrame(Duration.seconds(1),e->{
           timmer.setText( String.format("%02d:%02d",initmin,(--inittime)));
           if(inittime==0){
               if(initmin==0){
                   timeline.stop();
                      if(!resultPane.isVisible()){
                          Alert alert=new Alert(Alert.AlertType.INFORMATION);
                          alert.setContentText("Times Up !!!!");
                          alert.setHeaderText(null);
                          alert.initOwner(pane.getScene().getWindow());
                          alert.show();
                          alert.setOnCloseRequest(event1 -> {
                              questionNumber.setVisible(false);
                              mQuestions.setVisible(false);

                              fistAnswar.setVisible(false);
                              secondAnswar.setVisible(false);
                              thirdAnswar.setVisible(false);
                              fourthAnswar.setVisible(false);
                              sbar1.setVisible(false);
                              sbar2.setVisible(false);
                              submitA.setVisible(false);
                              nextQ.setVisible(false);
                              resultPane.setVisible(true);
                              timmer.setVisible(false);

                              scoreBoard.setText("Your Score - "+marks+"/2");

                              if(marks<2){
                                  failedLabel.setVisible(true);
                                  passedLabel.setVisible(false);
                              }else {
                                  failedLabel.setVisible(false);
                                  passedLabel.setVisible(true);
                              }
                          });

                      }
               }
               else {
                   inittime=60;
                   initmin--;
               }
           }
       });
       timeline.getKeyFrames().add(frame);
       timeline.playFromStart();




        EventHandler<KeyEvent> handler=new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.WINDOWS) ||event.getCode().equals(KeyCode.ALT) || event.getCode().equals(KeyCode.CONTROL) || event.getCode().equals(KeyCode.TAB) || !pane.isFocused()){

                    timeline.stop();
                    Stage stage=(Stage) logOutButton.getScene().getWindow();
                    stage.close();

                    Stage pStage=new Stage();
                    Parent root= null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/view/loginview.fxml"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    pStage.setScene(new Scene(root));
                    pStage.show();
                }
            }
        };

        if(pane.getScene()!=null) {
            pane.getScene().addEventHandler(KeyEvent.KEY_PRESSED, handler);
        }
        else {
            pane.sceneProperty().addListener(new ChangeListener<Scene>() {
                @Override
                public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                    if(newValue!=null){
                        pane.getScene().addEventHandler(KeyEvent.KEY_PRESSED,handler);
                    }
                }
            });
        }


        logOutButton.setOnMouseClicked(event -> {

            timeline.stop();
            Stage stage=(Stage) logOutButton.getScene().getWindow();
            stage.close();

            Stage pStage=new Stage();
            Parent root= null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/view/loginview.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            pStage.setScene(new Scene(root));
            pStage.show();
        });





        listOfPapers=new ArrayList<>();
        // Selection Code

        fistAnswar.setOnAction(e->{
            if(fistAnswar.isSelected()){
                secondAnswar.setSelected(false);
                thirdAnswar.setSelected(false);
                fourthAnswar.setSelected(false);
            }
        });        secondAnswar.setOnAction(e->{
           if(secondAnswar.isSelected()){
                fistAnswar.setSelected(false);
                thirdAnswar.setSelected(false);
                fourthAnswar.setSelected(false);
            }
        });       thirdAnswar.setOnAction(e->{
             if(thirdAnswar.isSelected()){
                secondAnswar.setSelected(false);
                fistAnswar.setSelected(false);
                fourthAnswar.setSelected(false);
            }
        });        fourthAnswar.setOnAction(e->{
            if (fourthAnswar.isSelected()){
                secondAnswar.setSelected(false);
                fistAnswar.setSelected(false);
                thirdAnswar.setSelected(false);
            }
        });

        // getting Q&A from database
        try {
            ResultSet resultSet=handeller.getQandA();


            while (resultSet.next()){
                Paper p=new Paper();
                List<String> l=new ArrayList<>();
                p.setqId(resultSet.getInt(1));
                p.setQuestion(resultSet.getString(2));
                l.add(resultSet.getString(3));
                l.add(resultSet.getString(4));
                l.add(resultSet.getString(5));
                l.add(resultSet.getString(6));
                p.setAnswars(l);
                listOfPapers.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mQuestions.setText(listOfPapers.get(0).getQuestion());
        Collections.shuffle(listOfPapers.get(0).getAnswars());

        fistAnswar.setText(listOfPapers.get(0).getAnswars().get(0));
        secondAnswar.setText(listOfPapers.get(0).getAnswars().get(1));
        thirdAnswar.setText(listOfPapers.get(0).getAnswars().get(2));
        fourthAnswar.setText(listOfPapers.get(0).getAnswars().get(3));



        nextQ.setOnAction(event -> {
            try {
                ResultSet set = handeller.getCorrectAnswer(listOfPapers.get(c - 1).getqId());
                while (set.next()) {
                    ans=(set.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

                    if(fistAnswar.isSelected()){
                        if(ans.equals(fistAnswar.getText())){
                            marks++;
                        }
                    }
                    else if(secondAnswar.isSelected()){
                        if(ans.equals(secondAnswar.getText())){
                            marks++;
                        }
                    }
                    else if(thirdAnswar.isSelected()){
                        if(ans.equals(thirdAnswar.getText())){
                            marks++;
                        }
                    }
                    else if(fourthAnswar.isSelected()){
                        if(ans.equals(fourthAnswar.getText())){
                            marks++;
                        }
                    }




            questionNumber.setText("Q."+(c+1));
            mQuestions.setText(listOfPapers.get(c).getQuestion());
            Collections.shuffle(listOfPapers.get(c).getAnswars());

            fistAnswar.setText(listOfPapers.get(c).getAnswars().get(0));
            secondAnswar.setText(listOfPapers.get(c).getAnswars().get(1));
            thirdAnswar.setText(listOfPapers.get(c).getAnswars().get(2));
            fourthAnswar.setText(listOfPapers.get(c).getAnswars().get(3));


           if(c==(listOfPapers.size()-1)){
                submitA.setVisible(true);
                nextQ.setVisible(false);
           }
           else {
               c++;
           }


            secondAnswar.setSelected(false);
            fistAnswar.setSelected(false);
            thirdAnswar.setSelected(false);
            fourthAnswar.setSelected(false);
        });

        submitA.setOnAction(event -> {

            try {
                ResultSet set = handeller.getCorrectAnswer(listOfPapers.get(c).getqId());
                while (set.next()) {
                    ans=(set.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(fistAnswar.isSelected()){
                if(ans.equals(fistAnswar.getText())){
                    marks++;
                }
            }
            else if(secondAnswar.isSelected()){
                if(ans.equals(secondAnswar.getText())){
                    marks++;
                }
            }
            else if(thirdAnswar.isSelected()){
                if(ans.equals(thirdAnswar.getText())){
                    marks++;
                }
            }
            else if(fourthAnswar.isSelected()){
                if(ans.equals(fourthAnswar.getText())){
                    marks++;
                }
            }

            questionNumber.setVisible(false);
            mQuestions.setVisible(false);

            fistAnswar.setVisible(false);
            secondAnswar.setVisible(false);
            thirdAnswar.setVisible(false);
            fourthAnswar.setVisible(false);
            sbar1.setVisible(false);
            sbar2.setVisible(false);
            submitA.setVisible(false);
            resultPane.setVisible(true);
            timmer.setVisible(false);

            scoreBoard.setText("Your Score - "+marks+"/"+listOfPapers.size());

            if(marks<listOfPapers.size()){
                failedLabel.setVisible(true);
                passedLabel.setVisible(false);
            }else {
                failedLabel.setVisible(false);
                passedLabel.setVisible(true);
            }

            handeller=new DBHandeller();
            User user=new User();
            user.setScore(String.valueOf(marks));
            try {
                handeller.setScore(user);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
