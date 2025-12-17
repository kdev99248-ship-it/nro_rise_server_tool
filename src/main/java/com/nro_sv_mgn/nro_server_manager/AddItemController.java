package com.nro_sv_mgn.nro_server_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

public class AddItemController {
    private int playerId;
    private int typeAdd;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setTypeAdd(int typeAdd) {
        this.typeAdd = typeAdd;
    }

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextArea txtOptions;

    @FXML
    private Button btnClose;

    @FXML
    public void onAddItem() {
        // validate ne
        // get item id
        try {
            int itemId = Integer.parseInt(txtItemId.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            String options = txtOptions.getText();
            boolean isValid = validateOption(options);
            if (isValid) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(itemId);
                jsonArray.add(quantity);
                jsonArray.add(options);
                jsonArray.add(playerId);
                jsonArray.add(typeAdd);
                Service.gI().sendAddItemToPlayer(AppController.out, jsonArray.toJSONString());
                // after add close this form
                onCancelAdd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateOption(String options) {
        String[] option = options.split(",");
        if (option.length == 0) {
            Helper.showInfo("Hãy thêm 1 option");
            return false;
        }
        for (String s : option) {
            String[] optionV = s.split("-");
            if (optionV.length != 2) {
                Helper.showInfo("Định dạng option ko đúng");
                return false;
            }
            try {
                if (Integer.parseInt(optionV[1]) > 2_000_000_000) {
                    Helper.showInfo("Giá trị option vượt quá 2 tỷ");
                    return false;
                }
            } catch (Exception e) {
                Helper.showInfo("Giá trị option không đúng");
                return false;
            }
        }
        return true;
    }

    @FXML
    public void onCancelAdd() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
