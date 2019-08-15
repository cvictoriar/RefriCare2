package com.example.refricare;

import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker marcador;
    double lat=0.0;
    double log =0.0;
    String mensaje;
    String direccion;
    String dir;
    Lista refrix = null;

    private static int PETICION_PERMISO_LOCALIZACION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle ObjetoEnviado = getIntent().getExtras();

        if(ObjetoEnviado != null){
            refrix = (Lista) ObjetoEnviado.getSerializable("RefrigeradorL");
        }

}
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(marcador)) {
            Intent intent = new Intent(this, RegistroRefrigerador.class);
            Bundle bun = new Bundle();
            bun.putSerializable("RefrigeradorL",refrix);
            startActivity(intent);
        }
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        miUbicacion();
        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    */
        googleMap.setOnMarkerClickListener(this);
    }

public void localizacionStart(){
    LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    final boolean gpsEneabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    if(!gpsEneabled){
        Intent SettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(SettingsIntent);
    }

}
public void setLocation(Location loc){
        if(loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0){
            try{
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                if(!list.isEmpty()){
                    Address DirCalle = list.get(0);
                    direccion = (DirCalle.getAddressLine(0));
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

}
    public void setLocationByText(String loc){
        if(!loc.isEmpty()){
            try{
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocationName(loc,10);
                if(!list.isEmpty()){
                    Address DirCalle = list.get(0);
                    lat = (DirCalle.getLatitude());
                    log = (DirCalle.getLongitude());
                    direccion = (DirCalle.getAddressLine(0));
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }
    public void AgregarMarcador(double lat, double log){
    LatLng coordenadas = new LatLng(lat,log);
    CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,16);

    if(marcador != null) marcador.remove();
    marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Direccion: "+direccion).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView())));
    mMap.animateCamera(miUbicacion);

    }
    public  void ActualizarUbicacion(Location location){
        if(location != null){
            lat = location.getLatitude();
            log = location.getLongitude();
            AgregarMarcador(lat,log);
        }
    }
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

                ActualizarUbicacion(location);
                setLocation(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            mensaje = ("GPS Activado");

            Mensaje();
        }

        @Override
        public void onProviderDisabled(String s) {
            mensaje = ("GPS Desactivado");
            localizacionStart();
            Mensaje();
        }
    };


    private Bitmap getMarkerBitmapFromView() {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.mapa);


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);

        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void miUbicacion(){

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PETICION_PERMISO_LOCALIZACION);
            return;
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,locListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locListener);


        }
    }
    private void UbicacionTexto(){

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PETICION_PERMISO_LOCALIZACION);
            return;
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(lat);
            location.setLongitude(log);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,locListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locListener);


        }
    }
    public void Mensaje(){
        Toast toast = Toast.makeText(this,mensaje,Toast.LENGTH_SHORT);
        toast.show();
    }
}
