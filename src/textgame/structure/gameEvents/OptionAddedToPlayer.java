/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Option;

/**
 *
 * @author David Brož
 */
public class OptionAddedToPlayer extends GameEvent{

    private Option option;

    public OptionAddedToPlayer(Option option) {
        this.option = option;
    }

    public OptionAddedToPlayer() {
    }
    
    
    @Override
    public GameEventType getEventType() {
        return GameEventType.OPTION_ADDED_TO_PLAYER;
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
        return "OptionAddedToPlayer{" + option + '}';
    }

    
    
}
