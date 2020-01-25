/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Item;

/**
 *
 * @author David Brož
 */
public class ItemRemovedFromInventory extends GameEvent{

    private Item item;
    
    public ItemRemovedFromInventory() {
    }

    public ItemRemovedFromInventory(Item item) {
        this.item=item;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.ITEM_REMOVED_FROM_INVENTORY;
    }

    @Override
    public Class getReturnClass() {
        return Item.class;
    }

    @Override
    public Class getSettingClass() {
        return Item.class;
    }

    @Override
    public void setValue(Object o) {
        if(o instanceof Item) item = (Item)o;
        else throw new IllegalArgumentException("Expected Item got " + o.getClass());
    }

    @Override
    public Object getValue() {
        return item;
    }

    @Override
    public String toString() {
        return "ItemRemovedFromInventory{"+item+"}";
    }
    
}
