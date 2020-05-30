package com.example.sugorenge.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugorenge.Childcategory;
import com.example.sugorenge.ContactPage;
import com.example.sugorenge.MainActivity;
import com.example.sugorenge.OTPscreen;
import com.example.sugorenge.R;
import com.example.sugorenge.Sub_Staff_Category;
import com.example.sugorenge.model.ShareboxModel;
import com.example.sugorenge.model.Subcategorymodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    ArrayList<Subcategorymodel> arrayList;
    Context context;
    Boolean value;

    public SubcategoryAdapter(ArrayList<Subcategorymodel> arrayList, Context context,Boolean value) {
        this.arrayList = arrayList;
        this.context = context;
        this.value = value;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategorylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.text.setText(arrayList.get(position).getStatus());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value)
                {
                    Intent in = new Intent(context, Childcategory.class);
                    in.putExtra("id",arrayList.get(position).getId());
                    in.putExtra("name",arrayList.get(position).getStatus());
                    context.startActivity(in);
                }else
                {
                    Intent in = new Intent(context, ContactPage.class);
                    context.startActivity(in);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

          TextView text;
          LinearLayout layout;
            public ViewHolder(@NonNull View itemView) {
            super(itemView);

                layout = itemView.findViewById(R.id.layout);
              text = itemView.findViewById(R.id.text);

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
