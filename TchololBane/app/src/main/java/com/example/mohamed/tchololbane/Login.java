package com.example.mohamed.tchololbane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.tchololbane.Common.Common;
import com.example.mohamed.tchololbane.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Locale;

/**
 * Created by mohamed on 12/12/17.
 */

public class Login extends AppCompatActivity{
    MaterialEditText usrPhone, usrPassword;
    CardView sidentifier;
    TextView senregistrer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usrPhone = findViewById(R.id.identifiant);
        usrPassword = findViewById(R.id.motdepasse);
        sidentifier = findViewById(R.id.sidentifier);
        senregistrer = findViewById(R.id.senregistrer);
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        sidentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("En cours...");
                progressDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Vérifier si l'utilisateur n'existe pas dans la base de données
                        if (dataSnapshot.child(usrPhone.getText().toString()).exists()) {
                            Log.v("Login", "usrPhone existe");

                            progressDialog.dismiss();

                            // On va récupérer les informations de notre utilisateur
                            User user = dataSnapshot.child(usrPhone.getText().toString()).getValue(User.class);
                            user.setPhone(usrPhone.getText().toString());
                            if (user.getPassword().equals(usrPassword.getText().toString())) {
                                Intent homeIntent = new Intent(Login.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "L'utilisateur n'existe pas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        senregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enregistrement = new Intent(Login.this, SignUp.class);
                startActivity(enregistrement);
                finish();
            }
        });
    }
}
