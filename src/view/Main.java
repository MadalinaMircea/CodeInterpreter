package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.utils.ExecStack;
import model.utils.IExecStack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("Window.fxml"));
            Scene scene = new Scene(root,540,400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Interpreter");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


        public static void main(String[] args) {
        launch(args);
    }
}
