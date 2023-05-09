package com.example.crudpimenta.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.crudpimenta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "crudpimenta";
    public static final String NOME_TABELA = "usuario";
    private static final int VERSAO_BANCO = 12;
    public static final String COLUNA_ID = "usuario_id";
    public static final String COLUNA_NOME = "usuario_nome";
    public static final String COLUNA_CPF = "usuario_cpf";
    public static final String COLUNA_TELEFONE = "usuario_telefone";
    private Context context;

    public Database(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }

    //truy vấn không trả về kết quả: CREATE, INSERT, UPDATE, DELETE...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        //để thực thi lệnh cần gọi
        database.execSQL(sql);
    }

    //truy vấn có trả về kết quả: SELECT
    //dữ liệu trả về dưới dạng con trỏ từng dòng (cursor)
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    //Tìm kiếm bằng query
    public Cursor pesquisaPorNome(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_NOME + " Like '%"+ text +"%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NOME_TABELA +
                " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_NOME + " VARCHAR(100), " + COLUNA_CPF + " VARCHAR(14) UNIQUE" +
                ", " + COLUNA_TELEFONE + " VARCHAR(25))";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(Usuario user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUNA_NOME, user.getNome());
        cv.put(COLUNA_CPF, user.getCpf());
        cv.put(COLUNA_TELEFONE, user.getTelefone());


        long insert = db.insert(NOME_TABELA, null, cv);
        db.close();
        if(insert == -1){
            return false;
        }else {
            return true;
        }
    }


    public int updateUser(int id, String name, String cpf, String telefone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, name);
        values.put(COLUNA_CPF, cpf);
        values.put(COLUNA_TELEFONE, telefone);

        return db.update(NOME_TABELA,values, COLUNA_ID +"=?",
                new String[] { String.valueOf(id)});
    }

    public boolean deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOME_TABELA + " WHERE " + COLUNA_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }


    public Usuario recuperaPorId(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOME_TABELA, new String[] {COLUNA_ID, COLUNA_NOME,COLUNA_CPF,COLUNA_TELEFONE},
                COLUNA_ID + "=?",new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Usuario user = new Usuario(
                id ,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        cursor.close();
        db.close();
        return user;
    }



    public List<Usuario> recuperarUsuarios(){
        List<Usuario> returnList = new ArrayList<Usuario>();
        //get data from database
        String selectQuery = "SELECT * FROM " + NOME_TABELA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Usuario user = new Usuario();
                user.setId(cursor.getInt(0));
                user.setNome(cursor.getString(1));
                user.setCpf(cursor.getString(2));
                user.setTelefone(cursor.getString(3));
                returnList.add(user);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return returnList;
    }

    // Get Count person in Table Person


    public int pesquisaPorCpf(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_CPF + " = " + text;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}