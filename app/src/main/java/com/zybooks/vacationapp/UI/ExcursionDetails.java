package com.zybooks.vacationapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.zybooks.vacationapp.R;
import com.zybooks.vacationapp.database.Repository;
import com.zybooks.vacationapp.entities.Excursion;
import com.zybooks.vacationapp.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String excursionName;
    String excursionDate;
    Integer excursionID;
    Integer associatedVacationID;
    EditText editExcursionNameForm;
    EditText editExcursionDateForm;
//    EditText editNote;
    Repository repository;
    Excursion currentExcursion;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart= Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());

        editExcursionNameForm = findViewById(R.id.ExcursionNameForm);
        editExcursionDateForm = findViewById(R.id.ExcursionDateForm);

        // get from recyclerView values
        excursionID = getIntent().getIntExtra("id",-1);
        excursionName = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("date");
        associatedVacationID = getIntent().getIntExtra("associatedVacationID", -1);

        editExcursionNameForm.setText(excursionName);
        editExcursionDateForm.setText(excursionDate);

//        editNote = findViewById(R.id.noteForm);

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExcursionDateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                // get value
                String info = editExcursionDateForm.getText().toString();
                if (info.equals("")){
                    info = "01/01/2023";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(
                        ExcursionDetails.this, startDate,
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
    }

    public void updateLabelStart(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExcursionDateForm.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        // Save Excursion
        if (item.getItemId() == R.id.excursionSave){
            Excursion excursion;
            // Save new excursion
            if (excursionID == -1){
                if(repository.getmAllExcursions().size()==0){
                    excursionID= 1;
                }
                else {
                    excursionID =repository.getmAllExcursions().get(repository.getmAllExcursions().size()-1).getExcursionID()+1;
                }

                try {
                    if(isExcursionDateValid(editExcursionDateForm, associatedVacationID)) {
                        // save new excursion
                        excursion = new Excursion(
                                excursionID,
                                editExcursionNameForm.getText().toString(),
                                editExcursionDateForm.getText().toString(),
                                associatedVacationID
                        );
                        repository.insert(excursion);
                        this.finish();
                        this.onResume();
//                        Log.i("LOOK AT THIS!", "IF STATEMENT: "+isExcursionDateValid(editExcursionDateForm, associatedVacationID));
                    }
                    else {
                        Toast.makeText(ExcursionDetails.this, "Changes not saved! Please make sure excursion date is during vacation dates!", Toast.LENGTH_LONG).show();
//                        Log.i("LOOK AT THIS!", "IF STATEMENT: "+isExcursionDateValid(editExcursionDateForm, associatedVacationID));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
            // Update existing Excursion
                try {
                    if(isExcursionDateValid(editExcursionDateForm, associatedVacationID)) {
                        excursion = new Excursion(
                                excursionID,
                                editExcursionNameForm.getText().toString(),
                                editExcursionDateForm.getText().toString(),
                                associatedVacationID
                        );
                        repository.update(excursion);
                        this.finish();
                        this.onResume();
                    }
                    else {
                        Toast.makeText(ExcursionDetails.this, "Changes not saved! Please make sure excursion date is during vacation dates!", Toast.LENGTH_LONG).show();
//                        Log.i("LOOK AT THIS!", "IF STATEMENT: "+isExcursionDateValid(editExcursionDateForm, associatedVacationID));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Notify
        else if(item.getItemId() == R.id.excursionNotify){
            String excursionDate = editExcursionDateForm.getText().toString();
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDateExcursion = null;
            try {
                myDateExcursion = sdf.parse(excursionDate);
            } catch (ParseException e){
                e.printStackTrace();
            }
            Long triggerExcursion = myDateExcursion.getTime();
            // Open app and notify on date
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            ////////////////display name of excursion notified
            intent.putExtra("key", "Alert for " + excursionName +"!");

            PendingIntent senderExcursion = PendingIntent.getBroadcast(ExcursionDetails.this, 2, intent, PendingIntent.FLAG_IMMUTABLE );
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerExcursion,senderExcursion);
            // Notify
            this.finish();
            this.onResume();
        }
        // Delete excursion
        else if (item.getItemId() == R.id.excursionDelete) {
            for (Excursion excursion: repository.getmAllExcursions()){
                if (excursion.getExcursionID()==excursionID){
                    currentExcursion = excursion;
                }
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, "Excursion was deleted!", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }

        //        else if (item.getItemId() == R.id.excursionShare){
//            //Share Excursion
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT,editNote.getText().toString() + "NOTE_TEXT");
//            sendIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString()+ "TITLE_TEXT");
//            sendIntent.setType("text/plain");
//
//            Intent shareIntent = Intent.createChooser(sendIntent,null);
//            startActivity(shareIntent);
//            return true;
////            this.finish();
////            this.onResume();
//        }
        return true;
    }

    public boolean isExcursionDateValid(EditText excursionDate, Integer associatedVacationID) throws InterruptedException, ParseException {
        Vacation vacationToCompare = null;
        repository = new Repository(getApplication());
        for (Vacation vacation: repository.getmAllVacations()){
            if (vacation.getVacationID()==associatedVacationID){
                vacationToCompare=vacation;
            }
        }


//        Vacation vacation = repository.getmAllVacations().get(associatedVacationID);
        String vacationStartDateString= vacationToCompare.getVacationStartDate().toString();
        String vacationEndDateString= vacationToCompare.getVacationEndDate().toString();
        String excursionDateString= excursionDate.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date vacationStartDateCompare = dateFormat.parse(vacationStartDateString);
        Date vacationEndDateCompare = dateFormat.parse(vacationEndDateString);
        Date excursionDateCompare = dateFormat.parse(excursionDateString);

        if (excursionDateCompare.after(vacationStartDateCompare) && excursionDateCompare.before(vacationEndDateCompare)){
            return true;
        }
        else {
            return false;
        }
    }
}

