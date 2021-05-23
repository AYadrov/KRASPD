package com.example.android_project.ui;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button ok;
    DBHelper DB;
    SQLiteDatabase db;
    TextView txt_incident, txt_date, txt_place, txt_description;
    int position;

    public CustomDialogClass(Activity a, AdapterView<?> parent, View view, int pos, long id) {
        super(a);
        position = pos;
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        DB = new DBHelper(getContext());

        db = DB.getReadableDatabase();

        ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        txt_incident = (TextView) findViewById(R.id.txt_incident);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_place = (TextView) findViewById(R.id.txt_place);
        txt_description = (TextView) findViewById(R.id.txt_description);


        Cursor c = db.query("kraspd", null, null,
                null, null, null, null);

        List<String> array = new ArrayList<String>();


        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("date");
            int emailColIndex = c.getColumnIndex("place");
            int incidentColIndex = c.getColumnIndex("incident");
            int descriptionColIndex = c.getColumnIndex("description");

            c.moveToPosition(position);
            txt_incident.setText(c.getString(incidentColIndex));
            txt_date.setText(c.getString(nameColIndex));
            txt_place.setText(c.getString(emailColIndex));
            txt_description.setText(c.getString(descriptionColIndex));
        }
        c.close();
    }

    @Override
    public void onClick(View v) {

    }
}
