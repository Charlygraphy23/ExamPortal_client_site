package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("view/loginview.fxml")));
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


    public static void main(String[] args) {
        launch(args);
    }
}
