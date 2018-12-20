package org.elis.jp4application;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity implements FoodListAdapter.OnQuantityChange{

    TextView welcomeTW, emailTv, totalTextView;

    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    FoodListAdapter adapter;
    ProgressBar progressBar;
    int progress = 0;
    int total = 0;
    Button buy;
    ArrayList<Food> foods = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        recyclerView = findViewById(R.id.food_rv);
        welcomeTW = findViewById(R.id.welcome_tv);
        emailTv = findViewById(R.id.email_et);
        totalTextView = findViewById(R.id.total);



        layoutManager = new LinearLayoutManager(this);

        getProducts();

        progressBar = findViewById(R.id.progressBar);


        buy = findViewById(R.id.buy);
    }

    public void setProgressBar(){
        if(progress <= 15)
            progress = total;
        progressBar.setProgress(progress);
    }


    private void getProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://5ba19290ee710f0014dd764c.mockapi.io/Food";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Success",response);
                         try {
                            JSONObject responseJSON = new JSONObject(response);
                             JSONArray jsonArray = responseJSON.getJSONArray("food");

                             for(int i = 0; i<jsonArray.length();i++){
                                 Food food = new Food(jsonArray.getJSONObject(i));
                                 foods.add(food);
                             }
                             setFoodView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.getMessage());

            }
        });
        queue.add(stringRequest);


    }

    @Override
    public void onItemAdded(float price) {

        total += price;
        totalTextView.setText("Total :" + total);
        progressBar.setProgress(total);
        if(total>=15)
        buy.setEnabled(true);



    }

    @Override
    public void onItemRemoved(float price) {
        if (total == 0) return;
        total -= price;
        totalTextView.setText("Total :" + total);
         progressBar.setProgress(total);
        if(total<15)
            buy.setEnabled(false);


    }
 private void setFoodView(){

     adapter = new FoodListAdapter(this, foods);
     adapter.setOnQuantityChange(this);
     recyclerView.setLayoutManager(layoutManager);
     recyclerView.setAdapter(adapter);
    }
}