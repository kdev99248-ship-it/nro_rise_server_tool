module com.nro_sv_mgn.nro_server_manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires junrar;
    requires java.prefs;
    requires javafx.graphics;
    requires json.simple;

    opens com.nro_sv_mgn.nro_server_manager to javafx.fxml;
    opens com.nro_sv_mgn.nro_server_manager.dto to javafx.base;
    exports com.nro_sv_mgn.nro_server_manager;
}