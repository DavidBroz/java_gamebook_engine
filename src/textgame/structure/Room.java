/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.StaticObjectRemovedFromRoom;
import textgame.structure.gameEvents.OptionRemovedFromRoom;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import textgame.structure.actions.Action;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import textgame.structure.actions.AddPathFromRoom;
import textgame.structure.actions.MovePlayerToRoom;
import textgame.structure.actions.PickUpItem;
import textgame.structure.actions.ShowPlayerInventory;
import textgame.structure.gameEvents.GameEvent;
import textgame.structure.gameEvents.ItemRemovedFromRoom;
import textgame.structure.gameEvents.OptionRemovedFromPlayer;
import textgame.structure.gameEvents.RoomLostItem;

/**
 *
 * @author David Brož
 */
public class Room implements java.io.Serializable {
    
    private Game game;
    private int id;
    private String name;
    private String description;
    private ArrayList<StaticObject> staticObjects;
    private ArrayList<Item> items;
    private ArrayList<Room> rooms;
    private ArrayList<Action> actions;
    private ArrayList<Option> options;
    private transient Image image;
    
    private ArrayList isReferencedBy;
    
    private double location_x = 0;
    private double location_y = 0;
    
    public Room(int roomId, String roomName, String roomDesc) {
        isReferencedBy = new ArrayList();
        this.options = new ArrayList();
        this.items = new ArrayList();
        this.rooms = new ArrayList();
        this.staticObjects = new ArrayList();
        this.id = roomId;
        this.name = roomName;
        this.description = roomDesc;
        game = Game.getInstance();
    }
    
    @Override
    public String toString() {
        return id + "_" + name;
    }
    
    public Room(String roomName, String roomDesc) {
        isReferencedBy = new ArrayList();
        id = Game.getInstance().getRoomMaxID();
        Game.getInstance().incRoomMaxID();
        this.options = new ArrayList();
        this.items = new ArrayList();
        this.rooms = new ArrayList();
        this.staticObjects = new ArrayList();
        this.name = roomName;
        this.description = roomDesc;
        game = Game.getInstance();
    }
    
    public void removeItemWithID(int id_to_remove) {        
        Item to_remove = null;
        to_remove.removeReferencedBy(this);
        for (Item i : items) {
            if (i.getId() == id_to_remove) {
                to_remove = i;
                System.out.println("Room round item to remove with id " + id_to_remove);
                break;
                
            }
        }
        items.remove(to_remove);
    }
    
    public void addItemToRoom(Item item_to_add) {
        item_to_add.addReferencedBy(this);
        System.out.println("Started to add");
        if (game.getItemWithID(item_to_add.getId()) == null) {
            game.getAllItems().add(item_to_add);
        } else if (game.getItemWithID(item_to_add.getId()) != item_to_add) {
            throw new IllegalArgumentException("An item with this ID already exists. Collision of IDs");
        }
        
        items.add(item_to_add);
    }
    
    public void addPath(Room room_to_add) {
        System.out.println("ROOM: Adding path to room with ID: " + room_to_add.id);
        room_to_add.addIsReferencedBy(this);
        rooms.add(room_to_add);
    }
    
