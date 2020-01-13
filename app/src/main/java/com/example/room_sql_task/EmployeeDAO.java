package com.example.room_sql_task;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EmployeeDAO {

    @Query("SELECT * FROM Employee")
    List<Employee> getAllEmployees();

    @Insert
    void insertAll(Employee... employees);

    @Update
    void update(Employee... employee);

    @Query("SELECT * FROM Employee WHERE email = :emailAddress")
    Employee getEmployeeWithEmail(String emailAddress);
}
