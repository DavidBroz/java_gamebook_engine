/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import textgame.structure.actions.Action;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import textgame.structure.actions.AddPathFromRoom;
import textgame.structure.actions.PickUpItem;
import textgame.structure.actions.ShowPlayerInventory;
import textgame.structure.gameEvents.ItemRemovedFromRoom;
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
    private String imagePath = null;
    private ArrayList<StaticObject> staticObjects;
    private ArrayList<Item> items;
    private ArrayList<Room> rooms;
    private ArrayList<Action> actions;
    private ArrayList<Option> options;
    private transient Image image;

    private double location_x = 0;
    private double location_y = 0;

    public Room(int roomId, String roomName, String roomDesc) {
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
        rooms.add(room_to_add);
    }

    public ArrayList<Option> GenerateOptions() {
        Option tempOption;
        System.out.println("--Generating options--");
        options.clear();
        for (Room room : rooms) {
            tempOption = new Option("Jdi do " + room.name);
            tempOption.addAction(new AddPathFromRoom(this, room));
            options.add(tempOption);
        }

        for (Item item : items) {
            tempOption = new Option("Seber " + item.getName());
            tempOption.addAction(new PickUpItem(item));
            System.out.println("Option for ITEM with ID " + item.getId());
            options.add(tempOption);
        }

        staticObjects.forEach((so) -> {
            for (Option o : so.getAllOptions()) {
                options.add(o);
            }
        });
        tempOption = new Option("Ukaž inventář");
        tempOption.addAction(new ShowPlayerInventory());
        options.add(tempOption);

        return options;
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
        options.add(option);
    }

    void removeStaticObject(StaticObject so) {
        staticObjects.remove(so);
    }

    public void addOption(Option whatToAdd) {
        options.add(whatToAdd);
    }

    public void removeStaticObjectFromRoom(StaticObject whatToRemove) {
        staticObjects.remove(whatToRemove);
    }

    public void removeItem(Item item, boolean throwEvents) {
        if (items.contains(item)) {
            if (throwEvents) {
                Game.getInstance().throwGameEvent(new ItemRemovedFromRoom(item));
                Game.getInstance().throwGameEvent(new RoomLostItem(this));
            }
            items.remove(item);
        } else throw new IllegalArgumentException("Room "+ this.toString()+" doesnt have item "+item.toString());
        

    }

    public void removeOption(Option whatToRemove) {
        options.remove(whatToRemove);
    }

    public void removePathToRoom(Room toWhere) {
        rooms.remove(toWhere);
    }

    public void setDescription(String newDesctiption) {
        description = newDesctiption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
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

}
