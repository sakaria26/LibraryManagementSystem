package com.thelibrary.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.thelibrary.util.ViewUtil.appIcon;

public class Assistant extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Librarian.class.getResource("/com/thelibrary/views/assistant/assistant.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = appIcon;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
