package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemTemplate;
import com.nro_sv_mgn.nro_server_manager.dto.MapTemplate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AppData {
    public static Map<Integer, MapTemplate> MAP_TEMPLATES = new HashMap<>();
    public static Map<Integer, ItemTemplate> ITEM_TEMPLATES = new HashMap<>();

    static Socket resClient;
    static DataOutputStream resDos;
    static DataInputStream resIn;
    static File packDir = new File(Settings.PACKAGE_PATH);

    public static boolean hasData() {
        try {
            return Files.exists(Paths.get(Settings.RESOURCE_PATH, "map.json"))
                    && Files.exists(Paths.get(Settings.RESOURCE_PATH, "item.json"));
        } catch (Exception e) {
            return false;
        }
    }



    // Giải nén .rar
    public static void extractZip(File zipFile, String outputDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                    continue;
                }
                newFile.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    zis.transferTo(fos);
                }
            }
        }
        System.out.println("🧩 Giải nén xong: " + zipFile.getName());
    }

    public static void loadDataToSystem() {
        try {
            ITEM_TEMPLATES.clear();
            MAP_TEMPLATES.clear();
            if (hasData()) {
                // load map first
                FileReader fileReader = new FileReader(Settings.RESOURCE_PATH + "/map.json");
                if (fileReader.ready()) {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(fileReader);
                    if (jsonArray != null && !jsonArray.isEmpty()) {
                        for (Object o : jsonArray) {
                            JSONArray mapArr = (JSONArray) o;
                            MapTemplate map = new MapTemplate();
                            map.setId(Integer.parseInt(mapArr.get(0).toString()));
                            map.setName(mapArr.get(1).toString());
                            map.setType(Byte.parseByte(mapArr.get(2).toString()));
                            map.setPlanetId(Byte.parseByte(mapArr.get(3).toString()));
                            map.setMaxPlayerPerZone(Byte.parseByte(mapArr.get(4).toString()));
                            map.setZones(Byte.parseByte(mapArr.get(5).toString()));
                            MAP_TEMPLATES.put(map.getId(), map);
                        }
                    }
                }

                FileReader itemReader = new FileReader(Settings.RESOURCE_PATH + "/item.json");
                if (itemReader.ready()) {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(itemReader);
                    if (jsonArray != null && !jsonArray.isEmpty()) {
                        for (Object o : jsonArray) {
                            JSONArray itemArr = (JSONArray) o;
                            ItemTemplate item = new ItemTemplate();
                            item.setId(Integer.parseInt(itemArr.get(0).toString()));
                            item.setName(itemArr.get(1).toString());
                            item.setType(Byte.parseByte(itemArr.get(2).toString()));
                            item.setGender(Byte.parseByte(itemArr.get(3).toString()));
                            item.setDescription(itemArr.get(4).toString());
                            item.setIconID(Short.parseShort(itemArr.get(5).toString()));
                            item.setHead(Short.parseShort(itemArr.get(6).toString()));
                            item.setBody(Short.parseShort(itemArr.get(7).toString()));
                            item.setLeg(Short.parseShort(itemArr.get(8).toString()));
                            item.setGold(Long.parseLong(itemArr.get(9).toString()));
                            ITEM_TEMPLATES.put(item.getId(), item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadResourceWithProgress() throws IOException {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (!packDir.exists()) packDir.mkdirs();
                // 1️⃣ Nhận metadata
                String fileName;
                long fileSize;
                try {
                    fileName = resIn.readUTF();
                    fileSize = resIn.readLong();
                } catch (EOFException eof) {
                    updateMessage("❌ Không nhận được metadata từ server!");
                    throw new IOException("Không nhận được metadata từ server", eof);
                }

                File rarFile = new File(packDir, fileName);
                updateMessage("⬇️ Đang tải " + fileName + " (" + fileSize / 1024 / 1024 + " MB)");

                // 2️⃣ Nhận dữ liệu file
                try (FileOutputStream fos = new FileOutputStream(rarFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    byte[] buffer = new byte[8192];
                    long totalBytes = 0;
                    int bytesRead;

                    while (totalBytes < fileSize &&
                            (bytesRead = resIn.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalBytes))) != -1) {

                        bos.write(buffer, 0, bytesRead);
                        totalBytes += bytesRead;

                        // cập nhật tiến độ (UI thread tự update)
                        updateProgress(totalBytes, fileSize);
                        updateMessage(String.format("📦 Đang tải... %.2f%%", (double) totalBytes / fileSize * 100));
                    }

                    bos.flush();
                } finally {
                    resIn.close();
                    if (resDos != null) resDos.close();
                    if (resClient != null) resClient.close();
                }

                System.out.println("✅ Tải hoàn tất: " + rarFile.getAbsolutePath());
                updateMessage("🧩 Đang giải nén...");
                File resDir = new File(Settings.RESOURCE_PATH);
                if (!resDir.exists()) {
                    resDir.mkdirs();
                }
                extractZip(rarFile, resDir.getAbsolutePath());

                updateMessage("✅ Đã tải và giải nén hoàn tất!");
                return null;
            }
        };

        // 🔹 UI hiển thị thanh tiến độ
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.progressProperty().bind(task.progressProperty());

        Label statusLabel = new Label("Chuẩn bị tải...");
        statusLabel.textProperty().bind(task.messageProperty());

        VBox vbox = new VBox(10, statusLabel, progressBar);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Stage progressStage = new Stage();
        progressStage.setScene(new Scene(vbox, 350, 120));
        progressStage.setTitle("Đang tải Resource...");
        progressStage.show();

        task.setOnSucceeded(e -> {
            progressStage.close();
            Platform.runLater(() -> {
                        new Alert(Alert.AlertType.INFORMATION,
                                "✅ Đã tải và giải nén hoàn tất! Khởi động lại app để đồng bộ dữ liệu").showAndWait();
                        System.exit(0);
                    }
            );
        });

        task.setOnFailed(e -> {
            progressStage.close();
            Throwable ex = task.getException();
            ex.printStackTrace();
            Platform.runLater(() ->
                    new Alert(Alert.AlertType.ERROR, "❌ Lỗi khi tải resource: " + ex.getMessage()).showAndWait());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void startUnrarTask() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                File packageFile = new File(Settings.PACKAGE_PATH, Settings.PACKAGE_NAME);
                File resDir = new File(Settings.RESOURCE_PATH);
                if (!resDir.exists()) {
                    resDir.mkdirs();
                }
                extractZip(packageFile, resDir.getAbsolutePath());
                return null;
            }
        };
        // 🔹 UI hiển thị thanh tiến độ
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.progressProperty().bind(task.progressProperty());

        Label statusLabel = new Label("Chuẩn bị giải nén...");
        statusLabel.textProperty().bind(task.messageProperty());

        VBox vbox = new VBox(10, statusLabel, progressBar);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Stage progressStage = new Stage();
        progressStage.setScene(new Scene(vbox, 350, 120));
        progressStage.setTitle("Đang giải nén...");
        progressStage.show();

        task.setOnSucceeded(e -> {
            progressStage.close();
            Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION,
                    "✅ Đã giải nén hoàn tất!").showAndWait());
        });

        task.setOnFailed(e -> {
            progressStage.close();
            Throwable ex = task.getException();
            ex.printStackTrace();
            Platform.runLater(() ->
                    new Alert(Alert.AlertType.ERROR, "❌ Lỗi khi giải nén resource: " + ex.getMessage()).showAndWait());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void sendRequestRes(String command) {
        try {
            if (resDos == null || resClient == null || !resClient.isConnected()) {
                Helper.showInfo("Không thể kết nối với Res server");
                return;
            }
            resDos.writeUTF(command);
            resDos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openConnect() throws IOException {
        resClient = new Socket(Settings.SERVER_IP, Settings.RES_PORT);
        resIn = new DataInputStream(resClient.getInputStream());
        resDos = new DataOutputStream(resClient.getOutputStream());
    }

    public static void handleCheckResouce() {
        try {
            if (!AppData.isResFolderExit()) {
                if (AppData.isPackageExit()) {
                    boolean confirmUnrar = Helper.showConfirm("Giải nén dữ liệu", "Dữ liệu hệ thống đã tải nhưng chưa giải nén, giải nén ngay?");
                    if (confirmUnrar) {
                        AppData.startUnrarTask();
                    }
                    return;
                }
                boolean confirmDownload = Helper.showConfirm("Cập nhật dữ liệu", "Hệ thống nhận ra thiếu file dữ liệu nên cần bạn phải tải dữ liệu");
                if (confirmDownload) {
                    openConnect();
                    sendRequestRes(PanelCommand.CMD_SEND_RES);
                    AppData.downloadResourceWithProgress();
                }
            }
            // load data here
            loadDataToSystem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isResFolderExit() {
        File resFolder = new File(Settings.RESOURCE_PATH);
        return resFolder.exists() && resFolder.isDirectory();
    }

    public static boolean isResFolderLengthFull() {
        File file = new File(Settings.RESOURCE_PATH);
        if (!file.exists() && !file.isDirectory()) {
            return false;
        }
        return Integer.parseInt(StaticStorge.getSetting("RES_LENGTH", "0")) == Objects.requireNonNull(file.listFiles()).length;
    }

    public static boolean isPackageExit() {
        File existingFile = new File(Settings.PACKAGE_PATH, Settings.PACKAGE_NAME);
        return existingFile.exists() && existingFile.isFile();
    }

}
