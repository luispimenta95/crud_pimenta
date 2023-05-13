package com.example.crudpimenta.activity;


import android.content.Context;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


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
    private boolean preenchido;
    private String cpf;
    private TextView inputNome,inputCpf,inputTelefone;
    Helper hp = new Helper();
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


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
            inputCpf.setKeyListener(null);
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
                cpf = inputCpf.getText().toString();
                if(validarPreenchimento()) {

                    int numDados = bd.pesquisaPorCpf(cpf);

                    if(hp.isCPF(cpf) && numDados ==0 || inputCpf.getText().toString().equals(user.getCpf())){
                        gerenciarCadastro(id,view);

                    }else{
                        Toast.makeText(GerenciarUsuario.this, "CPF já cadastrado ou inválido!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GerenciarUsuario.this, "Dados não preenchidos ou inválidos !", Toast.LENGTH_SHORT).show();
                }
                }

        });
    }
    private void gerenciarCadastro(int id,View view){
        if (id == 0) {
            user = new Usuario();
            user.setNome(inputNome.getText().toString());
            user.setCpf(inputCpf.getText().toString());
            user.setTelefone(inputTelefone.getText().toString());
            bd.addUser(user);
        } else {

            user = bd.recuperaPorId(id);
            bd.updateUser(id, inputNome.getText().toString(), user.getCpf(),inputTelefone.getText().toString());
        }
        Toast.makeText(GerenciarUsuario.this, "Registro salvo com sucesso !", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);

    }
    private boolean validarPreenchimento(){
        preenchido = true;
        if(inputNome.getText().toString().isEmpty()
                || inputCpf.getText().toString().isEmpty() ||
                inputTelefone.getText().toString().isEmpty() || inputTelefone.getText().toString().length() < 10 || inputTelefone.getText().toString().length() > 11 ){
            preenchido = false;
        }
        return preenchido;
    }
}


//     Log.d("user", "Valor: "+id);
//   Log.d("userName", "Valor: "+user.getNome());

