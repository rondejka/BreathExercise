package com.ondejka.breathexercise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayLogin extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Cancel;
    Parameters parameters;
    SharedPreferences mPrefs;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.editTextEmailAddress);
        Password = findViewById(R.id.editTextPassword);
        Info = findViewById(R.id.textViewInfo);
        Login = findViewById(R.id.buttonLogin);
        Cancel = findViewById(R.id.buttonCancel);

        parameters = (Parameters)getIntent().getSerializableExtra("PARAMETERS");

        initJsonPlaceHolderApi();
        token = "";

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser(Email.getText().toString(), Password.getText().toString());
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////////
    private void initJsonPlaceHolderApi() {

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.129.65.192/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }


    //////////////////////////////////////////////////////////////////////////////
    private void registerNewUser(final String email, final String password) {
        Log.i("registerNewUser_010", "OK");
        Log.i("username:", email);
        Log.i("password:", password);

        Call<Token> call = jsonPlaceHolderApi.registration(email, password, password);
        Log.i("registerNewUser_020", "OK");

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.i("registerNewUser_030", String.valueOf(response.code()));
                    Log.i("registerNewUser_035", String.valueOf(response.body()));
                    token = "";

                    loginUser(email, password);

                    return;
                }

                Token tokenResponse = response.body();

                token = "Token " + tokenResponse.getToken();
                Toast.makeText(getApplicationContext(), "Token: " + token, Toast.LENGTH_SHORT).show();
                Log.i("Token ", token);

                parameters.setEmail(email);
                savePreferences("email", email, "start123");

                finish();

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Token is not correct :(" + t.getMessage(), Toast.LENGTH_SHORT).show();
                token = "";
                Log.i("Token is not correct", t.getMessage());
            }
        });

    }


    //////////////////////////////////////////////////////////////////////////////
    private void loginUser(final String email, String password) {

    }


        //////////////////////////////////////////////////////////////////////////////
    private void savePreferences(String name_key, String value, String defValue){
        mPrefs = getSharedPreferences(name_key, 0);
        String mString = mPrefs.getString(name_key, defValue);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(name_key, value).commit();
    }



}