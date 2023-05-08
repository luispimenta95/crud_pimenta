package com.example.crudpimenta.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    private ListView lvCongViec;
    private List<Usuario> arrCongViec;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = (ListView) findViewById(R.id.lv_congviec);

        // khởi tạo database
        db = new Database(MainActivity.this);
        extracted(db.getEveryone());

    }

    private void extracted(List<Usuario> arrCongViec) {
        adapter = new UserAdapter(this, R.layout.item_cong_viec, arrCongViec);
        lvCongViec.setAdapter(adapter);
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
                Cursor cursor = db.searchUsers(s);
                //arrCV.clear();
                while (cursor.moveToNext()){
                    int IdCV = cursor.getInt(0);
                    String tencv = cursor.getString(1);
                    pesquisa.add(new Usuario(IdCV, tencv));
                }
                UserAdapter adapterSearch = new UserAdapter(MainActivity.this, R.layout.item_cong_viec, pesquisa);
                lvCongViec.setAdapter(adapterSearch);
                adapterSearch.notifyDataSetChanged();
                return true;

                //Cách 2:
//                ArrayList<CongViec> arrCV = new ArrayList<>();
//                for (CongViec item : arrCongViec) {
//                    if(item.getTenCV().contains(s)){
//                        arrCV.add(item);
//                    }
//                }
//                CongViecAdapter adapterSearch = new CongViecAdapter(MainActivity.this, R.layout.item_cong_viec, arrCV);
//                lvCongViec.setAdapter(adapterSearch);
//                adapterSearch.notifyDataSetChanged();
//                return true;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_add:
                dialogThem();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTen = (EditText) dialog.findViewById(R.id.edit_nome);
        Button btnThem = (Button) dialog.findViewById(R.id.btnAdicionar);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnCancelar);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dialogTen = edtTen.getText().toString().trim();
                if(!dialogTen.equals(""))
                {
                    boolean sussess = db.addCongViec(dialogTen);
                    Toast.makeText(MainActivity.this, "Registro salvo com sucesso !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    extracted(db.getEveryone());
                }
            }
        });

        //setCanceledOnTouchOutside khi chạm bên ngoài dialog sẽ k bị tắt; mặc định là true
        dialog.setCanceledOnTouchOutside(false);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void dialogSua(int id, String tencv){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        EditText edtTen = (EditText) dialog.findViewById(R.id.edit_nome);
        Button btnCapNhat = (Button) dialog.findViewById(R.id.btnCadastro);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnCancelar);

        edtTen.setText(tencv);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dialogTenMoi = edtTen.getText().toString().trim();
                db.UpdateCongViec(id, dialogTenMoi);
                Toast.makeText(MainActivity.this, "Registro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                extracted(db.getEveryone());
            }
        });

        //setCanceledOnTouchOutside khi chạm bên ngoài dialog sẽ k bị tắt; mặc định là true
        dialog.setCanceledOnTouchOutside(false);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void xoaCv(final int id, String tenCV){
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setMessage("Deseja realmente excluir o registro ?");
        diaBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteCongViec(tenCV);
                Toast.makeText(MainActivity.this, "Registro excluido com sucesso !", Toast.LENGTH_SHORT).show();
                extracted(db.getEveryone());
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