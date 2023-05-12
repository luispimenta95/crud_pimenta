package com.example.crudpimenta.activity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.crudpimenta.R;
import com.example.crudpimenta.bd.Database;
import com.example.crudpimenta.model.Usuario;
import com.example.crudpimenta.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class GerenciarUsuario extends AppCompatActivity {
    //good luck!
    private Database bd;

    private Usuario user;
    private Button btnAdd,btnVoltar;
    private TextView inputNome,inputCpf,inputTelefone;
    Helper hp = new Helper();


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
         inputCpf = (TextView) findViewById(R.id.inputCpf);
         inputTelefone = (TextView) findViewById(R.id.inputTelefone);


        btnAdd.setText("Cadastrar");
         if(id!=0){
             user = bd.recuperaPorId(id);
             btnAdd.setText("Atualizar");
             inputNome.setText(user.getNome());
             inputCpf.setText(user.getCpf());
             inputTelefone.setText(user.getTelefone());
             Log.d("userName", "Valor: "+user.getCpf());

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
                int numCPF = bd.pesquisaPorCpf(inputCpf.getText().toString());

                if (inputNome.getText().toString()=="" ||
                        inputCpf.getText().toString().trim().equals("") || inputTelefone.getText().toString().trim().equals("")) {

                    Toast.makeText(GerenciarUsuario.this, "Registro salvo com sucesso !", Toast.LENGTH_LONG).show();

                } else {
                    if (numCPF == 0 && hp.isCPF(inputCpf.getText().toString()) || user.getCpf().toString() == inputCpf.getText().toString()) {

                        if (id == 0) {
                            user = new Usuario();
                            user.setNome(inputNome.getText().toString());
                            user.setCpf(inputCpf.getText().toString());
                            user.setTelefone(inputTelefone.getText().toString());
                            bd.addUser(user);
                            Toast.makeText(GerenciarUsuario.this, "Registro salvo com sucesso !", Toast.LENGTH_LONG).show();
                        } else {

                            user = bd.recuperaPorId(id);

                            bd.updateUser(id, inputNome.getText().toString(), inputCpf.getText().toString(), inputTelefone.getText().toString());
                            Toast.makeText(GerenciarUsuario.this, "Registro atualizado com sucesso !", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(view.getContext(), MainActivity.class);

                        view.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(GerenciarUsuario.this, "CPF já cadastrado ou inválido !", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    }


   //     Log.d("user", "Valor: "+id);
     //   Log.d("userName", "Valor: "+user.getNome());





