package com.example.lab2v2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecretFeature extends AppCompatActivity {

    private Button braveButton;
    private TextView question;
    private EditText braveText;
    private Intent outsider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_feature);

        this.outsider = getIntent();

        setResult(1, outsider);

        this.question = (TextView) findViewById(R.id.questionTextView);
        this.question.setText(outsider.getStringExtra("text"));

        this.braveButton = (Button) findViewById(R.id.braveButton);
        this.braveText = (EditText) findViewById(R.id.braveText);
    }//on create

    public void onBraveButtonClicked(View view) {
        String data = braveText.getText().toString();

        outsider.putExtra("answer", data);
        finish();
        //outsider.setData()
    }// on brave button clicked


}//class
