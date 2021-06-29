package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudScore extends ListActivity implements ActionMode.Callback {
    int screenWidth;
    int screenHeihgt;
    private String token;
    private TextView textViewResult;
    protected Object mActionMode;
    public int selectedItem = -1;
    Parameters param;
    List<UserScore> userScores = new ArrayList<UserScore>();
    final String mode = "CLOUD";

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CloudScore01", "OK");
        super.onCreate(savedInstanceState);
        Log.i("CloudScore02", "OK");

        param = (Parameters)getIntent().getSerializableExtra("PARAMETERS");
        Log.i("CloudScore03", "OK");

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;

        initJsonPlaceHolderApi();

        getToken();

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
    private void getToken() {
//        Login login = new Login( "admin", "admin");
        Login login = new Login( param.getEmail(), param.getPassword());

        Call<Token> call = jsonPlaceHolderApi.getToken(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Token postResponse = response.body();

                token = "Token " + postResponse.getToken();
                Log.i("token ", token);

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
//                    Collections.reverse(results);

                    for (ResultFromCloud result : results) {

                        userScores.add(new UserScore(result.getId(), result.getUserName(), result.getTime(),
                                result.getTime1(), result.getTime2(), result.getTime3(), result.getTime4(), 0,
                                0, 0, 0, 0, 0,
                                result.getTime_max(), result.getTime_avg(), result.getTime_max2(), result.getTime_max3(),
                                true, 0, 0, 0, 0));

                        Log.i("getScore120 ", "OK");
                    }

                    MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(CloudScore.this, userScores, param, mode);
                    setListAdapter(adapter);

                    getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            if (mActionMode != null) {
                                return false;
                            }
                            selectedItem = position;

                            // Start the CAB using the ActionMode.Callback defined above
                            CloudScore.this.startActionMode(CloudScore.this);
                            view.setSelected(true);
                            return true;
                        }
                    });

//                    Toast.makeText(MainActivity.this, response.body().getTime3(), Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("getScore130 ", "OK");
                    Toast.makeText(CloudScore.this, "token is not correct :(", Toast.LENGTH_SHORT).show();

                    textViewResult.setText(textViewResult.getText() + "\n\n" + "scoreNotSuccessCode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResultFromCloud>> call, Throwable t) {
                Log.i("getScore140 ", "OK");
                Toast.makeText(CloudScore.this, "error :(", Toast.LENGTH_SHORT).show();

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


    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.rowselection, menu);
        return true;
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    // Called when the user selects a contextual menu item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem1_show:
//                show();
                // Action picked, so close the CAB
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    // Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        selectedItem = -1;
    }
}