package com.udem.homeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRemindFragment extends Fragment {

    public ViewRemindFragment(){}
    public ArrayList<String> recordatorios;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        recordatorios = new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_test2, container, false);
        ListView lv2 = (ListView) view.findViewById(R.id.lv_recordatorio);

        Button btnCreateRemind = (Button) view.findViewById(R.id.btnCreateRemind);
        btnCreateRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CreateRemindActivity.class);
                startActivity(intent);
            }
        });

        getReminds();;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, recordatorios);
        lv2.setAdapter(adapter);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);
                Intent i = new Intent(getActivity(), UpdateRemindActivity.class);
                i.putExtra("RecordatorioId", value);
                startActivity(i);
            }
        });

        return view;
    }

    public void getReminds(){
        SqlHelper sqlDb = new SqlHelper(getContext(), "tblrecordatorio", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

        Cursor cursor = BaseDatos.rawQuery("Select nombre from tblrecordatorio",null);
        while(cursor.moveToNext()){
            recordatorios.add(cursor.getString(0));
        }
        BaseDatos.close();
    }

}