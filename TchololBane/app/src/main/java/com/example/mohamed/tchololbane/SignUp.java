package com.example.mohamed.tchololbane;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.tchololbane.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText usrPhone, usrPassword, usrName, usrMailto;
    CardView signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usrName = (EditText) findViewById(R.id.usrName);
        usrPhone = (EditText) findViewById(R.id.usrPhone);
        usrPassword = (EditText) findViewById(R.id.usrPassword);
        usrMailto = (EditText) findViewById(R.id.usrMailto);
        signUp = (CardView) findViewById(R.id.signUp);

        // Initialisation de la base de données
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("En cours...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // On vérifie s'il n'est pas déjà dans la base de données
                        if (dataSnapshot.child(usrPhone.getText().toString()).exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Un utilisateur avec ce numero de téléphone est déja enregistré", Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            User user = new User(usrName.getText().toString(), usrMailto.getText().toString(), usrPassword.getText().toString());
                            table_user.child(usrPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
