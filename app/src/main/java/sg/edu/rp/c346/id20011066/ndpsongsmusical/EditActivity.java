package sg.edu.rp.c346.id20011066.ndpsongsmusical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    Button btnUpdate, btnDelete, btnCancel;
    EditText etTitle, etSingers, etYear, etID, etURL;
    RatingBar rb;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etURL = findViewById(R.id.etURL);
        etYear = findViewById(R.id.etYear);
        etID = findViewById(R.id.etID);
        rb = findViewById(R.id.ratingBar2);

        Intent i = getIntent();
        song = (Song) i.getSerializableExtra("song");
        etID.setText(song.getId() + "");
        etTitle.setText(song.getTitle());
        etSingers.setText(song.getSingers());
        etURL.setText(song.getURL());
        etYear.setText(song.getYears() + "");
        rb.setRating((float)song.getStars());


        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                song.setTitle(etTitle.getText().toString().trim());
                song.setSingers(etSingers.getText().toString().trim());
                song.setURL(etURL.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(EditActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                song.setYears(year);

                song.setStars((int) rb.getRating());
                int result = dbh.updateSong(song);
                if (result > 0) {
                    Toast.makeText(EditActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper = new DBHelper(EditActivity.this);
                                dbHelper.deleteSong(song.getId());
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes" );
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}