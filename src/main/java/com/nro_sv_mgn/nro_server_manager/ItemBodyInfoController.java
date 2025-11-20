package com.nro_sv_mgn.nro_server_manager;

import com.nro_sv_mgn.nro_server_manager.dto.ItemBody;
import com.nro_sv_mgn.nro_server_manager.dto.ItemOption;
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

    private ItemBody data;

    public void blindData(int playerId, ItemBody data) {
        this.playerId = playerId;
        this.data = data;
        this.options.clear();
        this.options.addAll(this.data.itemOptions);
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

    }

    @FXML
    public void onRemoveOption() {

    }

    @FXML
    public void onMoveItemToBag() {

    }

    @FXML
    public void onDeleteAllOption() {

    }

    @FXML
    public void onDeleteItem() {
    }
}
