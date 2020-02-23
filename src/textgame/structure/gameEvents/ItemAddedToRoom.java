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
public class ItemAddedToRoom extends GameEvent {
    
    private Item item;
    private Room room;
    
    public ItemAddedToRoom() {
    }

    public ItemAddedToRoom(Item item, Room room) {
        this.item = item;
        this.room = room;
    }

    @Override
    public GameEvent.GameEventType getEventType() {
        return GameEvent.GameEventType.ITEM_ADDED_TO_ROOM;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Item.class,Room.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(o[0] instanceof Item) throw new IllegalArgumentException("Expected Item, got " + o[0].getClass());
        if(o[1] instanceof Room) throw new IllegalArgumentException("Expected Room, got " +o[1].getClass());
        room = (Room) o[0];
        item = (Item) o[1];
    }

    @Override
    public Object[] getValues() {
        return new Object[]{item,room};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Item.class,Room.class};
    }

    @Override
    public String toString() {
        return "ItemAddedToRoom: {"+ item + ", Room: "+room+"}";
    }
    
}
