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
public class ItemAddedToInventory extends GameEvent {
    private Item item;

    public ItemAddedToInventory(Item item) {
        this.item = item;
    }

    public ItemAddedToInventory() {
    }
    

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ItemAddedToInventory: "+ item;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.ITEM_ADDED_TO_INVENTORY;
    } 

    @Override
    public Class[] getReturnClasses() {
         return new Class[]{Item.class};
    }

    @Override
    public Object[] getValues() {
        return new Object[]{getItem()};
    }

    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof Item))throw new IllegalArgumentException("Expected Item got " + o[0].getClass());
        setItem((Item)o[0]);
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Item.class};
    }
}
