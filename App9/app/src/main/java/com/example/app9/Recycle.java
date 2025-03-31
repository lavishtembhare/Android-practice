package com.example.app9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Recycle extends RecyclerView.Adapter<Recycle.ViewHolder> {
    private Context context;
    ArrayList<ContactModel> arrModel;
Recycle(Context context, ArrayList<ContactModel> arrModel){
    this.context=context;
    this.arrModel=arrModel;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate a layout for each item
        View v=LayoutInflater.from(context).inflate(R.layout.contact_row, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.imgContact.setImageResource(arrModel.get(position).img);
    holder.txtName.setText(arrModel.get(position).name);
    holder.txtNum.setText(arrModel.get(position).number);
    }

    @Override
    public int getItemCount() {
        return 0; // Return correct item count
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
TextView txtName,txtNum;
ImageView imgContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtNum=itemView.findViewById(R.id.txtNumber);
            imgContact=itemView.findViewById(R.id.imgContact);
        }
    }
}
