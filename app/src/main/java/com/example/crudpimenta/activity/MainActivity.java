package com.example.crudpimenta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.crudpimenta.R;
import com.example.crudpimenta.adapter.UserAdapter;
import com.example.crudpimenta.bd.Database;
import com.example.crudpimenta.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //good luck!
    private Database db;
    private ListView listUsers;
    private  Button btnAdicionar;
    private List<Usuario> arrCongViec;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listUsers = (ListView) findViewById(R.id.listUsers);
        btnAdicionar = (Button) findViewById(R.id.btnAddUser);
        btnAdicionar.setText("Adicionar usuário");
        db = new Database(MainActivity.this);

        extracted(db.recuperarUsuarios());
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GerenciarUsuario.class);

                view.getContext().startActivity(intent);
            }
        });
    }

    private void extracted(List<Usuario> arrCongViec) {
        adapter = new UserAdapter(this, R.layout.item_cong_viec, arrCongViec);
        listUsers.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_congviec, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Usuario> pesquisa = new ArrayList<>();
                Cursor cursor = db.pesquisaPorNome(s);
                //arrCV.clear();
                while (cursor.moveToNext()){
                    int IdCV = cursor.getInt(0);
                    String tencv = cursor.getString(1);
                    pesquisa.add(new Usuario(IdCV, tencv));
                }
                UserAdapter adapterSearch = new UserAdapter(MainActivity.this, R.layout.item_cong_viec, pesquisa);
                listUsers.setAdapter(adapterSearch);
                adapterSearch.notifyDataSetChanged();
                return true;


            }
        });
        return super.onCreateOptionsMenu(menu);
    }





    public void excluirUsuario(final int id, String tenCV){
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setMessage("Deseja realmente excluir o registro ?");
        diaBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteOneCongViec(id);
                Toast.makeText(MainActivity.this, "Registro excluido com sucesso !", Toast.LENGTH_SHORT).show();
                extracted(db.recuperarUsuarios());
            }
        });
        diaBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        diaBuilder.show();
    }

}