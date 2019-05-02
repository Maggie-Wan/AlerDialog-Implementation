package com.example.student.lab05;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

//將對話框動作按鈕工作委託給MainActivity處理
public class MainActivity extends AppCompatActivity
        implements DialogInterface.OnClickListener {
    private TextView text;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView)findViewById(R.id.text);
    }


    public void ok(View view) {
        //AlertDialog.Builder(Context context)
        //Creates a builder for an alert dialog that uses the default alert dialog theme.
        new AlertDialog.Builder(this)//打造的對話框使用當前的activity主題顏色
                .setMessage("你好帥喔")
                //AlertDialog.Builder setPositiveButton (int textId,DialogInterface.OnClickListener listener)
                //Set a listener to be invoked when the positive button of the dialog is pressed.
                //listener=>DialogInterface.OnClickListener: The DialogInterface.OnClickListener to use.
                .setPositiveButton("我知道",this) //將此按鈕事件委託給當前的activity
                .show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        text.setText("我知道");
    }

    //按下secondbtn，要將兩個按鈕交給同一個alert a物件處理
    public void secondbtn(View view) {
        alert a=new alert();
        new AlertDialog.Builder(this)
                .setMessage("你好帥喔")
                .setPositiveButton("謝謝",a)
                .setNegativeButton("狗腿",a)
                .show();
    }


    //這邊將第個二按鈕的對話框按鈕事件委託給內部類別alert
    private class alert implements AlertDialog.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                //DialogInterface.BUTTON_POSITIVE會是一個整數，類似R.id的作法
                case DialogInterface.BUTTON_POSITIVE:
                    text.setText("謝謝");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    text.setText("狗腿");
                    break;
            }
        }
    }

    //當第三個按鈕按下去時，用匿名類別直接實作程式碼，不再另外寫一個類別，當裡面的程式很簡單的時候適用
    public void thirdbtn(View view) {
        new AlertDialog.Builder(this)
                .setMessage("你好帥喔")
                .setPositiveButton("謝謝讚美", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText("謝謝讚美");
                    }
                })
                .setNegativeButton("太狗腿了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText("太狗腿了");
                    }
                })
                .setNeutralButton("無言", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText("無言");
                    }
                })
                .show();

    }

    //按鈕四會產生項目清單
    public void fourbtn(View view) {  //android.R.style.Theme_DeviceDefault_Dialog 指定要用的主題風格
       final String[] arr=getResources().getStringArray(R.array.array);
        //this就是誰要使用這個dialog
        new AlertDialog.Builder(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
                .setTitle("你好帥")
                .setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                    //int i是代表陣列中選取的那個項目的索引碼
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText(arr[i]);
                    }
                })
                .show();
    }

    public void fifthbtn(View view) {
        //當匿名類別使用外部的變數時，因為考量到匿名類別的生命週期和外部變數可能會不一樣，所以要把被使用到的外部變數宣告為final，而匿名類別會自動把該變數copy下來
        final String[] arr=getResources().getStringArray(R.array.array);
        //建立一個陣列紀錄每個checkbox是否有被選取，陣列長度要和字串長度一樣
        final boolean[] selected=new boolean[arr.length];
        new AlertDialog.Builder(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
                .setTitle("你超正")
                //多選要用setMultiChoiceItems
                //selected陣列用來接收值：
                // String: specifies the column name on the cursor to use to determine whether a checkbox
                // is checked or not. It must return an integer value where 1 means checked and 0 means unchecked.
                .setMultiChoiceItems(arr, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    //這邊的onClick是代表每次勾選或取消勾選時執行，i代表這次勾選的項目編號，b代表這次勾選的選項狀態
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    //因為勾選後沒有要執行甚麼程式，所以這邊空白，執行是在ok按鈕按下去才做
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                      StringBuilder result=new StringBuilder();
                        //按下ok按鈕後，到selected陣列逐一檢查，如果某個選項is true，就把同樣編碼的arr陣列字串丟到stringbuilder中
                        for(int i=0;i<selected.length;i++){
                            if(selected[i]){
                                result.append(arr[i]).append("\n");
                            }
                        }
                        text.setText(result);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override//cancelbutton沒有其他動作
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }
    //mChoice用來記錄被選取的選項，因為selectN(被選取選項的編號)是一個區域變數，沒辦法丟給ok的onclick，所以要丟給一個全域變數來記錄
    private int mChoice;
    public void sixthbtn(View view) {
        final String[] arr=getResources().getStringArray(R.array.array);
        mChoice=0;
        new AlertDialog.Builder(this)
                .setTitle("你好美")
                //單一選項用setSingleChoiceItems,第二個參數是被選取選項的編號
                .setSingleChoiceItems(arr, mChoice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectN) {
                        mChoice=selectN;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText(arr[mChoice]);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.setText("算了吧");
                    }
                })
                .show();

    }


    public void seventhbtn(View view) {
        //建立自訂的dialog
        dialogfragment my=new dialogfragment();
        //顯示diaglog
        //void show (FragmentManager manager,String tag)
        //Display the dialog, adding the fragment to the given FragmentManager
        my.show(getSupportFragmentManager(),"dialogfragment");

    }

    public void changeContent(CharSequence msg){
        text.setText(msg);
    }

    public int times(){
        count=++count;
        return count;
    }

}
