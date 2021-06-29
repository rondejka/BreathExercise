package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudScore2 extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    private String token;
    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CloudScore 01", "OK");
        super.onCreate(savedInstanceState);
        Log.i("CloudScore 02", "OK");

        setContentView(R.layout.activity_cloud_score);
        Log.i("CloudScore 03", "OK");

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;

        textViewResult = findViewById(R.id.text_view_result);
        textViewResult.setText("");

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

        Log.i("CloudScore onCreate ", "OK");

        getToken();

    }

    private void getToken() {
        Login login = new Login( "admin", "admin");

        Call<Token> call = jsonPlaceHolderApi.getToken(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Token postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getToken() + "\n\n";

                token = "Token " + postResponse.getToken();
                Log.i("token ", token);

                textViewResult.setText(content);

                getScore();

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void getScore() {
        Log.i("getScore010 ", "OK");
        Log.i("token ", token);

        Call<List<ResultFromCloud>> call = jsonPlaceHolderApi.getScore(token);

        call.enqueue(new Callback<List<ResultFromCloud>>() {
            @Override
            public void onResponse(Call<List<ResultFromCloud>> call, Response<List<ResultFromCloud>> response) {
                Log.i("getScore108 ", "OK");
                if (response.isSuccessful()) {
                    Log.i("getScore110 ", "OK");

                    List<ResultFromCloud> results = response.body();
                    Collections.reverse(results);

                    for (ResultFromCloud result : results) {
                        String content = "";
                        content = content + "User: " + result.getUserName() + "\n";
                        content = content + "Id: " + result.getId() + "\n";
                        content = content + "Date, Time: " + result.getTime() + "\n";
                        content = content + "Time1: " + result.getTime1() + "\n";
                        content = content + "Time1: " + result.getTime2() + "\n";
                        content = content + "Time1: " + result.getTime3() + "\n";
                        content = content + "Time3: " + result.getTime4() + "\n\n";

                        textViewResult.append(content);
//                        textViewResult.setText(textViewResult.getText() + "\n\n" + "Time3= " + response.body().getTime3());
                        Log.i("getScore120 ", "OK");
                    }

//                    Toast.makeText(MainActivity.this, response.body().getTime3(), Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("getScore130 ", "OK");
                    Toast.makeText(CloudScore2.this, "token is not correct :(", Toast.LENGTH_SHORT).show();

                    textViewResult.setText(textViewResult.getText() + "\n\n" + "scoreNotSuccessCode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResultFromCloud>> call, Throwable t) {
                Log.i("getScore140 ", "OK");
                Toast.makeText(CloudScore2.this, "error :(", Toast.LENGTH_SHORT).show();

                textViewResult.setText(textViewResult.getText() + "\n\n" + "scoreFailureCode: " + t.getMessage());
            }
        });

    }




    //duplicite with MainActivity
    private String SecondsToTimeString(int secondsTotal){
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal % 60;

        String secondsString;
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        }
        else secondsString = String.valueOf(seconds);

        return String.valueOf(minutes) + ":" + secondsString;
    }

    //duplicite with MainActivity
    @SuppressLint("LongLogTag")
    private String SecondsFloatToTimeString(Float secondsTotal){
        String timeString = "";
        String secondsString = String.valueOf(secondsTotal);

        int pos = secondsString.indexOf(".");
        if (pos != -1) {
            timeString =  SecondsToTimeString(Integer.parseInt(secondsString.substring(0, pos))) + secondsString.substring(pos);
        } else {
            timeString = SecondsToTimeString(Integer.parseInt(secondsString));
        }

        return timeString;
    }


}