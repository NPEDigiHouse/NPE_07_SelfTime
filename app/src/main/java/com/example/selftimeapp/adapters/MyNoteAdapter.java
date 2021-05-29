package com.example.selftimeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selftimeapp.R;
import com.example.selftimeapp.models.Note;

import java.util.List;

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.ViewHolder>{
    private List<Note> list;
    private OnItemClick onItemClick;

    public MyNoteAdapter(List<Note> list, OnItemClick onItemClick) {
        this.list = list;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNoteAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(list.get(position).getJudul());
        holder.tvKeterangan.setText(list.get(position).getKeterangan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OnItemClick onItemClick;
        TextView tvNama, tvKeterangan;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            this.onItemClick = onItemClick;

            tvNama = itemView.findViewById(R.id.tv_nama_catatan);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan_catatan);
            btnEdit = itemView.findViewById(R.id.ib_edit);
            btnDelete = itemView.findViewById(R.id.ib_trash);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.btnEditClicked(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.btnDeleteClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClick {
        void btnEditClicked(int position);
        void btnDeleteClicked(int position);
    }
}
