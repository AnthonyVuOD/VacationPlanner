package com.zybooks.vacationapp.database;

import android.app.Application;

import com.zybooks.vacationapp.dao.ExcursionDAO;
import com.zybooks.vacationapp.dao.VacationDAO;
import com.zybooks.vacationapp.entities.Excursion;
import com.zybooks.vacationapp.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;


    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecuter = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }

    public List<Vacation> getmAllVacations() throws InterruptedException {
        databaseExecuter.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        return mAllVacations;
    }

    public void insert(Vacation vacation) {
        databaseExecuter.execute(()-> {
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation){
        databaseExecuter.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation){
        databaseExecuter.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    ///// Excursions/////
    public List<Excursion> getmAllExcursions(){
        databaseExecuter.execute(()->{
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
           Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public List<Excursion> getAssociatedExcursions(int vacationID){
        databaseExecuter.execute(()->{
            mAllExcursions = mExcursionDAO.getAssociateExcursions(vacationID);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion){
        databaseExecuter.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion){
        databaseExecuter.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion){
        databaseExecuter.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}