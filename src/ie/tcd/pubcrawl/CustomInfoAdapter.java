package ie.tcd.pubcrawl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;



class CustomInfoAdapter implements InfoWindowAdapter {
	private Context context;
		CustomInfoAdapter(Context c) {
			context = c;
			
		}

	public View getInfoContents(Marker arg0) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		
		View v = inflater.inflate(R.layout.info_window_layout, null);
		
		LatLng latLng = arg0.getPosition();
		
		TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
		
		TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
		
		tvLat.setText("Latitude:" + latLng.latitude);
		
		tvLng.setText("Longitude:"+ latLng.longitude);
		
		return v;
	}

	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
