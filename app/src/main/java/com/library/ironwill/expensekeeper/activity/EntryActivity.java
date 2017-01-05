package com.library.ironwill.expensekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.view.MaterialLoginView.DefaultLoginView;
import com.library.ironwill.expensekeeper.view.MaterialLoginView.DefaultRegisterView;
import com.library.ironwill.expensekeeper.view.MaterialLoginView.MaterialLoginView;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        ((DefaultLoginView) login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String user = loginUser.getEditText().getText().toString();
                if (user.isEmpty()) {
                    loginUser.setError("User name can't be empty");
                    return;
                }
                loginUser.setError("");

                String pass = loginPass.getEditText().getText().toString();
//                if (!pass.equals(user)) {
                if (pass.isEmpty()) {
                    loginPass.setError("Password can't be empty");
                    return;
                }
                loginPass.setError("");
                Snackbar.make(login, "Login success!", Snackbar.LENGTH_SHORT).show();
                Intent mIntent = new Intent(EntryActivity.this, MainActivity.class);
                startActivity(mIntent);
                EntryActivity.this.finish();
            }
        });

        ((DefaultRegisterView) login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                String user = registerUser.getEditText().getText().toString();
                if (user.isEmpty()) {
                    registerUser.setError("User name can't be empty");
                    return;
                }
                registerUser.setError("");

                String pass = registerPass.getEditText().getText().toString();
                if (pass.isEmpty()) {
                    registerPass.setError("Password can't be empty");
                    return;
                }
                registerPass.setError("");

                String passRep = registerPassRep.getEditText().getText().toString();
                if (!pass.equals(passRep)) {
                    registerPassRep.setError("Passwords are different");
                    return;
                }
                registerPassRep.setError("");

                Snackbar.make(login, "Register success!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}