package android.eservices.webrequests.data.db;

import android.content.Context;

import androidx.room.Room;

public class BookDatabaseCreator {

    private static BookDatabase myBookDatabase;

    public static BookDatabase database(Context context){
        if(myBookDatabase == null){
            myBookDatabase = Room.databaseBuilder(context, BookDatabase.class, "BookDB").build();
        }

        return myBookDatabase;
    }
}
