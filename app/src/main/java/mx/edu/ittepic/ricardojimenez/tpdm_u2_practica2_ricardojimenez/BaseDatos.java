package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica2_ricardojimenez;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    public BaseDatos(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROPIETARIO(telefono VARCHAR(20) PRIMARY KEY NOT NULL,nombre VARCHAR(200) NOT NULL," +
                "direccion VARCHAR(500),fecha DATE)");
        db.execSQL("CREATE TABLE SEGURO(IdSeguro VARCHAR(20) PRIMARY KEY NOT NULL,telefono VARCHAR(20) NOT NULL, descripcion VARCHAR(200) NOT NULL,tipo INTEGER," +
                " fecha DATE, FOREIGN KEY(telefono) REFERENCES PROPIETARIO(telefono))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
