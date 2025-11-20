package com.nro_sv_mgn.nro_server_manager;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Service {
    private static Service I;

    public static Service gI() {
        if (I == null) {
            I = new Service();
        }
        return I;
    }

    public void getListPlayer(BufferedWriter writer) throws Exception {
        writer.write(PanelCommand.CMD_LIST_PLAYER);
        writer.newLine();
        writer.flush();
    }

    public void getAccountStatus(BufferedWriter writer) throws Exception {
        writer.write(PanelCommand.CMD_STATUS_ACCOUNT);
        writer.newLine();
        writer.flush();
    }

    public String combineCommand(String cmd, String data) {
        return String.format("%s:%s", cmd, data);
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
        out.write(combineCommand(PanelCommand.CMD_ADD_ITEM_BAG, jsonString));
        out.newLine();
        out.flush();
    }

    public void getPlayerBag(BufferedWriter out, int id) throws Exception {
        out.write(combineCommand(PanelCommand.CMD_GET_PLAYER_BAG, String.valueOf(id)));
        out.newLine();
        out.flush();
    }

    public void getServerInfo(BufferedWriter out) throws Exception {
        out.write(PanelCommand.CMD_GET_INFO_SERVER);
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
}
