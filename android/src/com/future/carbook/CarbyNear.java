package com.future.carbook;
/* Added to Git */
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CarbyNear extends Activity {

	GoogleMap googleMap;
	double[] latitude = { 30.735362, 30.716667, 30.739834, 30.718064 };
	double[] longitude = { 76.829656, 76.833333, 76.782702, 76.749523 };
	String address1;
	String city1;
	private Marker marker;
	private final LatLng zoomloc = new LatLng(30.735362, 76.829656);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.carmap);

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// 20/10/14 status Showing for google play service
		// Google Play Services are
		// not available

		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {

			// Google Play Services is available then draw google map
			// with current and predefind latlng

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			googleMap.clear();
			googleMap.setMyLocationEnabled(true);
			
			googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());//custom window
			
			for (int i = 0; i < latitude.length; i++) {

				// getting address from latlng

				try {
					Geocoder geocoder;
					List<Address> addresses;
					geocoder = new Geocoder(CarbyNear.this, Locale.getDefault());
					addresses = geocoder.getFromLocation(latitude[i],
							longitude[i], 1);

					address1 = addresses.get(0).getAddressLine(0);
					city1 = addresses.get(0).getAddressLine(1);
					String country = addresses.get(0).getAddressLine(2);

				} catch (Exception e) {
					e.printStackTrace();
				}

			//create marker for add markers in google map
				
				final Marker mark = googleMap.addMarker(new MarkerOptions()
						.position(new LatLng(latitude[i], longitude[i]))
						.title(address1)
						.snippet("Kiel is cool")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_launcher)));

			}

			googleMap
					.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomloc, 15));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
	}

	// CustomInfoWindowAdapter class for setting custom view when click on
	// marker

	private class CustomInfoWindowAdapter implements InfoWindowAdapter {

		private View view;

		public CustomInfoWindowAdapter() {
			view = getLayoutInflater().inflate(R.layout.custom_info_window,
					null);
		}

		@Override
		public View getInfoContents(Marker marker) {

			if (CarbyNear.this.marker != null
					&& CarbyNear.this.marker.isInfoWindowShown()) {
				CarbyNear.this.marker.hideInfoWindow();
				CarbyNear.this.marker.showInfoWindow();
			}
			return null;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			CarbyNear.this.marker = marker;

			final ImageView image = ((ImageView) view.findViewById(R.id.badge));

			image.setImageResource(R.drawable.ic_launcher);
			final String title = marker.getTitle();
			final TextView titleUi = ((TextView) view.findViewById(R.id.title));
			if (title != null) {
				titleUi.setText(title);
			} else {
				titleUi.setText("");
			}

			final String snippet = marker.getSnippet();
			final TextView snippetUi = ((TextView) view
					.findViewById(R.id.snippet));
			if (snippet != null) {
				snippetUi.setText(snippet);
			} else {
				snippetUi.setText("");
			}

			//click listener for custom info window
			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

						@Override
						public void onInfoWindowClick(Marker arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									"it is correct!", 5000).show();
						}
					});

			return view;
		}
	}

}
