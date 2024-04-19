package com.example.weatherapp.bib.activities;

import android.os.Bundle;

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
    private RecyclerView recyclerView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initRecycleView();



    }

    private void initRecycleView() {
        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("9 am", 28, "cloudy"));
        items.add(new Hourly("12 am", 20, "rainy"));
        items.add(new Hourly("2 am", 30, "wind"));
        items.add(new Hourly("4 am", 23, "storm"));
        items.add(new Hourly("6 am", 29, "sun"));

        // Corrected line: Assign the result of findViewById to recyclerView
        recyclerView = findViewById(R.id.view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        hourlyadapter = new HourlyAdapter(items);
        recyclerView.setAdapter(hourlyadapter);
    }

}