package com.truedigital.vhealth.ui.address;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.PermissionUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class AddressActivity extends BaseMvpActivity<AddressActivityInterface.Presenter>
        implements AddressActivityInterface.View, OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "Map";

    public static final String KEY_LATITUDE = "KEY_LATITUDE";
    public static final String KEY_LONGTITUDE = "KEY_LONGTITUDE";
    public static final String KEY_SHIPADDRESS = "KEY_SHIPADDRESS";

    private GoogleMap mMap;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation;
    private static final int DEFAULT_ZOOM = 15;
    private double latitude;
    private double longtitude;

    private TextView tv_shipping_address;
    private Button btnDone;
    private Toolbar toolbar;
    private ImageButton btnBackPress;
    private TextView tvToolbarName;

    @Override
    protected AddressActivityInterface.Presenter createPresenter() {
        return AddressActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_address;
    }

    @Override
    protected void bindView() {
        tv_shipping_address = (TextView) findViewById(R.id.tv_shipping_address);
        btnDone = (Button) findViewById(R.id.btn_done);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarName = (TextView) findViewById(R.id.tvToolbarName);
        btnBackPress = (ImageButton) findViewById(R.id.ic_button_back);
    }

    @Override
    protected void initInstance() {

    }


    @Override
    protected void initialize() {

    }

    @Override
    protected void setupView() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvToolbarName.setText(R.string.ship_address_title_activity);
        setupListener();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        updateMyLocation();
        mMap.setOnMapClickListener(onMapClickListener());
        mMap.setOnMapLongClickListener(onMapLongClickListener());
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener());
        //mMap.setOnMarkerDragListener(onMarkerDragListener());
    }


    /*
    private GoogleMap.OnMarkerDragListener onMarkerDragListener() {
        return new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                setShippingAddress(marker.getPosition());
            }
        };
    }
    */

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener() {
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mLastKnownLocation = getDeviceLocation();

                setShippingAddress(new LatLng(mLastKnownLocation.getLatitude(),
                        mLastKnownLocation.getLongitude()));

                return true;
            }
        };
    }

    private GoogleMap.OnMapClickListener onMapClickListener() {
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                setShippingAddress(latLng);
            }
        };
    }

    private GoogleMap.OnMapLongClickListener onMapLongClickListener() {
        return new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                setShippingAddress(latLng);
            }
        };
    }


    private void updateMyLocation() {
        if (mMap == null) {
            return;
        }

        // Enable the location layer. Request the location permission if needed.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mLastKnownLocation = getDeviceLocation();

        } else {
            // Uncheck the box until the layer has been enabled and request missing permission.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }


    private Location getDeviceLocation() {

        try {
            //if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                            setShippingAddress(new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()));

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            //}
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

        return  mLastKnownLocation;
    }

    private void setShippingAddress(LatLng latLng) {

        this.latitude = latLng.latitude;
        this.longtitude = latLng.longitude;

        //addNewMarker(latLng);
        tv_shipping_address.setText( getCompleteAddressString(
                latLng.latitude,
                latLng.longitude));

        String snippet = String.format(Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude);

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.ship_address_title))
                .snippet(snippet));
    }

    private void addNewMarker(LatLng latLng) {
        String snippet = String.format(Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude);

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                //.title(getString(R.string.dropped_pin))
                .title("สถานที่รับยา")
                .snippet(snippet));
    }

    /*

    public void addFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_address, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    private void setupShipAddress() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //Fragment fragmentPayment = PaymentFragment.newInstance(PaymentFragment.PAYMENT_MODE_PRODUCT, getDoctorId(), amount, doctorPrice);
        Fragment fragmentAddress = AddressFragment.newInstance("",getLatitude(),getLongtitude());

        if(fragmentAddress != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_address, fragmentAddress);
            ft.commitAllowingStateLoss();
        }

        ((AddressFragment) fragmentAddress).setOnAddressSelectedListener(new AddressFragment.OnAddressSelectedListener() {
            @Override
            public void onConfirm(double latitude, double longtitude, String name, String address) {
                Log.e("Address","lat " + latitude + " " + longtitude);
                onShipAddressSuccess(latitude, longtitude, name, address);
            }
        });

    }

    private double getLatitude() {
        return getIntent().getDoubleExtra(AddressActivity.KEY_LATITUDE,0.00);
    }

    private double getLongtitude() {
        return getIntent().getDoubleExtra(AddressActivity.KEY_LONGTITUDE,0.00);
    }

    */


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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
            Log.w("loction address", "Cannot get Address!");
        }
        return strAdd;
    }

    private void setupListener() {
        btnDone.setOnClickListener(onDoneButtonClick());
        btnBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private View.OnClickListener onDoneButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShipAddressSuccess(latitude, longtitude, getAddress());
            }
        };
    }

    private String getAddress() {
        return tv_shipping_address.getText().toString();
    }

    private void onShipAddressSuccess(double latitude, double longtitude, String address) {
        Intent output = new Intent();
        output.putExtra(KEY_LATITUDE, latitude);
        output.putExtra(KEY_LONGTITUDE, longtitude);
        output.putExtra(KEY_SHIPADDRESS, address);
        setResult(RESULT_OK, output);
        finish();
    }
}
