/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Option;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class OptionRemovedFromPlayer extends GameEvent {
    
    private Option option;
    
    public OptionRemovedFromPlayer() {
    }

    public OptionRemovedFromPlayer(Option option) {
        this.option=option;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.OPTION_REMOVED_FROM_PLAYER;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Option.class};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Option.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof Option))throw new IllegalArgumentException("Expected Option got " + o[0].getClass());
            option=((Option)o[0]);
    }

    @Override
    public Object[] getValues() {
        return new Object[]{option};
    }

    @Override
    public String toString() {
        return "OptionRemovedFromPlayer{" + option + '}';
    }

    
    
}
