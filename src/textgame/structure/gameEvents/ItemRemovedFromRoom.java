/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Item;
import textgame.structure.Room;
import textgame.utility.Pair;

/**
 *
 * @author David Brož
 */
public class ItemRemovedFromRoom extends GameEvent {
    
    private Item item;
    private Room room;
    
    public ItemRemovedFromRoom() {
    }

    public ItemRemovedFromRoom(Item item, Room room) {
        this.item = item;
        this.room = room;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.ITEM_REMOVED_FROM_ROOM;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Item.class, Room.class};
    }

    @Override
    public void setValues(Object[] o) {

        if(o[0] instanceof Item) throw new IllegalArgumentException("Expected Item, got " + o[0].getClass());
        if(o[1] instanceof Room) throw new IllegalArgumentException("Expected Room, got " + o[1].getClass());
        room = (Room) o[1];
        item = (Item) o[0];
    }

    @Override
    public Object[] getValues() {
        return new Object[]{item,room};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Item.class, Room.class};
    }

    @Override
    public String toString() {
        return "ItemRemovedFromRoom: {"+ item + ", Room: "+room+"}";
    }
    
}
