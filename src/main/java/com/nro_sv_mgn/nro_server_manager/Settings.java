package com.nro_sv_mgn.nro_server_manager;

import javafx.application.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    public static final int RES_PORT = 1234;
    //    public static String SERVER_IP = "160.250.246.84";
    public static String SERVER_IP = "127.0.0.1";
    public static int SERVER_PORT = 8888;
    public static final String RESOURCE_PATH =
            System.getProperty("user.home") + "/.myapp/resources/images";
    public static final String PACKAGE_PATH = System.getProperty("user.home") + "/.myapp/resources";
    public static final String PACKAGE_NAME = "update_pack.zip";
}
