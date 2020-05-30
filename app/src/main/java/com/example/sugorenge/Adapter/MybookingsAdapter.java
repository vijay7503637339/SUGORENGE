package com.example.sugorenge.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugorenge.Childcategory;
import com.example.sugorenge.R;
import com.example.sugorenge.model.BookinggetModel;
import com.example.sugorenge.model.Subcategorymodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.sugorenge.Adapter.ShareboxxAdapter.IMAGE_BASE_URL;

public class MybookingsAdapter extends RecyclerView.Adapter<MybookingsAdapter.ViewHolder> {

    ArrayList<BookinggetModel> arrayList;
    Context context;

    public MybookingsAdapter(ArrayList<BookinggetModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usermybookinglayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.text.setText(arrayList.get(position).getTitle());
        holder.prize.setText("₹" + arrayList.get(position).getPrice());
        holder.date.setText("Date: " + arrayList.get(position).getDate());
        holder.idservice.setText("" + arrayList.get(position).getParentcatname() + " for " + arrayList.get(position).getTitle());
        Picasso.with(context)
                .load(IMAGE_BASE_URL + arrayList.get(position).getIconimage())
                .into(holder.greywebicon);


        holder.viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = LayoutInflater.from(context);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View convertView = inflater.inflate(R.layout.viewindetaildiaolog, null);

                 TextView nameew = convertView.findViewById(R.id.name);
                TextView mobile = convertView.findViewById(R.id.mobile);
                TextView adress = convertView.findViewById(R.id.adress);
                TextView servicetype = convertView.findViewById(R.id.servicetypee);
                TextView Amount = convertView.findViewById(R.id.Amount);
                TextView qty = convertView.findViewById(R.id.qty);
                ImageView cross = convertView.findViewById(R.id.cross);
                TextView discription = convertView.findViewById(R.id.discription);
                //  EditText discription = convertView.findViewById(R.id.discription);
                nameew.setText("OD2020"+arrayList.get(position).getId());
                adress.setText(arrayList.get(position).getAddress());
                mobile.setText(arrayList.get(position).getMobile());
                Amount.setText("₹"+arrayList.get(position).getPrice()+"/-");
                qty.setText(arrayList.get(position).getQty());
                discription.setText(arrayList.get(position).getDate());
                servicetype.setText(arrayList.get(position).getParentcatname() + " for " + arrayList.get(position).getTitle());


                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();


                    }
                });

                alertDialog.setView(convertView);
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text, prize, date, idservice;
        Button viewdetail;
        ImageView greywebicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            text = itemView.findViewById(R.id.name);
            greywebicon = itemView.findViewById(R.id.greywebicon);
            prize = itemView.findViewById(R.id.prize);
            date = itemView.findViewById(R.id.date);
            idservice = itemView.findViewById(R.id.idservice);
            viewdetail = itemView.findViewById(R.id.viewdetail);
        }
    }


}
