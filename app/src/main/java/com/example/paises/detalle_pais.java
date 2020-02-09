package com.example.paises;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class detalle_pais extends AppCompatActivity implements Asynchtask, OnMapReadyCallback {

    TextView nombrePais;
    TextView coordenadas;
    TextView capital;
    GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mapa);

        Bundle bundle = this.getIntent().getExtras();
        String codigo = bundle.getString("CODIGO");
        Map<String, String> dt = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/"+codigo+".json", dt,this, this);
        ws.execute("");
    }


    String coord1="";
    String coord2="";
    Double n1;
    Double n2;
    Double n3;
    Double n4;
    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonObj = new JSONObject(result);
        JSONObject datos = jsonObj.getJSONObject("Results");
        nombrePais=(TextView)findViewById(R.id.lblPais);
        String nombre = datos.getString("Name");
        nombrePais.setText(nombre);

        capital = (TextView)findViewById(R.id.lblCapital);
        String cap = datos.getJSONObject("Capital").getString("Name");
        capital.setText(cap);
        String geoPT1= datos.getJSONObject("Capital").getJSONArray("GeoPt").getString(0);
        String geoPT2= datos.getJSONObject("Capital").getJSONArray("GeoPt").getString(1);
        coord1=geoPT1;
        coord2=geoPT2;

        JSONObject coord = datos.getJSONObject("GeoRectangle");
        String norte = String.valueOf(coord.getDouble("North"));
        n1=Double.parseDouble(norte);
        String sur = String.valueOf(coord.getDouble("South"));
        n2=Double.parseDouble(sur);
        String este = String.valueOf(coord.getDouble("East"));
        n3=Double.parseDouble(este);
        String oeste = String.valueOf(coord.getDouble("West"));
        n4=Double.parseDouble(oeste);
        coordenadas = (TextView)findViewById((R.id.lblCoordenadas));
        String coordenada = "Norte: "+norte+"\nSur: "+sur+"\nEste: "+este+"\nOeste: "+oeste;
        coordenadas.setText(coordenada);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    com.google.android.gms.maps.model.PolylineOptions PolylineOptions = new PolylineOptions();
    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        //MOVIMIENTO DE LA CAMARA
        LatLng latLng = new LatLng(Double.parseDouble(coord1), Double.parseDouble(coord2)); //COORDENADAS DE CENTRO
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,3);
        mapa.moveCamera(cameraUpdate);

        PolylineOptions.add(new LatLng(n1,n4 ));
        PolylineOptions.add(new LatLng(n1,n3 ));

        PolylineOptions.add(new LatLng(n1,n3 ));
        PolylineOptions.add(new LatLng(n2,n3 ));

        PolylineOptions.add(new LatLng(n2,n3 ));
        PolylineOptions.add(new LatLng(n2,n4 ));

        PolylineOptions.add(new LatLng(n2,n4 ));
        PolylineOptions.add(new LatLng(n1,n4 ));


        PolylineOptions.width(5);
        PolylineOptions.color(Color.RED);
        mapa.addPolyline(PolylineOptions);

    }
}
