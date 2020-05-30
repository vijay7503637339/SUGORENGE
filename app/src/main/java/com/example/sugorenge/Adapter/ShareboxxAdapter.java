package com.example.sugorenge.Adapter;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugorenge.R;
import com.example.sugorenge.Sub_Staff_Category;
import com.example.sugorenge.model.ShareboxModel;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShareboxxAdapter extends RecyclerView.Adapter<ShareboxxAdapter.ViewHolder> {

    ArrayList<ShareboxModel> arrayList;
    Context context;
    public static final String IMAGE_BASE_URL = "https://imscertificate.com/servicehat/uploads/allimages/";

    public ShareboxxAdapter(ArrayList<ShareboxModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shareboxlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final String url=IMAGE_BASE_URL+arrayList.get(position).getImage();

        Picasso.with(context)
                .load(   IMAGE_BASE_URL+arrayList.get(position).getImage())
                .into( holder.Image);

        holder.text.setText(arrayList.get(position).getStatus());
        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Sub_Staff_Category.class);
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("name",arrayList.get(position).getStatus());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView text;
        LinearLayout layout1;
       // TextView fileName,imaegSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.image1);
            text = itemView.findViewById(R.id.text);
            layout1 = itemView.findViewById(R.id.layout1);

            //imaegSize = itemView.findViewById(R.id.size);
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
    private void showDialogToShare(){



    }
}
