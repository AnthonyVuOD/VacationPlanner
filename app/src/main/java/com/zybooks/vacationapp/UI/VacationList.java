package com.zybooks.vacationapp.UI;

import static com.zybooks.vacationapp.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.vacationapp.R;
import com.zybooks.vacationapp.database.Repository;
import com.zybooks.vacationapp.entities.Excursion;
import com.zybooks.vacationapp.entities.Vacation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_vacation_list);

        FloatingActionButton fab = findViewById(id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);

            }
        });
        // get recycler view
        RecyclerView recyclerView=findViewById(id.recyclerView);
        // get database
        repository = new Repository(getApplication());
        // get list of all vacations
        List<Vacation> allVacations = null;
        try {
            allVacations = repository.getmAllVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // get vacationAdapter
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        // set vacationAdapter to recyclerView
        recyclerView.setAdapter(vacationAdapter);
        // set LayoutManager to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set allVacations to vacationAdapter
        vacationAdapter.setVacations(allVacations);


        //System.out.println(getIntent().getStringExtra("test"));
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//
//        if(item.getItemId()==R.id.sample){
//        //    Toast.makeText(VacationList.this, "put in sample data", Toast.LENGTH_LONG).show();
//        //?   Repository repository = new Repository(getApplication());
//
//            Repository repository = new Repository(getApplication());
//            Vacation vacation1 = new Vacation("Trekking", "Vietnam", "11/01/2023", "12/01/2023");
//            Vacation vacation2 = new Vacation("Camping", "Colorado", "01/01/2024", "2/01/2024");
//            repository.insert(vacation1);
//            repository.insert(vacation2);
//
//            Excursion excursion1= new Excursion("Trek","11/02/2023", 0);
//            Excursion excursion2 = new Excursion("Camp", "01/02/2024", 1);
//
//            repository.insert(excursion1);
//            repository.insert(excursion2);
//
//            return true;
//        }
//        if(item.getItemId()==android.R.id.home){
//            this.finish();
//            return true;
//        }
//        return true;
//    }
    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation> allVacations = null;
        try {
            allVacations = repository.getmAllVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RecyclerView recyclerView = findViewById(id.recyclerView);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }
}