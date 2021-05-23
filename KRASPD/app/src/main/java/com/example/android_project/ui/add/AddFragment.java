package com.example.android_project.ui.add;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_project.R;
import com.example.android_project.ui.DBHelper;
import com.google.android.gms.maps.SupportMapFragment;

public class AddFragment extends Fragment {
    String[] data = {"one", "two", "three", "four", "five"};
    Spinner dropdown;
    EditText when, where, description;
    DBHelper DB;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        dropdown = root.findViewById(R.id.spinner);
        initSpinner();

        when = root.findViewById(R.id.date);
        where = root.findViewById(R.id.where);
        description = root.findViewById(R.id.description);

        Button add = (Button) root.findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add_btn();
            }
        });
        return root;
    }

    private void initSpinner() {
        String[] items = new String[]{
                "Assault", "Burglary", "Drugs", "Motor Vehicle Theft", "Robbery", "Sex Crimes",
                "Vandalism", "Weapons", "Homicide",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void add_btn() {
        DB = new DBHelper(getActivity().getApplicationContext());
        String place = where.getText().toString().trim();
        String date = when.getText().toString().trim(); // INSERT INTO t(dob) VALUES(TO_DATE('17/12/2015', 'DD/MM/YYYY'));
        String text = description.getText().toString().trim();
        String type = dropdown.getSelectedItem().toString();

        Boolean checkinsertdata = false;
        if (place.isEmpty() || date.isEmpty() || text.isEmpty() || type.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Some rows are empty", Toast.LENGTH_SHORT).show();
        }
        else{
            checkinsertdata = DB.insertkraspd(date, place, type, text);
        }

        if (checkinsertdata) {
            Toast.makeText(getActivity().getApplicationContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
            when.setText("");
            where.setText("IKIT SFU");
            description.setText("");
        }
        else
            Toast.makeText(getActivity().getApplicationContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
        DB.close();
    }
}
