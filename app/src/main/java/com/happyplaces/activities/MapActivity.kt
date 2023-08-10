package com.happyplaces.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.happyplaces.R
import com.happyplaces.models.HappyPlaceModel
import kotlinx.android.synthetic.main.activity_map.*

/**
 * https://www.raywenderlich.com/230-introduction-to-google-maps-api-for-android-with-kotlin
 */
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mHappyPlaceDetails: HappyPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
//if there is extra info in the intent that launched this activity of the place details
        //then adssign the happy place details via the model to our happyPlace variable of type Place
        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            mHappyPlaceDetails =
                    intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as HappyPlaceModel
        }
//
        if (mHappyPlaceDetails != null) {
//setting the support action bar
            setSupportActionBar(toolbar_map)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
           //assign the title of the place to the toolbar area
            supportActionBar!!.title = mHappyPlaceDetails!!.title
// placing the onBackPressed Button to the toolbar also
            toolbar_map.setNavigationOnClickListener {
                onBackPressed()
            }
//xml types for API's use the type Fragment(in the XML) so we use the fragment
            //keyword to define its functionality
            val supportMapFragment: SupportMapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
        }
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
    override fun onMapReady(googleMap: GoogleMap) {

        /**
         * Add a marker on the location using the latitude and longitude and move the camera to it.
         */
        val position = LatLng(
                mHappyPlaceDetails!!.latitude,
                mHappyPlaceDetails!!.longitude
        )
        //adding a marker to the maps fragment interface at position of type latLng and the location detail
        googleMap.addMarker(MarkerOptions().position(position).title(mHappyPlaceDetails!!.location))
       //adding the zoom functionality to the marked position of the map with a value of zooming which the developer can define
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, 15f)
        googleMap.animateCamera(newLatLngZoom)
    }
}