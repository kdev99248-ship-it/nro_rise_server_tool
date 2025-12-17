package com.nro_sv_mgn.nro_server_manager;

import javafx.application.Platform;
import org.json.simple.JSONArray;

import java.io.BufferedWriter;
import java.util.function.DoubleBinaryOperator;

public class Service {
    private static Service I;

    public Service() {
    }

    public void getServerKey(BufferedWriter out) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_REQUEST_SERVER_KEY, null));
        out.newLine();
        out.flush();
    }

    public static Service gI() {
        if (I == null) {
            I = new Service();
        }
        return I;
    }

    public void getListPlayer(BufferedWriter writer) throws Exception {
        writer.write(combineCommand(PanelCommand.CMD_LIST_PLAYER, null));
        writer.newLine();
        writer.flush();
    }

    public void getAccountStatus(BufferedWriter writer) throws Exception {
        writer.write(combineCommand(PanelCommand.CMD_STATUS_ACCOUNT, null));
        writer.newLine();
        writer.flush();
    }

    public String combineCommand(String cmd, String data) {
        return String.format("%s:%s:%s", cmd, data, Settings.serverKey);
    }

    public void kickPlayer(BufferedWriter writer, int playerId) throws Exception {
        writer.write(combineCommand(PanelCommand.CMD_KICK_PLAYER, String.valueOf(playerId)));
        writer.newLine();
        writer.flush();
    }

    public void sendBanPlayer(BufferedWriter writer, int playerId) throws Exception {
        writer.write(combineCommand(PanelCommand.CMD_BAN_PLAYER, String.valueOf(playerId)));
        writer.newLine();
        writer.flush();
    }

    public void sendSearchPlayer(BufferedWriter out, String val) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_SEARCH_PLAYER, val));
        out.newLine();
        out.flush();
    }

    public void sendDeletePlayer(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_DELETE_PLAYER, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void removeStuff(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_REMOVE_STUFF, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void deleteAccount(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_DELETE_ACCOUNT, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void sendAddItemToPlayer(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_ADD_ITEM, jsonString));
        out.newLine();
        out.flush();
    }

    public void getPlayerBag(BufferedWriter out, int id) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_GET_PLAYER_BAG, String.valueOf(id)));
        out.newLine();
        out.flush();
    }

    public void getServerInfo(BufferedWriter out) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_GET_INFO_SERVER, null));
        out.newLine();
        out.flush();
    }

    public void sendDeleteItemBag(BufferedWriter writer, int selectedPlayerId, int selectedItemId) throws Exception {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(selectedPlayerId);
        jsonArray.add(selectedItemId);
        writer.write(combineCommand(PanelCommand.CMD_DELETE_ITEM_BAG, jsonArray.toJSONString()));
        writer.newLine();
        writer.flush();
    }

    public void sendDeleteAllItemBag(BufferedWriter writer, int selectedPlayerId) throws Exception {
        writer.write(combineCommand(PanelCommand.CMD_DELETE_ALL_ITEM_BAG, String.valueOf(selectedPlayerId)));
        writer.newLine();
        writer.flush();
    }

    public void getPlayerItemBody(BufferedWriter writer, int playerId) throws Exception {
        writer.write(combineCommand(PanelCommand.GET_PLAYER_BODY, String.valueOf(playerId)));
        writer.newLine();
        writer.flush();
    }

    public void getPlayerItemBox(BufferedWriter writer, int playerId) throws Exception {
        writer.write(combineCommand(PanelCommand.GET_PLAYER_BOX, String.valueOf(playerId)));
        writer.newLine();
        writer.flush();
    }

    public void getPlayerBasePoint(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.GET_PLAYER_POINT, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void deleteItemBoxPlayer(BufferedWriter out, int playerId, int tempId) throws Exception {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(playerId);
        jsonArray.add(tempId);
        out.write(combineCommand(PanelCommand.DELETE_PLAYER_ITEM_BOX, jsonArray.toJSONString()));
        out.newLine();
        out.flush();
    }

    public void deleteAllItemBoxPlayer(BufferedWriter writer, int playerId) throws Exception {
        writer.write(combineCommand(PanelCommand.DELETE_ALL_ITEM_BOX_PLAYER, String.valueOf(playerId)));
        writer.newLine();
        writer.flush();
    }

    public void deleteAllItemBody(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.DELETE_ALL_ITEM_BODY, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void moveAllItemBodyToBag(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.MOVE_ALL_ITEM_BODY_TO_BAG, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void sendPlayerPoint(BufferedWriter out, JSONArray jsonArray) throws Exception {
        out.write(combineCommand(PanelCommand.UPDATE_PLAYER_POINT, jsonArray.toJSONString()));
        out.newLine();
        out.flush();
    }

    public void resetPlayerPoint(BufferedWriter out, int playerId) throws Exception {
        out.write(combineCommand(PanelCommand.RESET_PLAYER_POINT, String.valueOf(playerId)));
        out.newLine();
        out.flush();
    }

    public void addOptionForItem(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.ADD_ITEM_OPTION, jsonString));
        out.newLine();
        out.flush();
    }

    public void deleteItemOption(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.DELETE_ITEM_OPTION, jsonString));
        out.newLine();
        out.flush();
    }

    public void moveItemBodyToBag(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.MOVE_ITEM_TO_BAG, jsonString));
        out.newLine();
        out.flush();
    }

    public void deleteAllItemOption(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.DELETE_ALL_ITEM_OPTION, jsonString));
        out.newLine();
        out.flush();
    }

    public void deleteItem(BufferedWriter out, String jsonString) throws Exception {
        out.write(combineCommand(PanelCommand.DELETE_ITEM, jsonString));
        out.newLine();
        out.flush();
    }
}
