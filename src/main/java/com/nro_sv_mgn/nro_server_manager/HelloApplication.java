package com.nro_sv_mgn.nro_server_manager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HelloApplication extends Application {
    private static HelloController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 500);
        controller = fxmlLoader.getController();
        stage.setTitle("NRO RISE");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        AppData.handleCheckResouce();
        new Thread(AppController.gI()).start();
    }

    // Getter để class khác có thể gọi setter của controller
    public static HelloController getController() {
        return controller;
    }

    public static void openPlayerFragment() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("player-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage playerStage = new Stage();
        playerStage.setTitle("Quản lý player");
        playerStage.setScene(scene);
        playerStage.show();
    }

}
