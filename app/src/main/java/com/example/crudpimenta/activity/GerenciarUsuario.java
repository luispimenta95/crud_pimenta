package com.example.crudpimenta.activity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.example.crudpimenta.R;
import com.example.crudpimenta.bd.Database;
import com.example.crudpimenta.model.Usuario;
import com.example.crudpimenta.util.Helper;

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
                int numDados = bd.pesquisaPorCpf(inputCpf.getText().toString());
                   Log.d("Count", "Count: " + numDados);

                }

        });
    }
}


//     Log.d("user", "Valor: "+id);
//   Log.d("userName", "Valor: "+user.getNome());

