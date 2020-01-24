/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Item;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class RoomLostItem extends GameEvent {

    private Room room;
    
    public RoomLostItem() {
    }

    public RoomLostItem(Room room) {
        this.room=room;
    }
    
    @Override
    public GameEventType getEventType() {
        return GameEventType.ROOM_LOST_ITEM;
    }

    @Override
    public Class getReturnClass() {
        return Room.class;
    }

    @Override
    public Class getSettingClass() {
        return Room.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Room))throw new IllegalArgumentException("Expected Room got " + o.getClass());
        room = ((Room)o);
    }

    @Override
    public Object getValue() {
        return room;
    }

    @Override
    public String toString() {
        return "RoomLostItem: " + room;
    }
    
}
