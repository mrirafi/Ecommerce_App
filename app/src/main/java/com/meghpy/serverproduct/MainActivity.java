package com.meghpy.serverproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private String thumbnail,images, rating, price,title,description,discountPercentage;
    private ImageSlider bannerSlider;
    private HashMap<String,String> hashMap;
    private ArrayList <HashMap <String,String>> arrayList = new ArrayList<>();
    private  ShimmerFrameLayout shimmerFrameLayout;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        bannerSlider = findViewById(R.id.bannerSlider);

        ArrayList<SlideModel> imageLists = new ArrayList<>();
        imageLists.add(new SlideModel("https://dummyjson.com/image/i/products/5/3.jpg", null));
        imageLists.add(new SlideModel("https://dummyjson.com/image/i/products/6/3.png", null));
        bannerSlider.setImageList(imageLists);

        data();

    }

    //=====================================
    private void data(){
        shimmerFrameLayout = findViewById(R.id.shimmerEffect);
        gridView.setVisibility(View.INVISIBLE);

        String url ="https://dummyjson.com/products";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString("title");
                        rating = jsonObject.getString("rating");
                        price = jsonObject.getString("price");
                        description = jsonObject.getString("description");
                        discountPercentage = jsonObject.getString("discountPercentage");
                        thumbnail = jsonObject.optString("thumbnail");
                        images = jsonObject.getString("images");


                        hashMap = new HashMap<>();
                        hashMap.put("thumbnail",thumbnail);
                        hashMap.put("title",title);
                        hashMap.put("rating", rating);
                        hashMap.put("images", images);
                        hashMap.put("description", description);
                        hashMap.put("discountPercentage", discountPercentage+"%");
                        hashMap.put("price", price);
                        arrayList.add(hashMap);

                    }

                    MyAdapter myAdapter = new MyAdapter();
                    gridView.setAdapter(myAdapter);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> hashMap = arrayList.get(position);
                title = hashMap.get("title");
                description = hashMap.get("description");
                thumbnail = hashMap.get("thumbnail");
                images = hashMap.get("images");
                rating = hashMap.get("rating");
                price = hashMap.get("price");

                Product_Details_Activity.TITLE = title;
                Product_Details_Activity.DES = description;
                Product_Details_Activity.PRICE = price;
                Product_Details_Activity.RATING = rating;
                Product_Details_Activity.IMGSLIDE = images;

                Intent intent = new Intent(MainActivity.this,Product_Details_Activity.class);
                intent.putExtra("thumbnail",images);
                startActivity(intent);

            }
        });

    }

    //=====================================

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = inflater.inflate(R.layout.design_product,parent,false);

            ImageView itemImage = myView.findViewById(R.id.itemImage);
            TextView itemTitle = myView.findViewById(R.id.itemTitle);
            TextView itemRating = myView.findViewById(R.id.itemRating);
            TextView itemPrice = myView.findViewById(R.id.itemPrice);
            TextView itemDiscount = myView.findViewById(R.id.itemDiscount);



            HashMap<String,String> hashMap = arrayList.get(position);
            title = hashMap.get("title");
            description = hashMap.get("description");
            thumbnail = hashMap.get("thumbnail");
            rating = hashMap.get("rating");
            price = hashMap.get("price");
            discountPercentage = hashMap.get("discountPercentage");
            Picasso.get().load(thumbnail)
                    .placeholder(R.drawable.img_background)
                    .into(itemImage);


            itemRating.setText(rating);
            itemTitle.setText(title);
            itemPrice.setText(price);
            itemDiscount.setText("(-" + discountPercentage + ")");


            return myView;
        }
    }

//========================


}