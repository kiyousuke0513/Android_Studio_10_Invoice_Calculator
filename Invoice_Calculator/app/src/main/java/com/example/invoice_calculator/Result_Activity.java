package com.example.invoice_calculator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Result_Activity extends AppCompatActivity {
    TextView textView_Month_Number_Result;
    TextView textView_Number_Result;
    TextView textView_Result;
    int User_Input_Number;
    String[] Str_Month_Option;
    String[] Bingo_Number;
    String resName;
    int User_Choose;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        textView_Month_Number_Result = findViewById(R.id.textView_Month_Number_Result);
        textView_Number_Result = findViewById(R.id.textView_Number_Result);
        textView_Result = findViewById(R.id.textView_Result);
        Str_Month_Option = getResources().getStringArray(R.array.Month_Option);

        //檢查Intent是否有Extra
        if (this.getIntent().hasExtra("User_Input_Number")
        &&  this.getIntent().hasExtra("resName"))
        {
            Bundle bundle = this.getIntent().getExtras();
            User_Input_Number = bundle.getInt("User_Input_Number");
            resName = bundle.getString("resName");
            User_Choose = bundle.getInt("User_Choose");
            //取得Resources中，選擇月份的字串陣列中的字串，顯示到TextView
            textView_Month_Number_Result.setText(Str_Month_Option[User_Choose] + "中獎號碼 :");

            //動態取得Resources中，選擇月份的中獎號碼陣列
            int resId =getApplicationContext().getResources()
                    .getIdentifier(resName
                            ,"array"
                            ,getPackageName());

            Resources res_Number = getResources();
            Bingo_Number = res_Number.getStringArray(resId);

            //將選擇月份的中獎號碼陣列，轉為String並加入獎項與換行後，顯示到TextView
            StringBuilder Number_Str = new StringBuilder();
            for(int i=0;i<Bingo_Number.length;i++) {
                if(i == 0)
                    Number_Str.append("特別獎: ").append(Bingo_Number[i]).append("\n");
                else if(i == 1)
                    Number_Str.append("特獎: ").append(Bingo_Number[i]).append("\n");
                else if(i < 5)
                    Number_Str.append("頭獎: ").append(Bingo_Number[i]).append("\n");
                else if(i < Bingo_Number.length - 1)
                    Number_Str.append("增開六獎: ").append(Bingo_Number[i]).append("\n");
                else if(i == Bingo_Number.length - 1)
                    Number_Str.append("增開六獎: ").append(Bingo_Number[i]);
            }
            textView_Number_Result.setText(Number_Str);

            //顯示到TextView
            //textView_Result.setText(User_Input_Number + resName);
            determine();
        }
    }

    private void determine() {
        StringBuilder format_of_bingo_number = new StringBuilder();

        textView_Result.setTextColor(getResources().getColor(R.color.red));
        textView_Result.setText("沒有中獎!");

        for(int i=0;i<2;i++)
        {
            format_of_bingo_number.delete(0,format_of_bingo_number.length());
            format_of_bingo_number.append(Bingo_Number[i]);
            format_of_bingo_number.delete(0,5);
            if(User_Input_Number == Integer.parseInt(format_of_bingo_number.toString()))
            {
                textView_Result.setTextColor(getResources().getColor(R.color.teal_200));
                if(i == 0)
                    textView_Result.setText("請留意特別獎!");
                else
                    textView_Result.setText("請留意特獎!");
            }
        }

        for(int i=2;i<5;i++)
        {
            format_of_bingo_number.delete(0,format_of_bingo_number.length());
            format_of_bingo_number.append(Bingo_Number[i]);
            format_of_bingo_number.delete(0,5);
            if(User_Input_Number == Integer.parseInt(format_of_bingo_number.toString()))
            {
                textView_Result.setTextColor(getResources().getColor(R.color.teal_200));
                textView_Result.setText("六獎!(200元)\n請留意頭獎到五獎!");
            }
        }

        for(int i=5;i<Bingo_Number.length;i++)
        {
            format_of_bingo_number.delete(0,format_of_bingo_number.length());
            format_of_bingo_number.append(Bingo_Number[i]);
            if(User_Input_Number == Integer.parseInt(format_of_bingo_number.toString()))
            {
                textView_Result.setTextColor(getResources().getColor(R.color.teal_200));
                textView_Result.setText("增開六獎!(200元)");
            }
        }
    }

    //重新輸入號碼按鈕
    public void BTN_Re_Input_Trig(View view) {
        Intent intent = new Intent();
        intent.setClass(Result_Activity.this , Input_Activity.class);

        Bundle bundle = new Bundle();//建立Bundle以傳送資料
        bundle.putInt("User_Choose", User_Choose);//put進Bundle
        intent.putExtras(bundle);//將Bundle物件put給intent

        startActivity(intent);
        Result_Activity.this.finish();
    }

    //重新選擇月份按鈕
    public void BTN_Repick_Month_Result_Trig(View view) {
        Intent intent = new Intent();
        intent.setClass(Result_Activity.this , MainActivity.class);
        startActivity(intent);
        Result_Activity.this.finish();
    }

    //中獎規則按鈕
    public void BTN_Rule_Trig(View view) {
        AlertDialog.Builder alertDialog =
                new AlertDialog.Builder(Result_Activity.this);
        alertDialog.setTitle("中獎規則");
        alertDialog.setMessage(getResources().getString(R.string.TV_Rule));
        alertDialog.setPositiveButton("我知道了!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getBaseContext(),"確定",Toast.LENGTH_SHORT).show();
            }
        });
//        alertDialog.setNegativeButton("中立",(dialog, which) -> {
//            //setToast("中立");
//        });
//        alertDialog.setNeutralButton("取消",(dialog, which) -> {
//            //setToast("取消");
//        });
        alertDialog.setCancelable(true);
        alertDialog.show();

    }
}
