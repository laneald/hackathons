package com.example.hackapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MiddlewareActivity extends FragmentActivity {
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        final String zipCodeValue = extras.getString("zipCodeValue");
        setContentView(R.layout.check_response);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=password&scopes=accounts%3Aread%2Ctransactions%3Aread%2Ctransfers%3Awrite%2Caccount%3Awrite%2Cinstitution-users%3Aread%2Crecipients%3Aread%2Crecipients%3Awrite%2Crecipients%3Adelete%2Cdisclosures%3Aread%2Cdisclosures%3Awrite&username=HACKATHONUSER001&password=test123");
        Request request = new Request.Builder()
                .url("http://ncrqe-qe.apigee.net/digitalbanking/oauth2/v1/token")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic NDAxZGFhYjIyZTNiNDAxNjgwZTY4ZTk0NmNiZWI5YzI6MDgxMDBmYjIyYWYzNDBmZGIwZDBjYmNjZTViMGJjMmU=")
                .addHeader("transactionId", "f3df8be7-621d-4278-994a-1f3d6a156c1d")
                .addHeader("institutionId", "DI0516")
                .addHeader("Accept", "application/json")
                .addHeader("Date", "Sat, 02 Nov 2019 07:14:10 GMT")
                .addHeader("User-Agent", "PostmanRuntime/7.19.0")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "048b8555-3f60-471f-b2cd-bad0bdfaa644,f9688438-1098-4b0a-b68d-ab05c30085b2")
                .addHeader("Host", "ncrqe-qe.apigee.net")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Length", "278")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        response.body().close();
                        Request request = new Request.Builder()
                                .url("http://ncrqe-qe.apigee.net/digitalbanking/db-transactions/v1/transactions?accountId=rf5ao6Qclwsth9OfOvUb-EeV1m2BfmTzUEALGLQ3ehU&hostUserId=HACKATHONUSER100")
                                .get()
                                .addHeader("Authorization", "Bearer " + jsonObject.getString("access_token"))
                                .addHeader("transactionId", "fdd1542a-bcfd-439b-a6a1-5a064023b0ce")
                                .addHeader("Accept", "application/json")
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                JSONObject resObject = null;
                                try {
                                    resObject = new JSONObject(response.body().string());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                response.body().close();
                                try {
                                    TextView txt = findViewById(R.id.editText);
                                    //String result = resObject.getString("transactions");
                                    JSONArray jArray = resObject.getJSONArray("transactions");
                                    String transactionNum[] = new String[10];
                                    for(int i = 0; i < 8; i++) {
                                        JSONObject object = jArray.getJSONObject(i);
                                        transactionNum[i] = object.getString("transactionNumber");
                                    }

                                    Map<String, String> mMap = new HashMap<String,String>();
                                    mMap.put(transactionNum[0], "Walmart");
                                    mMap.put(transactionNum[1], "QuikTrip");
                                    mMap.put(transactionNum[2], "Publix");
                                    mMap.put(transactionNum[3], "Walgreens");
                                    mMap.put(transactionNum[4], "Cvs");
                                    mMap.put(transactionNum[5], "Kroger");
                                    mMap.put(transactionNum[6], "Planet Fitness");
                                    mMap.put(transactionNum[7], "LA Fitness");

                                    Geocoder geo = new Geocoder(MiddlewareActivity.this);


                                    String[] address = new String[8];
                                    int x = 0;

                                    Iterator iter = mMap.entrySet().iterator();

                                    while(iter.hasNext()) {
                                        Map.Entry mEntry = (Map.Entry) iter.next();
                                        address[x++] = mEntry.getValue()+", " + zipCodeValue;
                                    }

                                    List<Address> addr1 = new ArrayList<Address>();
                                    List<Address> addr2 = new ArrayList<Address>();
                                    List<Address> addr3 = new ArrayList<Address>();
                                    List<Address> addr4 = new ArrayList<Address>();
                                    List<Address> addr5 = new ArrayList<Address>();
                                    List<Address> addr6 = new ArrayList<Address>();
                                    List<Address> addr7 = new ArrayList<Address>();
                                    List<Address> addr8 = new ArrayList<Address>();

                                    try {
                                        addr1 = geo.getFromLocationName(address[0], 1);
                                        addr2 = geo.getFromLocationName(address[1], 1);
                                        addr3 = geo.getFromLocationName(address[2], 1);
                                        addr4 = geo.getFromLocationName(address[3], 1);
                                        addr5 = geo.getFromLocationName(address[4], 1);
                                        addr6 = geo.getFromLocationName(address[5], 1);
                                        addr7 = geo.getFromLocationName(address[6], 1);
                                        addr8 = geo.getFromLocationName(address[7], 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    Map<String,ArrayList<String>> iMap = new HashMap();
                                    ArrayList<String> store1 = new ArrayList<String>();
                                    ArrayList<String> store2 = new ArrayList<String>();
                                    ArrayList<String> store3 = new ArrayList<String>();
                                    ArrayList<String> store4 = new ArrayList<String>();
                                    ArrayList<String> store5 = new ArrayList<String>();
                                    ArrayList<String> store6 = new ArrayList<String>();
                                    ArrayList<String> store7 = new ArrayList<String>();
                                    ArrayList<String> store8 = new ArrayList<String>();

                                    store1.add(String.valueOf(addr1.get(0).getLatitude()));
                                    store1.add(String.valueOf(addr1.get(0).getLongitude()));
                                    iMap.put("Walmart", store1);
                                    store2.add(String.valueOf(addr2.get(0).getLatitude()));
                                    store2.add(String.valueOf(addr2.get(0).getLongitude()));
                                    iMap.put("QuikTrip", store2);
                                    store3.add(String.valueOf(addr3.get(0).getLatitude()));
                                    store3.add(String.valueOf(addr3.get(0).getLongitude()));
                                    iMap.put("Publix", store3);
                                    store4.add(String.valueOf(addr4.get(0).getLatitude()));
                                    store4.add(String.valueOf(addr4.get(0).getLongitude()));
                                    iMap.put("Walgreens", store4);
                                    store5.add(String.valueOf(addr5.get(0).getLatitude()));
                                    store5.add(String.valueOf(addr5.get(0).getLongitude()));
                                    iMap.put("Cvs", store5);
                                    store6.add(String.valueOf(addr6.get(0).getLatitude()));
                                    store6.add(String.valueOf(addr6.get(0).getLongitude()));
                                    iMap.put("Kroger", store6);
                                    store7.add(String.valueOf(addr7.get(0).getLatitude()));
                                    store7.add(String.valueOf(addr7.get(0).getLongitude()));
                                    iMap.put("Planet Fitness", store7);
                                    store8.add(String.valueOf(addr8.get(0).getLatitude()));
                                    store8.add(String.valueOf(addr8.get(0).getLongitude()));
                                    iMap.put("LA Fitness", store8);


                                    HashMap<String,ArrayList<String>> aMap = new HashMap<>();
                                    ArrayList<String> apt1 = new ArrayList<String>();
                                    ArrayList<String> apt2 = new ArrayList<String>();
                                    ArrayList<String> apt3 = new ArrayList<String>();

                                    apt1.add("33.820386");
                                    apt1.add("-84.2307692");
                                    aMap.put("Apartment1", apt1);
                                    apt2.add("33.822267");
                                    apt2.add("-84.231973");
                                    aMap.put("Apartment2", apt2);
                                    apt3.add("38.2029363");
                                    apt3.add("-84.2317686");
                                    aMap.put("Apartment3", apt3);

                                    Intent i = new Intent(MiddlewareActivity.this, MapsActivity.class);
                                    i.putExtra("ApartmentsList", aMap);
                                    startActivity(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
