/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.GameEvent;
import java.util.ArrayList;
import textgame.structure.gameEvents.GameEvent.GameEventType;
/**
 *
 * @author David Brož
 */
public class Player implements java.io.Serializable {
    private Room currentRoom;
    public int inventorySize = 10;
    public ArrayList<Item> inventory;
    public int maxHealth = 100;
    public int currentHealth = 100;
    
    public Player(){
        inventory = new ArrayList();
    }
    
    public void MovePlayer(Room where){
        currentRoom = where;
        //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.PLAYER_MOVED,Integer.toString(where.getId())));
    }
    
    public boolean ItemPickUp(int item_id){
        
        if(inventorySize > inventory.size()){
            System.out.println("ITEM PICKED UP -- ID: "+item_id);
            inventory.add(Game.getInstance().getItemWithID(item_id));
             Game.getInstance().setInfo_line("Sbral jsi "+  Game.getInstance().getItemWithID(item_id).getName());
             Game.getInstance().removeItemWithID(item_id);
            //System.out.println("PLAYER: THROWING EVENT "+ GameEventType.ITEM_PICKED.toString());
            //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.ITEM_PICKED,Integer.toString(item_id)));
            return true;
        }else {
            System.out.println("INVENTORY FULL");
            //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.INVENTORY_FULL,Integer.toString(inventory.size())));
            return false;
        }
    }
    public void AddItemToPlayer(Item item_to_add){
        inventory.add(item_to_add);
    }
    public void RemoveItem(Item itemToRemove){
        if(inventory.contains(itemToRemove)){
            inventory.remove(itemToRemove);
            //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.ITEM_REMOVED, Integer.toString(itemToRemove.getId())));
        }
    }
    
    public void ChangeHealth(int amount){
        currentHealth += amount;
        //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.HEALTH_CHANGED, Integer.toString(amount)));
        if(currentHealth<=0){
            //Game.getInstance().throwGameEvent(new GameEvent(GameEventType.DEATH,""));
        }
    }

    public String inventoryToSting() {
        StringBuilder sb = new StringBuilder();
            sb.append("Mas u sebe: ");
        for(Item i : inventory){
            sb.append(i.getName()+", ");
        }
        return sb.toString();
    }

    public void PickUpItem(Item whatToPickUp) {
         inventory.add(whatToPickUp);
         Game.getInstance().setInfo_line("Sbral jsi "+ whatToPickUp.getName());
         Game.getInstance().removeItem(whatToPickUp);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    
}
