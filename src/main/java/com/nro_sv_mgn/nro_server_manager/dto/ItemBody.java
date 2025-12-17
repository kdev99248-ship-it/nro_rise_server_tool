package com.nro_sv_mgn.nro_server_manager.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemBody {
    public int tempId;
    public String name;
    public int quantity;
    public String description;
    public int iconID;
    public List<ItemOption> itemOptions = new ArrayList<>();

    public static ItemBody fromItemView(ListItemView itemView) {
        ItemBody itemBody = new ItemBody();
        itemBody.tempId = itemView.getTxtItemId();
        itemBody.name = itemView.getTxtItemName();
        itemBody.quantity = itemView.getTxtQuantity();
        itemBody.description = itemView.getTxtDesc();
        itemBody.iconID = itemView.getIconID();
        itemBody.itemOptions = itemView.getItemOptions();
        return itemBody;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public List<ItemOption> getItemOptions() {
        return itemOptions;
    }

    public void setItemOptions(List<ItemOption> itemOptions) {
        this.itemOptions = itemOptions;
    }

    public ItemBody(int tempId, String name, int quantity, String description, int iconID, List<ItemOption> itemOptions) {
        this.tempId = tempId;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.iconID = iconID;
        this.itemOptions = itemOptions;
    }

    public ItemBody() {
    }
}
