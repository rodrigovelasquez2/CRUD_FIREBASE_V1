package com.velasquez.crud_firebasev1;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.velasquez.crud_firebasev1.models.Persona;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;

    EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword;
    ListView listView;

    //    Firebase:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        //Encontrar componentes por ID:
        editTextNombre = findViewById(R.id.editText_nombre);
        editTextApellido = findViewById(R.id.editText_apellido);
        editTextEmail = findViewById(R.id.editText_correo);
        editTextPassword = findViewById(R.id.editText_password);
        listView = findViewById(R.id.listView_listaPesonas);


//        Inicializar Firebase:
        inicializarFirebase();
        listarDatosFirebase();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }//Fin oncreate

    private void inicializarFirebase() {
        try {
            FirebaseApp.initializeApp(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

        } catch (Exception e) {
            Log.e("Firebase", "Error" + e.getMessage());
        }
    }//Fin inicializarFirebase

    public void listarDatosFirebase() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Persona p = ds.getValue(Persona.class);
                    listPersona.add(p);

                    //Llenar el arrayu adapter:
                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1,listPersona);
                    listView.setAdapter(arrayAdapterPersona);
                }//Fin for
            }//Fin onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }//Fin  onCancelled
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }//Fin onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String correo = editTextEmail.getText().toString();
        String contraseña = editTextPassword.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("")) {
                    validacion();
                } else {

                    try {
                        //Creacion del objeto Persona:
                        Persona p = new Persona();
                        p.setUid(UUID.randomUUID().toString()); //Generar un ID unico
                        p.setNombre(nombre);
                        p.setApellido(apellido);
                        p.setCorreo(correo);
                        p.setContrasena(contraseña);

                        /**
                         * @param: databaseReference es la referencia a la base de datos de Firebase Realtime Database
                         * @param : p es el objeto Persona que se quiere agregar a la base de datos
                         * @param: p.getUid() es el ID que se genero para el objeto Persona
                         * @param: child(" Persona ") es la referencia a la tabla Persona de la base de datos, nodo hijo que estara dentro de la ubicacion actual de la base de datos
                         */


                        databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    } catch (Exception e) {
                        Log.e("Firebase", "Error" + e.getMessage());
                    }

                    Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.icon_save:
                Toast.makeText(this, "Guardar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_delete:
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }//Fin switch
        return super.onOptionsItemSelected(item);
    }//Fin onOptionsItemSelected

    private void limpiarCajas() {
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
    }//Fin limpiarCajas

    private void validacion() {
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String correo = editTextEmail.getText().toString();
        String contraseña = editTextPassword.getText().toString();

        if (nombre.equals("")) {
            editTextNombre.setError("Required");
        }//Fin if

        if (apellido.equals("")) {
            editTextApellido.setError("Required");
        }
        if (correo.equals("")) {
            editTextEmail.setError("Required");
        }
        if (contraseña.equals("")) {
            editTextPassword.setError("Required");
        }


    }//Fin validacion
}//Fin MainActivity




