/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Room;
import textgame.structure.StaticObject;

/**
 *
 * @author David Brož
 */
public class RemoveStaticObjectFromRoom implements Action, java.io.Serializable {
    private StaticObject whatToRemove;
    private Room whereToRemove;

    @Override
    public void act() {
        whereToRemove.removeStaticObjectFromRoom(whatToRemove,true);
    }

    public RemoveStaticObjectFromRoom(StaticObject whatToRemove, Room whereToRemove) {
        this.whatToRemove = whatToRemove;
        this.whereToRemove = whereToRemove;
    }

    @Override
    public String toString() {
        return "RemoveStaticObjectFromRoom{" +  whereToRemove + ", Object: " + whatToRemove +'}';
    }

    public StaticObject getWhatToRemove() {
        return whatToRemove;
    }

    public void setWhatToRemove(StaticObject whatToRemove) {
        this.whatToRemove = whatToRemove;
    }

    public Room getWhereToRemove() {
        return whereToRemove;
    }

    public void setWhereToRemove(Room whereToRemove) {
        this.whereToRemove = whereToRemove;
    }
    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
    }
    
    
}
