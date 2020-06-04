/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class Player implements java.io.Serializable {
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private HashMap<String,Integer> customValues;
    private ArrayList<Option> playerOptions;
    
    public Player(){
        inventory = new ArrayList();
        customValues = new HashMap<>();
        playerOptions = new ArrayList();
    }
    
    public void move(Room where){
        Game.getInstance().throwGameEvent(new GameEvent(new Object[]{currentRoom},GameEvent.GameEventType.PLAYER_LEFT_ROOM));
        currentRoom = where;
        Game.getInstance().throwGameEvent(new GameEvent(new Object[]{currentRoom},GameEvent.GameEventType.PLAYER_ENTERED_ROOM));  
        Game.getInstance().setCurrentImage(currentRoom.getImage());
    }
    
    public void addItem(Item item,boolean throwEvent){
        if(throwEvent)Game.getInstance().throwGameEvent(new GameEvent(new Object[]{item},GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY));
        inventory.add(item);
    }
    public void removeItem(Item item, boolean throwEvent){
        if(inventory.contains(item)){
            inventory.remove(item);
            if(throwEvent)Game.getInstance().throwGameEvent(new GameEvent(new Object[]{item},GameEvent.GameEventType.ITEM_REMOVED_FROM_INVENTORY));
        }
    }
    public boolean hasItem(Item item){
        for(Item i: inventory){
            if(i==item)return true;
        }
        return false;
    }
    
    public String inventoryToSting() {
        StringBuilder sb = new StringBuilder();
            sb.append("Mas u sebe: ");
        for(Item i : inventory){
            sb.append(i.getName()+", ");
        }
        return sb.toString();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void PickUpItem(Item item, boolean throwEvent) {
        
        inventory.add(item);
        if(!throwEvent)return;
        Game.getInstance().removeItemFromRooms(item);
        Game.getInstance().throwGameEvent(new GameEvent(new Object[]{item},GameEvent.GameEventType.ITEM_ADDED_TO_INVENTORY));
    }
    public Integer getCtustomValue(String key){
        return customValues.get(key);
    }
    
    public Map<String,Integer> getCustomValues(){
        return customValues;
    }

    public ArrayList<Option> getOptions() {
        return playerOptions;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addCustomValue(String valueName, Integer value){
        customValues.put(valueName, value);
    }
    
    public void setCustomValue(String valueName, Integer value, boolean throwEvent) {
        if (throwEvent) {
            Object[] temp = new Object [2];
            temp[0]=valueName;
            temp[1]=value;
            Game.getInstance().throwGameEvent(new GameEvent(temp,GameEvent.GameEventType.CUSTOM_VALUE_CHANGED));
        }
        customValues.replace(valueName, value);
    }

    public void addCustomValues(Map<String, Integer> newCustomValue) {
        if(newCustomValue ==null)return;
        customValues.putAll(newCustomValue);
    }

    public boolean hasCustomValue(String valueName) {
        return customValues.containsKey(valueName);
    }

    public void addOption(Option option, boolean throwEvent) {
        if(throwEvent)Game.getInstance().throwGameEvent(new GameEvent(new Object[]{option},GameEvent.GameEventType.OPTION_ADDED_TO_PLAYER));
        option.addReferencedBy(this);
        playerOptions.add(option);
    }

    public void removeOption(Option option , boolean throwEvent) {
        if(playerOptions.contains(option)){
            if(throwEvent)Game.getInstance().throwGameEvent(new GameEvent(new Object[]{option},GameEvent.GameEventType.OPTION_REMOVED_FROM_PLAYER));
            playerOptions.remove(option);
        }
    }
    
}