    public ArrayList<Option> GenerateOptions() {
        ArrayList<Option> result = new ArrayList<>();
        Option tempOption;
        System.out.println("--Generating options--");
        result.clear();
        for (Room room : rooms) {
            tempOption = new Option("Go to " + room.name);
            tempOption.addAction(new MovePlayerToRoom(room));
            result.add(tempOption);
        }
        
        for (Item item : items) {
            tempOption = new Option("Pick up " + item.getName());
            tempOption.addAction(new PickUpItem(item));
            result.add(tempOption);
        }
        staticObjects.forEach((so) -> {
            for (Option o : so.getAllOptions()) {
                result.add(o);
            }
        });
        
        for(Option o : options){
            result.addAll(options);
        }
            
        tempOption = new Option("Show inventory");
        tempOption.addAction(new ShowPlayerInventory());
        result.add(tempOption);
        
        return result;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void addStaticObjectToRoom(StaticObject staticObject_to_add) {
        
        System.out.println("ROOM: Started to add static object");
        if (game.getStaticObjectWithID(staticObject_to_add.getId()) == null) {
            game.getAllStaticObjects().add(staticObject_to_add);
        } else if (game.getStaticObjectWithID(staticObject_to_add.getId()) != staticObject_to_add) {
            System.out.println("ROOM ERROR");
            throw new IllegalArgumentException("An staticObject with this ID already exists. Collision of IDs");
        }
        
        staticObject_to_add.addIsReferencedBy(this);
        staticObjects.add(staticObject_to_add);
        System.out.println("ROOM: StaticObject added. ");
    }
    
    public ArrayList<StaticObject> getAllStaticObjects() {
        return staticObjects;
    }
    
    public ArrayList<Room> getAllPaths() {
        return rooms;
    }
    
    public ArrayList<Item> getAllItems() {
        return items;
    }
    
    public ArrayList<Option> getAllOptions() {
        return options;
    }
    
    void addOptionToRoom(Option option) {
        option.addReferencedBy(this);
        options.add(option);
    }
    
    void removeStaticObject(StaticObject so) {
        so.removeIsReferencedBy(this);
        staticObjects.remove(so);
    }
    
    public void addOption(Option whatToAdd) {
        addOptionToRoom(whatToAdd);
    }
    
    public void removeStaticObjectFromRoom(StaticObject whatToRemove, boolean throwEvent) {
        whatToRemove.removeIsReferencedBy(this);
        if(throwEvent)Game.getInstance().throwGameEvent(new StaticObjectRemovedFromRoom(whatToRemove));
        staticObjects.remove(whatToRemove);
    }
    
    public void removeItem(Item item, boolean throwEvents) {
        if (items.contains(item)) {
            if (throwEvents) {
                Game.getInstance().throwGameEvent(new ItemRemovedFromRoom(item));
                Game.getInstance().throwGameEvent(new RoomLostItem(this));
            }
            item.removeReferencedBy(this);
            items.remove(item);
        } else {
            throw new IllegalArgumentException("Room " + this.toString() + " doesnt have item " + item.toString());
        }
        
    }
    
    public void removeOption(Option whatToRemove, boolean throwEvent) {
        whatToRemove.removeReferencedBy(this);
        if(throwEvent) Game.getInstance().throwGameEvent(new OptionRemovedFromRoom(whatToRemove));
        options.remove(whatToRemove);
    }
    
    public void removePathToRoom(Room toWhere) {
        toWhere.removeIsReferencedBy(this);
        rooms.remove(toWhere);
    }
    
    public void setDescription(String newDesctiption) {
        description = newDesctiption;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Game getGame() {
        return game;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ArrayList<StaticObject> getStaticObjects() {
        return staticObjects;
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public ArrayList<Action> getActions() {
        return actions;
    }
    
    public ArrayList<Option> getOptions() {
        return options;
    }
    
    public void setImage(Image img) {
        image = img;
    }
    
    public Image getImage() {
        return image;
    }
    
    public void readAndSetImage(ObjectInputStream s) throws IOException, ClassNotFoundException {
        //s.defaultReadObject();
        image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
    }
    
    public void writeImage(ObjectOutputStream s) throws IOException {
        // s.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
    }
    
    public boolean hasPathTo(Room room) {
        for (Room r : rooms) {
            if (r.equals(room)) {
                return true;
            }
        }
        return false;
    }
    
    public double getLocation_x() {
        return location_x;
    }
    
    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }
    
    public double getLocation_y() {
        return location_y;
    }
    
    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }
    
    public boolean hasItem(Item item) {
        return items.contains(item);
    }
    
    public void deleteAllReferencesToThis() {
        for (Object o : isReferencedBy) {
            if (o instanceof Room) {
                Room r = (Room) o;                
                r.removePathToRoom(this);
            } else if (o instanceof Player) {
                Player pl = (Player) o;                
                pl.setCurrentRoom(null);
            }if(o instanceof Action){
                Action a =(Action) o;
                a.setValidity(false);
            }
        }
    }
    
    public void addIsReferencedBy(Object o) {
        isReferencedBy.add(0);
    }

    public void removeIsReferencedBy(Object o) {
        if (isReferencedBy.contains(o)) {
            isReferencedBy.remove(0);
        }else 
        System.out.println("-ROOM-:Object " + o + " was attemed to be removed from IsReferencedBy of" + toString() + " but was not found!");
    }

}
