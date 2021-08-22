package com.udem.homeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText txtNota, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notecreation);

        txtNota = (EditText) findViewById(R.id.txtNote);
        txtDescripcion = (EditText) findViewById(R.id.txtDescrip);
    }

    public void SaveNote(View view){
        String nombre = txtNota.getText().toString();
        String descripcion = txtDescripcion.getText().toString();

        SqlHelper sqlDb = new SqlHelper(this, "tblnotas", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

        if(!nombre.isEmpty() && !descripcion.isEmpty()){
            ContentValues registro = new ContentValues();
                registro.put("nombre",nombre);
                registro.put("descripcion",descripcion);
            BaseDatos.insert("tblnotas",null, registro);
            BaseDatos.close();

            Toast.makeText(this, R.string.create_note, Toast.LENGTH_SHORT).show();
            txtNota.setText("");
            txtDescripcion.setText("");
        }else{
            Toast.makeText(this, R.string.fail_create, Toast.LENGTH_SHORT).show();
        }
    }

    public void regresar(View view){
        Intent backi = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backi);
    }
}