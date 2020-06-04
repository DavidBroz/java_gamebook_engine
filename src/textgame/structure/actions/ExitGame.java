/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import javafx.application.Platform;
import textgame.structure.actions.Action;

/**
 *
 * @author david
 */
public class ExitGame implements Action, java.io.Serializable {

    private boolean isValid = true;
    public ExitGame() {
    }

    @Override
    public void act() {
        Platform.exit();
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
    }

    @Override
    public String toString() {
        return "ExitGame";
    }
    
    
}
