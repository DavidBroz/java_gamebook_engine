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
    public Class getReturnClass() {
        return Pair.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Pair))throw new IllegalArgumentException("Expected Pair got " + o.getClass());
        Pair temp = (Pair) o;
        if(temp.first instanceof Item) throw new IllegalArgumentException("Expected first Item in Pair got " + temp.first.getClass());
        if(temp.second instanceof Room) throw new IllegalArgumentException("Expected second Room in Pair got " + temp.second.getClass());
        room = (Room) temp.second;
        item = (Item) temp.first;
    }

    @Override
    public Object getValue() {
        return new Pair<>(item,room);
    }

    @Override
    public Class getSettingClass() {
        return Pair.class;
    }

    @Override
    public String toString() {
        return "ItemAddedToRoom: {"+ item + ", Room: "+room+"}";
    }
    
}
