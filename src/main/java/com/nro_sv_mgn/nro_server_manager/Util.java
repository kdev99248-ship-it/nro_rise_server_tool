package com.nro_sv_mgn.nro_server_manager;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    public static String powerToString(double power) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat num = NumberFormat.getInstance(locale);
        num.setMaximumFractionDigits(0); // Không lấy số lẻ sau dấu phẩy
        return num.format(power);
    }

    public static String combinePlayerAndItem(int tempId, int playerId, int index, int type) {
        return tempId + ":" + playerId + ":" + index + ":" + type;
    }

    public static int[] decodePlayerAndItem(String string) {
        String[] split = string.split(":");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }
}
