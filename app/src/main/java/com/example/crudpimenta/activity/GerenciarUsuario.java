package com.example.crudpimenta.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
    private Usuario user;
    private Button btnAdd,btnVoltar;
    private TextView inputNome,inputCpf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar);


        // instanciar bd
        bd = new Database(GerenciarUsuario.this);
        Intent intent = getIntent();
         int id = intent.getIntExtra("id",0);
         btnAdd = (Button) findViewById(R.id.btnAdd);
         btnVoltar = (Button) findViewById(R.id.btnVoltar);
         inputNome = (TextView) findViewById(R.id.inputNome);

         btnAdd.setText("Cadastrar");
         if(id!=0){
             user = bd.recuperaPorId(id);
             btnAdd.setText("Atualizar");
             inputNome.setText(user.getNome());

         }
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);

                view.getContext().startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               if(id ==0){
                   user = new Usuario();
                   user.setNome(inputNome.getText().toString());
                   bd.addUser(user);
                   Toast.makeText(GerenciarUsuario.this, "Registro salvo com sucesso !", Toast.LENGTH_LONG).show();
               }else{

                   user = bd.recuperaPorId(id);
                   bd.updateUser(id,inputNome.getText().toString());
                   Toast.makeText(GerenciarUsuario.this, "Registro atualizado com sucesso !", Toast.LENGTH_LONG).show();
               }
                Intent intent = new Intent(view.getContext(), MainActivity.class);

                view.getContext().startActivity(intent);
            }
        });
    }
    }


   //     Log.d("user", "Valor: "+id);
     //   Log.d("userName", "Valor: "+user.getNome());





