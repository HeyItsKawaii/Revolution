package org.mdev.revolution.game.rooms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.database.domain.rooms.Room;
import org.mdev.revolution.network.sessions.Session;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
    private static final Logger logger = LogManager.getLogger(Session.class);

    private ConcurrentHashMap<Integer, RoomBean> rooms;
    //private Object roomLoadingSync;

    public RoomManager() {
        //rooms = TCollections.synchronizedMap(new TIntObjectHashMap<RoomBean>());
        rooms = new ConcurrentHashMap<>(20, 0.9f, 4);
    }

    public RoomBean loadRoom(int roomId) {
        RoomBean roomBean = null;
        if (rooms.containsKey(roomId)) {
            roomBean = rooms.get(roomId);
            if (roomBean.isUnloaded()) {
                return null;
            }

            return roomBean;
        }

        // REMOVED OBJECT SYNCHRONIZATION FOR METHODS USING PUT/REMOVE
        // WILL PROBABLY RE-ADD THIS LATER IN DEVELOPMENT
        // SYNCHRONIZE THE LOADING OBJECT FOR CODE
        // synchronized (roomLoadingSync) {}
        Room room = Revolution.getInstance().getGame().getRoomDao().findRoom(roomId);
        if (room == null) {
            return null;
        }

        roomBean = new RoomBean(room);
        roomBean = rooms.put(roomId, roomBean);
        if (roomBean == null) {
            logger.info("<Room " + roomId + " expected " + room.getId() + "> was loaded successfully.");
            return rooms.get(roomId);
        }
        else {
            logger.info("<Room " + roomId + " expected " + room.getId() + "> failed to load (a room with the same key already exists)");
            return null;
        }
    }
}
