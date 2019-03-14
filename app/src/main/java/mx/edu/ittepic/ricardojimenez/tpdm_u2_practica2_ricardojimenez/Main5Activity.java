package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {
    ListView lista;
    TextView propietario;
    Button regresar,nuevoSeg;
    String[] tipos ={"Casa","Auto","Medico"};
    String infoProp, tel,feprop;
    int[] tipoSeg;
    Seguro[] segu;
    int numseg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        lista = findViewById(R.id.listaSeguros);
        propietario = findViewById(R.id.datosProp);
        regresar =findViewById(R.id.regresarSeguros);
        nuevoSeg = findViewById(R.id.nuevoSeg);

        Bundle datos = getIntent().getExtras();

        tel = datos.getString("tel");
        infoProp = datos.getString("nomP")+"\nTelefono: "+tel;
        feprop = datos.getString("feprop");
        propietario.setText(infoProp);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlerta(position);
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nuevoSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
    }

    private void mostrarAlerta(final int pos) {
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Confirmacion").setMessage("Seguro que desea actualizar/eliminar?")
                .setNegativeButton("Cancelar",null)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actualizar(pos);
                    }
                }).show();
    }

    private void insertar() {
        Intent se = new Intent(this,Main6Activity.class);
        se.putExtra("tipo","otro");
        se.putExtra("tel",tel);
        se.putExtra("tipoSeg",tipoSeg);
        se.putExtra("fe",feprop);
        startActivity(se);
    }

    private void actualizar(int pos) {
        Intent as = new Intent(this,Main7Activity.class);
        as.putExtra("idseg",segu[pos].getIdseguro());
        as.putExtra("tipo",segu[pos].getTipo());
        as.putExtra("desc",segu[pos].getDescripcion());
        as.putExtra("tel",tel);
        startActivity(as);
    }

    @Override
    protected void onStart() {
        Seguro s = new Seguro(this);
        segu = s.consultar(tel);
        numseg = segu.length;
        if(numseg==3){
            nuevoSeg.setEnabled(false);
        }else{ nuevoSeg.setEnabled(true); }
        String[] listaS= new String[numseg];
        tipoSeg = new int[numseg];


        for(int i=0;i<segu.length;i++){

                listaS[i] = segu[i].getDescripcion()+"\nNum. Seguro: "+segu[i].getIdseguro()+"\n Tipo: "+tipos[segu[i].getTipo()];
                tipoSeg[i] = segu[i].getTipo();

        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaS);
        lista.setAdapter(adaptador);
        super.onStart();
    }
    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }
}
