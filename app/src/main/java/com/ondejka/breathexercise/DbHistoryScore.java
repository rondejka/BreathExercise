package com.ondejka.breathexercise;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DbHistoryScore extends ListActivity implements ActionMode.Callback {

    protected Object mActionMode;
    public int selectedItem = -1;
    Parameters param;
    List<UserScore> userScores = new ArrayList<UserScore>();
    final String mode = "LOCAL";

    DbHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("DbHistoryScore 01", "OK");

        myDb = new DbHelper(this);
        Log.i("DbHistoryScore 02", "OK");

        param = (Parameters)getIntent().getSerializableExtra("PARAMETERS");
        Log.i("DbHistoryScore 03", "OK");

        Cursor res = myDb.getAllData();
        Log.i("DbHistoryScore 05", "OK");

        if (res.getCount() == 0) {
//            showMessage("Error", "No data found");
            return;
        }

        while (res.moveToNext()) {

            userScores.add(new UserScore(res.getInt(0), res.getString(1), res.getLong(2),
                        res.getInt(3), res.getInt(4), res.getInt(5), res.getInt(6), 0,
                        0, 0, 0, 0, 0,
                        res.getInt(7), res.getFloat(8), res.getFloat(9),res.getFloat(10),
                    res.getString(11).equals("True"), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15)));

            Log.i("DbHistoryScore 11", "OK");

        }
        //        Log.i("DbHistory 025, SIZE = ", String.valueOf(userScores.size()));


        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, userScores, param, mode);
        setListAdapter(adapter);
        Log.i("DbHistoryScore 030", "OK");

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (mActionMode != null) {
                    return false;
                }
                selectedItem = position;

                // Start the CAB using the ActionMode.Callback defined above
                DbHistoryScore.this.startActionMode(DbHistoryScore.this);
                view.setSelected(true);
                return true;
            }
        });

//        setContentView(R.layout.activity_db_history_score);
        Log.i("DbHistoryScore 040", "OK");

//        initView();
//        loadData();
//        createColumns();

//        fillData(userScores);

    }

    private void show() {
        Toast.makeText(DbHistoryScore.this, String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        // Assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.rowselection, menu);
        return true;
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }

    // Called when the user selects a contextual menu item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem1_show:
                show();
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









    private void initView() {
//        Log.i("initView 01", "OK");
//        tableLayout = (TableLayout) findViewById(R.id.tableLayoutProduct);
//        Log.i("initView 02", "OK");
    }

    private void loadData() {
        List<UserScore> userScores = new ArrayList<UserScore>();
//        userScores.add(new UserScore(1, "robo", 10122012, 105989, 130, 140, 150, 160, 160, 145, false));
//        userScores.add(new UserScore(2, "hela", 1, 1, 1, 1, 1, 1, 1, 1, false));
//        userScores.add(new UserScore(3, "ondejka@hotmail.com", 1, 1, 1, 1, 1, 1, 1, 1, false));
//        userScores.add(new UserScore(4, "ondejkova@hotmail.com", 1, 1, 1, 1, 1, 1, 1, 1, false));

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
//            showMessage("Error", "No data found");
            return;
        }

        while (res.moveToNext()) {

            userScores.add(new UserScore(res.getInt(0), res.getString(1), res.getLong(2),
                    res.getInt(3), res.getInt(4), res.getInt(5), res.getInt(6), 0,
                    0, 0, 0, 0, 0,
                    res.getInt(7), res.getFloat(8), res.getFloat(9),res.getFloat(10),
                    res.getString(11).equals("True"), res.getInt(12), res.getInt(13), res.getInt(14), res.getInt(15)));


        }


    }

    private void createColumns() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Id Column
        TextView textViewId = new TextView(this);
        textViewId.setText("Id");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewId);

        // Name Column
        TextView textViewName = new TextView(this);
        textViewName.setText("Name");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Date Column
        TextView textViewDate = new TextView(this);
        textViewDate.setText("Date");
        textViewDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewDate.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewDate);

        // Time Column
        TextView textViewTime = new TextView(this);
        textViewTime.setText("Time\n");
        textViewTime.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime);

        // Time1 Column
        TextView textViewTime1 = new TextView(this);
        textViewTime1.setText("Time1");
        textViewTime1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime1.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime1);

        // Time2 Column
        TextView textViewTime2 = new TextView(this);
        textViewTime2.setText("Time2");
        textViewTime2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime2.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime2);

        // Time Column
        TextView textViewTime3 = new TextView(this);
        textViewTime3.setText("Time4");
        textViewTime3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime3.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime3);

        // Time Column
        TextView textViewTime4 = new TextView(this);
        textViewTime4.setText("Time4");
        textViewTime4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime4.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime4);

