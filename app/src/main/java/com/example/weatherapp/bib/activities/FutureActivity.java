package com.example.weatherapp.bib.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.bib.Adapters.FutureAdapters;
import com.example.weatherapp.bib.Domins.FutureDomains;
import com.example.weatherapp.bib.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity {
    private RecyclerView.Adapter recyclerViewadapterfuture;
    public  RecyclerView recyclerView;
    ImageView backtomainbtn;
    TextView apirespone;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_future);
        backtomainbtn=findViewById(R.id.backbuttonfuture);
        apirespone=findViewById(R.id.apiresponse);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //Api responses
        url="https://jsonplaceholder.typicode.com/todos/1";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
                    JSONObject jsonObject = new JSONObject(response);

                    // Extract data from the JSON object
                    int userId = jsonObject.getInt("userId");
                    int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("title");
                    boolean completed = jsonObject.getBoolean("completed");

                    // Display the extracted data
                    apirespone.setText("userId: " + userId + "\n" +
                            "id: " + id + "\n" +
                            "title: " + title + "\n" +
                            "completed: " + completed);
                } catch (JSONException e) {
                    apirespone.setText("Error parsing JSON data");
                } catch (Exception e) {
                    apirespone.setText("Error fetching data");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FutureActivity.this, "Cannot fetch the data", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);





        initrecycleview();
        backtomainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(FutureActivity.this,MainActivity.class));
            }
        });




    }

    private void initrecycleview() {
        ArrayList<FutureDomains> items= new ArrayList<>();

        items.add(new FutureDomains("Mon","suncloud","suncloud",23,10));
        items.add(new FutureDomains("Tue","suncloud","suncloud",30,15));
        items.add(new FutureDomains("Wed","suncloud","suncloud",32,10));
        items.add(new FutureDomains("Thu","suncloud","suncloud",23,9));
        items.add(new FutureDomains("Fri","suncloud","suncloud",27,12));
        items.add(new FutureDomains("Sat","suncloud","suncloud",21, 17));

        recyclerView=findViewById(R.id.view2future);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerViewadapterfuture=new FutureAdapters(items);
        recyclerView.setAdapter(recyclerViewadapterfuture);


    }
}