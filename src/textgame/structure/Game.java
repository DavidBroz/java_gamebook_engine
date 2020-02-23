/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.GameEvent;
import textgame.structure.actions.Action;
import java.util.ArrayList;
import java.util.EventListener;
import javafx.scene.image.Image;
import textgame.structure.actions.ChangeRoomImage;

/**
 *
 * @author David Brož
 */
public class Game implements java.io.Serializable{
    private static final long serialVersionUID = 1L; 
    
    private static int gameID = 0;
    private String name = "Unnamed_game";
    private int id;
    private String info_line;
    private static Game instance = new Game();
    private Player player;
    private transient Image currentImage;
    private ArrayList<Room> allRooms;
    private ArrayList<Item> allItems;
    private ArrayList<StaticObject> allStaticObjects;
    private ArrayList<GameEventListener> allGameEventListeners;
    private ArrayList<Option> allOptions;
    private ArrayList<Action> imageActionsToSave;
    
    private int itemMaxID = 0,
            optionMaxID = 0,
            staticObjectMaxID = 0,
            roomMaxID = 0,
            gameEventListenerMaxID = 0;
            

    private Game() {
        imageActionsToSave= new ArrayList<>();
        id = gameID;
        gameID++;
        allStaticObjects = new ArrayList<>();
        allItems = new ArrayList<>();
        allRooms = new ArrayList<>();;
        allOptions = new ArrayList();
        allGameEventListeners = new ArrayList<>();
    }

    public void addNewOption(Option optionToAdd){
        allOptions.add(optionToAdd);
    }
    public void removeOption(Option optionToRemove){
        allOptions.remove(optionToRemove);
    }
    
    public Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Item getItemWithID(int item_id) {
        for (Item i : allItems) {
            if (i.getId() == item_id) {
                return i;
            }
        }
        return null;
    }

    public void removeItemWithID(int id_to_remove) {
        allItems.remove(getItemWithID(id_to_remove));
        for (Room r : allRooms) {
            r.removeItemWithID(id_to_remove);
        }
    }
    public static Game getInstance(boolean createNewGame){
        if(createNewGame){
            instance = new Game();
        }
        return instance;
    }
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        } 
        return instance;
      
    }
    
    public static void setInstance(Game new_game) {
        instance = new_game;
    }

    public void throwGameEvent(GameEvent gameEvent) {
        for (GameEventListener eListener : allGameEventListeners) {
            eListener.listen(gameEvent);
        }
    }

    public Room getRoomWithID(int room_id) {
        for (Room r : allRooms) {
            if (r.getId() == room_id) {
                return r;
            }
        }
        return null;
    }
    public Room addNewRoom(String name, String description){
        Room r = new Room(name,description);
        allRooms.add(r);        
        return r; 
    }
    
    public Room addNewRoom(){
        Room r = new Room("Unamed room","No desctition.");
        allRooms.add(r);        
        return r; 
    }

    StaticObject getStaticObjectWithID(int staticObject_id) {
        for (StaticObject s : allStaticObjects) {
            if (s.getId() == staticObject_id) {
                return s;
            }
        }
        return null;
    }

    public GameEventListener addEventListener(String name,GameEvent.GameEventType gameEventType, Object[] expectedValue, Action action) {
        GameEventListener gel = new GameEventListener(name, gameEventType, expectedValue, action);
        allGameEventListeners.add(gel);
        return gel;
    }
    
    public Room getRoomWithToSting(String toString){
        for(Room r : allRooms){
            if(toString.contentEquals(r.toString())){
                return r;
            }
        }
        return null;
    }
    
    public ArrayList<Room> getAllRooms() {
        return allRooms;
    }

    public Item addNewItem() {
        Item i = new Item("Unamed item","No desctition.");
        allItems.add(i);        
        return i; 
    }
    
    public StaticObject addNewStaticObject() {
        StaticObject so = new StaticObject("Unamed static object","No desctition.");
        allStaticObjects.add(so);        
        return so; 
    }

    public Item getItemWithToSting(String toString) {
        for(Item i : allItems){
            if(toString.contentEquals(i.toString())){
                return i;
            }
        }
        return null;
    }

    public StaticObject getStaticObjectWithToSting(String toString) {
        for(StaticObject so : allStaticObjects){
            if(toString.contentEquals(so.toString())){
                return so;
            }
        }
        return null;
    }

    public void removeItem(Item whatToRemove) {
        allItems.remove(whatToRemove);
    }

    public String getInfo_line() {
        return info_line;
    }

    public void setInfo_line(String info_line) {
        this.info_line = info_line;
    }

    public int getId() {
        return id;
    }

    public void removeStaticObject(StaticObject staticObject) {
        allStaticObjects.remove(staticObject);
    }

    public void removeRoom(Room room) {
        allRooms.remove(room);
        room.deleteAllReferencesToThis();
    }

    public void removeGameEventListener(GameEventListener gameEventListener) {
        allGameEventListeners.remove(gameEventListener);
    }

    public Option getOptionWithToSting(String str) {
        for(Option o : allOptions){
            if(str.contentEquals(o.toString())){
                return o;
            }
        }
        return null;
    }

    public ArrayList<Item> getAllItems() {
        return allItems;
    }

    public ArrayList<Option> getAllOptions() {
        return allOptions;
    }

    public ArrayList<StaticObject> getAllStaticObjects() {
        return allStaticObjects;
    }

    public ArrayList<GameEventListener> getAllGameEventListeners() {
        return allGameEventListeners;
    }

    int getItemMaxID() {
        return itemMaxID;
    }

    void incItemMaxID() {
        itemMaxID++;
    }

    int getGameEventListenerMaxID() {
        return gameEventListenerMaxID;
    }

    void incGameEventListenerMaxID() {
        gameEventListenerMaxID++;
    }

    int getOptionMaxID() {
        return optionMaxID;
    }

    void incOptionMaxID() {
        optionMaxID++;
    }

    int getStaticObjectMaxID() {
        return staticObjectMaxID;
    }

    void incStaticObjectMaxID() {
        staticObjectMaxID++;
    }

    int getRoomMaxID() {
        return roomMaxID;
    }

    void incRoomMaxID() {
        roomMaxID++;
    }

    public ArrayList getAllIntancesOf(Class requiredClass) {
        if(requiredClass.equals(Item.class)){
            return allItems;
        }
        if(requiredClass.equals(Option.class)){
            return allOptions;
        }
        if(requiredClass.equals(Room.class)){
            return allRooms;
        }
        if(requiredClass.equals(StaticObject.class)){
            return allStaticObjects;
        }
        if(requiredClass.equals(GameEventListener.class)){
            return allGameEventListeners;
        }
        System.out.println("--GAME--: getAllIntancesOf- no match found for: "+requiredClass);
        return new ArrayList();
    }

    public void removeItemFromRooms(Item item) {
        for(Room r : allRooms){
            if(r.hasItem(item))r.removeItem(item,true);
        }
    }

    public void addActionImageToSave(Action action) {
        imageActionsToSave.add(action);
    }
    
    public ArrayList<Action> getAllActionImagesToSave() {
        return imageActionsToSave;
    }

    public void removeActionImageToSave(Action action) {
        imageActionsToSave.add(action);
    }

    public Image getCurrentImage(){ 
        if(currentImage==null)setCurrentImage(getPlayer().getCurrentRoom().getImage());
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
    

}
