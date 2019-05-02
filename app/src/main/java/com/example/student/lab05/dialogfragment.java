package com.example.student.lab05;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



//繼承對象由Fragment變更為android.support.v4.app.DialogFragment
//因為不是一般的Fragment，要刪除onCreateView，並且override DialogFragment的onCreateDialog
public class dialogfragment extends DialogFragment {
    private EditText name;
    int count;
    //private EditText password;

    public dialogfragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    //Override to build your own custom Dialog container.
    // This is typically used to show an AlertDialog instead of a generic Dialog; when doing so, onCreateView(LayoutInflater, ViewGroup, Bundle) does not need to be implemented since the AlertDialog takes care of its own content.
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Instantiates a layout XML file into its corresponding View objects.
        //透過inflater讀取fragment_dialogfragment.xml來產生view
        LayoutInflater inflater=getActivity().getLayoutInflater(); //取得inflater

        //從fragment_dialogfragment.xml取得自訂畫面，作法inflate(resources,viewGroup)，因為這邊沒有viewgroup所以是null
        View v=inflater.inflate(R.layout.fragment_dialogfragment,null);
        name=v.findViewById(R.id.name);
        //password=v.findViewById(R.id.password);

        //Activity getActivity ()
        //Return the Activity this fragment is currently associated with.
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder setView (View view)
        //Sets a custom view to be the contents of the alert dialog.
        builder.setView(v)
                .setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity main=(MainActivity)getActivity();
                        //count=++count;
                        int count=main.times();
                        CharSequence Name=name.getText();
                        StringBuilder all=new StringBuilder();
                        all.append("次數").append(count).append(" 歡迎光臨 ").append(Name);
                        main.changeContent(all);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //先取得mainActivity，再透過寫在mainactivity中的 changeContent方法改變mainActivity的textview內容
                       MainActivity main=(MainActivity)getActivity();
                        main.changeContent("登入取消");
                    }
                });

        //AlertDialog create ()
        //Creates an AlertDialog with the arguments supplied to this builder.
        //回傳建立的Dialog
        return builder.create();
    }
}
