package com.company;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @ClassName ChangeScene
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/5
 * @Version 1.0
 */
public class ChangeScene extends Application {
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("场景1");
        button.setOnMouseClicked(event -> {
            primaryStage.setScene(scene2);
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(button);
        scene1 = new Scene(vBox, 200, 200);
        //------------------
        Button button2 = new Button("场景2");
        button2.setOnMouseClicked(event -> {
            primaryStage.setScene(scene1);
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button2);
        scene2 = new Scene(stackPane, 600, 600);


        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
