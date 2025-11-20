package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemBody;
import com.nro_sv_mgn.nro_server_manager.dto.ItemOption;
import com.nro_sv_mgn.nro_server_manager.dto.ListItemView;
import com.nro_sv_mgn.nro_server_manager.dto.ListPlayerView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AppController implements Runnable {
    public static Socket client;
    public static BufferedReader in;
    public static BufferedWriter out;
    private static AppController I;

    public static AppController gI() {
        if (I == null) {
            I = new AppController();
        }
        return I;
    }

    public void bindListViewPlayer(String[] val) {
        JSONArray vl = (JSONArray) JSONValue.parse(val[1]);
        List<ListPlayerView> playerViews = new ArrayList<>();
        if (vl != null && !vl.isEmpty()) {
            // read data gui
            for (Object jsonArray : vl) {
                try {
                    JSONArray dataPl = (JSONArray) jsonArray;
                    ListPlayerView listPlayerView = new ListPlayerView();
                    listPlayerView.setId(Integer.parseInt(dataPl.get(0).toString()));
                    listPlayerView.setName(dataPl.get(1).toString());
                    listPlayerView.setDlLv(Integer.parseInt(dataPl.get(2).toString()));
                    listPlayerView.setGold((long) Double.parseDouble(dataPl.get(3).toString()));
                    listPlayerView.setRuby((long) Double.parseDouble(dataPl.get(4).toString()));
                    listPlayerView.setDn((long) Double.parseDouble(dataPl.get(5).toString()));
                    listPlayerView.setCd((long) Double.parseDouble(dataPl.get(6).toString()));
                    playerViews.add(listPlayerView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        Platform.runLater(() -> {
            PlayerController.data.clear();
            PlayerController.data.addAll(playerViews);
        });
    }

    public void onMessage(String cmd, String[] val, BufferedWriter writer) {
        switch (cmd) {
            case PanelCommand.CMD_LIST_PLAYER:
                bindListViewPlayer(val);
                break;
            case PanelCommand.CMD_STATUS_ACCOUNT:
                JSONArray accountStatus = (JSONArray) JSONValue.parse(val[1]);
                if (accountStatus != null && !accountStatus.isEmpty()) {
                    Platform.runLater(() -> {
                        PlayerController.getInstance().updateTextOffline(accountStatus.get(0).toString());
                        PlayerController.getInstance().updateTextOnline(accountStatus.get(1).toString());
                        PlayerController.getInstance().updateTextBan(accountStatus.get(2).toString());
                    });
                }
                break;
            case PanelCommand.CMD_GET_INFO_SERVER:
                handleProcessServerInfo(val);
                break;
            case PanelCommand.CMD_BAN_PLAYER:
                bindListViewPlayer(val);
                Platform.runLater(() -> {
                    Helper.showInfo("Đã ban player");
                });
                break;
            case PanelCommand.CMD_SEARCH_PLAYER:
                bindListViewPlayer(val);
                break;
            case PanelCommand.CMD_DELETE_PLAYER:
                bindListViewPlayer(val);
                Platform.runLater(() -> {
                    Helper.showInfo("Đã xóa player");
                });
                break;
            case PanelCommand.CMD_KICK_PLAYER:
                bindListViewPlayer(val);
                Platform.runLater(() -> {
                    Helper.showInfo("Đã kick player");
                });
                break;
            case PanelCommand.CMD_DELETE_ACCOUNT:
                bindListViewPlayer(val);
                Platform.runLater(() -> {
                    Helper.showInfo("Đã xóa tài khoản");
                });
                break;
            case PanelCommand.CMD_GET_PLAYER_BAG:
                blindListViewItem(val);
                break;
            case PanelCommand.CMD_ADD_ITEM_BAG:
                blindListViewItem(val);
                Platform.runLater(() -> {
                    Helper.showInfo("Buff item success");
                });
                break;
            case PanelCommand.GET_PLAYER_BODY:
                Platform.runLater(() -> {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
                    int playerId = Integer.parseInt(jsonArray.get(0).toString());
                    PlayerDetailController playerDetailController = PlayerController.getInstance().getPlayerControllerById(playerId);
                    if (playerDetailController != null) {
                        JSONArray newArray = new JSONArray();
                        newArray.addAll(jsonArray.subList(1, jsonArray.size()));
                        List<ItemBody> itemBodies = parerItemBody(newArray);
                        playerDetailController.itAo = itemBodies.get(0);
                        playerDetailController.itQuan = itemBodies.get(1);
                        playerDetailController.itGang = itemBodies.get(2);
                        playerDetailController.itGiay = itemBodies.get(3);
                        playerDetailController.itRada = itemBodies.get(4);
                        playerDetailController.itCt = itemBodies.get(5);
                        playerDetailController.itGlt = itemBodies.get(6);
                        playerDetailController.itPet = itemBodies.get(7);
                        playerDetailController.itChanMenh = itemBodies.get(8);
                        playerDetailController.itCo = itemBodies.get(9);
                        playerDetailController.loadImv();
                    }
                });
                break;
            case PanelCommand.CMD_SUCCESS:
                Platform.runLater(() -> Helper.showInfo(val[1]));
                break;
        }
    }

    private List<ItemBody> parerItemBody(JSONArray data) {
        List<ItemBody> list = new ArrayList<>();
        for (Object o : data) {
            JSONArray jsIt = (JSONArray) o;
            if (jsIt != null && !jsIt.isEmpty()) {
                ItemBody itemBody = new ItemBody();
                itemBody.tempId = Integer.parseInt(jsIt.get(0).toString());
                itemBody.name = jsIt.get(1).toString();
                itemBody.iconID = Integer.parseInt(jsIt.get(2).toString());
                itemBody.quantity = Integer.parseInt(jsIt.get(3).toString());
                // load option
                JSONArray opts = (JSONArray) jsIt.get(4);
                for (Object opt : opts) {
                    JSONArray jsopt = (JSONArray) opt;
                    ItemOption itemOption = new ItemOption();
                    itemOption.setId(Integer.parseInt(jsopt.get(0).toString()));
                    itemOption.setName(jsopt.get(1).toString());
                    itemOption.setParam(Integer.parseInt(jsopt.get(2).toString()));
                    itemBody.itemOptions.add(itemOption);
                }
                list.add(itemBody);
            } else {
                list.add(null);
            }
        }
        return list;
    }

    private String timeConvert(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        return String.format("%d giờ %d phút %d giây%n", hours, minutes, seconds);
    }

    private void handleProcessServerInfo(String[] val) {
        try {
            HelloController helloController = HelloApplication.getController();
            JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                helloController.setTxtOnline("Số người online : " + jsonArray.get(0).toString());
                helloController.setTxtTimeUp("Thời gian bật server : " + timeConvert(System.currentTimeMillis() - Long.parseLong(jsonArray.get(1).toString())));
                helloController.setTxtTimeMaintance("Thời gian bảo trì : " + Integer.parseInt(jsonArray.get(2).toString()) + " Giờ " + Integer.parseInt(jsonArray.get(3).toString()) + " Phút");
                helloController.setCpuProgress(Double.parseDouble(jsonArray.get(4).toString()), 100);
                helloController.setRamProgress(Double.parseDouble(jsonArray.get(5).toString()), Double.parseDouble(jsonArray.get(6).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blindListViewItem(String[] val) {
        try {
            JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
            List<ListItemView> listItemViews = new ArrayList<>();
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (Object o : jsonArray) {
                    JSONArray data = (JSONArray) o;
                    ListItemView itemView = new ListItemView();
                    itemView.setTxtItemId(Integer.parseInt(data.get(0).toString()));
                    itemView.setTxtItemName(data.get(1).toString());
                    itemView.setImvIcon(Helper.getImageById(Integer.parseInt(data.get(2).toString()), 60, 60));
                    itemView.setTxtQuantity(Integer.parseInt(data.get(3).toString()));
                    itemView.setTxtDesc(data.get(4).toString());
                    listItemViews.add(itemView);
                }
            }
            Platform.runLater(() -> {
                PlayerController.itemData.clear();
                PlayerController.itemData.addAll(listItemViews);
                PlayerController.originList.clear();
                PlayerController.originList.addAll(PlayerController.itemData);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onConnect() throws Exception {
        try {
            client = new Socket(Settings.SERVER_IP, Settings.SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println("Connected to " + Settings.SERVER_IP + ":" + Settings.SERVER_PORT);
            new Thread(this::onListenConnect).start();
            new Thread(this::getServerInfo).start();
        } catch (Exception e) {
            Platform.runLater(() -> {
                Helper.showInfo("Không kết nối được tới server");
            });
        }
    }

    private void getServerInfo() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            try {
                Service.gI().getServerInfo(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void onListenConnect() {
        int countToTry = 0;
        int maxTry = 5;
        int timeDelayTry = 3000;
        long lastTimeTry = 0;

        // Biến giữ popup cảnh báo
        final Alert[] reconnectAlert = {null};

        try {
            while (true) {
                if (!client.isConnected()) {
                    if (System.currentTimeMillis() - lastTimeTry < timeDelayTry) {
                        Thread.sleep(200);
                        continue;
                    }

                    lastTimeTry = System.currentTimeMillis();
                    countToTry++;

                    // 🟡 Tạo popup cảnh báo chỉ 1 lần, không thể tắt
                    if (countToTry == 1) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Mất kết nối");
                            alert.setHeaderText("⚠️ Mất kết nối tới máy chủ");
                            alert.setContentText("Đang thử kết nối lại...");

                            // Ẩn nút mặc định
                            alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setVisible(false);

                            // Không cho đóng bằng nút X
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.setOnCloseRequest(WindowEvent::consume);

                            alert.show();
                            reconnectAlert[0] = alert;
                        });
                    }

                    // 🧩 Thử kết nối lại
                    try {
                        onConnect();
                        if (client.isConnected()) {
                            int tryCount = countToTry;
                            countToTry = 0; // reset

                            // 🔵 Đóng popup cảnh báo và hiện popup thành công
                            Platform.runLater(() -> {
                                if (reconnectAlert[0] != null) {
                                    reconnectAlert[0].close();
                                }
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Đã kết nối lại");
                                alert.setHeaderText("✅ Kết nối lại thành công!");
                                alert.setContentText("Đã kết nối lại sau " + tryCount + " lần thử.");
                                alert.show();
                            });
                        }
                    } catch (Exception ex) {
                        System.out.println("❌ Lỗi khi kết nối lại: " + ex.getMessage());
                    }

                    // 🚫 Quá số lần thử
                    if (countToTry >= maxTry) {
                        Platform.runLater(() -> {
                            if (reconnectAlert[0] != null) {
                                reconnectAlert[0].close();
                            }
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Không thể kết nối lại");
                            alert.setHeaderText("🚫 Thử kết nối thất bại");
                            alert.setContentText("Vui lòng kiểm tra mạng hoặc khởi động lại ứng dụng.");
                            alert.showAndWait();
                        });
                        break;
                    }
                }

                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            onConnect();
            String line;
            String cmd;
            while ((line = in.readLine()) != null) {
                String[] msgs = line.split(":");
                if (msgs.length == 0) continue;
                cmd = msgs[0];
                onMessage(cmd, msgs, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
