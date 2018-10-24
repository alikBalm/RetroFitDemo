package com.alikbalm.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String TOKEN = "Qw+XAFGyoYrYzQDuSFFipDBXOIYhfwcXmOAZDNR/9jXLODa2s5RSQw/rZfQDQSo/NYmrYeLQ+aSkCpYBVlt1aGu1j25eysZl1EjrbGff6fNTM10l1ogakn6HcTn25U/b";
    private String BASE_URL = "https://office.oberon-alfa.ru" ;

    Retrofit retrofit;

    String fileURI;

    TextView id, unread_messages, total_messages_count, time_modified;

    Button getFolder;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        fileURI = data.getData().getPath();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        unread_messages = findViewById(R.id.unread_messages);
        total_messages_count = findViewById(R.id.total_messages_count);
        time_modified = findViewById(R.id.time_modified);
        getFolder = findViewById(R.id.getFolder);

        getFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("*/*");
                startActivityForResult(i, 1);
            }
        });





        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlyOfficeApi service = retrofit.create(OnlyOfficeApi.class);
        Call<ResponseBody> rootMailFoldersList = service.rootMailFolders(TOKEN);

        /*rootMailFoldersList.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                Log.i("!!! Response", new Gson().toJson(response.body()));

                try {
                    JSONObject resp = new JSONObject(new Gson().toJson(response.body()));
                } catch (JSONException e) {
                    Log.i("!!! JSONException", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });*/

        rootMailFoldersList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {


                        String string1 = response.body().string();
                        Log.i("!!! Response", string1);

                        //id.setText(id.getText() + string1);
                        /////////////

                        JSONObject resp = new JSONObject(string1);
                        JSONArray responseArray = resp.getJSONArray("response");

                        for (int i = 0; i < responseArray.length(); i++) {

                            JSONObject respObj = responseArray.getJSONObject(i);

                            id.setText(id.getText() + respObj.getString("id") + " | ");
                            unread_messages.setText(unread_messages.getText() + respObj.getString("unread_messages") + " | ");
                            total_messages_count.setText(total_messages_count.getText() + respObj.getString("total_messages_count") + " | ");
                            time_modified.setText(time_modified.getText() + respObj.getString("time_modified") + " | ");

                        }
                    }
                } catch (IOException e) {

                    Log.i("!!! IOEsception", e.getMessage());

                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.i("!!! JSONException", e.getMessage());
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.i("!!! Error", t.getMessage());

            }
        });

    }


}