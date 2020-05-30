package com.example.sugorenge.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugorenge.BookingConfirmation;
import com.example.sugorenge.R;
import com.example.sugorenge.model.FAQModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
    Context context;
    LayoutInflater layoutInflater;

    String transaction;
    String brid;
    String Id,elseMessage,Amount,RechargeDate,OperatorName,status,message;
    int uid;

     JSONObject m;
    ProgressDialog pDialog;

    private ArrayList<FAQModel> originalData = null;
    private ArrayList<FAQModel> filteredData = null;

    private ItemFilter mFilter = new ItemFilter();

    public FAQAdapter(Activity context, ArrayList<FAQModel> data) {
        this.context = context;
        this.filteredData = data;
        this.originalData = data;
    }
    private JSONArray data;
    public FAQAdapter(Context context, JSONArray data ) {
        this.data = data;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView text;
        LinearLayout dropdown,relat;

        public ViewHolder(View convertView) {
            super(convertView);
            text = convertView.findViewById(R.id.text);
            dropdown = convertView.findViewById(R.id.layout);

        }

    }


    public FAQModel getItem(int position) {
        return filteredData.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.subcategorylayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FAQModel myListData = filteredData.get(position);
        holder.text.setText(myListData.getName());
        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, BookingConfirmation.class);
                intent.putExtra("p_name",myListData.getName());
                intent.putExtra("id",myListData.getId() );
                intent.putExtra("price",myListData.getPrice() );

                context.startActivity(intent);

            }
        });

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<FAQModel> list = originalData;

            int count = list.size();
            final ArrayList<FAQModel> nlist = new ArrayList<>(count);
            String filterableText;

            if (!filterString.equals("")) {
                for (FAQModel model : list) {
                    if(!filterString.equals("")) {
                        filterableText = model.getName();
                        if (filterableText != null && filterableText.toLowerCase().contains(filterString))  {
                            nlist.add(model);
                        }
                    }else{
                        nlist.add(model);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            } else {
                results.values = originalData;
                results.count = originalData.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<FAQModel>) results.values;
            notifyDataSetChanged();
        }

    }

}
