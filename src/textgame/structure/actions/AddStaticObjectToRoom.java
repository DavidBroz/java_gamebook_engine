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
public class AddStaticObjectToRoom implements Action {
    private StaticObject whatToAdd;
    private Room whereToAdd;
    @Override
    public void act() {
        whereToAdd.addStaticObjectToRoom(whatToAdd);
    }

    public AddStaticObjectToRoom(StaticObject whatToAdd, Room whereToAdd) {
        this.whatToAdd = whatToAdd;
        this.whereToAdd = whereToAdd;
    }

    @Override
    public String toString() {
        return "AddStaticObjectToRoom{" + whereToAdd +  ", Object: " +  whatToAdd + '}';
    }

    public StaticObject getWhatToAdd() {
        return whatToAdd;
    }

    public void setWhatToAdd(StaticObject whatToAdd) {
        this.whatToAdd = whatToAdd;
    }

    public Room getWhereToAdd() {
        return whereToAdd;
    }

    public void setWhereToAdd(Room whereToAdd) {
        this.whereToAdd = whereToAdd;
    }
    
    
}
