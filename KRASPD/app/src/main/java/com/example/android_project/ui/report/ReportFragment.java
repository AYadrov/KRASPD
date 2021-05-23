package com.example.android_project.ui.report;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_project.R;
import com.example.android_project.ui.CustomDialogClass;
import com.example.android_project.ui.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment implements AdapterView.OnItemClickListener {

    TextView header;
    ListView dataList;
    DBHelper DB;
    SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report, container, false);

        dataList = (ListView) root.findViewById(R.id.list);

        header = (TextView) root.findViewById(R.id.header);

        DB = new DBHelper(getActivity().getApplicationContext());

        Resume();
        return root;
    }

    public void Resume() {
        // открываем подключение
        db = DB.getReadableDatabase();
        Cursor c = db.query("kraspd", null, null,
                null, null, null, null);

        List<String> array = new ArrayList<String>();
        if (c.moveToFirst()) {
            header.setText("Report");
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("date");
            int emailColIndex = c.getColumnIndex("place");
            int incidentColIndex = c.getColumnIndex("incident");
            int descriptionColIndex = c.getColumnIndex("description");
            do {

                array.add(c.getString(incidentColIndex) + ", " + c.getString(nameColIndex));
                Log.d("My_Logs",
                        "ID = " + c.getInt(idColIndex) +
                                ", date = " + c.getString(nameColIndex) +
                                ", place = " + c.getString(emailColIndex));
            } while (c.moveToNext());
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, array);
            dataList.setAdapter(listAdapter);
            dataList.setOnItemClickListener(this);
        } else
            header.setText("Report: no data");
        c.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String month = parent.getItemAtPosition(position).toString();
        Log.d("My_Logs", "clicked!");
        CustomDialogClass cdd = new CustomDialogClass(getActivity(),
            parent, view, position, id);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();

    }
}