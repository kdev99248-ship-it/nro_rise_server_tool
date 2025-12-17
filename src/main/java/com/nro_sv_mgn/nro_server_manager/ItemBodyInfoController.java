package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemBody;
import com.nro_sv_mgn.nro_server_manager.dto.ItemOption;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

public class ItemBodyInfoController {

    private int playerId;

    @FXML
    private TableView<ItemOption> tblOption;

    @FXML
    private TextField txtId;

    @FXML
    private TextArea txtParam;

    @FXML
    private TableColumn<ItemOption, Integer> id;

    @FXML
    private TableColumn<ItemOption, String> name;

    @FXML
    private TableColumn<ItemOption, Integer> param;

    ObservableList<ItemOption> options = FXCollections.observableArrayList();

    private int type;

    private ItemBody data;

    public void blindData(int playerId, ItemBody data, int type) {
        this.playerId = playerId;
        this.data = data;
        this.type = type;
        this.options.clear();
        this.options.addAll(this.data.itemOptions);
        if (type == 1) {
            btnMoveItemToBag.setDisable(true);
        }
    }

    @FXML
    private ImageView imvIcon;

    @FXML
    private Text txtName;

    @FXML
    private VBox lstOption;

    public void onLoad() {
        if (data != null) {
            imvIcon.setImage(Helper.getImageByName(data.iconID));
            imvIcon.setFitHeight(108);
            imvIcon.setFitWidth(108);
            imvIcon.setPreserveRatio(true);
            txtName.setText(data.name);

            // add option
            for (ItemOption itemOption : data.itemOptions) {
                Text text = new Text(itemOption.getName().replace("#", String.valueOf(itemOption.getParam())));
                text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                text.setFill(Color.DARKGREEN);
                text.setWrappingWidth(200);
                lstOption.getChildren().add(text);
            }
        }
    }

    public void onLoadData(ItemBody data) {
        this.data = data;
        this.options.clear();
        this.options.addAll(data.itemOptions);
    }

    @FXML
    Button btnMoveItemToBag;

    @FXML
    private void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        param.setCellValueFactory(new PropertyValueFactory<>("param"));
        tblOption.setItems(options);
        tblOption.setEditable(false);
        tblOption.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public int getSelectedOptionId() {
        ItemOption index = tblOption.getSelectionModel().getSelectedItem();
        if (index != null) {
            return index.getId();
        }
        return -1;
    }

    @FXML
    public void onAddOption() {
        try {
            String optionId = txtId.getText();
            String params = txtParam.getText();
            if (optionId == null || params == null || optionId.isEmpty() || params.isEmpty()) {
                Helper.showInfo("Hãy nhập option id và param");
                return;
            }
            if (Integer.parseInt(params) > 2_000_000_000) {
                Helper.showInfo("Giá trị option quá lớn");
                return;
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(playerId);
            jsonArray.add(data.tempId);
            jsonArray.add(type);
            jsonArray.add(optionId);
            jsonArray.add(params);
            Service.gI().addOptionForItem(AppController.out, jsonArray.toJSONString());
            txtId.clear();
            txtParam.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemoveOption() {
        try {
            ItemOption option = tblOption.getSelectionModel().getSelectedItem();
            if (option != null) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(playerId);
                jsonArray.add(data.tempId);
                jsonArray.add(type);
                jsonArray.add(option.getId());
                Service.gI().deleteItemOption(AppController.out, jsonArray.toJSONString());
            } else {
                Helper.showInfo("Hãy chọn một option");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    Button btnClose;

    @FXML
    public void onMoveItemToBag() {
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(playerId);
            jsonArray.add(data.tempId);
            jsonArray.add(type);
            Service.gI().moveItemBodyToBag(AppController.out, jsonArray.toJSONString());
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteAllOption() {
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(playerId);
            jsonArray.add(data.tempId);
            jsonArray.add(type);
            Service.gI().deleteAllItemOption(AppController.out, jsonArray.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteItem() {
        try {
            if (Helper.showConfirm("Cảnh báo", "Bạn thực sự muốn xóa item này?")) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(playerId);
                jsonArray.add(data.tempId);
                jsonArray.add(type);
                Service.gI().deleteItem(AppController.out, jsonArray.toJSONString());
                Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
