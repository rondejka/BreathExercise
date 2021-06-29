package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DisplaySetupUser extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    SharedPreferences mPrefs;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String token;
    private String changeUserMode;

    EditText emailEditText;
    EditText passwordEditText;
    EditText firstNameEditText;
    EditText lastnameEditText;
    EditText genderEditText;
    EditText birthYearEditText;
    private Button Confirm;
    private Button Cancel;

    boolean updateParametersStarted;
    String updateParametersType;
    Parameters parameters;



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SetupUser_OnCr_010", "OK");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_setup_user);
        Log.i("SetupUser_OnCr_012", "OK");

        emailEditText = findViewById(R.id.EmailEditText);
        passwordEditText = findViewById(R.id.PasswordEditText);
        firstNameEditText = findViewById(R.id.FirstNameEditText);
        lastnameEditText = findViewById(R.id.LastNameEditText);
        genderEditText = findViewById(R.id.GenderEditText);
        birthYearEditText = findViewById(R.id.BirthYearEditText);
        Confirm = findViewById(R.id.buttonConfirm);
        Cancel = findViewById(R.id.buttonCancel);
        Log.i("SetupUser_OnCr_020", "OK");

        updateParametersStarted=false;
        updateParametersType = "";
        changeUserMode = "";

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.i("SetupUser_OnCr_020 screenWidth", String.valueOf(screenWidth));
        Log.i("SetupUser_OnCr_020 screenHeihgt", String.valueOf(screenHeihgt));



        parameters = (Parameters)getIntent().getSerializableExtra("PARAMETERS");
        Log.i("SetupUser_OnCr_022 param", "OK");

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        Log.i("SetupUser_OnCr_030", "OK");

        emailEditText.setText(parameters.getEmail());
        passwordEditText.setText(parameters.getPassword());
        firstNameEditText.setText(parameters.getFirstName());
        lastnameEditText.setText(parameters.getLastName());
        genderEditText.setText(parameters.getGender());
        birthYearEditText.setText(String.valueOf(parameters.getBirthYear()));

        Log.i("SetupUser_OnCr_032", "OK");


        initJsonPlaceHolderApi();
        token = "";


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeUserMode.equals("S")) {
                    registerNewUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
                } else if (changeUserMode.equals("L")) {
                    loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });



        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                saveEmail(String.valueOf(s));
                Log.i("afterEmailChanged", String.valueOf(s));
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                savePassword(String.valueOf(s));
                Log.i("afterPasswordChanged", String.valueOf(s));
            }
        });

        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                saveFirstName(String.valueOf(s));
                Log.i("afterFirstNameChanged", String.valueOf(s));
            }
        });

        lastnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                saveLastName(String.valueOf(s));
                Log.i("afterLastNameChanged", String.valueOf(s));
            }
        });

        genderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                saveGender(String.valueOf(s));
                Log.i("afterGenderChanged", String.valueOf(s));
            }
        });

        birthYearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                saveBirthYear(Integer.parseInt(String.valueOf(s)));
                Log.i("afterBirthYearChanged", String.valueOf(s));
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
                    return;
                }

                Token tokenResponse = response.body();
                token = "Token " + tokenResponse.getToken();
                Toast.makeText(getApplicationContext(), "Token: " + token, Toast.LENGTH_SHORT).show();
                Log.i("Token ", token);

                parameters.setEmail(email);
                savePreferences("email", email, "start123");

//                finish();
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
        Log.i("loginUser_010", "OK");

        Login login = new Login(email, password);
        Log.i("loginUser_015", "OK");

        Call<Token> call = jsonPlaceHolderApi.getToken(login);
        Log.i("loginUser_020", "OK");

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.i("loginUser_030", String.valueOf(response.code()));
                    Log.i("loginUser_035", String.valueOf(response.body()));
                    return;
                }

                Token tokenResponse = response.body();
                token = "Token " + tokenResponse.getToken();
                Log.i("Token ", token);

                parameters.setEmail(email);
                savePreferences("email", email, "start123");
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Token is not correct :(" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Token is not correct", t.getMessage());
            }
        });

    }


    // ---- Email
    public void saveEmail(String email){
        parameters.setEmail(email);
        savePreferences("email", email, "start123");
    }

    // ---- Password
    public void savePassword(String password){
        parameters.setPassword(password);
        savePreferences("password", password, "start123");
    }

    // ---- First Name
    public void saveFirstName(String firstName){
        parameters.setFirstName(firstName);
        savePreferences("firstName", firstName, "First_name");
    }

    // ---- Last Name
    public void saveLastName(String lastName){
        parameters.setLastName(lastName);
        savePreferences("lastName", lastName, "Last_name");
    }

    // ---- Gender
    public void saveGender(String gender){
        parameters.setGender(gender);
        savePreferences("gender", gender, "M/W");
    }

    // ---- Birth Year
    public void saveBirthYear(int birthYear){
        parameters.setBirthYear(birthYear);
        savePreferences("birthYear", String.valueOf(birthYear), "1970");
    }

    private void savePreferences(String name_key, String value, String defValue){
        mPrefs = getSharedPreferences(name_key, 0);
        String mString = mPrefs.getString(name_key, defValue);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(name_key, value).commit();
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the Change User text
     */
    public void changeUser(View view) {
        Intent intent = new Intent(this, DisplayLogin.class);
        intent.putExtra("PARAMETERS", parameters);
        startActivity(intent);
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the Sing in text
     */
    public void changeUser_NewUser(View view) {
        changeUserMode = "S";

        setEdittextAvailable(emailEditText);
        setEdittextAvailable(passwordEditText);

        setEdittextAvailable(firstNameEditText);
    }


    /**
     * Called when the user taps the Login text
     */
    public void changeUser_loginUser(View view) {
        changeUserMode = "L";

        setEdittextAvailable(emailEditText);
        setEdittextAvailable(passwordEditText);

        setEdittextAvailable(firstNameEditText);
    }

    private void setEdittextAvailable(EditText editTextField) {
        editTextField.setClickable(true);
        editTextField.setFocusable(true);
        editTextField.setFocusableInTouchMode(true);
        editTextField.setTextAppearance(this, R.style.SetupParamPersonTextValue);
    }

    private void setEdittextUnAvailable(EditText editTextField) {
        editTextField.setClickable(false);
        editTextField.setFocusable(false);
        editTextField.setFocusableInTouchMode(false);
        editTextField.setTextAppearance(this, R.style.SetupParamPersonTextValueUnAvailable);

    }

}