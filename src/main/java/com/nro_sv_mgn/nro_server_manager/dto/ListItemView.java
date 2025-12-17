package com.nro_sv_mgn.nro_server_manager.dto;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ListItemView {
    private int txtItemId;
    private String txtItemName;
    private int txtQuantity;
    private String txtDesc;
    private ImageView imvIcon;
    private int iconID;
    private List<ItemOption> itemOptions = new ArrayList<>();

    public ListItemView() {
    }

    public ListItemView(int txtItemId, String txtItemName, int txtQuantity, String txtDesc, ImageView imvIcon) {
        this.txtItemId = txtItemId;
        this.txtItemName = txtItemName;
        this.txtQuantity = txtQuantity;
        this.txtDesc = txtDesc;
        this.imvIcon = imvIcon;
    }

    public List<ItemOption> getItemOptions() {
        return itemOptions;
    }

    public void setItemOptions(List<ItemOption> itemOptions) {
        this.itemOptions = itemOptions;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public int getTxtItemId() {
        return txtItemId;
    }

    public void setTxtItemId(int txtItemId) {
        this.txtItemId = txtItemId;
    }

    public String getTxtItemName() {
        return txtItemName;
    }

    public void setTxtItemName(String txtItemName) {
        this.txtItemName = txtItemName;
    }

    public int getTxtQuantity() {
        return txtQuantity;
    }

    public void setTxtQuantity(int txtQuantity) {
        this.txtQuantity = txtQuantity;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public ImageView getImvIcon() {
        return imvIcon;
    }

    public void setImvIcon(ImageView imvIcon) {
        this.imvIcon = imvIcon;
    }
}
