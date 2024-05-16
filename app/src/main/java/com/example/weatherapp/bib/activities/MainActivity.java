package com.example.weatherapp.bib.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.bib.Adapters.HourlyAdapter;
import com.example.weatherapp.bib.Domins.Hourly;
import com.example.weatherapp.bib.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter hourlyadapter;
    public RecyclerView recyclerView;
    String cities;
    TextView futurebutton,apirespone,cloudsituation,datedayandTime,
    currentTemperature,humidityandL,rainPercentage,windPercentage,HumidityPercentage;
    SearchView searchcity;
    String urlFirst,urllast,url;
    RequestQueue requestQueue;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        futurebutton=findViewById(R.id.future_button);
        searchcity=findViewById(R.id.searchcity);
        apirespone=findViewById(R.id.apifetching);
        cloudsituation=findViewById(R.id.cloudsituation);
        datedayandTime=findViewById(R.id.Datedayandtime);
        currentTemperature =findViewById(R.id.currentTemperature);
        humidityandL=findViewById(R.id.humidityAndL);
        rainPercentage =findViewById(R.id.rainPercentage);
        windPercentage=findViewById(R.id.windpercentage);
        HumidityPercentage=findViewById(R.id.humidityPercentage);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);



        initRecycleView();


   searchcity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String query) {
           cities=searchcity.getQuery().toString().replace(" ","%20");
           apicalling();

           return false;
       }

       @Override
       public boolean onQueryTextChange(String newText) {
           return false;
       }
   });



        futurebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,FutureActivity.class));


            }
        });


    }

    private void apicalling() {

        //Api responses
        urlFirst="https://api.openweathermap.org/data/2.5/weather?q=";
        urllast="&appid=d95daa281ed9ebd8039495d5b6343512";

        url=urlFirst+cities+urllast;


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);



                    if (jsonObject.has("main")) {
                        JsonObject mainObject = jsonObject.getAsJsonObject("main");
                        JsonObject cloudobject = jsonObject.getAsJsonObject("clouds");
                        JsonObject cordinates = jsonObject.getAsJsonObject("coord");



                        //  Cordinates
                        double longitude = (cordinates.has("lon") ? cordinates.get("lon").getAsDouble() : 0.0);
                        double latitude = (cordinates.has("lat") ? cordinates.get("lat").getAsDouble() : 0.0);

//                        apirespone.setText(longitude+"\n"+ latitude);





                        double kelvin=273.15;
                        long date =  jsonObject.get("dt").getAsLong();

                        double temp = (mainObject.has("temp") ? mainObject.get("temp").getAsDouble() : 0.0)-kelvin;
                        double temp_feels = (mainObject.has("feels_like") ? mainObject.get("feels_like").getAsDouble() : 0.0)-kelvin;
                        double temp_min = (mainObject.has("temp_min") ? mainObject.get("temp_min").getAsDouble() : 0.0)-kelvin;
                        double temp_max = (mainObject.has("temp_max") ? mainObject.get("temp_max").getAsDouble() : 0.0)-kelvin;
                        int pressure = (mainObject.has("pressure") ? mainObject.get("pressure").getAsInt() : 0);
                        int humidity = (mainObject.has("humidity") ? mainObject.get("humidity").getAsInt() : 0);
                        int visibility = (mainObject.has("visibility") ? mainObject.get("visibility").getAsInt() : 0);

                        double cloudlyper=(cloudobject.has("all") ? cloudobject.get("all").getAsDouble():0.0);


                        interigatingallLocal( temp,temp_feels,temp_min,temp_max,pressure,humidity,visibility,cloudlyper);

//                        apirespone.setText(mainObject.toString());
//                        apirespone.setText(temp +"\n"+temp_feels+"\n"+temp_min+"\n"+temp_max+"\n"+pressure+"\n"+humidity+"\n"+visibility+"\n"+ date +"\n"+cloudlyper);
//                    Log.d("Response", response);


                        hourlyUpdatesCurrentDay(longitude,latitude);

                    } else {
                        apirespone.setText("No 'main' object found in the response");
                    }

                } catch (Exception e) {
                    apirespone.setText("Error fetching data: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Cannot fetch the data", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(stringRequest);




    }

    @SuppressLint("SetTextI18n")
    private void hourlyUpdatesCurrentDay(double longitude, double latitude){
//        apirespone.setText(longitude+"\n"+ latitude);

        String url="https://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&appid=d95daa281ed9ebd8039495d5b6343512";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {

                    JsonObject jsonObject= new Gson().fromJson(s,JsonObject.class);

                    if(jsonObject.has("list")){

                        JsonArray listJsonhours= jsonObject.getAsJsonArray("list");
                        JsonObject firsthour=listJsonhours.get(0).getAsJsonObject();
                        JsonObject cloudobject = firsthour.getAsJsonObject("clouds");


                        long unitimeStamp =  firsthour.get("dt").getAsLong();
                        int cloudper=cloudobject.has("all") ? cloudobject.get("all").getAsInt():0;
                        Date date = new Date(unitimeStamp * 1000L);
                        // Specify the desired date format (including AM/PM)
                        SimpleDateFormat sdf = new SimpleDateFormat("hh a", Locale.getDefault());

                        // Format the date
                        String formattedDate = sdf.format(date);








                        double kelvin=273.15;
                      if(firsthour.has("main")){

                          JsonObject firstmain= firsthour.get("main").getAsJsonObject();
                          int temp= (int) ((firstmain.get("temp").getAsInt())-kelvin);

                          apirespone.setText(formattedDate +"\n"+cloudper+"\n"+temp);

                      }else {
                          apirespone.setText("Firstmain error");
                      }

                    }else {

                        apirespone.setText("list not found");
                    }

                }catch (Exception e){
                    apirespone.setText("Error fetching data: "+ e.getMessage());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Cannot fetch the data :2nd String request", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }





    private void interigatingallLocal(double temp, double tempFeels, double tempMin, double tempMax, int pressure, int humidity, int visibility,Double cloudper) {

        String cloudDetails=(cloudper <=10)? "Mostly clear":
                (cloudper <=20) ? "Few clouds":
                        (cloudper <=40) ? "Partly cloudy":
                                (cloudper <=70) ? "Mostly cloudy":
                                        "Overcast or Cloudy";


        cloudsituation.setText(cloudDetails);
        currentTemperature.setText((String.valueOf(Math.round(temp))+"°C"));
        rainPercentage.setText(String.valueOf(Math.round(cloudper)+"%"));
        humidityandL.setText("H:"+String.valueOf(humidity)+"%");
        HumidityPercentage.setText(String.valueOf(humidity)+"%");



    }



        private void initRecycleView() {
            ArrayList<Hourly> items = new ArrayList<>();

            items.add(new Hourly("9 am", 28, "suncloud"));
            items.add(new Hourly("12 am", 20, "windrate"));
            items.add(new Hourly("2 am", 30, "raining"));
            items.add(new Hourly("4 am", 23, "humidity"));
            items.add(new Hourly("6 am", 29, "sunandcloyd"));

            // Corrected line: Assign the result of findViewById to recyclerView
            recyclerView = findViewById(R.id.view1);

            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            hourlyadapter = new HourlyAdapter(items);
            recyclerView.setAdapter(hourlyadapter);
    }

}