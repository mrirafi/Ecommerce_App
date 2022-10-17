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
    String thumbnail,images;
    private ImageSlider bannerSlider;
    private HashMap<String,String> hashMap;
    private HashMap<String,String> imgMap;
    private ArrayList <HashMap <String,String>> arrayList = new ArrayList<>();
    private ArrayList <HashMap <String,String>> arrayList2 = new ArrayList<>();

    ShimmerFrameLayout shimmerFrameLayout;


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
                        String title = jsonObject.getString("title");
                        String rating = jsonObject.getString("rating");
                        String price = jsonObject.getString("price");
                        String description = jsonObject.getString("description");
                         images = jsonObject.getString("images");
                        String discountPercentage = jsonObject.getString("discountPercentage");
                         thumbnail = jsonObject.optString("thumbnail");

//                        JSONArray imageArray = jsonObject.getJSONArray("images");
//                        for (int x=0; x<imageArray.length(); x++){
//                            String images = imageArray.getString(x);
//
//                            Log.d("pic",images);
//
////                            imgMap = new HashMap<>();
////                            for (int p=0; p<images.length(); p++){
////                                imgMap.put("images",images);
////                            }
//                            arrayList2.add(imgMap);
//                        }


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
                String title = hashMap.get("title");
                String description = hashMap.get("description");
                thumbnail = hashMap.get("thumbnail");
                       images = hashMap.get("images");
                String rating = hashMap.get("rating");
                String price = hashMap.get("price");
                Product_Details_Activity.TITLE = title;
                Product_Details_Activity.DES = description;
                Product_Details_Activity.PRICE = price;
                Product_Details_Activity.RATING = rating;
                Product_Details_Activity.IMGSLIDE = images;

                Intent intent = new Intent(MainActivity.this,Product_Details_Activity.class);
                //    Log.d("112233",images);
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
            LinearLayout layItem = myView.findViewById(R.id.layItem);



            HashMap<String,String> hashMap = arrayList.get(position);
            String title = hashMap.get("title");
            String description = hashMap.get("description");
             thumbnail = hashMap.get("thumbnail");
     //        images = hashMap.get("images");
            String rating = hashMap.get("rating");
            String price = hashMap.get("price");
            String discountPercentage = hashMap.get("discountPercentage");
            Picasso.get().load(thumbnail)
                    .placeholder(R.drawable.img_background)
                    .into(itemImage);


            itemRating.setText(rating);
            itemTitle.setText(title);
            itemPrice.setText(price);
            itemDiscount.setText("(-" + discountPercentage + ")");

//            Random rnd = new Random();
//            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            itemCat.setBackgroundColor(color);


//            layItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////
////                    Product_Details_Activity.TITLE = title;
////                    Product_Details_Activity.DES = description;
////                    Product_Details_Activity.PRICE = price;
////                    Product_Details_Activity.RATING = rating;
////                    Product_Details_Activity.IMGSLIDE = images;
////
////
//
////                    Bitmap bitmap = ((BitmapDrawable) itemImage.getDrawable()).getBitmap();
////                    Product_Details_Activity.MYBITMAP = bitmap;
//
//
//                }
//
//            });
//

            return myView;
        }
    }



}