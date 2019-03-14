package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Main7Activity extends AppCompatActivity {
    EditText descripcion;
    Spinner tipo;
    Button actualizar,regresar,eliminar;
    String idseg,tel;
    int tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        descripcion = findViewById(R.id.actualizadescripcionS);
        tipo = findViewById(R.id.actualziatipos);
        actualizar = findViewById(R.id.actualizaSeguro);
        regresar = findViewById(R.id.regresaActS);
        eliminar = findViewById(R.id.eliminaS);
        
        Bundle datos = getIntent().getExtras();
        idseg = datos.getString("idseg");
        tip = datos.getInt("tipo");
        tipo.setSelection(tip);
        tel = datos.getString("tel");
        descripcion.setText(datos.getString("desc"));
        
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimina();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualiza();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }

    private void actualiza() {
        Seguro s = new Seguro(this);
        Seguro[] ss =null;
        int seleccionado = tipo.getSelectedItemPosition();
        ss = s.consultarId(seleccionado,tel);
        if(ss!=null && seleccionado != tip){
            mensaje("Atencion","El seguro que desea cambiar ya existe, solo puede haber un seguro por tipo");
            tipo.setSelection(tip);
            return;
        }

        if(s.actualizar(new Seguro(idseg,tel,descripcion.getText().toString(),tipo.getSelectedItemPosition(),""))){
            mensajeSalir("Correcto","Se actualizo correctamente el seguro");
        }else{
            mensaje("Advertencia","No se logro actualizar el seguro "+s.error);
        }
    }

    private void elimina() {
        final Seguro s = new Seguro(this);
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Confirmacion").setMessage("Seguro que desea eliminar el seguro")
                .setNegativeButton("Cancelar",null)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         if(s.eliminar(idseg)){
                             mensajeSalir("Correcto","Se elimino el seguro");
                         }else{
                             mensaje("Advertencia","Ocurrio un error "+s.error);
                         }
                    }
                }).show();
                
    }
    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }
    private  void mensajeSalir(String title,String message){
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
