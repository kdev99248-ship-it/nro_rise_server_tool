package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemBody;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PlayerDetailController {

    public ItemBody itAo;

    public ItemBody itQuan;

    public ItemBody itGang;

    public ItemBody itGiay;

    public ItemBody itRada;

    public ItemBody itGlt;

    public ItemBody itCt;

    public ItemBody itPet;

    public ItemBody itCo;

    public ItemBody itChanMenh;

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

    public void showItemInfo(ItemBody it) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Helper.class.getResource("item-body-info.fxml"));
            Parent root = fxmlLoader.load();
            ItemBodyInfoController controller = fxmlLoader.getController();
            controller.blindData(playerId, it);
            controller.onLoad();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowItem(MouseEvent mouseEvent) {
        ImageView imv = (ImageView) mouseEvent.getSource();

        if (imv == imvAo && itAo != null) {
            showItemInfo(itAo);
        }
        if (imv == imvQuan && itQuan != null) {
            showItemInfo(itQuan);
        }
        if (imv == imvGang && itGang != null) {
            showItemInfo(itGang);
        }
        if (imv == imvGiay && itGiay != null) {
            showItemInfo(itGiay);
        }
        if (imv == imvRada && itRada != null) {
            showItemInfo(itRada);
        }
        if (imv == imvGlt && itGlt != null) {
            showItemInfo(itGlt);
        }
        if (imv == imvCaiTrang && itCt != null) {
            showItemInfo(itCt);
        }
        if (imv == imvFlagBag && itCo != null) {
            showItemInfo(itCo);
        }
        if (imv == imvPet && itPet != null) {
            showItemInfo(itPet);
        }
        if (imv == imvChanMenh && itChanMenh != null) {
            showItemInfo(itChanMenh);
        }
    }

    public void onLoad() {
        try {
            //load item body
            Service.gI().getPlayerItemBody(AppController.out, playerId);
            // load chi so
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshItemBody(MouseEvent mouseEvent) {
        try {
            Service.gI().getPlayerItemBody(AppController.out, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
