package com.example.mohamed.tchololbane;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohamed.tchololbane.Interface.ItemClickListener;
import com.example.mohamed.tchololbane.Model.Food;
import com.example.mohamed.tchololbane.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

public class FoodList extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference foodList;
    FirebaseRecyclerOptions foodOptions;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    String categoryId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // instanciate Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        foodList = firebaseDatabase.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // We get the Intent here
        if (getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if (!categoryId.isEmpty() && categoryId != null){
            Log.d("MenuId = ", ""+ foodList.child("MenuId"));
            Query foodQuery = foodList.orderByChild("MenuId").equalTo(categoryId);
//            Assert.assertEquals(foodList.child("MenuId"), categoryId);
            foodOptions = new FirebaseRecyclerOptions.Builder<Food>()
                    .setQuery(foodQuery, Food.class)
                    .build();
            Log.d("FOOD OPTIONS", "" + foodOptions.toString());
            loadFoodList();
        }

    }

    private void loadFoodList() {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(foodOptions) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d("ON CREATE VIEW HOLDER", "JE SUIS DANS LE ON CREATE VIEW HOLDER");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, final int position, @NonNull Food model) {
                Log.d("ON BIND VIEW HOLDER", "JE SUIS DANS LE ON BIND VIEW HOLDER");
                holder.food_name.setText(model.getName());
                Log.d("MODEL NAME", ""+model.getName());
                //Toast.makeText(FoodList.this, ""+model.getName(), Toast.LENGTH_SHORT).show();
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.food_image);

                final Food clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        // Start details activity
                        Intent foodDetails = new Intent(FoodList.this, FoodDetails.class);
                        foodDetails.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetails);
                    }
                });
            }
        };

        // Set the Adapter
        Log.d("TAG", ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
