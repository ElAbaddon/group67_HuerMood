package com.example.group67_huermood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent; // Required for switching pages
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // Required for text navigation
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Passw, UserName, PassComfirm;
    Button btnContinue;
    TextView btnGoToLogin; // page swap

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find IDs
        UserName = findViewById(R.id.UserName);
        Passw = findViewById(R.id.Passw);
        PassComfirm = findViewById(R.id.PassComfirm);
        btnContinue = findViewById(R.id.btnContinue);


        btnGoToLogin = findViewById(R.id.btnGoToLogin);


        btnContinue.setOnClickListener(this);
        btnGoToLogin.setOnClickListener(this); // Listen for the text click


        db = openOrCreateDatabase("HuerMoodDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(username VARCHAR, password VARCHAR, comfirmpassword VARCHAR);");
    }

    @Override
    public void onClick(View v) {

        if (v == btnGoToLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        if (v == btnContinue) {
            String user = UserName.getText().toString().trim();
            String pass = Passw.getText().toString().trim();
            String confirm = PassComfirm.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                showMessage("Error", "Please fill all fields");
                return;
            }

            if (!pass.equals(confirm)) {
                showMessage("Error", "Passwords do not match");
                return;
            }


            db.execSQL("INSERT INTO student VALUES('" + user + "','" + pass + "','" + confirm + "');");
            showMessage("Success", "Account Successfully Created \n please go to the log in page to continue");
            clearText();
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        UserName.setText("");
        Passw.setText("");
        PassComfirm.setText("");
        UserName.requestFocus();
    }
}