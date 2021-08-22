package com.udem.homeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.udem.homeapp.Model.Notes;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText txtNom, txtDes;
    private String nameOld="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        txtNom = (EditText) findViewById(R.id.txtNom1);
        txtDes = (EditText) findViewById(R.id.txtDescrip2);

        String nombreID = getIntent().getStringExtra("NotaId");
        refreshView(nombreID);
        nameOld = txtNom.getText().toString();
    }

    public void refreshView(String nombre){
        Notes notes = null;
        SqlHelper sqlDb = new SqlHelper(this, "tblnotas", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();
        Cursor cursor = BaseDatos.rawQuery("Select * from tblnotas where nombre='"+nombre+"'",null);
        while(cursor.moveToNext()){
            notes = new Notes(cursor.getString(0), cursor.getString(1));
        }
        BaseDatos.close();

        txtNom.setText(notes.getNombre());
        txtDes.setText(notes.getDescripcion());
    }

    public void regresar(View view){
        Intent backi = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backi);
    }

    public void UpdateNote(View view){
        if(!txtNom.getText().toString().isEmpty() && !txtDes.getText().toString().isEmpty()){
            SqlHelper sqlDb = new SqlHelper(this, "tblnotas", null, 1);
            SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("nombre",txtNom.getText().toString());
            registro.put("descripcion",txtDes.getText().toString());

            int cantidad = BaseDatos.update("tblnotas", registro,"nombre='"+nameOld+"'",null);

            if(cantidad == 1){
                Toast.makeText(this, R.string.nota_update, Toast.LENGTH_SHORT).show();
                txtNom.setText("");
                txtDes.setText("");
            }else{
                Toast.makeText(this, R.string.nota_not_found, Toast.LENGTH_SHORT).show();
            }
            BaseDatos.close();
        }else{
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteNote(View view){
        if(!txtNom.getText().toString().isEmpty()){
            SqlHelper sqlDb = new SqlHelper(this, "tblnotas", null, 1);
            SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();
            int cantidad = BaseDatos.delete("tblnotas","nombre='"+txtNom.getText().toString()+"'",null);
            BaseDatos.close();

            if(cantidad == 1){
                Toast.makeText(this, R.string.nota_delete, Toast.LENGTH_SHORT).show();
                txtNom.setText("");
                txtDes.setText("");
            }else{
                Toast.makeText(this, R.string.nota_not_found, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show();
        }
    }

}