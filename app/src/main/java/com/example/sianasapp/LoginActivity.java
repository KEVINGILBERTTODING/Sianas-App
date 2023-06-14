package com.example.sianasapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sianasapp.FragmentAdmin.AdminMainActivty;
import com.example.sianasapp.Model.UserModel;
import com.example.sianasapp.Util.AuthInterface;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    AuthInterface authInterface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        if (sharedPreferences.getBoolean("logged_in", false)) {
            if (sharedPreferences.getInt("role", 0) == 2) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else if (sharedPreferences.getInt("role", 0) == 1) {
                startActivity(new Intent(LoginActivity.this, AdminMainActivty.class));
                finish();
            }else if (sharedPreferences.getInt("role", 0) == 3) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username tidak boleh kosong");
                    etUsername.requestFocus();
                    return;
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                    return;
                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setCancelable(false);
                    alert.setTitle("Login");
                    alert.setMessage("Tunggu sebentar...");
                    AlertDialog progressDialog = alert.create();
                    progressDialog.show();

                    authInterface.login(
                            etUsername.getText().toString(),
                            etPassword.getText().toString().toString()
                            ).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful() && response.body().getCode() == 200) {
                                if (response.body().getRole() == 1) { // admin
                                    editor.putBoolean("logged_in", true);
                                    editor.putString(Constans.SHARED_PREF_NAMA_LENGKAP, response.body().getNama());
                                    editor.putString(Constans.SHARED_PREF_USER_ID, response.body().getUserId());
                                    editor.putInt(Constans.SHARED_PREF_ROLE, response.body().getRole());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, AdminMainActivty.class));
                                    finish();
                                }else if (response.body().getRole() == 2) {
                                    editor.putBoolean("logged_in", true);
                                    editor.putString(Constans.SHARED_PREF_NAMA_LENGKAP, response.body().getNama());
                                    editor.putString(Constans.SHARED_PREF_USER_ID, response.body().getUserId());
                                    editor.putInt(Constans.SHARED_PREF_ROLE, response.body().getRole());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }



                            }else {
                                Toasty.error(LoginActivity.this, response.body().getMessage()).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Log.e("dada", "onFailure: ", t );
                            Toasty.error(LoginActivity.this, "Tidak ada koneksi internet").show();
                            progressDialog.dismiss();

                        }
                    });


                }
            }
        });
    }

    private void init() {
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        authInterface = DataApi.getClient().create(AuthInterface.class);
        sharedPreferences = getSharedPreferences(Constans.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


}