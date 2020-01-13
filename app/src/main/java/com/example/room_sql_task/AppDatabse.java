package com.example.room_sql_task;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabse extends RoomDatabase {
    public abstract EmployeeDAO employeeDAO();
}
