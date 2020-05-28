package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DBHandeller;
import sample.model.User;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.spi.DateFormatProvider;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LoginPageController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label register;

    @FXML
    private JFXButton loginButton;

    private DBHandeller handeller;
    public static int loginID;
    private String time,date,score;
    public   static String liveTime;

    @FXML
    void initialize() {
//
//        pane.setOnKeyReleased(event -> {
//            if(!pane.isFocused()){
//                System.out.println("Logout");
//            }
//            if(event.getCode().equals(KeyCode.WINDOWS) || event.getCode().equals(KeyCode.ALT) || event.getCode().equals(KeyCode.CONTROL) || event.getCode().equals(KeyCode.SHIFT)){
//                System.out.println("Anothor");
//            }
//        });


        register.setOnMouseClicked(e->{

            Stage stage=(Stage) register.getScene().getWindow();
            stage.close();

            Stage pStage=new Stage();
            Scene scene= null;
            try {
                scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/view/signupview.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            pStage.setScene(scene);
            pStage.getIcons().add(new Image("/sample/assets/educaion_portal - Copy.png"));
            pStage.show();

            Node rootr=scene.getRoot();
            Bounds rootBounds=rootr.getBoundsInLocal();
            double width=pStage.getWidth() - rootBounds.getWidth();
            double height=pStage.getHeight() - rootBounds.getHeight();

            Bounds prefBounds=getPrefBoucnds(rootr);
            pStage.setMinWidth(prefBounds.getWidth()+ width);
            pStage.setMinHeight(prefBounds.getHeight() + height);

        });

        loginButton.setOnAction(event -> {
            int c = 0;
            if (!username.getText().trim().equals("") && !password.getText().trim().equals("")) {

                handeller = new DBHandeller();
                try {
                    User user = new User();
                    user.setUserName(username.getText().trim());
                    user.setPassword(password.getText().trim());

                    ResultSet resultSet = handeller.getData(user);

                    while (resultSet.next()) {
                        loginID = resultSet.getInt(1);
                        date = resultSet.getString("datee");
                        time = resultSet.getString("timee");
                        score = resultSet.getString("score");
                        c++;
                    }

                    if (c > 0) {
                        if (LocalDate.now().equals(LocalDate.parse(date))) {
                            if(Integer.parseInt(score)==-1){
                                String localtime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss"));

                                String s1[] = localtime.split("\\.");
                                String s2[] = time.split("\\.");


                                if ((Integer.parseInt(s1[1]) - Integer.parseInt(s2[1])) <= 1 && (Integer.parseInt(s1[1]) - Integer.parseInt(s2[1])) >= 0) {
                                    if ((Integer.parseInt(s1[2]) - Integer.parseInt(s2[2])) < 60) {
//                                        System.out.println(LocalTime.now());
//                                        System.out.println((Integer.parseInt(s1[1]) - Integer.parseInt(s2[1])) + " " + (Integer.parseInt(s1[2]) - Integer.parseInt(s2[2])));
                                          liveTime = String.format("%02d.%02d", (Integer.parseInt(s1[1]) - Integer.parseInt(s2[1])), (Integer.parseInt(s1[2]) - Integer.parseInt(s2[2])));

                                        Stage stage = (Stage) loginButton.getScene().getWindow();
                                        stage.close();

                                        Stage pStage = new Stage();
                                        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/sample/view/mainsample.fxml")));
                                        pStage.setScene(scene);
                                        pStage.getIcons().add(new Image("/sample/assets/educaion_portal - Copy.png"));
                                        pStage.initStyle(StageStyle.UNDECORATED);
                                        pStage.setMaximized(true);
                                        pStage.setResizable(false);
                                        pStage.fullScreenExitKeyProperty().setValue(null);
                                        pStage.setAlwaysOnTop(true);
                                        pStage.show();

                                        Node rootr=scene.getRoot();
                                        Bounds rootBounds=rootr.getBoundsInLocal();

                                        double width=pStage.getWidth() - rootBounds.getWidth();
                                        double height=pStage.getHeight() - rootBounds.getHeight();

                                        Bounds prefBounds=getPrefBoucnds(rootr);
                                        pStage.setMinWidth(prefBounds.getWidth()+ width);
                                        pStage.setMinHeight(prefBounds.getHeight() + height);
                                    }
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Your Session has Expired \n Socre is - " + score);
                                    alert.setHeaderText(null);
                                    Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                                    stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                                    alert.show();
                                }
                            }else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "You Have already attempt the exam and Your Score is = "+score);
                                alert.setHeaderText(null);
                                Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                                stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "The Exam is no Available");
                            alert.setHeaderText(null);
                            Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                            alert.show();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Username or Password Wrong!!");
                        alert.setHeaderText(null);
                        Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                        alert.show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please provide All Details");
                alert.setHeaderText(null);
                Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
                stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                alert.show();
            }

        });
    }    // End of Initialize

    private Bounds getPrefBoucnds(Node rootr) {

        double prefWidth,prefHeight;
        Orientation bias=rootr.getContentBias();

        if(bias==Orientation.HORIZONTAL){
            prefHeight=rootr.prefHeight(-1);
            prefWidth=rootr.prefWidth(prefHeight);
        }else if(bias==Orientation.VERTICAL){
            prefWidth=rootr.prefWidth(-1);
            prefHeight=rootr.prefHeight(prefWidth);
        }else {
            prefHeight=rootr.prefHeight(-1);
            prefWidth=rootr.prefWidth(-1);
        }

        return new BoundingBox(0,0,prefWidth,prefHeight);

    }
}
