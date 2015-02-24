package com.mx.hotbook.android.ui.view;

import android.app.FragmentTransaction;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

public class RegistrationLocation extends AbstractUI 
                               implements GoogleMap.OnMyLocationChangeListener {

  private GoogleMap googleMap = null;
  private MapFragment mapFragment = null;
	  
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.registration_location, container, false);
	mapFragment = MapFragment.newInstance();
	FragmentTransaction fragmentTransaction =getFragmentManager()
					                         .beginTransaction();
    fragmentTransaction.add(R.id.mapContainer, mapFragment);
	fragmentTransaction.commit();
	return view;
  }
	  
  public void onStart() {
	super.onStart();
	googleMap= mapFragment.getMap();
	googleMap.setMyLocationEnabled(true);
	googleMap.setOnMyLocationChangeListener(this);
  }
	  
  @Override
  public void onMyLocationChange (Location location) {
	LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
  }
}
