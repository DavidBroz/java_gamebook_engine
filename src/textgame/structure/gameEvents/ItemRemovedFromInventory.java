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
    public Class[] getReturnClasses() {
        return new Class[]{Item.class};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Item.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(o[0] instanceof Item) item = (Item)o[0];
        else throw new IllegalArgumentException("Expected Item got " + o[0].getClass());
    }

    @Override
    public Object[] getValues() {
        return new Object[]{item};
    }

    @Override
    public String toString() {
        return "ItemRemovedFromInventory{"+item+"}";
    }
    
}
