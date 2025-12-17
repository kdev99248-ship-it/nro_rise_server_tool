package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemBody;
import com.nro_sv_mgn.nro_server_manager.dto.ListItemView;
import com.nro_sv_mgn.nro_server_manager.dto.PlayerPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class PlayerDetailController {
    public ImageView imvAvatar;
    public Text txtHpMax;
    public Text txtMpMax;
    public Text txtDame;
    public Text txtDefMax;
    public Text txtCritMax;

    public TextField txtHpG;
    public TextField txtMpG;
    public TextField txtAttack;
    public TextField txtCritG;
    public TextField txtPower;
    public TextField txtTn;
    public TextField txtDefG;
    public TextField txtNe;


    public ItemBody itAo;

    public ItemBody itQuan;

    public ItemBody itGang;

    public PlayerPoint playerPoint;

    public ItemBody itGiay;

    public ItemBody itRada;

    public ItemBody itGlt;

    public ItemBody itCt;

    public ItemBody itPet;

    public ItemBody itCo;

    public ItemBody itChanMenh;

    public List<ItemBody> itemsBoxOriginList = new ArrayList<>();
    @FXML
    public TableColumn<ItemBody, Integer> tblId;
    @FXML
    public TableColumn<ItemBody, String> tblName;
    @FXML
    public TableColumn<ItemBody, String> tblDesc;
    @FXML
    public TableColumn<ItemBody, Integer> tblQuantity;
    @FXML
    public TableColumn<ItemBody, Integer> tblIcon;

    @FXML
    public TableView<ItemBody> tblItemsBox;

    public ObservableList<ItemBody> dataItemBox = FXCollections.observableArrayList();

    @FXML
    public TextField txtSearchBox;


    private int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    @FXML
    private ImageView imvAo;
    @FXML
    private ImageView imvQuan;

    @FXML
    private ImageView imvCaiTrang;
    @FXML
    private ImageView imvGang;

    @FXML
    private ImageView imvGiay;

    @FXML
    private ImageView imvRada;

    @FXML
    private ImageView imvGlt;

    @FXML
    private ImageView imvFlagBag;

    @FXML
    private ImageView imvPet;

    @FXML
    private ImageView imvChanMenh;

    @FXML
    public void initialize() {
        tblId.setCellValueFactory(new PropertyValueFactory<>("tempId"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblIcon.setCellValueFactory(new PropertyValueFactory<>("iconID"));
        tblIcon.setCellFactory(col -> new TableCell<ItemBody, Integer>() {
            @Override
            protected void updateItem(Integer integer, boolean b) {
                super.updateItem(integer, b);
                if (b) {
                    setGraphic(null);
                    return;
                }

                ImageView image = Helper.getImageById(integer, 30, 30);
                setGraphic(image);
            }
        });
        tblItemsBox.setItems(dataItemBox);
        tblItemsBox.setEditable(false);
        tblItemsBox.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // defined table here
    }

    public void getImv(ImageView imv, ItemBody it) {
        imv.setImage(Helper.getImageByName(it.iconID));
        imv.setFitWidth(40);
        imv.setFitHeight(40);
        imv.setPreserveRatio(true);
        imv.setSmooth(true);
    }

    public void loadImv() {
        if (itAo != null) {
            getImv(imvAo, itAo);
        }
        if (itQuan != null) {
            getImv(imvQuan, itQuan);
        }
        if (itGang != null) {
            getImv(imvGang, itGang);
        }
        if (itGiay != null) {
            getImv(imvGiay, itGiay);
        }
        if (itRada != null) {
            getImv(imvRada, itRada);
        }
        if (itGlt != null) {
            getImv(imvGlt, itGlt);
        }
        if (itCt != null) {
            getImv(imvCaiTrang, itCt);
        }
        if (itChanMenh != null) {
            getImv(imvChanMenh, itChanMenh);
        }
        if (itCo != null) {
            getImv(imvFlagBag, itCo);
        }
        if (itPet != null) {
            getImv(imvPet, itPet);
        }
    }

    public void showItemInfo(ItemBody it, int type, int index) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Helper.class.getResource("item-body-info.fxml"));
            Parent root = fxmlLoader.load();
            ItemBodyInfoController controller = fxmlLoader.getController();
            controller.blindData(playerId, it, type);
            controller.onLoad();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            PlayerController.itemBodyInfoControllers.put(Util.combinePlayerAndItem(it.tempId, playerId, index, type), controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowItem(MouseEvent mouseEvent) {
        ImageView imv = (ImageView) mouseEvent.getSource();

        if (imv == imvAo && itAo != null) {
            showItemInfo(itAo, 0, 0);
        }
        if (imv == imvQuan && itQuan != null) {
            showItemInfo(itQuan, 0, 1);
        }
        if (imv == imvGang && itGang != null) {
            showItemInfo(itGang, 0, 2);
        }
        if (imv == imvGiay && itGiay != null) {
            showItemInfo(itGiay, 0, 3);
        }
        if (imv == imvRada && itRada != null) {
            showItemInfo(itRada, 0, 4);
        }
        if (imv == imvGlt && itGlt != null) {
            showItemInfo(itGlt, 0, 6);
        }
        if (imv == imvCaiTrang && itCt != null) {
            showItemInfo(itCt, 0, 5);
        }
        if (imv == imvFlagBag && itCo != null) {
            showItemInfo(itCo, 0, 9);
        }
        if (imv == imvPet && itPet != null) {
            showItemInfo(itPet, 0, 7);
        }
        if (imv == imvChanMenh && itChanMenh != null) {
            showItemInfo(itChanMenh, 0, 8);
        }
    }

    public void onLoad() {
        try {
            //load item body
            Service.gI().getPlayerItemBody(AppController.out, playerId);
            Service.gI().getPlayerBasePoint(AppController.out, playerId);
            Service.gI().getPlayerItemBox(AppController.out, playerId);
            // load chi so
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshItemBox() {
        try {
            Service.gI().getPlayerItemBox(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshItemBody(MouseEvent mouseEvent) {
        try {
            Service.gI().getPlayerItemBody(AppController.out, playerId);
            Service.gI().getPlayerBasePoint(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showItemBoxInfo() {
        ItemBody itemBody = tblItemsBox.getSelectionModel().getSelectedItem();
        int index = tblItemsBox.getSelectionModel().getSelectedIndex();
        if (itemBody != null) {
            showItemInfo(itemBody, 2, index);
        } else {
            Helper.showInfo("Hãy chọn một item");
        }
    }

    @FXML
    public void deleteItemBox() {
        try {
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa ?")) {
                ItemBody itemBody = tblItemsBox.getSelectionModel().getSelectedItem();
                if (itemBody != null) {
                    Service.gI().deleteItemBoxPlayer(AppController.out, playerId, itemBody.tempId);
                } else {
                    Helper.showInfo("Hãy chọn một item");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteAllItemBody() {
        try {
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa ?")) {
                Service.gI().deleteAllItemBody(AppController.out, playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void moveAllItemToBag() {
        try {
            Service.gI().moveAllItemBodyToBag(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteAllItemBox() {
        try {
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa ?")) {
                Service.gI().deleteAllItemBoxPlayer(AppController.out, playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onUpdatePlayerPoint() {
        try {
            double hpg = Double.parseDouble(txtHpG.getText());
            double mpG = Double.parseDouble(txtMpG.getText());
            double dameG = Double.parseDouble(txtAttack.getText());
            int critg = Integer.parseInt(txtCritG.getText());
            long power = Long.parseLong(txtPower.getText());
            long tn = Long.parseLong(txtTn.getText());
            int defg = Integer.parseInt(txtDefG.getText());
            int tlNeDon = Integer.parseInt(txtNe.getText());

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(playerId);
            jsonArray.add(hpg);
            jsonArray.add(mpG);
            jsonArray.add(dameG);
            jsonArray.add(critg);
            jsonArray.add(power);
            jsonArray.add(tn);
            jsonArray.add(defg);
            jsonArray.add(tlNeDon);
            Service.gI().sendPlayerPoint(AppController.out, jsonArray);
        } catch (Exception e) {
            Helper.showInfo("Chỉ số không hợp lệ");
        }
    }

    @FXML
    public void showFormAddItemBox() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-item-view.fxml"));
            Parent root = loader.load();
            AddItemController controller = loader.getController();
            controller.setPlayerId(this.playerId);
            controller.setTypeAdd(1);
            Stage playerStage = new Stage();
            playerStage.setTitle("Buff Item Player");
            playerStage.setScene(new Scene(root));
            playerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerPoint(PlayerPoint playerPoint) {
        imvAvatar.setImage(Helper.getImageByName(playerPoint.avatarId));
        this.playerPoint = playerPoint;
        txtHpG.setText(String.valueOf(playerPoint.getHp()));
        txtMpG.setText(String.valueOf(playerPoint.getMp()));
        txtAttack.setText(String.valueOf(playerPoint.getAttack()));
        txtCritG.setText(String.valueOf(playerPoint.getCrits()));
        txtPower.setText(String.valueOf(playerPoint.getPower()));
        txtTn.setText(String.valueOf(playerPoint.getTn()));
        txtDefG.setText(String.valueOf(playerPoint.getDef()));
        txtNe.setText(String.valueOf(playerPoint.getTlNeDon()));

        txtHpMax.setText("HP: " + Util.powerToString(playerPoint.getCurrentHp()) + " / " + Util.powerToString(playerPoint.getHpMax()));
        txtMpMax.setText("MP: " + Util.powerToString(playerPoint.getCurrentMp()) + " / " + Util.powerToString(playerPoint.getMpMax()));
        txtDame.setText("DAME: " + Util.powerToString(playerPoint.getDame()));
        txtDefMax.setText("DEF: " + Util.powerToString(playerPoint.getDefMax()));
        txtCritMax.setText("CRITS: " + Util.powerToString(playerPoint.getCritMax()));
    }

    @FXML
    public void searchItemBox() {
        String query = txtSearchBox.getText();
        if (query.isEmpty()) {
            onLoadItemBoxList();
            return;
        }
        List<ItemBody> itemsFound = new ArrayList<>();
        for (ItemBody item : itemsBoxOriginList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                itemsFound.add(item);
            }
        }
        this.dataItemBox.clear();
        this.dataItemBox.addAll(itemsFound);
    }

    public void onLoadItemBoxList() {
        this.dataItemBox.clear();
        this.dataItemBox.addAll(itemsBoxOriginList);
    }

    public void resetItem() {
        this.itAo = null;
        this.itQuan = null;
        this.itGang = null;
        this.itGiay = null;
        this.itRada = null;
        this.itCt = null;
        this.itGlt = null;
        this.itPet = null;
        this.itChanMenh = null;
        this.itCo = null;

        this.imvAo.setImage(null);
        this.imvQuan.setImage(null);
        this.imvGang.setImage(null);
        this.imvGiay.setImage(null);
        this.imvRada.setImage(null);
        this.imvRada.setImage(null);
        this.imvCaiTrang.setImage(null);
        this.imvChanMenh.setImage(null);
        this.imvGlt.setImage(null);
        this.imvPet.setImage(null);
        this.imvFlagBag.setImage(null);
    }

    @FXML
    public void onResetPlayerPoint(MouseEvent mouseEvent) {
        try {
            Service.gI().resetPlayerPoint(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
