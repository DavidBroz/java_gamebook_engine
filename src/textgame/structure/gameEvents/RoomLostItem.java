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
    public Class[] getReturnClasses() {
        return new Class[] {Room.class};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[] {Room.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof Room))throw new IllegalArgumentException("Expected Room got " + o[0].getClass());
        room = ((Room)o[0]);
    }

    @Override
    public Object[] getValues() {
        return new Object[]{room};
    }

    @Override
    public String toString() {
        return "RoomLostItem: " + room;
    }
    
}
