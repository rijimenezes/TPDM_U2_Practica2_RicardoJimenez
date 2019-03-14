package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {
    EditText idSeguro,descripcion;
    Spinner tipo;
    Button insertar,regresar;
    int[] tipos;
    String[]tipoSeg ={"Casa","Auto","Medico"};
    String tel,feprop;
    List s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        idSeguro = findViewById(R.id.insertaNumeroSeg);
        descripcion = findViewById(R.id.descripcionS);
        tipo = findViewById(R.id.tipos);
        insertar = findViewById(R.id.registraSeguro);
        regresar = findViewById(R.id.regresaInse);
        s = new ArrayList();
        s.add("Casa");
        s.add("Auto");
        s.add("Medico");
        Bundle datos = getIntent().getExtras();
        tel = datos.getString("tel");
        feprop = datos.getString("fe");
       if( datos.getString("tipo").equals("otro")){
           tipos= datos.getIntArray("tipoSeg");
           for (int a : tipos) {
               s.remove(tipoSeg[a]);
           }
       }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s);
        tipo.setAdapter(adaptador);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserta();
            }
        });
    }

    private void inserta() {
        Seguro s = new Seguro(this);
        String selec =tipo.getSelectedItem().toString();
        boolean result = s.insertar(new Seguro(idSeguro.getText().toString(),tel,
                descripcion.getText().toString(),(selec.equals("Casa")?0:selec.equals("Auto")?1:2),feprop));
        if(result){
            mensaje("Correcto","SE INSERTO EL SEGURO "+tipo.getSelectedItem().toString());
        }else{
            mensaje("Atencion","No se logro insertar el seguro "+s.error);
        }
    }
    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }
}
