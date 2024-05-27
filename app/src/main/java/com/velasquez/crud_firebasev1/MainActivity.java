package com.velasquez.crud_firebasev1;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    // Persona:
    Persona personaSelected;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Esto hace que al hacer clic sobre un elemento de la lista, se muestren sus datos en los campos de texto:
                personaSelected = (Persona) adapterView.getItemAtPosition(position);

                // para luego poder editarlos:
                editTextNombre.setText(personaSelected.getNombre());
                editTextApellido.setText(personaSelected.getApellido());
                editTextEmail.setText(personaSelected.getCorreo());
                editTextPassword.setText(personaSelected.getContrasena());


            }//Fin onItemClick
        });//Fin OnItemClickListener

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
            firebaseDatabase.setPersistenceEnabled(true);
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
                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPersona);
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
                    // Consulta para verificar si el correo ya existe en la base de datos
                    databaseReference.child("Persona").orderByChild("correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Si el correo ya existe, muestra un mensaje de error
                                Toast.makeText(MainActivity.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                // Si el correo no existe, agrega el nuevo objeto Persona a la base de datos
                                try {
                                    // Creación del objeto Persona
                                    Persona p = new Persona();
                                    p.setUid(UUID.randomUUID().toString()); // Generar un ID único
                                    p.setNombre(nombre);
                                    p.setApellido(apellido);
                                    p.setCorreo(correo);
                                    p.setContrasena(contraseña);

                                    // Agregar el objeto Persona a la base de datos
                                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                                } catch (Exception e) {
                                    Log.e("Firebase", "Error: " + e.getMessage());
                                }

                                Toast.makeText(MainActivity.this, "Dato agregado", Toast.LENGTH_SHORT).show();
                                limpiarCajas();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Manejo de errores
                            Log.e("Firebase", "Error: " + databaseError.getMessage());
                        }
                    });
                }
                break;
            case R.id.icon_save:
                Persona p = new Persona();

                // Esto hace que se actualice el objeto Persona con los datos que se ingresaron en las cajas de texto:
                // se obtiene cada elemento de la caja de texto y se asigna a la propiedad del objeto Persona:
                p.setUid(personaSelected.getUid());
                p.setNombre(editTextNombre.getText().toString().trim());
                p.setApellido(editTextApellido.getText().toString().trim());
                p.setCorreo(editTextEmail.getText().toString().trim());
                p.setContrasena(editTextPassword.getText().toString().trim());

                //Esto hace que se actualice el objeto Persona en la base de datos, a travez de la referencia a la tabla Persona y al ID del objeto Persona:
                databaseReference.child("Persona").child(p.getUid()).setValue(p);

                Toast.makeText(this, "Actualizado...", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            case R.id.icon_delete:
                p = new Persona();
                p.setUid(personaSelected.getUid());
                databaseReference.child("Persona").child(p.getUid()).removeValue();
                limpiarCajas();


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




