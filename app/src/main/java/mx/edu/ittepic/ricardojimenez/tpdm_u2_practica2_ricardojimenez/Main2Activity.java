package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText nom, tel, fe,dir;
    Button insert,regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nom = findViewById(R.id.insertaNombre);
        tel = findViewById(R.id.insertaTelefono);
        fe = findViewById(R.id.insertaFechaNac);
        dir = findViewById(R.id.insertaDireccion);
        insert = findViewById(R.id.registraPropietario);
        regresar = findViewById(R.id.regresaPropietario);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tel.getText().toString().equals("") || nom.getText().toString().equals("")) {
                    Toast.makeText(Main2Activity.this,"Debe ingresar un numero de telefono y un nombre",Toast.LENGTH_LONG).show();
                    return;
                }
                insertar();
            }
        });
    }

    private void insertar() {
        String mensaje = "Se logro registrar con exito el propietario";
        Propietario p = new Propietario(this);
        boolean salida = p.insertar(new Propietario(tel.getText().toString(),nom.getText().toString(),
                dir.getText().toString(),fe.getText().toString()));
        if(!salida)
            mensaje = "No se pudo registrar el propietario.. "+p.error;

        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Atención")
                .setMessage(mensaje)
                .setPositiveButton("OK",null).show();

        vaciarCampos();
    }

    private void vaciarCampos() {
        nom.setText("");
        tel.setText("");
        fe.setText("");
        dir.setText("");
        nom.requestFocus();
    }

}
