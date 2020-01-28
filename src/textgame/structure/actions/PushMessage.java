/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;

/**
 *
 * @author David Brož
 */
public class PushMessage implements Action, java.io.Serializable{
    private String message;
    @Override
    public void act() {
        Game.getInstance().setInfo_line(message);
    }

    public PushMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PushMessage{" + message + '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
    }
    
}
