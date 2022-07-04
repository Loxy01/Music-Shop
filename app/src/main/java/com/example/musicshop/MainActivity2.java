package com.example.musicshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    TextView Name; // = String;
    TextView Product;
    TextView Quantity;
    TextView FinalPrice;
    Button Send_order;
    String[] addresses = {"andrey_akimov_2006@vk.com"};// тут я созадл массив со своей почтой
    String subject = "Order from Music Shop";// Тема нашего заказа из музыкального магазина
    String EmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("My cart");
        Send_order = findViewById(R.id.send_order);
        Send_order.setTransformationMethod(null);

        Intent recivedOrderIntent = getIntent();
        String getName = recivedOrderIntent.getStringExtra("Name");
        String getProduct = recivedOrderIntent.getStringExtra("Product");
        int getQuantity = recivedOrderIntent.getIntExtra("Quantity",0);
        double getFinalPrice = recivedOrderIntent.getDoubleExtra("FinalPrice",0.0);

        Name = findViewById(R.id.Name);
        Name.setText(getName);
        Product = findViewById(R.id.Product);
        Product.setText(getProduct);
        Quantity = findViewById(R.id.Quantity);
        Quantity.setText(""+getQuantity);
        FinalPrice = findViewById(R.id.FinalPrice);
        FinalPrice.setText(""+getFinalPrice+"$");
        EmailText = "My name: "+getName+"\n"+"My order: "+getProduct+"\n"+"Quantity: "+getQuantity+"\n"+"My Final Price: "+getFinalPrice+"$";
    }
    public void Send__order(View view) {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        send.setData(Uri.parse("mailto:"));//только приложения электронной почты могут это обработать!!!
        send.putExtra(Intent.EXTRA_EMAIL, addresses);
        send.putExtra(Intent.EXTRA_SUBJECT,subject);
        send.putExtra(Intent.EXTRA_TEXT, EmailText);//Тут будет текст письма
        if(send.resolveActivity(getPackageManager()) != null){
            startActivity(send);
        }
        else{
            Toast.makeText(this, "plz download email app) ", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}