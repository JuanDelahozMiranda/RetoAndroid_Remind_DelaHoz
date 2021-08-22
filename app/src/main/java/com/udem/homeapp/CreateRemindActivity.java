package com.udem.homeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateRemindActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnfecha, btnhora;
    EditText txtNomRemind, txtDesRemind, txtfecha, txthora;
    private int dia, mes, ano, hora, minuto, segundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_remind);

        btnfecha = (Button) findViewById(R.id.btnDate);
        btnfecha.setOnClickListener(this);
        btnhora = (Button) findViewById(R.id.btnHour);
        btnhora.setOnClickListener(this);

        txtNomRemind = (EditText) findViewById(R.id.txtIdRemind);
        txtDesRemind = (EditText) findViewById(R.id.txtDescripRemind);
        txtfecha = (EditText) findViewById(R.id.txtfecha);
        txthora = (EditText) findViewById(R.id.txtHora);
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

    public void regresar(View view){
        Intent backi = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backi);
    }

    public void SaveRemind(View view){

        SqlHelper sqlDb = new SqlHelper(this, "tblrecordatorio", null, 1);
        SQLiteDatabase BaseDatos = sqlDb.getWritableDatabase();

        if(!txtNomRemind.getText().toString().isEmpty() && !txtDesRemind.getText().toString().isEmpty() && !txtfecha.getText().toString().isEmpty() && !txthora.getText().toString().isEmpty()){
            ContentValues registro = new ContentValues();
                registro.put("nombre",txtNomRemind.getText().toString());
                registro.put("descripcion",txtDesRemind.getText().toString());
                registro.put("fecha", txtfecha.getText().toString());
                registro.put("hora", txthora.getText().toString());
            BaseDatos.insert("tblrecordatorio",null, registro);
            BaseDatos.close();

            Toast.makeText(this, R.string.create_remind, Toast.LENGTH_SHORT).show();
            txthora.setText("");
            txtNomRemind.setText("");
            txtDesRemind.setText("");
            txtfecha.setText("");
        }else{
            Toast.makeText(this, R.string.fail_create, Toast.LENGTH_SHORT).show();
        }
    }
}