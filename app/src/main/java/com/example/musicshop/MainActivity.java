package com.example.musicshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    int plus2 = 0;


    ArrayList predmeti;
    ArrayAdapter spinnerAdapter;
    HashMap kluchikiMap;
    Double price;
    String kluchikiNames;
    EditText PoleName;
    String pustoePole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        predmeti = new ArrayList();
        PoleName = findViewById(R.id.Your_name);
        Button Add = findViewById(R.id.button_add_to_cart);
        Add.setTransformationMethod(null);

        predmeti.add("guitar");
        predmeti.add("keyboard");
        predmeti.add("drums");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, predmeti);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        kluchikiMap = new HashMap();
        kluchikiMap.put("guitar", 500.0);
        kluchikiMap.put("keyboard", 1000.0);
        kluchikiMap.put("drums", 600.0);
    }

    public void Plus1(View view) {
        plus2++;
        if(plus2 >= 20){
            plus2 = 20;
        }
        TextView plus1 = findViewById(R.id.textView2);
            plus1.setText("" + plus2);

        TextView TextViewPrice = findViewById(R.id.textView4);
        TextViewPrice.setText("" + plus2 * price);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        kluchikiNames = spinner.getSelectedItem().toString();
        price = (double) kluchikiMap.get(kluchikiNames);
        TextView TextViewPrice = findViewById(R.id.textView4);
        TextViewPrice.setText("" + plus2 * price);

        ImageView ZamenaPhoto = findViewById(R.id.imageView2);
        if (kluchikiNames.equals("drums")){                                 //Данные типа String сравниваются при помощи метода <<equals>>
            ZamenaPhoto.setImageResource(R.drawable.drums__two);
        }
        else if (kluchikiNames.equals("keyboard")){
            ZamenaPhoto.setImageResource(R.drawable.keyboard_three);
        }
        else {
            ZamenaPhoto.setImageResource(R.drawable.screenshot);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void minus1(View view) {
        plus2 = plus2 - 1;
        TextView plus1 = findViewById(R.id.textView2);
        plus1.setText(" " + plus2);
        if (plus2 <= 0){
            plus2 = 0;
        }

        TextView TextViewPrice = findViewById(R.id.textView4);
        Double TextViewPriceTypeDouble = Double.parseDouble(TextViewPrice.getText().toString());
        Double Resultat = TextViewPriceTypeDouble - price;
        TextViewPrice.setText("" + Resultat); //тут короче надо решить как вычесть из price заначение в HashMap!

        if (plus2 <= 0){
            TextViewPrice.setText("0.0");
            plus1 = findViewById(R.id.textView2);
            plus1.setText("0");
        }
    }
    public void Add_to_card(View view) {
        PoleName = (EditText) findViewById(R.id.Your_name);
        String strUserName = PoleName.getText().toString();
        if(plus2 == 0){
            Toast.makeText(this, "plz select your product and his quantity ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(strUserName.trim().equals("")){
            Toast.makeText(this, "plz enter your name ", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Order order = new Order();

            order.UserName = PoleName.getText().toString();
            Log.d("printUserName",order.UserName);//c помощью Log.d(debug) мы можем выводить и проверять значения элементов заранее
            order.kluchikiNames = kluchikiNames;    // ебаа, тут я ссылкаюсь на уже мною созданную переменную, которой я уже задал нужные параметры
            Log.d("printKluchikiNames",order.kluchikiNames);
            order.Quatity = plus2;
            Log.d("printQuatity", String.valueOf(order.Quatity));
            order.Order_price = price*plus2;
            Log.d("printPrice", String.valueOf(order.Order_price));

            Intent Perexod_v_korziny = new Intent(this, MainActivity2.class);
            Perexod_v_korziny.putExtra("Name", order.UserName);
            Perexod_v_korziny.putExtra("Product", order.kluchikiNames);
            Perexod_v_korziny.putExtra("Quantity", order.Quatity);
            Perexod_v_korziny.putExtra("FinalPrice", order.Order_price);
        startActivity(Perexod_v_korziny);
        }
    }
}