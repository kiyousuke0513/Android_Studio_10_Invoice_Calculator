package com.example.invoice_calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Input_Activity extends AppCompatActivity {
    String[] Str_Month_Option;
    TextView textView_Month_Number;
    TextView textView_Number;
    EditText editText_User_Input_Number;
    String resName;
    int Word_Count;
    int User_Choose;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        Str_Month_Option = getResources().getStringArray(R.array.Month_Option);
        textView_Month_Number = findViewById(R.id.textView_Month_Number);
        textView_Number = findViewById(R.id.textView_Number);
        editText_User_Input_Number = findViewById(R.id.editTextNumber_User_Input_Number);
        editText_User_Input_Number.addTextChangedListener(textWatcher);

        //檢查Intent是否有Extra
        if (this.getIntent().hasExtra("User_Choose"))
        {
            Bundle bundle = this.getIntent().getExtras();
            User_Choose = bundle.getInt("User_Choose");
            //取得Resources中，選擇月份的字串陣列中的字串，顯示到TextView
            textView_Month_Number.setText(Str_Month_Option[User_Choose] + "中獎號碼 :");

            //動態取得Resources中，選擇月份的中獎號碼陣列
            resName = "Month_" + (User_Choose*2 + 1) + "_" + (User_Choose*2 + 2);
            int resId =getApplicationContext().getResources()
                    .getIdentifier(resName
                            ,"array"
                            ,getPackageName());

            Resources res_Number = getResources();
            String[] Number = res_Number.getStringArray(resId);

            //將選擇月份的中獎號碼陣列，轉為String並加入獎項與換行後，顯示到TextView
            StringBuilder Number_Str = new StringBuilder();
            for(int i=0;i<Number.length;i++) {
                if(i == 0)
                    Number_Str.append("特別獎: ").append(Number[i]).append("\n");
                else if(i == 1)
                    Number_Str.append("特獎: ").append(Number[i]).append("\n");
                else if(i < 5)
                    Number_Str.append("頭獎: ").append(Number[i]).append("\n");
                else if(i < Number.length - 1)
                    Number_Str.append("增開六獎: ").append(Number[i]).append("\n");
                else if(i == Number.length - 1)
                    Number_Str.append("增開六獎: ").append(Number[i]);
            }
            textView_Number.setText(Number_Str);
        }
    }

    //重新選擇月份按鈕
    public void BTN_Repick_Month_Trig(View view) {
            Intent intent = new Intent();
            intent.setClass(Input_Activity.this , MainActivity.class);
            startActivity(intent);
            Input_Activity.this.finish();
    }

    //中獎結果按鈕
    public void BTN_Result_Trig(View view) {
        // 取得鍵盤管理物件
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // 隱藏指定 view 的虛擬鍵盤
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if(Word_Count == 3) {
            Intent intent = new Intent();
            intent.setClass(Input_Activity.this, Result_Activity.class);
            int User_Input_Number = Integer.parseInt(editText_User_Input_Number.getText().toString());

            Bundle bundle = new Bundle();//建立Bundle以傳送資料
            bundle.putString("resName", resName);//月份Resources名稱，put進Bundle
            bundle.putInt("User_Choose", User_Choose);//User選擇月份，put進Bundle
            bundle.putInt("User_Input_Number", User_Input_Number);//輸入數字，put進Bundle
            intent.putExtras(bundle);//將Bundle物件put給intent

            startActivity(intent);
            Input_Activity.this.finish();
        }
        else {
            final Toast toast = Toast.makeText(getBaseContext(), "輸入數字太少",Toast.LENGTH_SHORT);
            toast.show();
            new CountDownTimer(600, 100)
            {
                public void onTick(long millisUntilFinished) {toast.show();}
                public void onFinish() {toast.cancel();}
            }.start();
        }
    }

    //輸入字元檢查
    TextWatcher textWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int editStart = editText_User_Input_Number.getSelectionStart();
            int editEnd = editText_User_Input_Number.getSelectionEnd();
            if (temp.length() > 3) {
                final Toast toast = Toast.makeText(getBaseContext(), "輸入數字已超過!",Toast.LENGTH_SHORT);
                toast.show();
                new CountDownTimer(600, 100)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.cancel();}
                }.start();
                s.delete(editStart -1, editEnd);
                editText_User_Input_Number.removeTextChangedListener(this);
                editText_User_Input_Number.setText(s);
                editText_User_Input_Number.addTextChangedListener(this);
                editText_User_Input_Number.setSelection(editStart-1);
            }
            Word_Count = s.length();//輸入字數
        }
    };
}