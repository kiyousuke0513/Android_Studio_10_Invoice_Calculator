package com.example.invoice_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    int User_Choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        Button Btn_Start = findViewById(R.id.button_Start);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,   //對應的Context
                        R.array.Month_Option,                   //資料選項內容
                        android.R.layout.simple_spinner_item);  //預設Spinner未展開時的View(預設及選取後樣式)

        //指定Spinner展開時，選項清單的樣式
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);//Dialog用

        spinner.setAdapter(adapter);
        set_spinner_Listener();

        Btn_Start.setOnClickListener(Btn_Start_OnClickListener);
    }

    private final Button.OnClickListener Btn_Start_OnClickListener = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Input_Activity.class);

            Bundle bundle = new Bundle();//建立Bundle以傳送資料
            bundle.putInt("User_Choose", User_Choose);//put進Bundle
            intent.putExtras(bundle);//將Bundle物件put給intent

            startActivity(intent);
            MainActivity.this.finish();
        }
    };

    private void set_spinner_Listener() {
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                  User_Choose = position;
              }
              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {
              }
          }
        );
    }
}