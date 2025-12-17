package com.nro_sv_mgn.nro_server_manager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.io.IOException;

public class HelloController {

    @FXML
    private Text txtOnline;

    @FXML
    private Text txtTimeUp;

    @FXML
    private Text txtTimeMaintance;

    @FXML
    private ProgressBar cpu;

    @FXML
    private ProgressBar ram;

    @FXML
    protected void onUpdatePackButtonClick() throws IOException {
        AppData.openConnect();
        AppData.sendRequestRes(PanelCommand.CMD_SEND_RES);
        AppData.downloadResourceWithProgress();
    }

    @FXML
    protected void onPlayerMenuButtonClick() throws IOException {
        HelloApplication.openPlayerFragment();
    }


    public void setTxtOnline(String value) {
        Platform.runLater(() -> txtOnline.setText(value));
    }

    public void setCpuProgress(double value, double maxValue) {
        Platform.runLater(() -> cpu.setProgress(value / maxValue));
    }

    public void setRamProgress(double value, double maxValue) {
        Platform.runLater(() -> ram.setProgress(value / maxValue));
    }

    public void setTxtTimeUp(String value) {
        Platform.runLater(() -> txtTimeUp.setText(value));
    }

    public void setTxtTimeMaintance(String value) {
        Platform.runLater(() -> txtTimeMaintance.setText(value));
    }
}
