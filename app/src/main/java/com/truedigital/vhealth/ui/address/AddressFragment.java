package com.truedigital.vhealth.ui.address;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class AddressFragment extends BaseMvpFragment<AddressFragmentInterface.Presenter>
        implements AddressFragmentInterface.View{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Location myLocation;
    private String address;
    private double latitude;
    private double longtitude;
    private EditText edit_address;
    private Button btnDone;
    private GoogleMap mMap;

    public AddressFragment() {
        super();
    }


    public static AddressFragment newInstance(String address, double latitude, double longtitude) {
        AddressFragment fragment = new AddressFragment();

        Bundle args = new Bundle();
        fragment.address = address;
        fragment.latitude = latitude;
        fragment.longtitude = longtitude;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public AddressFragmentInterface.Presenter createPresenter(){
        return AddressFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_address;
    }

    @Override
    public void bindView( View view ){
        edit_address = (EditText) view.findViewById(R.id.edit_shipping_address);
        btnDone = (Button) view.findViewById(R.id.btn_done);
    }

    @Override
    public void setupInstance(){

    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.ship_address_title_activity,true);
    }

    @Override
    public void setupView(){
        showToolbar();

        if (this.latitude == 0) {
            this.latitude = 13.736717;
            this.longtitude = 100.523186;
        }
        setupMap(latitude, longtitude);
        setupListener();
    }

    private void setupListener() {
        btnDone.setOnClickListener(onDoneButtonClick());
    }

    private View.OnClickListener onDoneButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }


    @Override
    public void initialize(){

    }

    void setupMap(final double latitude, final double longtitude) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fg_map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latitude,
                        longtitude);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude,longtitude))
                        //.title(getString(R.string.dropped_pin))
                        .title("สถานที่รับยา")
                        .snippet(snippet));



                mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
                mMap.setOnMyLocationClickListener(onMyLocationClickListener);
                mMap.setOnMapLongClickListener(onMapLongClickListener());

                enableMyLocationIfPermitted();
            }
        });

        edit_address.setText( getCompleteAddressString(this.latitude,
                this.longtitude));

    }


    private GoogleMap.OnMapLongClickListener onMapLongClickListener() {
        return new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                mMap.clear(); //clear old markers

                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        //.title(getString(R.string.dropped_pin))
                        .title("สถานที่รับยา")
                        .snippet(snippet));

                //marker = map.addMarker(markerOptions).position(new Latlng(31.647316, 74.763791));

                edit_address.setText( getCompleteAddressString(latLng.latitude,
                        latLng.longitude));

                latitude = latLng.latitude;
                longtitude = latLng.longitude;
            }
        };
    }


    private OnMapReadyCallback onMapReadyCallback() {
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
                mMap.setOnMyLocationClickListener(onMyLocationClickListener);
                enableMyLocationIfPermitted();

                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMinZoomPreference(11);

            }
        };
    }

    private void enableMyLocationIfPermitted() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {

            mMap.setMyLocationEnabled(true);
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            this.latitude = myLocation.getLatitude();
            this.longtitude = myLocation.getLongitude();
            LatLng latLng = new LatLng(this.latitude, this.longtitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14), 1500, null);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));


            /*
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14), 1500, null);

            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                    .title("สถานที่ส่งยา")
            );
            */


            /*
            //..
            mMap.clear();
            LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                    .title("สถานที่ส่งยา")
            );
            */
            Log.e("Lat Long : ", myLocation.getLatitude() + " " + myLocation.getLongitude());
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(getActivity(), "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(13.736717, 100.523186);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);
                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                }
            };


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("loction address", strReturnedAddress.toString());
            } else {
                Log.w("loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("loction address", "Canont get Address!");
        }
        return strAdd;
    }
}

