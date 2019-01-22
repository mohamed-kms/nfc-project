package com.example.mohamed.tchololbane;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.tchololbane.Common.Common;
import com.example.mohamed.tchololbane.Database.Database;
import com.example.mohamed.tchololbane.Model.Order;
import com.example.mohamed.tchololbane.Model.Request;
import com.example.mohamed.tchololbane.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrice;
    Button btnValider;

    List<Order> carts = new ArrayList<>();

    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // database
        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        // Init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnValider = findViewById(R.id.btnPlaceOrder);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
        builder.setTitle("One more step!");
        builder.setMessage("Enter Table number: ");

        final EditText tableNumber = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        tableNumber.setLayoutParams(layoutParams);
        builder.setView(tableNumber);
        builder.setIcon(R.drawable.ic_action_cart);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // New Request
                Request req = new Request(Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        tableNumber.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        carts);

                // Firebase

                request.child(String.valueOf(System.currentTimeMillis())).setValue(req);

                // Empty cart after submit
                new Database(getBaseContext()).cleanCart();
                Intent intent = new Intent();
                intent.setAction("fr.mbds.bankapp.TRANSACTION");
                String lol = txtTotalPrice.getText().toString();
                lol = lol.substring(0, (lol.length() -2));
                lol = lol.replace(",", ".");
                intent.putExtra(Intent.EXTRA_TEXT, lol);
                intent.setType("text/plain");
                startActivity(intent);
                finish();
            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void loadListFood() {
        carts = new Database(this).getCarts();
        cartAdapter = new CartAdapter(carts, this);
        recyclerView.setAdapter(cartAdapter);

        // total Price
        int total = 0;
        for (Order order: carts){
            total += (Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("fr", "FR");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(format.format(total));

    }
}
