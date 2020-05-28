/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.actions.Action;
import textgame.utility.ResourceManager;

/**
 *
 * @author david
 */
public class LoadGame implements Action {

    private boolean isValid = true;
    public LoadGame() {
    }

    @Override
    public void act() {
        Game.setInstance(ResourceManager.loadGameDialog());
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
        return "LoadGame";
    }
    
    
    
}
