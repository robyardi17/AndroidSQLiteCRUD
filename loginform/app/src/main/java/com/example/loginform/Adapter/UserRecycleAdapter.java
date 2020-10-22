package com.example.loginform.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.DetailUser;
import com.example.loginform.LoginActivity;
import com.example.loginform.MainActivity;
import com.example.loginform.Model.ModelUser;
import com.example.loginform.R;

import java.util.List;

public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.UserViewHolder>{
    private List<ModelUser> listUsers;
    private Context context;
    public UserRecycleAdapter(Context context, List<ModelUser> listUsers) {
        this.listUsers = listUsers;
        this.context = context;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_user, parent, false);
        return new UserViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.bind(listUsers.get(position));
    }
    @Override
    public int getItemCount() {
        return listUsers.size();
    }
    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName, textViewAlamat, textViewNomor, textViewPw, tvID;

        public UserViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewAlamat = view.findViewById(R.id.textViewAlamat);
            textViewNomor = view.findViewById(R.id.textViewNomor);
            textViewPw = view.findViewById(R.id.textViewPassword);
            tvID = view.findViewById(R.id.idRecycle);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nama = textViewName.getText().toString();
                    String pw = textViewPw.getText().toString();
                    String nomor = textViewNomor.getText().toString();
                    String alamat = textViewAlamat.getText().toString();
                    String id = tvID.getText().toString();
                    Intent intent = new Intent(context, DetailUser.class);
                    intent.putExtra("Username", nama);
                    intent.putExtra("Pw", pw);
                    intent.putExtra("Alamat", alamat);
                    intent.putExtra("Nomor", nomor);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(ModelUser modelUser) {
            textViewName.setText(modelUser.getUsername());
            textViewAlamat.setText(modelUser.getAlamat());
            textViewNomor.setText(modelUser.getNomor());
            textViewPw.setText(modelUser.getPassword());
            tvID.setText(String.valueOf(modelUser.getId()));
        }
    }
}