//        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
//                TableRow.LayoutParams.FILL_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));

        // Add Divider
        //-----------------------------------------
        tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Id Column
        textViewId = new TextView(this);
        textViewId.setText("-----------");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewId);

        // Name Column
        textViewName = new TextView(this);
        textViewName.setText("-----------");
        textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewName.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewName);

        // Date Column
        textViewDate = new TextView(this);
        textViewDate.setText("-----------");
        textViewDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewDate.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewDate);

        // Time Column
        textViewTime = new TextView(this);
        textViewTime.setText("---------------\n");
        textViewTime.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime);

        // Time Column
        textViewTime1 = new TextView(this);
        textViewTime1.setText("-------------------------");
        textViewTime1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime1.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime1);

        // Time Column
        textViewTime2 = new TextView(this);
        textViewTime2.setText("-------------------------");
        textViewTime2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime2.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime2);

        // Time Column
        textViewTime3 = new TextView(this);
        textViewTime3.setText("-------------------------");
        textViewTime3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime3.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime3);

        // Time Column
        textViewTime4 = new TextView(this);
        textViewTime4.setText("-------------------------");
        textViewTime4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTime4.setPadding(5, 5, 5, 0);
        tableRow.addView(textViewTime4);

//        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
//                TableRow.LayoutParams.FILL_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));

    }

    private void fillData(List<UserScore> userScores) {
        Log.i("fillData 01", "OK");

//        int i = userScores.get(1).getId();
//        Log.i("fillData 012 - i =", String.valueOf(i));

        for (UserScore userScore : userScores) {
            Log.i("fillData 02", "OK");


            TableRow tableRow = new TableRow(this);

            Log.i("fillData 03", "OK");
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            Log.i("fillData 04", "OK");

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("fillDataonClick 01", "OK");
                    TableRow currentRow = (TableRow) view;

                    Log.i("fillDataonClick 02", "OK");
                    TextView textViewId = (TextView) currentRow.getChildAt(0);

                    Log.i("fillDataonClick 03", "OK");
                    String id = textViewId.getText().toString();

                    Log.i("fillDataonClick 04", "OK");
                    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                }
            });

            Log.i("fillData 05", "OK");
            // Id Column
            TextView textViewId = new TextView(this);
            Log.i("fillData 051", "OK");
            int k = userScore.getId();
            Log.i("fillData 0511", String.valueOf(k));
            textViewId.setText(String.valueOf(k));
            Log.i("fillData 052", "OK");
            textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Log.i("fillData 053", "OK");
            textViewId.setPadding(5, 5, 5, 0);
            Log.i("fillData 054", "OK");
            tableRow.addView(textViewId);
            Log.i("fillData 055", "OK");

            Log.i("fillData 06", "OK");
            // Name Column
            TextView textViewName = new TextView(this);
            textViewName.setText(String.valueOf(userScore.getUserName()));
            textViewName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewName.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewName);

            Log.i("fillData 07", "OK");
            // Date Column
            TextView textViewDate = new TextView(this);
            textViewDate.setText(String.valueOf(userScore.getDateTime()));
            textViewDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewDate.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewDate);

            Log.i("fillData 08", "OK");
            // Time Column
            TextView textViewTime = new TextView(this);
            textViewTime.setText(String.valueOf(userScore.getDateTime()));
            textViewTime.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTime.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewTime);

            Log.i("fillData 09", "OK");
            // Time Column
            TextView textViewTime1 = new TextView(this);
            textViewTime1.setText(String.valueOf(userScore.getTime1()));
            textViewTime1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTime1.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewTime1);

            Log.i("fillData 10", "OK");
            // Time Column
            TextView textViewTime2 = new TextView(this);
            textViewTime2.setText(String.valueOf(userScore.getTime2()));
            textViewTime2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTime2.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewTime2);

            Log.i("fillData 11", "OK");
            // Time Column
            TextView textViewTime3 = new TextView(this);
            textViewTime3.setText(String.valueOf(userScore.getTime3()));
            textViewTime3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTime3.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewTime3);

            Log.i("fillData 12", "OK");
            // Time Column
            TextView textViewTime4 = new TextView(this);
            textViewTime4.setText(String.valueOf(userScore.getTime4()));
            textViewTime4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewTime4.setPadding(5, 5, 5, 0);
            tableRow.addView(textViewTime4);

            // Photo Column
//            ImageView imageViewPhoto = new ImageView(this);
//            imageViewPhoto.setImageResource(product.getPhoto());
//            tableRow.addView(imageViewPhoto);

//            Log.i("fillData 20", "OK");
//            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
//                    TableRow.LayoutParams.FILL_PARENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
            Log.i("fillData 22", "OK");
        }
    }


}
