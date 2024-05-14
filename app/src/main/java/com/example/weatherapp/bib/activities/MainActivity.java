package com.example.weatherapp.bib.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.bib.Adapters.HourlyAdapter;
import com.example.weatherapp.bib.Domins.Hourly;
import com.example.weatherapp.bib.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter hourlyadapter;
    public RecyclerView recyclerView;
    String cities;
    TextView futurebutton;
    SearchView searchcity;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        futurebutton=findViewById(R.id.future_button);
        searchcity=findViewById(R.id.searchcity);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        cities=searchcity.getQuery().toString();

        initRecycleView();


        futurebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,FutureActivity.class));


            }
        });


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