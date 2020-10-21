package com.example.loginform.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.Model.ModelUser;
import com.example.loginform.R;

import java.util.List;

public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.UserViewHolder>{
    private static final String TAG = UserRecycleAdapter.class.getSimpleName();
    private List<ModelUser> listUsers;
    public UserRecycleAdapter(List<ModelUser> listUsers) {
        this.listUsers = listUsers;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_user, parent, false);
        return new UserViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
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
        private TextView textViewName;
        private TextView textViewAlamat;
        public UserViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewAlamat = view.findViewById(R.id.textViewAlamat);
        }

        public void bind(ModelUser modelUser) {
            textViewName.setText(modelUser.getUsername());
            textViewAlamat.setText(modelUser.getAlamat());
        }
    }
}
