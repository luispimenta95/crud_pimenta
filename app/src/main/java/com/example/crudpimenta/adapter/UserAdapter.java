package com.example.crudpimenta.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudpimenta.R;
import com.example.crudpimenta.activity.GerenciarUsuario;
import com.example.crudpimenta.activity.MainActivity;
import com.example.crudpimenta.model.Usuario;
import com.example.crudpimenta.util.Helper;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Usuario> usuarios;
    private AlertDialog alerta;
    Helper hp = new Helper();

    public UserAdapter(MainActivity context, int layout, List<Usuario> users) {
        this.context = context;
        this.layout = layout;
        this.usuarios = users;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDel, imgEdit, imgView;
        MainActivity ma;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Gán layout bằng biến
            view = inflater.inflate(layout, null);
            holder.txtTen = (TextView) view.findViewById(R.id.txt_ten);
            holder.imgDel = (ImageView) view.findViewById(R.id.iv_del);
            holder.imgEdit = (ImageView) view.findViewById(R.id.iv_edit);
            holder.imgView = (ImageView) view.findViewById(R.id.iv_view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Usuario u = usuarios.get(i);
        holder.txtTen.setText(u.getNome());


        holder.imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //define o titulo
                builder.setTitle("Dados do usuário "+ u.getNome());
                //define a mensagem
                builder.setMessage("Nome: " + u.getNome()+
                        "\n\n CPF: " + hp.imprimeCpf(u.getCpf()) +"\n\n Telefone: "
                        + hp.imprimeTelefone(u.getTelefone()));

                alerta = builder.create();
                //Exibe
                alerta.show();

                Log.i("Dados", "Nome:"+u.getNome() +"\n\n CPF:" +u.getCpf());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GerenciarUsuario.class);
                intent.putExtra("id",u.getId());
                view.getContext().startActivity(intent);
            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.excluirUsuario(u.getId(), u.getNome());
            }
        });
        return view;

    }

}
