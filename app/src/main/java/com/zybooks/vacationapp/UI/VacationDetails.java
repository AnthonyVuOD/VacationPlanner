package com.zybooks.vacationapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.vacationapp.R;
import com.zybooks.vacationapp.database.Repository;
import com.zybooks.vacationapp.entities.Excursion;
import com.zybooks.vacationapp.entities.Vacation;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String vacationName;
    String vacationLocation;
    String vacationStartDate;
    String vacationEndDate;
    Integer vacationID;
    EditText editVacationNameForm;
    EditText editVacationLocationForm;
    EditText editVacationStartForm;
    EditText editVacationEndForm;
    Repository repository;
    Vacation currentVacation;
    Integer numExcursions;
    EditText editNote;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonToEx);

        editVacationNameForm = findViewById(R.id.VacationNameForm);
        editVacationLocationForm = findViewById(R.id.VacationLocationForm);
        editVacationStartForm = findViewById(R.id.VacationStartForm);
        editVacationEndForm = findViewById(R.id.VacationEndForm);


        // get from recyclerview values
        vacationName = getIntent().getStringExtra("name");
        vacationLocation = getIntent().getStringExtra("location");
        vacationStartDate = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");
        vacationID = getIntent().getIntExtra("id", -1);

        editVacationNameForm.setText(vacationName);
        editVacationLocationForm.setText(vacationLocation);
        editVacationStartForm.setText(vacationStartDate);
        editVacationEndForm.setText(vacationEndDate);

        editNote = findViewById(R.id.noteForm);

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationStartForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                // get value
                String info = editVacationStartForm.getText().toString();
                if (info.equals("")){
                    info = "01/01/2023";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(
                        VacationDetails.this, startDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        editVacationEndForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                // get value
                String info = editVacationEndForm.getText().toString();
                if (info.equals("")){
                    info = "01/01/2023";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(
                        VacationDetails.this, endDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("associatedVacationID", vacationID);
                startActivity(intent);

            //// Need to pass through to excursion

            }
        });
        RecyclerView recyclerView=findViewById(R.id.excursionRecyclerView);
        // get Repository
        repository = new Repository(getApplication());
        // Get excursionAdapter
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        // set excursionAdapter to recyclerView
        recyclerView.setAdapter(excursionAdapter);
        // set Layout manager to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // filter excursions
        List<Excursion> filteredExcursions = new ArrayList<Excursion>();
        for(Excursion excursion: repository.getmAllExcursions()){
            if (excursion.getVacationID()==vacationID){
                filteredExcursions.add(excursion);
            }
        }
        // list excursions
        excursionAdapter.setExcursions(filteredExcursions);
    }

    public void updateLabelStart(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationStartForm.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateLabelEnd(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacationEndForm.setText(sdf.format(myCalendarStart.getTime()));
    }

    // Creates Menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_details,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /// if select "Save Vacation" menu
        if (item.getItemId() == R.id.vacationSave) {
            Vacation vacation;
            if (vacationID == -1) {
                try {
                    if (repository.getmAllVacations().size()==0){
                        vacationID=1;
                    }
                    else {
                        vacationID =repository.getmAllVacations().get(repository.getmAllVacations().size()-1).getVacationID()+1;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    if (areDatesValid(editVacationStartForm, editVacationEndForm)) {
                        vacation = new Vacation(
                                vacationID,
                                editVacationNameForm.getText().toString(),
                                editVacationLocationForm.getText().toString(),
                                editVacationStartForm.getText().toString(),
                                editVacationEndForm.getText().toString());
                        repository.insert(vacation);
//                        Log.i("LOOK AT THIS!", "ELSE STATEMENT "+areDatesValid(editVacationStartForm, editVacationEndForm)+"");
                    } else {
                        Toast.makeText(VacationDetails.this, "Changes not saved! Please make sure end date is after start date!", Toast.LENGTH_LONG).show();
//                        Log.i("LOOK AT THIS!", "IF STATEMENT "+areDatesValid(editVacationStartForm, editVacationEndForm)+"");
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    if (areDatesValid(editVacationStartForm, editVacationEndForm)) {
                        vacation = new Vacation(
                                vacationID,
                                editVacationNameForm.getText().toString(),
                                editVacationLocationForm.getText().toString(),
                                editVacationStartForm.getText().toString(),
                                editVacationEndForm.getText().toString());
                        repository.update(vacation);
                    }
                    else {
                        Toast.makeText(VacationDetails.this, "Changes not saved! Please make sure end date is after start date!", Toast.LENGTH_LONG).show();
//                        Log.i("LOOK AT THIS!", "IF STATEMENT "+areDatesValid(editVacationStartForm, editVacationEndForm)+"");
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            this.finish();
            this.onResume();
        }
        else if (item.getItemId() == R.id.vacationShare){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString() + "My "+ editVacationNameForm.getText().toString() + " vacation in "+ editVacationLocationForm.getText().toString() + " from " + editVacationStartForm.getText().toString() + " to " + editVacationEndForm.getText().toString() + " is starting soon!");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString()+ "I am sharing my Vacation!");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent,null);
            startActivity(shareIntent);
            return true;
//            this.finish();
//            this.onResume();
        }

        else if (item.getItemId() == R.id.vacationNotify){
            /////Alert End date/////////
            String dateFromScreenStart = editVacationStartForm.getText().toString();
            String dateFromScreenEnd = editVacationEndForm.getText().toString();

            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;

            try {
                myStartDate = sdf.parse(dateFromScreenStart);
                myEndDate = sdf.parse(dateFromScreenEnd);
            } catch (ParseException e){
                e.printStackTrace();
            }

            Long triggerStartDate = myStartDate.getTime();
            Long triggerEndDate = myEndDate.getTime();

            Intent intentStart = new Intent(VacationDetails.this, MyReceiver.class);
            Intent intentEnd = new Intent(VacationDetails.this, MyReceiver.class);

            intentStart.putExtra("key", "Alert for STARTING " + vacationName +"!");
            intentEnd.putExtra("key", "Alert for ENDING " + vacationName +"!");

            PendingIntent senderStart = PendingIntent.getBroadcast(VacationDetails.this, 0, intentStart, PendingIntent.FLAG_IMMUTABLE );
            PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this, 1, intentEnd, PendingIntent.FLAG_IMMUTABLE );

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerStartDate,senderStart);
            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerEndDate,senderEnd);

            this.finish();
            this.onResume();


//           ----------------------------WORKS ---------------------------------------

//            /////Alert End date/////////
//            String dateFromScreenStart = editVacationStartForm.getText().toString();
////            String dateFromScreenEnd = editVacationEndForm.getText().toString();
//
//            String myFormat = "MM/dd/yyyy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//            Date myStartDate = null;
////            Date myEndDate = null;
//
//            try {
//                myStartDate = sdf.parse(dateFromScreenStart);
////                myEndDate = sdf.parse(dateFromScreenEnd);
//            } catch (ParseException e){
//                e.printStackTrace();
//            }
//
//            Long triggerStartDate = myStartDate.getTime();
////            Long triggerEndDate = myEndDate.getTime();
//            // Open app and notify on date
//            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
//            ////////////////display name of excursion notified
//
//            intent.putExtra("key", "Alert for STARTING " + vacationName +"!");
////            intent.putExtra("key", "Alert for ENDING " + vacationName +"!");
//
//            PendingIntent senderStart = PendingIntent.getBroadcast(VacationDetails.this, 0, intent, PendingIntent.FLAG_IMMUTABLE );
////            PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this, 0, intent, PendingIntent.FLAG_IMMUTABLE );
//
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerStartDate,senderStart);
////            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerEndDate,senderEnd);
//
//            this.finish();
//            this.onResume();
//-----------------------------------DOES NOT WORK------------------------
            ///////Alert Start date//////////
