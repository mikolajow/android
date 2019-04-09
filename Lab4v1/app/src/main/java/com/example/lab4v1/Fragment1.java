package com.example.lab4v1;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;

    AppCompatActivity A1;
    OnWyborOpcjiListener sluchaczF1;


    public interface OnWyborOpcjiListener {
        public void onWyborOpcji(int opcja);
    }


    public Fragment1() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        setRetainInstance(true);

        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_main);
        option1 = (RadioButton) view.findViewById(R.id.radio1);
        option2 = (RadioButton) view.findViewById(R.id.radio2);

        setRadioGroupListener();

        return view;
    }// on create view

    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        sluchaczF1.onWyborOpcji(1);
                        break;
                    case R.id.radio2:
                        sluchaczF1.onWyborOpcji(2);
                        break;
                }//switch
            }// on chexked change
        });// listener constructor
    }// set radio group loistener



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            A1 = (AppCompatActivity) context;
            sluchaczF1 = (OnWyborOpcjiListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(A1.toString() + " musi implementować OnWyborOpcjiListener");
        }//catch
    }// on attach
}//class
