package com.udem.homeapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewNoteFragment extends Fragment {

    public ViewNoteFragment(){}
    public ArrayList<String> notas;

    @Override
    public void onCreate(Bundle savedInstanceState) {  super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notas = new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_test1, container, false);
        ListView lv1 = (ListView) view.findViewById(R.id.lv_notas);

        Button btnCreateNote = (Button) view.findViewById(R.id.btnCreatenote);
        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        getNotes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, notas);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);
                //Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), UpdateNoteActivity.class);
                i.putExtra("NotaId", value);
                startActivity(i);
            }
        });

        return view;
    }

    public void getNotes(){
        SqlHelper sqlDb = new SqlHelper(getContext(), "tblnotas", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

        Cursor cursor = BaseDatos.rawQuery("Select nombre from tblnotas",null);
        while(cursor.moveToNext()){
            notas.add(cursor.getString(0));
        }
        BaseDatos.close();
    }


}