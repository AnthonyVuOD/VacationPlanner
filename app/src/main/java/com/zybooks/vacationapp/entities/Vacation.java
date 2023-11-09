package com.zybooks.vacationapp.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Set;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String vacationLocation;
    private String vacationStartDate;
    private String vacationEndDate;
    //private Set <Excursion> excursions;

    @Ignore
    public Vacation(int vacationID,
                    String vacationName,
                    String vacationLocation,
                    String vacationStartDate,
                    String vacationEndDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.vacationLocation = vacationLocation;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }
    public Vacation(String vacationName,
                    String vacationLocation,
                    String vacationStartDate,
                    String vacationEndDate) {
        this.vacationName = vacationName;
        this.vacationLocation = vacationLocation;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getVacationLocation() {
        return vacationLocation;
    }

    public void setVacationLocation(String vacationLocation) {this.vacationLocation = vacationLocation;}

    public String getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(String vacationStartDate) {this.vacationStartDate = vacationStartDate;}

    public String getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(String vacationEndDate) {this.vacationEndDate = vacationEndDate;}

    //public Set<Excursion> getExcursions() {return excursions;}

    //public void setExcursions(Set<Excursion> excursions) {this.excursions = excursions;}
}
