package com.udem.homeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.udem.homeapp.Model.Notes;
import com.udem.homeapp.Model.Remind;

import java.util.Calendar;

public class UpdateRemindActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnfecha, btnhora;
    EditText txtNomRemind, txtDesRemind, txtfecha, txthora;
    private String nameOld="";
    private int dia, mes, ano, hora, minuto, segundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_remind);

        btnfecha = (Button) findViewById(R.id.btnDate);
        btnfecha.setOnClickListener(this);
        btnhora = (Button) findViewById(R.id.btnHour);
        btnhora.setOnClickListener(this);

        txtNomRemind = (EditText) findViewById(R.id.txtIdRemind2);
        txtDesRemind = (EditText) findViewById(R.id.txtDescripRemind2);
        txtfecha = (EditText) findViewById(R.id.txtfecha2);
        txthora = (EditText) findViewById(R.id.txtHora2);

        String nombreID = getIntent().getStringExtra("RecordatorioId");
        refreshView(nombreID);
        nameOld = txtNomRemind.getText().toString();
    }

    public void refreshView(String nombre){
        Remind recordi = null;
        SqlHelper sqlDb = new SqlHelper(this, "tblrecordatorio", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();
        Cursor cursor = BaseDatos.rawQuery("Select * from tblrecordatorio where nombre='"+nombre+"'",null);
        while(cursor.moveToNext()){
            recordi = new Remind(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        BaseDatos.close();

        txtNomRemind.setText(recordi.getNombre());
        txtDesRemind.setText(recordi.getDescripcion());
        txtfecha.setText(recordi.getFecha());
        txthora.setText(recordi.getHora());
    }

    public void regresar(View view){
        Intent backi = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backi);
    }

    public void UpdateRemind(View view){
        if(!txtNomRemind.getText().toString().isEmpty() && !txtDesRemind.getText().toString().isEmpty() && !txtfecha.getText().toString().isEmpty() && !txthora.getText().toString().isEmpty()){
            SqlHelper sqlDb = new SqlHelper(this, "tblrecordatorio", null, 1);
            SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

            ContentValues registro = new ContentValues();
                registro.put("nombre",txtNomRemind.getText().toString());
                registro.put("descripcion",txtDesRemind.getText().toString());
                registro.put("fecha", txtfecha.getText().toString());
                registro.put("hora", txthora.getText().toString());

            int cantidad = BaseDatos.update("tblrecordatorio", registro,"nombre='"+nameOld+"'",null);

            if(cantidad == 1){
                Toast.makeText(this, R.string.remind_update, Toast.LENGTH_SHORT).show();
                txthora.setText("");
                txtNomRemind.setText("");
                txtDesRemind.setText("");
                txtfecha.setText("");
            }else{
                Toast.makeText(this, R.string.remind_not_found, Toast.LENGTH_SHORT).show();
            }
            BaseDatos.close();
        }else{
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteRemind(View view){
        if(!txtNomRemind.getText().toString().isEmpty() && !txtDesRemind.getText().toString().isEmpty() && !txtfecha.getText().toString().isEmpty() && !txthora.getText().toString().isEmpty()){
            SqlHelper sqlDb = new SqlHelper(this, "tblrecordatorio", null, 1);
            SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();
            int cantidad = BaseDatos.delete("tblrecordatorio","nombre='"+txtNomRemind.getText().toString()+"'",null);
            BaseDatos.close();

            if(cantidad == 1){
                Toast.makeText(this, R.string.remind_delete, Toast.LENGTH_SHORT).show();
                txthora.setText("");
                txtNomRemind.setText("");
                txtDesRemind.setText("");
                txtfecha.setText("");
            }else{
                Toast.makeText(this, R.string.remind_not_found, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnfecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtfecha.setText(dayOfMonth+"/"+month+"/"+year);
                }
            }, ano, mes, dia);
            datepicker.show();
        }
        if(v == btnhora){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minuto = c.get(Calendar.MINUTE);
            segundo = c.get(Calendar.SECOND);

            TimePickerDialog timerpicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txthora.setText(hourOfDay+"/"+minute+"/"+segundo);
                }
            }, hora, minuto, false);
            timerpicker.show();
        }
    }

}