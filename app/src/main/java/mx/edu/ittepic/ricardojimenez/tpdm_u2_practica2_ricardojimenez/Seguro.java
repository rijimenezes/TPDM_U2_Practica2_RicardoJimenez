package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Seguro {
    private  String fecha;
    private String idseguro,telefono,descripcion;
    private int tipo;
    private BaseDatos bd;
    protected String error,consulta="";

    public Seguro(Activity activity){
        bd = new BaseDatos(activity,"PROPIETARIOS",null,1);
    }

    public Seguro(String idseguro, String telefono, String descripcion,  int tipo,String fecha) {
        this.idseguro = idseguro;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public boolean insertar(Seguro s){
        try{
            SQLiteDatabase transaccionInsertar = bd.getWritableDatabase();
            String sql = "INSERT INTO SEGURO(IdSeguro,telefono,descripcion,tipo,fecha) VALUES('"+s.getIdseguro()+"','"+
                    s.getTelefono()+"','"+s.getDescripcion()+"',"+s.getTipo()+",'"+s.getFecha()+"')";
            transaccionInsertar.execSQL(sql);
            transaccionInsertar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }
    public boolean actualizar(Seguro s){
        try {
            SQLiteDatabase transaccionActualizar = bd.getWritableDatabase();
            String sql = "UPDATE SEGURO SET descripcion='%1', tipo=" +s.getTipo()+
                    " WHERE telefono like '"+s.getTelefono()+"%' " +
                    "AND IdSeguro like '"+s.getIdseguro()+"'";
            sql = sql.replace("%1",s.getDescripcion());
            sql = sql.replace("%2",s.getFecha());
            transaccionActualizar.execSQL(sql);
            transaccionActualizar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }
    public boolean eliminar(String idseg){
        try {
            SQLiteDatabase transaccionEliminar = bd.getWritableDatabase();
            transaccionEliminar.execSQL("DELETE FROM SEGURO WHERE IdSeguro like '"+idseg+"'");
            transaccionEliminar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }

    public Seguro[] consultarId(int tiposeg, String idseg){
        Seguro[] seg = null;
        try {
            SQLiteDatabase transaccionConsultar = bd.getReadableDatabase();
            Cursor c = transaccionConsultar.rawQuery("SELECT * FROM SEGURO WHERE tipo="+tiposeg+" AND telefono like '"
                    +idseg+"'",null);
            if(c.moveToFirst()){
                int i=0;
                seg = new Seguro[c.getCount()];
                do{
//                    consulta+=c.getString(0)+"  "+c.getString(1)+" "+c.getString(2)+" "+c.getInt(3)+" "+c.getString(4)+"\n";
                    seg[i] = new Seguro(c.getString(0),c.getString(1),
                            c.getString(2),c.getInt(3),c.getString(4));
                    i++;
                }while (c.moveToNext());
            }
            transaccionConsultar.close();
        }catch (SQLiteException e){error = e.getMessage(); return null;}
        return seg;
    }

    public Seguro[] consultar(String tel){
        Seguro[] seg = null;
        try {
            SQLiteDatabase transaccionConsultar = bd.getReadableDatabase();
            Cursor c = transaccionConsultar.rawQuery("SELECT * FROM SEGURO WHERE telefono like '"+tel+"'",null);
            int i=0;
            if(c.moveToFirst()){
                seg = new Seguro[c.getCount()];
                do{

                    seg[i] = new Seguro(c.getString(0),c.getString(1),c.getString(2),c.getInt(3),c.getString(4));
                    i++;
                }while (c.moveToNext());
            }
            transaccionConsultar.close();
        }catch (SQLiteException e){error = e.getMessage(); return null;}
        return seg;
    }

    public String getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(String idseguro) {
        this.idseguro = idseguro;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
