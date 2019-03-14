package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Propietario[] prop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.lista);
    }

    @Override
    protected void onStart() {
        Propietario p = new Propietario(this);
        prop = p.consultar();
        String[] listaProp=null;
        if(prop ==null){
            listaProp = new String[1];
            listaProp[0] = "No hay propietarios aun";
        }else{
            listaProp = new String[prop.length];
            for(int i=0;i<prop.length;i++){
                Propietario tmp = prop[i];
                listaProp[i] = tmp.getNombre()+"\nTelefono: "+tmp.getTelefono();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaProp);
        lista.setAdapter(adaptador);

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nuevoregistro:
                Intent nr = new Intent(this,Main2Activity.class);
                startActivity(nr);
                break;

            case R.id.consultap:
                Intent cs = new Intent(this,Main3Activity.class);
                startActivity(cs);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
