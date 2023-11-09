// Builds DB

package com.zybooks.vacationapp.database;


import android.content.Context;
import android.view.WindowAnimationFrameStats;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zybooks.vacationapp.dao.ExcursionDAO;
import com.zybooks.vacationapp.dao.VacationDAO;
import com.zybooks.vacationapp.entities.Excursion;
import com.zybooks.vacationapp.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
        public abstract VacationDAO vacationDAO();
        public abstract ExcursionDAO excursionDAO();
        private static volatile VacationDatabaseBuilder INSTANCE;

        static VacationDatabaseBuilder getDatabase(final Context context){
            if (INSTANCE==null){
                synchronized (VacationDatabaseBuilder.class){
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

}
