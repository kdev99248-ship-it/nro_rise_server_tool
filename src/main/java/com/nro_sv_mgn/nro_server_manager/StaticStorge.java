package com.nro_sv_mgn.nro_server_manager;

import java.util.prefs.Preferences;

public class StaticStorge {
    private static final Preferences prefs = Preferences.userNodeForPackage(Settings.class);

    public static void saveSetting(String key, String value) {
        prefs.put(key, value);
    }

    public static String getSetting(String key, String defaultValue) {
        return prefs.get(key, defaultValue);
    }

    public static void removeSetting(String key) {
        prefs.remove(key);
    }

    public static void clearAll() throws Exception {
        prefs.clear();
    }
}
