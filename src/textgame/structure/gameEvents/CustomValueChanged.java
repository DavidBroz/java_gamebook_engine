/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.utility.Pair;

/**
 *
 * @author David Brož
 */
public class CustomValueChanged extends GameEvent {
    
    private String valueName;
    private Integer value;
    public CustomValueChanged(String valueName, Integer value) {
        this.value=value;
        this.valueName=valueName;
    }

    public CustomValueChanged() {
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.CUSTOM_VALUE_CHANGED;
    }

    @Override
    public Class getReturnClass() {
        return Pair.class;
    }

    @Override
    public Class getSettingClass() {
        return Pair.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Pair))throw new IllegalArgumentException("Expected Pair got " + o.getClass());
        Pair p= (Pair)o;
        if(!(p.first instanceof String))throw new IllegalArgumentException("Got Pair but first is not a String");
        if(!(p.second instanceof Integer))throw new IllegalArgumentException("Got Pair but second is not a Integer");
        
        valueName=(String)p.first;
        value=(Integer)p.second;
    }

    @Override
    public Object getValue() {
        return new Pair(valueName,value);
    }

    @Override
    public String toString() {
        return "CustomValueChanged{" + valueName + ", value=" + value + '}';
    }

    
    
}
