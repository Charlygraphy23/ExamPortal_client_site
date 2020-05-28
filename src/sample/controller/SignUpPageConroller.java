package sample.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.database.DBHandeller;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpPageConroller {

    @FXML
    private JFXTextField firstnameTextBox;

    @FXML
    private JFXTextField lastnameTextBox;

    @FXML
    private JFXTextField usernameTextBox;

    @FXML
    private JFXTextField passwordTextBox;

    @FXML
    private JFXTextField emailIdTextBox;

    @FXML
    private JFXTextField mobileNoTextBox;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton buttoncancle;

    private DBHandeller handeller;

    @FXML
    void initialize() {
       mobileNoTextBox.setOnKeyReleased(e->{
          if( !mobileNoTextBox.getText().trim().matches("\\d+")){
              mobileNoTextBox.setStyle("-fx-text-fill: red");
          }else {
              mobileNoTextBox.setStyle("-fx-text-fill: black");
          }
       });

       signupButton.setOnAction(e->{
           int a=0,b=0,c=0;
           if(mobileNoTextBox.getText().trim().matches("[8 9[7 6]]\\d{9}")){

               if(emailIdTextBox.getText().trim().matches("\\S+@\\S+\\.\\S+")) {
                   if(!firstnameTextBox.getText().trim().equals("") && !lastnameTextBox.getText().trim().equals("") && !usernameTextBox.getText().trim().equals("") &&!passwordTextBox.getText().trim().equals("")){

                       handeller=new DBHandeller();
                       try {
                         User u=new User();
                         u.setUserName(usernameTextBox.getText().trim());
                         u.setEmail(emailIdTextBox.getText().trim());
                         u.setMobilNo(mobileNoTextBox.getText().trim());
                           ResultSet resultSet= handeller.checkUsernameDetails(u);

                           while(resultSet.next()){
                               a++;
                           }

                           resultSet= handeller.checkEmailDetails(u);

                           while(resultSet.next()){
                               b++;
                           }

                           resultSet= handeller.checkMobileNoDetails(u);

                           while(resultSet.next()){
                               c++;
                           }
                           if(a>0 || b>0 || c>0 ){
                               Alert alert=new Alert(Alert.AlertType.ERROR,"The details has already been declared !!");
                               alert.setHeaderText(null);
                               Stage stage1=(Stage) alert.getDialogPane().getScene().getWindow();
                               stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                               alert.show();
                           }else {
                               handeller.setUser(new User(firstnameTextBox.getText().trim(),lastnameTextBox.getText().trim(),usernameTextBox.getText().trim(),passwordTextBox.getText().trim(),emailIdTextBox.getText().trim(),mobileNoTextBox.getText().trim(),String.valueOf(-1),String.valueOf(-1),String.valueOf(-1)));

                               Alert alert=new Alert(Alert.AlertType.INFORMATION,"Successfully Inserted");
                               alert.setHeaderText(null);
                               Stage stage1=(Stage) alert.getDialogPane().getScene().getWindow();
                               stage1.getIcons().add(new Image("/sample/assets/256px-Information.svg.png"));
                               alert.show();
                               alert.setOnCloseRequest(ee->{
                                   Stage stage=(Stage) signupButton.getScene().getWindow();
                                   stage.close();

                                   Stage primaryStage=new Stage();
                                   Scene scene= null;
                                   try {
                                       scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/view/loginview.fxml")));
                                   } catch (IOException ex) {
                                       ex.printStackTrace();
                                   }
                                   primaryStage.setScene(scene);
                                   primaryStage.getIcons().add(new Image("/sample/assets/educaion_portal - Copy.png"));
                                   primaryStage.show();

                                   Node root=scene.getRoot();
                                   Bounds rootBounds=root.getBoundsInLocal();

                                   double width=primaryStage.getWidth() - rootBounds.getWidth();
                                   double height=primaryStage.getHeight() - rootBounds.getHeight();

                                   Bounds prefBounds=getPrefBoucnds(root);
                                   primaryStage.setMinWidth(prefBounds.getWidth()+ width);
                                   primaryStage.setMinHeight(prefBounds.getHeight() + height);

                               });
                           }

                       } catch (SQLException ex) {
                           ex.printStackTrace();
                       } catch (ClassNotFoundException ex) {
                           ex.printStackTrace();
                       }

                   }else {

                       Alert alert=new Alert(Alert.AlertType.ERROR,"Enter Text Proprly");
                       alert.setHeaderText(null);
                       Stage stage1=(Stage) alert.getDialogPane().getScene().getWindow();
                       stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                       alert.show();
                   }
               }else {
                   Alert alert=new Alert(Alert.AlertType.ERROR,"Email is not Valid");
                   alert.setHeaderText(null);
                   Stage stage1=(Stage) alert.getDialogPane().getScene().getWindow();
                   stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
                   alert.show();
               }
           }else {
               Alert alert=new Alert(Alert.AlertType.ERROR,"Mobile No No Valid");
               alert.setHeaderText(null);
               Stage stage1=(Stage) alert.getDialogPane().getScene().getWindow();
               stage1.getIcons().add(new Image("/sample/assets/Mix_color_5__info-512.png"));
               alert.show();
           }
       });


       buttoncancle.setOnAction(e->{
           Stage stage=(Stage) buttoncancle.getScene().getWindow();
           stage.close();

           Stage primaryStage=new Stage();
           Scene scene= null;
           try {
               scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/view/loginview.fxml")));
           } catch (IOException ex) {
               ex.printStackTrace();
           }
           primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/sample/assets/educaion_portal - Copy.png"));
        primaryStage.show();

        Node root=scene.getRoot();
        Bounds rootBounds=root.getBoundsInLocal();

        double width=primaryStage.getWidth() - rootBounds.getWidth();
        double height=primaryStage.getHeight() - rootBounds.getHeight();

        Bounds prefBounds=getPrefBoucnds(root);
        primaryStage.setMinWidth(prefBounds.getWidth()+ width);
        primaryStage.setMinHeight(prefBounds.getHeight() + height);
    });

}

    private Bounds getPrefBoucnds(Node root) {
        double prefWidth,prefHeight;
        Orientation bias=root.getContentBias();

        if(bias==Orientation.HORIZONTAL){
            prefHeight=root.prefHeight(-1);
            prefWidth=root.prefWidth(prefHeight);
        }else if(bias==Orientation.VERTICAL){
            prefWidth=root.prefWidth(-1);
            prefHeight=root.prefHeight(prefWidth);
        }else {
            prefHeight=root.prefHeight(-1);
            prefWidth=root.prefWidth(-1);
        }

        return new BoundingBox(0,0,prefWidth,prefHeight);
    }
}
