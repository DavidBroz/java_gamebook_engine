/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.Option;

/**
 *
 * @author David Brož
 */
public class RemoveOptionFromPlayer implements Action, java.io.Serializable{

    private Option option;
    private boolean isValid=true;

    public RemoveOptionFromPlayer(Option option) {
        this.option = option;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
    
    @Override
    public void act() {
        Game.getInstance().getPlayer().removeOption(option, true);
    }

    @Override
    public boolean isValid() {
       return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid=b;
    }
    
}
