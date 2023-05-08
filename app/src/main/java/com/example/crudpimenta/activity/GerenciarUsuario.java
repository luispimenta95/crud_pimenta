package com.example.crudpimenta.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.example.crudpimenta.R;
import com.example.crudpimenta.adapter.UserAdapter;
import com.example.crudpimenta.bd.Database;
import com.example.crudpimenta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciarUsuario extends AppCompatActivity {
    //good luck!
    private Database bd;
    private UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar);


        // instanciar bd
        bd = new Database(GerenciarUsuario.this);

    }


}