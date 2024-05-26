package com.velasquez.crud_firebasev1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword;
    ListView listView;


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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }//Fin oncreate

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
                    break;
                } else {
                    Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                    break;
                }
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
        if(correo.equals("")) {
            editTextEmail.setError("Required");
        }
        if (contraseña.equals("")) {
            editTextPassword.setError("Required");
        }


    }//Fin validacion
}//Fin MainActivity




