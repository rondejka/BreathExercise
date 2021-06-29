package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MySimpleArrayAdapter extends ArrayAdapter<UserScore> {
    private final Context context;
    Parameters param;
    private final String mode;

//    private final UserScore[] userScores;
    private final List<UserScore> userScores;

    public MySimpleArrayAdapter(Context context, List<UserScore> userScores1, Parameters param, String mode) {
        super(context, R.layout.rowlayout, userScores1);
        this.context = context;
        this.userScores = userScores1;
        this.param = param;
        this.mode = mode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int pos;
        pos = userScores.size() - position - 1;

        // ID: 0=not used, 1=top ever, 2=best year, 3=best half y., 4=best q., 5=best month, 6=best week
        // value: 0=no trophy, 1=trophy exists
        int[] trophies = new int[] {0, 0, 0, 0, 0, 0, 0};

        View rowView = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView userTextView = (TextView) rowView.findViewById(R.id.userTextView);
        TextView dateTextView = (TextView) rowView.findViewById(R.id.dateTextView);
        TextView time1TextView = (TextView) rowView.findViewById(R.id.time1TextView);
        TextView time2TextView = (TextView) rowView.findViewById(R.id.time2TextView);
        TextView time3TextView = (TextView) rowView.findViewById(R.id.time3TextView);
        TextView time4TextView = (TextView) rowView.findViewById(R.id.time4TextView);
        TextView max4TimeTextView = (TextView) rowView.findViewById(R.id.max4TimeTextView);
        TextView max3TimeTextView = (TextView) rowView.findViewById(R.id.max3TimeTextView);
        TextView max2TimeTextView = (TextView) rowView.findViewById(R.id.max2TimeTextView);
        TextView max1TimeTextView = (TextView) rowView.findViewById(R.id.max1TimeTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView trophy1ImageView = (ImageView) rowView.findViewById(R.id.trophy1ImageView);
        ImageView trophy2ImageView = (ImageView) rowView.findViewById(R.id.trophy2ImageView);
        ImageView trophy3ImageView = (ImageView) rowView.findViewById(R.id.trophy3ImageView);
        ImageView trophy4ImageView = (ImageView) rowView.findViewById(R.id.trophy4ImageView);

        ImageView trophy11ImageView = (ImageView) rowView.findViewById(R.id.trophy11ImageView);
        TextView trophy11descrTextView = (TextView) rowView.findViewById(R.id.trophy11descrTextView);
        ImageView trophy22ImageView = (ImageView) rowView.findViewById(R.id.trophy22ImageView);
        TextView trophy22descrTextView = (TextView) rowView.findViewById(R.id.trophy22descrTextView);
        ImageView trophy33ImageView = (ImageView) rowView.findViewById(R.id.trophy33ImageView);
        TextView trophy33descrTextView = (TextView) rowView.findViewById(R.id.trophy33descrTextView);
        ImageView trophy44ImageView = (ImageView) rowView.findViewById(R.id.trophy44ImageView);
        TextView trophy44descrTextView = (TextView) rowView.findViewById(R.id.trophy44descrTextView);

        String pattern = "d MMM yyyy";
        String pattern2 = "HH:mm";
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(pattern2);

//        String date;
//        date = String.valueOf(userScores.get(pos).getDateTime());
//        Log.i("dateOrig = ", date);

//        date = date.substring(0, date.length()-1);
//        Log.i("dateSubstr = ", date);

//        long dateLong = Long.parseLong(date);

        String dateDate = simpleDateFormatDate.format(userScores.get(pos).getDateTime());
        String dateTime = simpleDateFormatTime.format(userScores.get(pos).getDateTime());

//        Log.i("pos = ", String.valueOf(pos));
//        Log.i("datum = ", String.valueOf(userScores.get(pos).getDateTime()));
//        Log.i("date = ", date);
//        Log.i("dateFormat = ", simpleDateFormatDate.format(dateLong));

        String userName = bringUserName(userScores.get(pos).getUserName());
        userTextView.setText(userName);

        dateTextView.setText(dateDate + " at " + dateTime);


        time1TextView.setText(SecondsToTimeString(userScores.get(pos).getTime1()));
        time2TextView.setText(SecondsToTimeString(userScores.get(pos).getTime2()));
        time3TextView.setText(SecondsToTimeString(userScores.get(pos).getTime3()));
        time4TextView.setText(SecondsToTimeString(userScores.get(pos).getTime4()));

        max4TimeTextView.setText(SecondsFloatToTimeString(userScores.get(pos).getTimeAvg()));
        max3TimeTextView.setText(SecondsFloatToTimeString(userScores.get(pos).getTimeMax3()));
        max2TimeTextView.setText(SecondsFloatToTimeString(userScores.get(pos).getTimeMax2()));
        max1TimeTextView.setText(SecondsToTimeString(userScores.get(pos).getTimeMax()));


        int i;
        Log.i("getView_100", "OK");

//        if (userScores.get(pos).getTrophy1() == 1) {
//            trophy4ImageView.setImageResource(R.drawable.trophy1);
//        }
//
//        if (userScores.get(pos).getTrophy2() == 1) {
//            trophy1ImageView.setImageResource(R.drawable.trophy1);
//        }
//
//        if (userScores.get(pos).getTrophy3() == 1) {
//            trophy3ImageView.setImageResource(R.drawable.trophy1);
//        }
//
//        if (userScores.get(pos).getTrophy4() == 1) {
//            trophy2ImageView.setImageResource(R.drawable.trophy1);
//        }

        switch(userScores.get(pos).getTrophy1()) {
            case 1:
                trophy4ImageView.setImageResource(R.drawable.trophy1);
                trophies[1] = 1;
                break;
            case 2:
                trophy4ImageView.setImageResource(R.drawable.trophy1);
                trophies[2] = 1;
                break;
            case 3:
                trophy4ImageView.setImageResource(R.drawable.trophy3);
                trophies[2] = 1;
                break;
            case 4:
                trophy4ImageView.setImageResource(R.drawable.trophy4);
                trophies[4] = 1;
                break;
            case 5:
                trophy4ImageView.setImageResource(R.drawable.trophy5);
                trophies[5] = 1;
                break;
            case 6:
                trophy4ImageView.setImageResource(R.drawable.trophy6);
                trophies[6] = 1;
                break;
            default:
                // code block
        }
        switch(userScores.get(pos).getTrophy2()) {
            case 1:
                trophy1ImageView.setImageResource(R.drawable.trophy1);
                trophies[1] = 1;
                break;
            case 2:
                trophy1ImageView.setImageResource(R.drawable.trophy1);
                trophies[2] = 1;
                break;
            case 3:
                trophy1ImageView.setImageResource(R.drawable.trophy3);
                trophies[3] = 1;
                break;
            case 4:
                trophy1ImageView.setImageResource(R.drawable.trophy4);
                trophies[4] = 1;
                break;
            case 5:
                trophy1ImageView.setImageResource(R.drawable.trophy5);
                trophies[5] = 1;
                break;
            case 6:
                trophy1ImageView.setImageResource(R.drawable.trophy6);
                trophies[6] = 1;
                break;
            default:
                // code block
        }
        switch(userScores.get(pos).getTrophy3()) {
            case 1:
                trophy3ImageView.setImageResource(R.drawable.trophy1);
                trophies[1] = 1;
                break;
            case 2:
                trophy3ImageView.setImageResource(R.drawable.trophy1);
                trophies[2] = 1;
                break;
            case 3:
                trophy3ImageView.setImageResource(R.drawable.trophy3);
                trophies[3] = 1;
                break;
            case 4:
                trophy3ImageView.setImageResource(R.drawable.trophy4);
                trophies[4] = 1;
                break;
            case 5:
                trophy3ImageView.setImageResource(R.drawable.trophy5);
                trophies[5] = 1;
                break;
            case 6:
                trophy3ImageView.setImageResource(R.drawable.trophy6);
                trophies[6] = 1;
                break;
            default:
                // code block
        }
        switch(userScores.get(pos).getTrophy4()) {
            case 1:
                trophy2ImageView.setImageResource(R.drawable.trophy1);
                trophies[1] = 1;
                break;
            case 2:
                trophy2ImageView.setImageResource(R.drawable.trophy1);
                trophies[2] = 1;
                break;
            case 3:
                trophy2ImageView.setImageResource(R.drawable.trophy3);
                trophies[3] = 1;
                break;
            case 4:
                trophy2ImageView.setImageResource(R.drawable.trophy4);
                trophies[4] = 1;
                break;
            case 5:
                trophy2ImageView.setImageResource(R.drawable.trophy5);
                trophies[5] = 1;
                break;
            case 6:
                trophy2ImageView.setImageResource(R.drawable.trophy6);
                trophies[6] = 1;
                break;
            default:
                // code block
        }

        int trophiestCount = 0;
        if (trophies[1] == 1) {
            trophy11ImageView.setImageResource(R.drawable.trophy1);
            trophy11descrTextView.setText("best \nEVER");
            trophiestCount++;
        }
        if (trophies[2] == 1) {
            if (trophiestCount == 0) {
                trophy11ImageView.setImageResource(R.drawable.trophy2);
                trophy11descrTextView.setText("best of \nYEAR");
                trophiestCount++;
            } else {
                trophy22ImageView.setImageResource(R.drawable.trophy2);
                trophy22descrTextView.setText("best of \nYEAR");
                trophiestCount++;
            }
        }
        if (trophies[3] == 1) {
            if (trophiestCount == 0) {
                trophy11ImageView.setImageResource(R.drawable.trophy3);
                trophy11descrTextView.setText("best of \nHALF Y.");
                trophiestCount++;
            } else if (trophiestCount == 1) {
                trophy22ImageView.setImageResource(R.drawable.trophy3);
                trophy22descrTextView.setText("best of \nHALF Y.");
                trophiestCount++;
            } else {
                trophy33ImageView.setImageResource(R.drawable.trophy3);
                trophy33descrTextView.setText("best of \nHALF Y.");
                trophiestCount++;
            }
        }
        if (trophies[4] == 1) {
            if (trophiestCount == 0) {
                trophy11ImageView.setImageResource(R.drawable.trophy4);
                trophy11descrTextView.setText("best of \nQUARTER");
                trophiestCount++;
            } else if (trophiestCount == 1) {
                trophy22ImageView.setImageResource(R.drawable.trophy4);
                trophy22descrTextView.setText("best of \nQUARTER");
                trophiestCount++;
            } else if (trophiestCount == 2) {
                trophy33ImageView.setImageResource(R.drawable.trophy4);
                trophy33descrTextView.setText("best of \nQUARTER");
                trophiestCount++;
            } else {
                trophy44ImageView.setImageResource(R.drawable.trophy4);
                trophy44descrTextView.setText("best of \nQUARTER");
                trophiestCount++;
            }
        }
        if (trophies[5] == 1) {
            if (trophiestCount == 0) {
                trophy11ImageView.setImageResource(R.drawable.trophy5);
                trophy11descrTextView.setText("best of \nMONTH");
                trophiestCount++;
            } else if (trophiestCount == 1) {
                trophy22ImageView.setImageResource(R.drawable.trophy5);
                trophy22descrTextView.setText("best of \nMONTH");
                trophiestCount++;
            } else if (trophiestCount == 2) {
                trophy33ImageView.setImageResource(R.drawable.trophy5);
                trophy33descrTextView.setText("best of \nMONTH");
                trophiestCount++;
            } else {
                trophy44ImageView.setImageResource(R.drawable.trophy5);
                trophy44descrTextView.setText("best of \nMONTH");
                trophiestCount++;
            }
        }
        if (trophies[6] == 1) {
            if (trophiestCount == 0) {
                trophy11ImageView.setImageResource(R.drawable.trophy6);
                trophy11descrTextView.setText("best of \nWEEK");
            } else if (trophiestCount == 1) {
                trophy22ImageView.setImageResource(R.drawable.trophy6);
                trophy22descrTextView.setText("best of \nWEEK");
            } else if (trophiestCount == 2) {
                trophy33ImageView.setImageResource(R.drawable.trophy6);
                trophy33descrTextView.setText("best of \nWEEK");
            } else {
                trophy44ImageView.setImageResource(R.drawable.trophy6);
                trophy44descrTextView.setText("best of \nWEEK");
            }
        }

        // Change the icon for man or woman
        String g = param.getGender();
        if (g.startsWith("W")) {
            imageView.setImageResource(R.drawable.girl);
        } else {
            imageView.setImageResource(R.drawable.boy);
        }

        return rowView;
    }

    private String bringUserName (String email){

        String e1 = String.valueOf(param.getEmail());
        String e2 = String.valueOf(email);

        if (e1.equals(e2)){
            Log.i("bringUserName 03", "OK");
            return param.getFirstName() + " " + param.getLastName();
        }else {
            Log.i("bringUserName 04", "OK");
            return email;
        }
    }

    //duplicite with HistoryScore
    public String SecondsToTimeString(int secondsTotal){
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal % 60;

        String secondsString;
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        }
        else secondsString = String.valueOf(seconds);

        return String.valueOf(minutes) + ":" + secondsString;
    }

    //duplicite with HistoryScore
    @SuppressLint("LongLogTag")
    public String SecondsFloatToTimeStringDecimal(Float secondsTotal){
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

    //duplicite with HistoryScore
    @SuppressLint("LongLogTag")
    public String SecondsFloatToTimeString(Float secondsTotal){
        String timeString = "";
        String secondsString = String.valueOf(secondsTotal);

        int pos = secondsString.indexOf(".");
        if (pos != -1) {
            timeString =  SecondsToTimeString(Integer.parseInt(secondsString.substring(0, pos)));
        } else {
            timeString = SecondsToTimeString(Integer.parseInt(secondsString));
        }

        return timeString;
    }


}
