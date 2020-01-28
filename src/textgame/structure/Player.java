/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.OptionRemovedFromPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import textgame.structure.gameEvents.CustomValueChanged;
import textgame.structure.gameEvents.ItemAddedToInventory;
import textgame.structure.gameEvents.ItemRemovedFromInventory;
import textgame.structure.gameEvents.OptionAddedToPlayer;
import textgame.structure.gameEvents.PlayerEnteredRoom;
import textgame.structure.gameEvents.PlayerLeftRoom;
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
    
    public void MovePlayer(Room where){
        Game.getInstance().throwGameEvent(new PlayerLeftRoom(currentRoom));
        currentRoom = where;
        Game.getInstance().throwGameEvent(new PlayerEnteredRoom(where));  
    }
    
    public void addItemToInvenotory(Item item_to_add,boolean throwEvent){
        if(throwEvent)Game.getInstance().throwGameEvent(new ItemAddedToInventory(item_to_add));
        inventory.add(item_to_add);
    }
    public void RemoveItem(Item itemToRemove, boolean throwEvent){
        if(inventory.contains(itemToRemove)){
            inventory.remove(itemToRemove);
            if(throwEvent)Game.getInstance().throwGameEvent(new ItemRemovedFromInventory(itemToRemove));
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

    public void PickUpItem(Item item) {
        inventory.add(item);
        Game.getInstance().removeItemFromRooms(item);
        Game.getInstance().throwGameEvent(new ItemAddedToInventory(item));
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
            Game.getInstance().throwGameEvent(new CustomValueChanged(valueName,value));
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
        if(throwEvent)Game.getInstance().throwGameEvent(new OptionAddedToPlayer(option));
        option.addReferencedBy(this);
        playerOptions.add(option);
    }

    public void removeOption(Option option , boolean throwEvent) {
        if(playerOptions.contains(option)){
            if(throwEvent)Game.getInstance().throwGameEvent(new OptionRemovedFromPlayer(option));
            playerOptions.remove(option);
        }
    }
    
}
