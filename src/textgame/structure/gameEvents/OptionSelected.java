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
public class OptionSelected extends GameEvent {
    private Option option;

    public OptionSelected(Option option) {
        this.option = option;
    }

    public OptionSelected() {
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.OPTION_SELECTED;
    }

    @Override
    public String toString() {
        return "OptionSelected: " + option;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Option.class};
    }
    
    @Override
    public Object[] getValues() {
        return new Object[]{option};
    }
    
    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof Option))throw new IllegalArgumentException("Expected Option got " + o[0].getClass());
        setOption((Option)o[0]);
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Option.class};
    }
    
}
