package com.example.sugorenge.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugorenge.BookingConfirmation;
import com.example.sugorenge.MyInterface;
import com.example.sugorenge.R;
import com.example.sugorenge.model.Childcategorymodel;

import java.util.ArrayList;

public class ChildcategoryAdapter extends RecyclerView.Adapter<ChildcategoryAdapter.ViewHolder> {

    ArrayList<Childcategorymodel> arrayList;
    private MyInterface listener;
    Context context;
      int total=0;

    public ChildcategoryAdapter(ArrayList<Childcategorymodel> arrayList, Context context ,MyInterface listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.childcatelayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.text.setText(arrayList.get(position).getStatus());
        holder.prize.setText(arrayList.get(position).getPrice());
        final String prize=arrayList.get(position).getPrice();



        holder.addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, BookingConfirmation.class);
                intent.putExtra("p_name",arrayList.get(position).getStatus());
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("price",arrayList.get(position).getPrice());

                context.startActivity(intent);
            }
        });
         /*holder.addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll1.setVisibility(View.GONE);
                holder.ll2.setVisibility(View.VISIBLE);
                holder.count++;
                total=total+Integer.parseInt(prize )* holder.count;

                listener.foo(holder.count,total);
                 holder.number2.setText("" + holder.count);
            }
        });
          holder.dec.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 holder.count--;
                   total=total-Integer.parseInt(prize );
                 CalculateitemModel model=new CalculateitemModel();
                 model.setId(arrayList.get(position).getId());
                 model.setPrice(arrayList.get(position).getPrice());
                 model.setQuantity(""+holder.count);

                 listener.foo(holder.count,total);

                 if (holder.count>=1)
                 {

                     holder.number2.setText(""+holder.count);
                    // subtotal.setText("Subtotal  ₹"+500*holder.count);
                 }
                 else
                 {
                     holder.ll1.setVisibility( View.VISIBLE);
                     holder.ll2.setVisibility( View.GONE);

                     //proceedlayout.setVisibility(View.GONE);

                 }

             }
         });
          holder.increment.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  holder.count++;
                   total= total+Integer.parseInt(prize );

                  listener.foo(holder.count,total);

                  holder.number2.setText(""+holder.count);
              }
          });
*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text, prize;
        Button addpro;
        LinearLayout layout ;
        int count=0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            prize = itemView.findViewById(R.id.prize);
            text = itemView.findViewById(R.id.text);

            addpro = itemView.findViewById(R.id.addpro);

        }
    }

    /*private void shareImageTo(String pack, Bitmap bitmap){

        PackageManager pm = context.getPackageManager();
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
            Uri imageUri = Uri.parse(path);
            PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            waIntent.setType("image/*");
            waIntent.setPackage(pack);
            waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            waIntent.putExtra(Intent.EXTRA_TEXT, pack);
            try {
                context.startActivity(waIntent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
            Toast.makeText(context, "App not Installed", Toast.LENGTH_SHORT).show();
        }

    }
*/

}
