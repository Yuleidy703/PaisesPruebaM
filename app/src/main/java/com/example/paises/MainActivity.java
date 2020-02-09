package com.example.paises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mindorks.placeholderview.PlaceHolderView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    ArrayList<Paises> listaPaises;
    PlaceHolderView phvPaises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.json-generator.com/api/json/get/coVvSGEWKW?indent=2", datos,this, this);
        ws.execute("");


    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonObj = new JSONObject(result);
        JSONObject datos = jsonObj.getJSONObject("RestResponse");
        JSONArray paises= datos.getJSONArray("result");

        listaPaises = new ArrayList<>();
        phvPaises = (PlaceHolderView)findViewById(R.id.phvPaises);
        phvPaises.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 3));
        for (int i = 0; i < paises.length(); i++) {
            JSONObject p = paises.getJSONObject(i);
            listaPaises.add(new Paises(p));
        }

        AdaptadorPaises adaptador = new AdaptadorPaises(listaPaises,this);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, detalle_pais.class);
                Bundle b = new Bundle();
                String cod = listaPaises.get(phvPaises.getChildAdapterPosition(v)).getAlfacode();
                Toast.makeText(MainActivity.this, cod, Toast.LENGTH_SHORT).show();
                b.putString("CODIGO", cod);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        phvPaises.setAdapter(adaptador);
    }




}
