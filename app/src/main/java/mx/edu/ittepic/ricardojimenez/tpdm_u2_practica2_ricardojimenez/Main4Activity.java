package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main4Activity extends AppCompatActivity {
    String tel;
    EditText nombre,direccion,fecha;
    Button actualiza,regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        nombre = findViewById(R.id.actualizaNombre);
        direccion = findViewById(R.id.actualizaDireccion);
        fecha = findViewById(R.id.actualizaFechaNac);
        actualiza = findViewById(R.id.actualizaPropietario);
        regresar = findViewById(R.id.regresaActualiza);

        Bundle atributos = getIntent().getExtras();
        nombre.setText(atributos.getString("nombre"));
        direccion.setText(atributos.getString("dir"));
        fecha.setText(atributos.getString("fecha"));
        tel = atributos.getString("telefono");

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });


    }

    private void actualizar() {
        Propietario p = new Propietario(this);
        boolean result = p.actualizar(new Propietario(tel,nombre.getText().toString(),
                direccion.getText().toString(),fecha.getText().toString()));
        if(result){
            mensaje("Correcto","Se actualizo el propietario "+nombre.getText().toString());
        }else{
            mensaje("Atencion","No se pudo actualizar "+p.error);
        }

    }
    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }
}
