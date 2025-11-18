package com.nro_sv_mgn.nro_server_manager;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;

import static com.nro_sv_mgn.nro_server_manager.Settings.RESOURCE_PATH;

public class Helper {
    public static String TITLE = "NRO RISE";

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TITLE);
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirm(String title, String message) {
        final boolean[] result = {false};

        // Nếu gọi từ thread khác, cần quay lại UI thread
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(() -> {
                    result[0] = showConfirm(title, message);
                });
                Thread.sleep(200); // đợi một chút để có kết quả (cách tạm)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result[0];
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> option = alert.showAndWait();
        return option.isPresent() && option.get() == yes;
    }

    public static Image getImageByName(int id) {
        File file = new File(RESOURCE_PATH, "x4/" + id + ".png");
        if (!file.exists()) {
            Helper.showInfo("Không tìm thấy tài nguyên ảnh");
            return null;
        }
        return new Image(file.toURI().toString());
    }

    public static ImageView getImageById(int id, int w, int h) {
        File file = new File(RESOURCE_PATH, "x4/" + id + ".png");
        if (!file.exists()) {
            Helper.showInfo("Không tìm thấy tài nguyên ảnh: " + id);
            return null;
        }

        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(w);
        imageView.setFitHeight(h);
        imageView.setPreserveRatio(true);
        return imageView;
    }

}
