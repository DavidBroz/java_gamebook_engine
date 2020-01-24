/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.GameEventListener;

/**
 *
 * @author David Brož
 */
public class DisableGameEventListener extends Action{
    private GameEventListener whatToDisable;

    public DisableGameEventListener(GameEventListener whatToDisable) {
        this.whatToDisable = whatToDisable;
    }

    @Override
    public void act() {
        whatToDisable.disable();
    }

    @Override
    public String toString() {
        return "DisableGameEventListener{" + whatToDisable + '}';
    }

    public GameEventListener getWhatToDisable() {
        return whatToDisable;
    }

    public void setWhatToDisable(GameEventListener whatToDisable) {
        this.whatToDisable = whatToDisable;
    }
    
    
}