//            String dateFromScreen = editVacationStartForm.getText().toString();
//
//            String myFormat = "MM/dd/yyyy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//            Date myDate = null;
//
//            try {
//                myDate = sdf.parse(dateFromScreen);
//            } catch (ParseException e){
//                e.printStackTrace();
//            }
//
//            Long trigger = myDate.getTime();
//
//            // Open app and notify on date
//            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
//            ////////////////display name of excursion notified
//            intent.putExtra("key", "Alert! Time to start "+ vacationName);
//            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE );
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//            alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
//
//        //-----------------------------------------------------------------------------------------------------//
//
//            /////Alert End date/////////
//            String dateFromScreenEnd = editVacationEndForm.getText().toString();
//
//            String myFormatEnd = "MM/dd/yyyy";
//            SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
//            Date myDateEnd = null;
//
//            try {
//                myDateEnd = sdfEnd.parse(dateFromScreenEnd);
//            } catch (ParseException e){
//                e.printStackTrace();
//            }
//
//            Long triggerEnd = myDateEnd.getTime();
//
//            // Open app and notify on date
//            Intent intentEnd = new Intent(VacationDetails.this, MyReceiver.class);
//            ////////////////display name of excursion notified
//            intentEnd.putExtra("key", "Alert! Time to end "+ vacationName);
//            PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intentEnd, PendingIntent.FLAG_IMMUTABLE );
//            AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManagerEnd.set(AlarmManager.RTC_WAKEUP,triggerEnd,senderEnd);
//
//            this.finish();
//            this.onResume();
        }

        else if (item.getItemId() == R.id.vacationDelete){
            try {
                for (Vacation vacation: repository.getmAllVacations()) {
                    if (vacation.getVacationID() == vacationID) {
                        currentVacation = vacation;
                    }
                }
                numExcursions = 0;
                for (Excursion excursion: repository.getmAllExcursions()){
                    if (excursion.getVacationID()==currentVacation.getVacationID()){
                        numExcursions++;
                    }
                }
                if (numExcursions==0){
                    repository.delete(currentVacation);
                    Toast.makeText(VacationDetails.this, "Vacation was deleted!", Toast.LENGTH_LONG).show();
                    VacationDetails.this.finish();
                }
                else{
                    Toast.makeText(VacationDetails.this,  "Vacation was not deleted due to having excursions!", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public boolean areDatesValid(EditText startDate, EditText endDate) throws ParseException {
        String startDateString=startDate.getText().toString();
        String endDateString= endDate.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date date1 = dateFormat.parse(startDateString);
        Date date2 = dateFormat.parse(endDateString);

        if (date1.before(date2)) {
            return true;
        } else {
            return false;
        }
    }
}

