package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {
    private String telefono,nombre,direccion,fecha;
    private BaseDatos bd;
    protected String error;
    public Propietario(Activity activity){
        bd = new BaseDatos(activity,"PROPIETARIOS",null,1);
    }

    public Propietario(String telefono, String nombre, String direccion, String fecha) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fecha = fecha;
    }

    public boolean insertar(Propietario p){
        try{
            SQLiteDatabase transaccionInsertar = bd.getWritableDatabase();
            String sql = "INSERT INTO PROPIETARIO VALUES('%1','%2','%3','%4')";
            sql = sql.replace("%1",p.getTelefono());
            sql = sql.replace("%2",p.getNombre());
            sql = sql.replace("%3",p.getDireccion());
            sql = sql.replace("%4",p.getFecha());
            transaccionInsertar.execSQL(sql);
            transaccionInsertar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }
    public boolean actualizar(Propietario p){
        try {
            SQLiteDatabase transaccionActualizar = bd.getWritableDatabase();
            String sql = "UPDATE PROPIETARIO SET nombre='%1', direccion='%2',fecha='%3' WHERE telefono like '"+p.getTelefono()+"%'";
            sql = sql.replace("%1",p.getNombre());
            sql = sql.replace("%2",p.getDireccion());
            sql = sql.replace("%3",p.getFecha());
            transaccionActualizar.execSQL(sql);
            transaccionActualizar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }
    public boolean eliminar(String tel){
        try {
            SQLiteDatabase transaccionEliminar = bd.getWritableDatabase();
            transaccionEliminar.execSQL("DELETE FROM PROPIETARIO WHERE telefono like '"+tel+"'");
            transaccionEliminar.close();
        }catch (SQLiteException e){error = e.getMessage(); return false;}
        return true;
    }

    public Propietario[] consultar(){
        Propietario[] prop = null;
        try {
            SQLiteDatabase transaccionConsultar = bd.getReadableDatabase();
            Cursor c = transaccionConsultar.rawQuery("SELECT * FROM PROPIETARIO",null);
            if(c.moveToFirst()){
                int i=0;
                prop = new Propietario[c.getCount()];
                do{
                    prop[i] = new Propietario(c.getString(0),c.getString(1),c.getString(2),c.getString(3));
                    i++;
                }while (c.moveToNext());
            }
            transaccionConsultar.close();
        }catch (SQLiteException e){error = e.getMessage(); return null;}
        return prop;
    }

    public Propietario consultar(String tel){
        Propietario prop = null;
        try {
            SQLiteDatabase transaccionConsultar = bd.getReadableDatabase();
            Cursor c = transaccionConsultar.rawQuery("SELECT * FROM PROPIETARIO WHERE telefono='"+tel+"'",null);
            if(c.moveToFirst()){

                prop = new Propietario(c.getString(0),c.getString(1),c.getString(2),c.getString(3));
            }
            transaccionConsultar.close();
        }catch (SQLiteException e){error = e.getMessage(); return null;}
        return prop;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
