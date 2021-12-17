package com.example.bulletbuddy;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bulletbuddy.databinding.FragmentErrandListBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Errands}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ErrandRecyclerViewAdapter extends RecyclerView.Adapter<ErrandRecyclerViewAdapter.ViewHolder> {

    private final List<Errands> errandsList;

    public ErrandRecyclerViewAdapter(List<Errands> errand) {
        errandsList = errand;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentErrandListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.errands = errandsList.get(position);
        //holder.mIdView.setText(errandsList.get(position).id);
        holder.errandNameView.setText(errandsList.get(position).getErrandName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return errandsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       // public final TextView mIdView;
        public final TextView errandNameView;
        public Errands errands;

        public ViewHolder(FragmentErrandListBinding binding) {
            super(binding.getRoot());
           // mIdView = binding.itemNumber;
            errandNameView = binding.nameTextView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + errandNameView.getText() + "'";
        }
    }
}