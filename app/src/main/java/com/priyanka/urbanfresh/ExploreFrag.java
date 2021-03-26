package com.priyanka.urbanfresh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.priyanka.urbanfresh.Adapter.ExploreAdapter;
import com.priyanka.urbanfresh.databinding.FragmentExploreBinding;
import com.priyanka.urbanfresh.model.datamodel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class ExploreFrag extends Fragment {

List<datamodel> mDatamodels;
String Banner,AdObject;
    String request_url = "http://139.59.83.144:9050/api/home_test_section?category_id=2";
    private static final String TAG_image = "category_picture";
    private static final String TAG_name = "category_name";
    RecyclerView myItems;
 //   FragmentExploreBinding binding;
    ImageView mImageView,adimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        myItems = rootView.findViewById(R.id.explorefrag);
        mImageView=rootView.findViewById(R.id.bannerimage);
        adimage=rootView.findViewById(R.id.adbanner);

        mDatamodels= new ArrayList<>();
        new GetData().execute(request_url);
      //  binding = FragmentExploreBinding.inflate(inflater,container,false);
    //   Log.d("getActivity",Banner);
        return rootView;

    }
    public class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
          //
            if (getActivity() == null) {
                return;
            }
            Glide.with(getActivity()).load(Banner).into(mImageView);
            Log.d("caregoryarray", String.valueOf(getActivity()));

            Glide.with(getActivity()).load(AdObject).into(adimage);
            ExploreAdapter exploreAdapter=new ExploreAdapter(getActivity(),mDatamodels);
            myItems.setLayoutManager(new GridLayoutManager(getActivity(), 3));
             myItems.setAdapter(exploreAdapter);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity){
            a=(Activity) context;
        }
    }

    public String POST(String url){
        InputStream inputStream = null;
        String result = "";

        try {

            // Toast.makeText(getActivity(),"HttpAsyncTask().execute",Toast.LENGTH_LONG).show();
            System.out.print("POST IS WORKING");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";

            // Student person1=new Student();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("category_id", 2);


            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
           // Log.d("Result", result);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        //ServiceHandler sh = new ServiceHandler();

        String jsonStr =result;
        if (jsonStr != null) {
            try {

                JSONObject jsonObj = new JSONObject(jsonStr);
                if(jsonObj.getBoolean("success")){
                 //   Log.d("jsonStr", jsonStr);
                    JSONArray banner = jsonObj.getJSONArray("components").getJSONObject(0).getJSONArray("StaticBanner");
                    JSONObject mJSONObject = banner.getJSONObject(0);
                    Log.d("banner", mJSONObject.getString("banner_image"));
                    Banner=mJSONObject.getString("banner_image");
                    JSONArray array = jsonObj.getJSONArray("components").getJSONObject(1).getJSONArray("categorydata");
                    JSONArray adarray = jsonObj.getJSONArray("components").getJSONObject(2).getJSONArray("AdsBanner");
                    JSONObject mObject = adarray.getJSONObject(0);
                    Log.d("adarray", mObject.getString("banner_image"));
                    AdObject=mObject.getString("banner_image");
          //     Log.d("caregoryarray", String.valueOf(adarray));

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        datamodel    datamodel1 = new datamodel();
                        datamodel1.setName(jsonObject1.getString(TAG_name));
                        datamodel1.setImage((jsonObject1.getString(TAG_image)));

                        mDatamodels.add(datamodel1);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

        } else {


            Log.e("ServiceHandler", "Couldn't get any data from the url");

        }


        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }
}


