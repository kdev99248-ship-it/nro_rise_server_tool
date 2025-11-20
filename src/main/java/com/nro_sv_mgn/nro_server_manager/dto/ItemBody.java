package com.nro_sv_mgn.nro_server_manager.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemBody {
    public int tempId;
    public String name;
    public int quantity;
    public int iconID;
    public List<ItemOption> itemOptions = new ArrayList<>();
}
