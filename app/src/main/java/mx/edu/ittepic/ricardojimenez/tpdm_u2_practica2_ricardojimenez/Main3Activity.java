package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    EditText txtConsulta;
    TextView resultado;
    Button consultar, eliminar,actualizar,regresar,seguros;
    Propietario prop;
    int numseguros;
    Seguro[] seg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        numseguros=0;
        regresar = findViewById(R.id.regresarconsulta);
        txtConsulta = findViewById(R.id.txtbuscar);
        resultado = findViewById(R.id.lblConsulta);
        consultar = findViewById(R.id.buscar);
        eliminar = findViewById(R.id.eliminar);
        actualizar = findViewById(R.id.actualizar);
        seguros = findViewById(R.id.btnVerSeg);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtConsulta.getText().toString().equals("")){
                    mensaje("Atencion","no se ingreso el telefono");
                    return;
                }
                consulta();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifica())
                    actualiza();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifica())
                    elimina();
            }
        });
        seguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifica())
                    verSeg();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void verSeg() {
        if(numseguros==0){
            Intent se = new Intent(this,Main6Activity.class);
            se.putExtra("tipo","new");
            se.putExtra("tel",prop.getTelefono());
            se.putExtra("fe",prop.getFecha());
            vaciar();
            startActivity(se);
        }else{
            Intent se = new Intent(this,Main5Activity.class);
            se.putExtra("tel",prop.getTelefono());
            se.putExtra("nomP",prop.getNombre());
            se.putExtra("feprop",prop.getFecha());
            vaciar();
            startActivity(se);
        }
    }

    private void elimina() {
        final Propietario p = new Propietario(this);
        final Seguro s=new Seguro(this);
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Confirmacion")
                .setMessage("Esta seguro de eliminar a "+prop.getNombre())
        .setNegativeButton("Cancelar",null)
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                s.eliminar(prop.getTelefono());
                if(p.eliminar(prop.getTelefono())){
                    mensaje("Correcto","Se elimino al propietario "+prop.getNombre());
                    vaciar();
                }else{
                    mensaje("Atencion","No se logro eliminar "+p.error);
                }


            }
        }).show();
    }

    private void actualiza() {
        Intent propietario = new Intent(this,Main4Activity.class);
        propietario.putExtra("nombre",prop.getNombre());
        propietario.putExtra("telefono",prop.getTelefono());
        propietario.putExtra("dir",prop.getDireccion());
        propietario.putExtra("fecha",prop.getFecha());
        vaciar();
        startActivity(propietario);
    }

    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }

    private void consulta() {
        Propietario p = new Propietario(this);
        Seguro s = new Seguro(this);
        String tel=txtConsulta.getText().toString();

        prop = p.consultar(tel);
        seg = s.consultar(tel);
        if(prop == null){
            mensaje("Atencion","Propietario no encontrado");
            return;
        }
        if (seg!=null)
            numseguros=seg.length;

        String propietario="Nombre: "+prop.getNombre()+"\nTelefono: "+prop.getTelefono()
                +"\nDireccion: "+prop.getDireccion()+"\nFecha nac: "+prop.getFecha()+
                "\nSeguros adquiridos: "+numseguros+"  ";
        resultado.setText(propietario);
    }
    public void vaciar(){
        resultado.setText("");
        prop=null;
        seg=null;
    }
    public boolean verifica(){
        if(prop==null){
            mensaje("Atencion","No se consulto el propietario");
            return false;
        }
        return true;
    }
}
