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
    public Class getReturnClass() {
        return Item.class;
    }

    @Override
    public Object getValue() {
        return getItem();
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Item))throw new IllegalArgumentException("Expected Item got " + o.getClass());
        setItem((Item)o);
    }

    @Override
    public Class getSettingClass() {
        return Item.class;
    }
}
