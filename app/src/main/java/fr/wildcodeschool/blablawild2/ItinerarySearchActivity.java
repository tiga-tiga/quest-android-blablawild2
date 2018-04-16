package fr.wildcodeschool.blablawild2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ItinerarySearchActivity extends AppCompatActivity {

    public static final String EXTRA_TRIP = "EXTRA_TRIP";

    Date mDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_search);

        this.setTitle(R.string.search_an_itinerary);

        Button bSearchItinerary = findViewById(R.id.b_search_itinerary);
        bSearchItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etDeparture = findViewById(R.id.et_departure);
                EditText etDestination = findViewById(R.id.et_destination);
                EditText etDate = findViewById(R.id.et_date);

                String departure = etDeparture.getText().toString();
                String destination = etDestination.getText().toString();
                String date = etDate.getText().toString();

                if (departure.isEmpty() || destination.isEmpty() || date.isEmpty()) {
                    Toast.makeText(ItinerarySearchActivity.this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ItinerarySearchActivity.this, ItineraryListActivity.class);
                    TripModel tripModel = new TripModel(departure, destination, date);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference tripRef = database.getReference("trips");
                    String key = tripRef.push().getKey();

                    tripRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Intent intent = new Intent(ItinerarySearchActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // en cas d'erreur de récupération de la données
                            Toast.makeText(ItinerarySearchActivity.this, "Failed to read value.", Toast.LENGTH_LONG).show();
                        }
                    });

                    tripRef.push().setValue(tripModel);

                    intent.putExtra(EXTRA_TRIP, tripModel);
                    startActivity(intent);
                }
            }
        });

        final EditText etDate = findViewById(R.id.et_date);
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etDate.setText(sdf.format(calendar.getTime()));
                mDate = calendar.getTime();
            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ItinerarySearchActivity.this, datePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
