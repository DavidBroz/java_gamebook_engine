/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.editor;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import textgame.structure.Game;

/**
 * FXML Controller class
 *
 * @author David Brož
 */
public class AddCustomValueController implements Initializable {

    static Stage addCustomValueStage;
    private boolean isOK = false;

    @FXML
    private TextField name_TextField, value_TextField;

    @FXML
    private Button ok_Button;

    @FXML
    private Label warning_label;
    /**
     * Initializes the controller class.
     */
    private static Map<String, Integer> result;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result = null;
        value_TextField.setText("0");
        warning_label.setText("");
        ok_Button.setDisable(!isOK);
        warning_label.setStyle("-fx-text-fill: #AB4642;");
        name_TextField.textProperty().addListener((observable) -> {
            if (name_TextField.getText().isEmpty() || name_TextField.getText().equals("")) {
                isOK = false;
                warning_label.setText("Name must not be empty");
            } else if (Game.getInstance().getPlayer().hasCustomValue(name_TextField.getText())) {
                isOK = false;
                warning_label.setText("A custom value with name \"" + name_TextField.getText() + "\" already exists.");
            } else {
                warning_label.setText("");
                isOK = true;
            }
            
            ok_Button.setDisable(!isOK);
        });
        value_TextField.textProperty().addListener((observable) -> {
            if (!value_TextField.getText().matches("\\d*")) {
                value_TextField.setText(value_TextField.getText().replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void handleOKButtonClick() {
        Map<String, Integer> temp_map = new HashMap();
        if(value_TextField.getText().equals(""))value_TextField.setText("0");
        temp_map.put(name_TextField.getText(), Integer.parseInt(value_TextField.getText()));
        result = temp_map;
        addCustomValueStage.close();
    }

    @FXML
    private void handleCancelButtonClick() {
        addCustomValueStage.close();
    }

    static Map<String, Integer> getResult() {
        return result;
    }

}
