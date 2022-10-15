package com.meghpy.serverproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Product_Details_Activity extends AppCompatActivity {

     ImageSlider images;
    TextView tvTitle, tvDes, itemPrice, itemRating;



    public static String TITLE = "";
    public static String DES = "";
    public static String PRICE = "";
    public static String RATING = "";
    public static String IMGSLIDE = "";

//    public static Bitmap MYBITMAP = null;

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvDes = findViewById(R.id.tvDes);
        itemPrice = findViewById(R.id.itemPrice);
        itemRating = findViewById(R.id.itemRating);
        images = findViewById(R.id.images);

        tvTitle.setText(TITLE);
        tvDes.setText(DES);
        itemPrice.setText(PRICE);
        itemRating.setText(RATING);

//        if (MYBITMAP!=null) coverImage.setImageBitmap(MYBITMAP);




        ArrayList<SlideModel> imageList = new ArrayList<>();
        for (int i=0; i<4; i++){
        imageList.add(new SlideModel(IMGSLIDE, null));
        }
        images.setImageList(imageList);




    }
}