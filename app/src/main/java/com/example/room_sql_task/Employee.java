package com.example.room_sql_task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Employee {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    String fullName;

    @ColumnInfo(name = "email")
    String emailAddress;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "gender")
    String gender;

    @ColumnInfo(name = "age")
    int age;

    @ColumnInfo(name = "image")
    String imageSource;

    @Ignore
    public Employee(int id, String imageSource) {
        this.id = id;
        this.imageSource = imageSource;
    }

    public Employee(String fullName, String emailAddress, String password, String gender, int age, String imageSource) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.imageSource = imageSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
