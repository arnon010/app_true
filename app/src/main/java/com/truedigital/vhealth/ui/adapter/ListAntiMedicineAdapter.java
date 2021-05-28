package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemAntiMedicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListAntiMedicineAdapter extends RecyclerView.Adapter<ListAntiMedicineAdapter.ViewHolder> {

    private Context context;
    private List<ItemAntiMedicine> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private ItemAntiMedicine data;
    private String strMedicine;

    public ListAntiMedicineAdapter(Context context) {
        this.context = context;
    }

    public ListAntiMedicineAdapter(Context context, List<ItemAntiMedicine> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_anti_medicine, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ItemAntiMedicine data = listData.get(position);

        if (position != 0) {
            data.setShowIconRemove(true);
        }

        holder.edMedicine.setText(data.getDescription());
        holder.imgRemove.setVisibility(data.isShowIconRemove() ? View.VISIBLE : View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onItemClickListener.onItemRemoveClick(v, position);
                removeItem(position);
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onItemClickListener.onItemAddClick(v, position, holder.edMedicine.getText().toString());
                addItem(new ItemAntiMedicine());
            }
        });


        /*
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data.setDescription(s.toString());
                Log.e("List Adapter ", position + " " + data.getDescription());
            }

            @Override
            public void afterTextChanged(Editable s) {

                //data.setDescription(s.toString());
                //Log.e("List Adapter ", position + " " + data.getDescription());


                //strMedicine = holder.edMedicine.getText().toString();
                //onItemClickListener.onEditChange(position, strMedicine);

            }
        };

        holder.edMedicine.addTextChangedListener(textWatcher);
        */

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public ItemAntiMedicine getData(int position) {
        return listData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAdd;
        private final ImageView imgRemove;
        private final EditText edMedicine;

        public ViewHolder(View v) {
            super(v);
            imgAdd = (ImageView) v.findViewById(R.id.imgAdd);
            imgRemove = (ImageView) v.findViewById(R.id.imgRemove);
            edMedicine = (EditText) v.findViewById(R.id.edMedicine);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listData.get(getAdapterPosition()).setDescription(s.toString());
                    //data.setDescription(s.toString());
                    Log.e("Textchange Adapter ", getAdapterPosition() + " " + listData.get(getAdapterPosition()).getDescription());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            edMedicine.addTextChangedListener(textWatcher);

        }
    }

    public List<ItemAntiMedicine> getListData() {
        return listData;
    }

    public void setListData(List<ItemAntiMedicine> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ItemAntiMedicine> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }


    public void addItem(ItemAntiMedicine data) {
        this.listData.add(data);
        listData.get(0).setShowIconRemove(false);
        if (getItemCount() > 1) {
            //listData.get(0).setShowIconRemove(false);
            //listData.get(getItemCount() - 1).setShowIconRemove(true);
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position > 0) {
            this.listData.remove(position);
        }
        //if (getItemCount() <= 1) {
        //    listData.get(0).setShowIconRemove(false);
        //}
        notifyDataSetChanged();
    }

    public void updateItem(int position, ItemAntiMedicine data) {
        this.listData.get(position).setDescription(data.getDescription());
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemAddClick(View view, int position, String detail);

        void onItemRemoveClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    /*
    public class EditTextListener implements TextWatcher {
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            listData.get(position).setDescription(s.toString());
            Log.e("List Adapter ", position + " " + listData.get(position).getDescription());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    */
}
