package com.mx.hotbook.android.ui.view;

import java.io.IOException;
import java.util.List;

import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

public class RegistrationLocation extends AbstractUI 
                               implements GoogleMap.OnMyLocationChangeListener {

  private GoogleMap googleMap = null;
  private MapFragment mapFragment = null;
  private MarkerOptions markerOptions;
  private LatLng latLng;
  private ImageButton ibFindLocation = null;
  
	  
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.registration_location, container, false);
	mapFragment = MapFragment.newInstance();
	ibFindLocation = (ImageButton) view.findViewById(R.id.ibFindLocation);
	FragmentTransaction fragmentTransaction =getFragmentManager()
					                         .beginTransaction();
    fragmentTransaction.add(R.id.mapContainer, mapFragment);
	fragmentTransaction.commit();
	return view;
  }
  
  public void onClickIbFindLocation(View clickedView){
	  EditText etLocation = (EditText) findViewById(R.id.etAddress);
		String location = etLocation.getText().toString();
		
		if(location!=null && !location.equals("")){
			new GeocoderTask().execute(location);
		}
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
	googleMap.setOnMyLocationChangeListener(null);
  }
  
//An AsyncTask class for accessing the GeoCoding Web Service
		private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

			@Override
			protected List<Address> doInBackground(String... locationName) {
				// Creating an instance of Geocoder class
				Geocoder geocoder = new Geocoder(getBaseContext());
				List<Address> addresses = null;
				
				try {
					// Getting a maximum of 3 Address that matches the input text
					addresses = geocoder.getFromLocationName(locationName[0], 3);
				} catch (IOException e) {
					e.printStackTrace();
				}			
				return addresses;
			}
			
			
			@Override
			protected void onPostExecute(List<Address> addresses) {			
		        
		        if(addresses==null || addresses.size()==0){
					Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
				}
		        
		        // Clears all the existing markers on the map
		        googleMap.clear();
				
		        // Adding Markers on Google Map for each matching address
				for(int i=0;i<addresses.size();i++){				
					
					Address address = (Address) addresses.get(i);
					
			        // Creating an instance of GeoPoint, to display in Google Map
			        latLng = new LatLng(address.getLatitude(), address.getLongitude());
			        
			        String addressText = String.format("%s, %s",
	                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
	                        address.getCountryName());

			        markerOptions = new MarkerOptions();
			        markerOptions.position(latLng);
			        markerOptions.title(addressText);

			        googleMap.addMarker(markerOptions);
			        
			        // Locate the first location
			        if(i==0)			        	
						googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng)); 	
				}			
			}		
		}
}
