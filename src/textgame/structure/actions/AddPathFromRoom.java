/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class AddPathFromRoom extends Action {
    private Room fromWhere;
    private Room toWhere;
    @Override
    public void act() {
        fromWhere.addPath(toWhere);
    }

    public AddPathFromRoom(Room fromWhere, Room toWhere) {
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
    }

    @Override
    public String toString() {
        return "AddPathFromRoom{" + fromWhere + ", to " + toWhere + '}';
    }

    public Room getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Room fromWhere) {
        this.fromWhere = fromWhere;
    }

    public Room getToWhere() {
        return toWhere;
    }

    public void setToWhere(Room toWhere) {
        this.toWhere = toWhere;
    }
    
    
}
