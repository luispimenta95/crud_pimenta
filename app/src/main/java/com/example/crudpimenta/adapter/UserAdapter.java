package com.example.crudpimenta.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Usuario> usuarios;

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
        ImageView imgDel, imgEdit;

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
            //ánh xạ
            holder.txtTen = (TextView) view.findViewById(R.id.txt_ten);
            holder.imgDel = (ImageView) view.findViewById(R.id.iv_del);
            holder.imgEdit = (ImageView) view.findViewById(R.id.iv_edit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Usuario u = usuarios.get(i);
        holder.txtTen.setText(u.getNome());

        //bắt sự kiện xóa và sửa
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GerenciarUsuario.class);
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
