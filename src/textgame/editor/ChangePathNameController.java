/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.editor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import textgame.structure.Room;

/**
 * FXML Controller class
 *
 * @author David Brož
 */
public class ChangePathNameController implements Initializable {

    static Stage stage;
    private boolean isOK = false;

    @FXML
    private TextField name_TextField;
    
    @FXML
    private Label roomNames;

    @FXML
    private Button ok_Button;

    @FXML
    private Label warning_label;
    /**
     * Initializes the controller class.
     */
    private static String result;
    private String ogName;
    private Room from, to;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result = null;
        warning_label.setText("");
        ok_Button.setDisable(!isOK);
        warning_label.setStyle("-fx-text-fill: #AB4642;");
        name_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (name_TextField.getText().isEmpty() || name_TextField.getText().equals("")) {
                isOK = false;
                warning_label.setText("Name must not be empty");
            } else if(from.getAllPaths().get(newValue) != null) {
                isOK = false;
                warning_label.setText("A path with name \"" + name_TextField.getText() + "\" already exists.");
            } else {
                warning_label.setText("");
                result = newValue;
                isOK = true;
            }
            
            ok_Button.setDisable(!isOK);
        });
    }

    @FXML
    private void handleOKButtonClick() {
        stage.close();
    }

    @FXML
    private void handleCancelButtonClick() {
        result = ogName;
        stage.close();
    }

    static String getResult() {
        return result;
    }

    public void setVariables(Room from, Room to, String pathName) {
        this.from = from;
        this.to=to;
        roomNames.setText(from.toString() +" -> "+to.toString());
        name_TextField.setText(pathName);
        ogName=pathName;
    }

    
    

}
