package com.company;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<MouseEvent> {
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        button = new Button("你好");
        // button.setOnMouseClicked(this);
        // button.setOnMouseClicked(new MyMouseEvent());
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("你点击了按钮");
            }
        });
        // 布局控件
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);

        // 场景控件
        Scene scene = new Scene(stackPane, 200, 200);
        // scene.setOnMousePressed(this);
        // scene.setOnMouseClicked(new SceneMouseEvent());
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("你点击了场景");
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource() == button) {
            System.out.println("你点击的是按钮");
        } else {
            System.out.println("你点击了场景");
        }
    }
}
