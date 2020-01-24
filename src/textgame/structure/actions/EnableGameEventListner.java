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
public class EnableGameEventListner extends Action {
    private GameEventListener whatToEnable;

    public EnableGameEventListner(GameEventListener whatToEnable) {
        this.whatToEnable = whatToEnable;
    }

    @Override
    public void act() {
        whatToEnable.enable();
    }

    @Override
    public String toString() {
        return "EnableGameEventListnener{" + whatToEnable + '}';
    }

    public GameEventListener getWhatToEnable() {
        return whatToEnable;
    }

    public void setWhatToEnable(GameEventListener whatToEnable) {
        this.whatToEnable = whatToEnable;
    }
    
}
