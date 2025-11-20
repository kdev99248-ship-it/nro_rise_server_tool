package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ListItemView;
import com.nro_sv_mgn.nro_server_manager.dto.ListPlayerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerController {
    private static PlayerController instance;

    public static Map<Integer, PlayerDetailController> playerControllers = new HashMap<>();

    private int selectedPlayerId = -1;

    public PlayerController() {
        instance = this; // lưu instance do FXML loader tạo
    }

    public static PlayerController getInstance() {
        return instance;
    }

    public static List<ListItemView> originList = new ArrayList<>();

    @FXML
    private Button btn_refresh_list;
    @FXML
    private Text txt_online;
    @FXML
    private Text txt_offline;

    @FXML
    private Text txt_ban;
    @FXML
    private TableView<ListPlayerView> tbt_list_player;
    @FXML
    private TableColumn<ListPlayerView, Integer> id;
    @FXML
    private TableColumn<ListPlayerView, String> name;
    @FXML
    private TableColumn<ListPlayerView, Integer> dlLv;
    @FXML
    private TableColumn<ListPlayerView, Long> gold;
    @FXML
    private TableColumn<ListPlayerView, Long> ruby;
    @FXML
    private TableColumn<ListPlayerView, Long> dn;
    @FXML
    private TableColumn<ListPlayerView, Long> cd;

    @FXML
    private Button btn_search;

    @FXML
    private TextField txt_search;

    @FXML
    private Button btnBan;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Button btnKick;

    @FXML
    private Button btnThoatKet;

    @FXML
    private Button btnShowInfo;
    @FXML
    private TableView<ListItemView> tbt_inventory;
    @FXML
    private TableColumn<ListPlayerView, Integer> txtItemId;
    @FXML
    TableColumn<ListPlayerView, String> txtItemName;
    @FXML
    private TableColumn<ListPlayerView, Integer> txtQuantity;
    @FXML
    private TableColumn<ListPlayerView, String> txtDesc;
    @FXML
    private TableColumn<ListPlayerView, ImageView> imvIcon;


    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnDeleteAllItem;

    @FXML
    private TextField txtSearchItem;

    @FXML
    private Button btnSearchItem;

    public static ObservableList<ListPlayerView> data = FXCollections.observableArrayList();

    public static ObservableList<ListItemView> itemData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            dlLv.setCellValueFactory(new PropertyValueFactory<>("dlLv"));
            gold.setCellValueFactory(new PropertyValueFactory<>("gold"));
            ruby.setCellValueFactory(new PropertyValueFactory<>("ruby"));
            dn.setCellValueFactory(new PropertyValueFactory<>("dn"));
            cd.setCellValueFactory(new PropertyValueFactory<>("cd"));
            tbt_list_player.setItems(data);
            tbt_list_player.setEditable(false);
            tbt_list_player.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tbt_list_player.setRowFactory(tv -> {
                TableRow<ListPlayerView> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    try {
                        if (event.getClickCount() == 2 && !row.isEmpty()) {
                            ListPlayerView selected = row.getItem();
                            this.selectedPlayerId = selected.getId();
                            System.out.println("Double clicked: " + selected.getName());
                            Service.gI().getPlayerBag(AppController.out, selected.getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                return row;
            });
            //init for inventory table
            txtItemId.setCellValueFactory(new PropertyValueFactory<>("txtItemId"));
            txtItemName.setCellValueFactory(new PropertyValueFactory<>("txtItemName"));
            txtQuantity.setCellValueFactory(new PropertyValueFactory<>("txtQuantity"));
            txtDesc.setCellValueFactory(new PropertyValueFactory<>("txtDesc"));
            imvIcon.setCellValueFactory(new PropertyValueFactory<>("imvIcon"));
            tbt_inventory.setItems(itemData);
            tbt_inventory.setEditable(false);
            tbt_list_player.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            // call load data CMD
            Service.gI().getListPlayer(AppController.out);
            Service.gI().getAccountStatus(AppController.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLoadListPlayer() {
        try {
            Service.gI().getListPlayer(AppController.out);
            Service.gI().getAccountStatus(AppController.out);
            Helper.showInfo("Load list player success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTextOnline(String o) {
        txt_online.setText("Online: " + o);
    }

    public void updateTextOffline(String o) {
        txt_offline.setText("Offline: " + o);
    }

    public void updateTextBan(String o) {
        txt_ban.setText("Ban: " + o);
    }

    @FXML
    public void onDeleteAccount() {
        try {
            // get player id
            int playerId = getSelectedPlayerId();
            if (playerId == -1) {
                Helper.showInfo("Hãy chọn một player");
                return;
            }
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa tài khoản này?Hành động này ko thể khôi phục được")) {
                Service.gI().deleteAccount(AppController.out, playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onKickPlayer() {
        try {
            // get player id
            int playerId = getSelectedPlayerId();
            if (playerId == -1) {
                Helper.showInfo("Hãy chọn một player");
                return;
            }
            Service.gI().kickPlayer(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onThoatKet() {
        try {
            // get player id
            int playerId = getSelectedPlayerId();
            if (playerId == -1) {
                Helper.showInfo("Hãy chọn một player");
                return;
            }
            Service.gI().removeStuff(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onShowInfo() {
        try {
            int playerId = getSelectedPlayerId();
            if (playerId != -1) {
                for (int id : playerControllers.keySet()) {
                    if (id == playerId) {
                        Helper.showInfo("Đã có 1 tab player này đang chạy rồi");
                        return;
                    }
                }
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("player-detail-view.fxml"));
                Parent root = loader.load();
                PlayerDetailController playerDetailController = loader.getController();
                playerDetailController.setPlayerId(playerId);
                playerDetailController.onLoad();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setOnCloseRequest(event -> {
                    removePlayerController(playerId);
                });
                stage.show();
                playerControllers.put(playerId, playerDetailController);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerDetailController getPlayerControllerById(int playerId) {
        return playerControllers.get(playerId);
    }

    public void removePlayerController(int playerId) {
        playerControllers.remove(playerId);
    }

    @FXML
    public void onDeletePlayer() {
        try {
            // get player id
            int playerId = getSelectedPlayerId();
            if (playerId == -1) {
                Helper.showInfo("Hãy chọn một player");
                return;
            }
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa player này?")) {
                Service.gI().sendDeletePlayer(AppController.out, playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getSelectedPlayerId() {
        ListPlayerView selectedPlayer = tbt_list_player.getSelectionModel().getSelectedItem();
        if (selectedPlayer != null) {
            return selectedPlayer.getId();
        } else {
            return -1; // hoặc -1 nếu bạn muốn trả về giá trị mặc định
        }
    }

    @FXML
    public void onBanPlayer() {
        try {
            // get player id
            int playerId = getSelectedPlayerId();
            if (playerId == -1) {
                Helper.showInfo("Hãy chọn một player");
                return;
            }
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn baned player này ? ")) {
                Service.gI().sendBanPlayer(AppController.out, playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSearchPlayer() {
        // get value from input
        try {
            String val = txt_search.getText();
            if (val == null || val.isEmpty()) {
                Helper.showInfo("Hãy nhập gì đó để tìm kiếm");
                return;
            }
            Service.gI().sendSearchPlayer(AppController.out, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddItem() {
        try {
            // get player id first
            // open mot cai form khac
            if (this.selectedPlayerId == -1) {
                Helper.showInfo("Hãy chọn 1 player");
                return;
            }
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-item-view.fxml"));
            Parent root = loader.load();
            AddItemController controller = loader.getController();
            controller.setPlayerId(this.selectedPlayerId);
            Stage playerStage = new Stage();
            playerStage.setTitle("Buff Item Player");
            playerStage.setScene(new Scene(root));
            playerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSelectedItemId() {
        if (tbt_inventory.getSelectionModel().getSelectedItem() != null) {
            return tbt_inventory.getSelectionModel().getSelectedItem().getTxtItemId();
        }
        return -1;
    }

    @FXML
    public void onDeleteItem() {
        try {
            if (this.selectedPlayerId == -1) {
                Helper.showInfo("Hãy chọn 1 player");
                return;
            }

            if (getSelectedItemId() == -1) {
                Helper.showInfo("Hãy chọn 1 item");
                return;
            }
            Service.gI().sendDeleteItemBag(AppController.out, selectedPlayerId, getSelectedItemId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onDeleteAllItem() {
        try {
            if (this.selectedPlayerId == -1) {
                Helper.showInfo("Hãy chọn 1 player");
                return;
            }
            Service.gI().sendDeleteAllItemBag(AppController.out, selectedPlayerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSearchItem() {
        String textSearch = txtSearchItem.getText();
        if (textSearch == null || textSearch.isEmpty()) {
            itemData.clear();
            itemData.addAll(originList);
            return;
        }

        List<ListItemView> itemsFound = new ArrayList<>();
        for (ListItemView item : originList) { // <<--- search trên originList
            if (item.getTxtItemName().toLowerCase().contains(textSearch.toLowerCase())) {
                itemsFound.add(item);
            }
        }

        itemData.clear();
        itemData.addAll(itemsFound);
    }

}
