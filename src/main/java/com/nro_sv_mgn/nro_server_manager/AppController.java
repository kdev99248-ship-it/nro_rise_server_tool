package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.*;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppController implements Runnable {
    public static Socket client;
    public static boolean isConnect = false;
    public static BufferedReader in;
    public static BufferedWriter out;
    private static AppController I;

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

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
            case PanelCommand.CMD_SEND_KEY:
                saveServerKey(val);
                break;
            case PanelCommand.CMD_LIST_PLAYER, PanelCommand.CMD_SEARCH_PLAYER:
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
            case PanelCommand.GET_PLAYER_POINT:
                Platform.runLater(() -> {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
                    if (jsonArray != null && !jsonArray.isEmpty()) {
                        int id = Integer.parseInt(jsonArray.get(0).toString());
                        PlayerDetailController playerDetailController = PlayerController.getInstance().getPlayerControllerById(id);
                        if (playerDetailController != null) {
                            PlayerPoint playerPoint = new PlayerPoint();
                            playerPoint.setHp(Double.parseDouble(jsonArray.get(1).toString()));
                            playerPoint.setMp(Double.parseDouble(jsonArray.get(2).toString()));
                            playerPoint.setAttack(Double.parseDouble(jsonArray.get(3).toString()));
                            playerPoint.setCrits(Integer.parseInt(jsonArray.get(4).toString()));
                            playerPoint.setPower(Long.parseLong(jsonArray.get(5).toString()));
                            playerPoint.setTn(Long.parseLong(jsonArray.get(6).toString()));
                            playerPoint.setDef(Integer.parseInt(jsonArray.get(7).toString()));
                            playerPoint.setTlNeDon(Integer.parseInt(jsonArray.get(8).toString()));


                            // set max cs
                            playerPoint.setCurrentHp(Double.parseDouble(jsonArray.get(9).toString()));
                            playerPoint.setHpMax(Double.parseDouble(jsonArray.get(10).toString()));
                            playerPoint.setCurrentMp(Double.parseDouble(jsonArray.get(11).toString()));
                            playerPoint.setMpMax(Double.parseDouble(jsonArray.get(12).toString()));
                            playerPoint.setDame(Double.parseDouble(jsonArray.get(13).toString()));
                            playerPoint.setCritMax(Short.parseShort(jsonArray.get(14).toString()));
                            playerPoint.setDefMax(Double.parseDouble(jsonArray.get(15).toString()));
                            playerPoint.avatarId = Integer.parseInt(jsonArray.get(16).toString());
                            playerDetailController.loadPlayerPoint(playerPoint);
                        }
                    }
                });
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
            case PanelCommand.GET_PLAYER_BODY:
                Platform.runLater(() -> {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
                    int playerId = Integer.parseInt(jsonArray.get(0).toString());
                    PlayerDetailController playerDetailController = PlayerController.getInstance().getPlayerControllerById(playerId);
                    if (playerDetailController != null) {
                        // reset item first
                        playerDetailController.resetItem();
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
                        // check for item body controller info
                        ItemBodyInfoController itemBodyInfoController = null;
                        if (playerDetailController.itAo != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itAo.tempId, playerId, 0, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itAo);
                            }
                        }
                        if (playerDetailController.itQuan != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itQuan.tempId, playerId, 1, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itQuan);
                            }
                        }
                        if (playerDetailController.itGang != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itGang.tempId, playerId, 2, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itGang);
                            }
                        }
                        if (playerDetailController.itGiay != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itGiay.tempId, playerId, 3, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itGiay);
                            }
                        }
                        if (playerDetailController.itRada != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itRada.tempId, playerId, 4, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itRada);
                            }
                        }
                        if (playerDetailController.itCt != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itCt.tempId, playerId, 5, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itCt);
                            }
                        }
                        if (playerDetailController.itGlt != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itGlt.tempId, playerId, 6, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itGlt);
                            }
                        }
                        if (playerDetailController.itPet != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itPet.tempId, playerId, 7, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itPet);
                            }
                        }
                        if (playerDetailController.itChanMenh != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itChanMenh.tempId, playerId, 8, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itChanMenh);
                            }
                        }
                        if (playerDetailController.itCo != null) {
                            itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(playerDetailController.itCo.tempId, playerId, 9, 0));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(playerDetailController.itCo);
                            }
                        }

                    }
                });
                break;
            case PanelCommand.GET_PLAYER_BOX:
                Platform.runLater(() -> {
                    JSONArray jsonArray = (JSONArray) JSONValue.parse(val[1]);
                    int playerId = Integer.parseInt(jsonArray.get(0).toString());
                    PlayerDetailController playerDetailController = PlayerController.getInstance().getPlayerControllerById(playerId);
                    if (playerDetailController != null) {
                        List<ItemBody> itemBodies = new ArrayList<>();
                        JSONArray newArray = new JSONArray();
                        newArray.addAll(jsonArray.subList(1, jsonArray.size()));
                        int index = 0;
                        for (Object o : newArray) {
                            JSONArray jsonItem = (JSONArray) o;
                            ItemBody itemBody = new ItemBody();
                            itemBody.tempId = Integer.parseInt(jsonItem.get(0).toString());
                            itemBody.name = jsonItem.get(1).toString();
                            itemBody.description = jsonItem.get(2).toString();
                            itemBody.iconID = Integer.parseInt(jsonItem.get(3).toString());
                            itemBody.quantity = Integer.parseInt(jsonItem.get(4).toString());
                            List<ItemOption> itemOptions = new ArrayList<>();
                            JSONArray jsonOpt = (JSONArray) jsonItem.get(5);
                            extractItemOption(itemOptions, jsonOpt);
                            itemBody.itemOptions = itemOptions;
                            itemBodies.add(itemBody);
                            ItemBodyInfoController itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(itemBody.getTempId(), playerId, index, 2));
                            if (itemBodyInfoController != null) {
                                itemBodyInfoController.onLoadData(itemBody);
                            }
                            index++;
                        }
                        playerDetailController.itemsBoxOriginList.clear();
                        playerDetailController.itemsBoxOriginList.addAll(itemBodies);
                        playerDetailController.onLoadItemBoxList();
                    }
                });
                break;
            case PanelCommand.CMD_SUCCESS:
                Platform.runLater(() -> Helper.showInfo(val[1]));
                break;
        }
    }

    private void extractItemOption(List<ItemOption> itemOptions, JSONArray jsonOpt) {
        for (Object object : jsonOpt) {
            JSONArray jsonItemOption = (JSONArray) object;
            ItemOption itemOption = new ItemOption();
            itemOption.setId(Integer.parseInt(jsonItemOption.get(0).toString()));
            itemOption.setName(jsonItemOption.get(1).toString());
            itemOption.setParam(Integer.parseInt(jsonItemOption.get(2).toString()));
            itemOptions.add(itemOption);
        }
    }

    private void saveServerKey(String[] val) {
        try {
            if (val.length != 2) {
                return;
            }
            Settings.serverKey = val[1];
            System.out.println("SERVER KEY SAVED : " + Settings.serverKey);
        } catch (Exception e) {
            e.printStackTrace();
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
            int index = 0;
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (Object o : jsonArray) {
                    JSONArray data = (JSONArray) o;
                    ListItemView itemView = new ListItemView();
                    itemView.setTxtItemId(Integer.parseInt(data.get(0).toString()));
                    itemView.setTxtItemName(data.get(1).toString());
                    itemView.setImvIcon(Helper.getImageById(Integer.parseInt(data.get(2).toString()), 30, 30));
                    itemView.setIconID(Integer.parseInt(data.get(2).toString()));
                    itemView.setTxtQuantity(Integer.parseInt(data.get(3).toString()));
                    itemView.setTxtDesc(data.get(4).toString());
                    JSONArray jsOption = (JSONArray) data.get(5);
                    List<ItemOption> itemOptions = new ArrayList<>();
                    extractItemOption(itemOptions, jsOption);
                    itemView.setItemOptions(itemOptions);
                    listItemViews.add(itemView);
                    ItemBodyInfoController itemBodyInfoController = PlayerController.getInstance().getItemBodyInfoControllerById(Util.combinePlayerAndItem(itemView.getTxtItemId(), PlayerController.getInstance().getSelectedPlayerId(), index, 1));
                    if (itemBodyInfoController != null) {
                        itemBodyInfoController.onLoadData(ItemBody.fromItemView(itemView));
                    }
                    index++;
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
            isConnect = true;
        } catch (Exception e) {
            Platform.runLater(() -> {
                Helper.showInfo("Không kết nối được tới server");
                isConnect = false;
                System.exit(0);
            });
        }
    }

    private void getServerInfo() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (isConnect) {
                    if (Settings.serverKey != null) {
                        Service.gI().getServerInfo(out);
                    } else {
                        Service.gI().getServerKey(out);
                    }
                }
            } catch (Exception e) {
                isConnect = checkCanWrite();
                if (!isConnect) {
                    Platform.runLater(() -> {
                        Helper.showInfo("Mất kết nối tới server");
                        System.exit(0);
                    });
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private boolean checkCanWrite() {
        try {
            out.write("hallo to server");
            out.newLine();
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        try {
            onConnect();
            getServerInfo();
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
