package com.example.mohamed.tchololbane;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.tchololbane.Common.Common;
import com.example.mohamed.tchololbane.Interface.ItemClickListener;
import com.example.mohamed.tchololbane.Model.Categories;
import com.example.mohamed.tchololbane.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference category;
    FirebaseRecyclerOptions categoryOptions;
    TextView userFullName;
    FirebaseRecyclerAdapter<Categories, MenuViewHolder> recyclerAdapter;

    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Let's initialise Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        category = firebaseDatabase.getReference("Categories");  // Peut etre utiliser getReference().Child("Categories") ici
        Query categoryQuery = category.orderByKey();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(Home.this, Cart.class);
                startActivity(cart);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Let's set the User Name
        View headerView = navigationView.getHeaderView(0);
        userFullName = headerView.findViewById(R.id.userFullName);

        userFullName.setText(Common.currentUser.getName());

        // Load Menu
        recyclerMenu = (RecyclerView)findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);

        categoryOptions = new FirebaseRecyclerOptions.Builder<Categories>()
                .setQuery(categoryQuery, Categories.class)
                .build();

        loadRecyclerMenu();
    }

    private void loadRecyclerMenu() {
        //FirebaseRecyclerAdapter<Categories, MenuViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Categories, MenuViewHolder>(Categories.class, R.layout.menu_items, MenuViewHolder.class, category) {
        recyclerAdapter = new FirebaseRecyclerAdapter<Categories, MenuViewHolder>(categoryOptions) {

            // onCreateViewHolder is where the row layout gets inflated.
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items, parent, false);
                return new MenuViewHolder(view);
            }

            // bindViewHolder() is where you populate the row with your model data fetched from Firebase.
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Categories model) {
                viewHolder.textMenuName.setText(model.getNom());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);

                final Categories clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this, ""+clickItem.getNom(), Toast.LENGTH_SHORT).show();
                        // Get the categorie and send it to new Activity
                        Intent foodList = new Intent(Home.this, FoodList.class);
                        // We use categoryId as key, so we'll take the position
                        foodList.putExtra("CategoryId", recyclerAdapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };

        recyclerMenu.setAdapter(recyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_panier) {
            Intent panier = new Intent(Home.this, Cart.class);
            startActivity(panier);
            finish();
        } else if (id == R.id.nav_commandes) {

        } else if (id == R.id.nav_deconnexion) {
            Intent main = new Intent(Home.this, MainActivity.class);
            startActivity(main);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
