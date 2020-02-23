/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;
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
    public Class[] getReturnClasses() {
        return new Class[]{String.class,Integer.class};
    }

    @Override
    public Class[] getSettingClasses() {
         return new Class[]{String.class,Integer.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof String))throw new IllegalArgumentException("Expected String got " + o[0].getClass());
        if(!(o[1] instanceof Integer))throw new IllegalArgumentException("Expected Integer got " + o[1].getClass());
        
        valueName=(String)o[0];
        value=(Integer)o[1];
    }

    @Override
    public Object[] getValues() {
        return new Object[] {valueName,value};
    }

    @Override
    public String toString() {
        return "CustomValueChanged{" + valueName + ", value=" + value + '}';
    }
}
