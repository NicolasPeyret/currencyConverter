package com.example.convertisseurdemonnaie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MonnaieBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "monnaiebd.db";

    private static final String TABLE_MONNAIE = "table_monnaie";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DEVISE = "devise";
    private static final int NUM_COL_DEVISE = 1;
    private static final String COL_DEVISE_CALCUL = "devise_calcul";
    private static final int NUM_COL_DEVISE_CALCUL = 2;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public MonnaieBDD(Context context) {
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertMonnaie(Monnaie monnaie) {
        ContentValues values = new ContentValues();
        values.put(COL_DEVISE, monnaie.getDevise());
        values.put(COL_DEVISE_CALCUL, monnaie.getDevise_calcul());
        return bdd.insert(TABLE_MONNAIE, null, values);
    }

    public int updateMonnaie(int id, Monnaie monnaie) {
        ContentValues values = new ContentValues();
        values.put(COL_DEVISE, monnaie.getDevise());
        values.put(COL_DEVISE_CALCUL, monnaie.getDevise_calcul());
        return bdd.update(TABLE_MONNAIE, values, COL_ID + " = " + id, null);
    }

    public int removeMonnaieWithID(int id) {
        return bdd.delete(TABLE_MONNAIE, COL_ID + " = " + id, null);
    }

    public Monnaie getMonnaieWithDevise(String titre) {
        Cursor c = bdd.query(TABLE_MONNAIE, new String[]{COL_ID, COL_DEVISE, COL_DEVISE_CALCUL}, COL_DEVISE_CALCUL + " LIKE \"" + titre + "\"", null, null, null, null);
        return cursorToMonnaie(c);
    }

    private Monnaie cursorToMonnaie(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Monnaie monnaie = new Monnaie();
        monnaie.setId(c.getInt(NUM_COL_ID));
        monnaie.setDevise(c.getString(NUM_COL_DEVISE));
        monnaie.setDevise_calcul(c.getDouble(NUM_COL_DEVISE_CALCUL));
        c.close();

        return monnaie;
    }
}