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
public class SetCustomValue implements Action {

    private String valueName;
    private Integer value;

    public SetCustomValue(String valueName, Integer value) {
        this.valueName = valueName;
        this.value = value;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
    
    @Override
    public void act() {
        Game.getInstance().getPlayer().setCustomValue(valueName,value);
    }

    @Override
    public String toString() {
        return "SetCustomValue{"+ valueName +":"+value+ '}';
    }
    
}
