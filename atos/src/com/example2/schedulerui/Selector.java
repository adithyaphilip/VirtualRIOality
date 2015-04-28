package com.example2.schedulerui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.quandrum.atos.R;

@SuppressLint("HandlerLeak")
public class Selector extends FragmentActivity {
	LatLng a;
	double lat=0.0,lon=0.0;
	int h1=0,h2=0,m1=0,m2=0;
	String placename="";
	EditText eh2,em2,en;
	
    /** This handles the message send from TimePickerDialogFragment on setting Time */
    @SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){
            /** Creating a bundle object to pass currently set Time to the fragment */
            Bundle b = m.getData();
 
            /** Getting the Hour of day from bundle */
            h1 = b.getInt("set_hour");
 
            /** Getting the Minute of the hour from bundle */
            m1 = b.getInt("set_minute");
 
            /** Displaying a short time message containing time set by Time picker dialog fragment */
            Toast.makeText(getBaseContext(), b.getString("set_time"), Toast.LENGTH_SHORT).show();
        }
    };
	
    Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message m){
            /** Creating a bundle object to pass currently set Time to the fragment */
            Bundle b = m.getData();
 
            /** Getting the Hour of day from bundle */
            h2 = b.getInt("set_hour");
 
            /** Getting the Minute of the hour from bundle */
            m2 = b.getInt("set_minute");
 
            /** Displaying a short time message containing time set by Time picker dialog fragment */
            Toast.makeText(getBaseContext(), b.getString("set_time"), Toast.LENGTH_SHORT).show();
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actual_selector);
	}
	
	
	
	public void startTime(View v){
		/** Creating a bundle object to pass currently set time to the fragment */
        Bundle b = new Bundle();

        /** Adding currently set hour to bundle object */
        b.putInt("set_hour", h1);

        /** Adding currently set minute to bundle object */
        b.putInt("set_minute", m1);

        /** Instantiating TimePickerDialogFragment */
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler);

        /** Setting the bundle object on timepicker fragment */
        timePicker.setArguments(b);

        /** Getting fragment manger for this activity */
        FragmentManager fm = getSupportFragmentManager();

        /** Starting a fragment transaction */
        FragmentTransaction ft = fm.beginTransaction();

        /** Adding the fragment object to the fragment transaction */
        ft.add(timePicker, "time_picker");

        /** Opening the TimePicker fragment */
        ft.commit();
        /*
		FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();
        //Close pre-existing dialogs if any
        final Fragment prev = manager.findFragmentByTag("time_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        final TimePickerDialogFragment fragment = TimePickerDialogFragment.newInstance();
        fragment.show(ft, "time_dialog");*/
	}
	
	public void endTime(View v){
		/** Creating a bundle object to pass currently set time to the fragment */
        Bundle b = new Bundle();

        /** Adding currently set hour to bundle object */
        b.putInt("set_hour", h2);

        /** Adding currently set minute to bundle object */
        b.putInt("set_minute", m2);

        /** Instantiating TimePickerDialogFragment */
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler2);

        /** Setting the bundle object on timepicker fragment */
        timePicker.setArguments(b);

        /** Getting fragment manger for this activity */
        FragmentManager fm = getSupportFragmentManager();

        /** Starting a fragment transaction */
        FragmentTransaction ft = fm.beginTransaction();

        /** Adding the fragment object to the fragment transaction */
        ft.add(timePicker, "time_picker");

        /** Opening the TimePicker fragment */
        ft.commit();
	}
	
	/*public void onDialogTimeSet(int hourOfDay, int minute) {
		h1=hourOfDay;
		m1=minute;
		Toast.makeText(getApplicationContext(), hourOfDay+":" +minute, Toast.LENGTH_LONG).show();
	}*/
	
	public void selectLocation(View view){
		startActivityForResult((new Intent(this,Map.class)),1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == 1) {
		     if(resultCode == RESULT_OK){      
		    	 /*double[] in=data.getDoubleArrayExtra("hello");
		    	 Log.d("double null",""+in);
		    	 lat=in[0];lon=in[1];
		    	 a=new LatLng(lat,lon);*/
		    	 Bundle bundle = data.getParcelableExtra("bundle");
		    	 a = bundle.getParcelable("position");
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         //Write your code if there's no result
		    	 a = null;
		     }
		  }
		}//onActivityResult
	
	public void done(View view){
		//eh2=(EditText)findViewById(R.id.hours);
		//em2=(EditText)findViewById(R.id.minutes);
		en=(EditText)findViewById(R.id.name);
		placename=en.getText().toString();
		//h2=Integer.parseInt(eh2.getText().toString());
		//m2=Integer.parseInt(em2.getText().toString());
		Intent returnIntent = new Intent();
    	Bundle args = new Bundle();
    	args.putParcelable("location", a);
    	args.putInt("start_hour", h1);
    	args.putInt("start_minute",m1);
    	args.putInt("end_hour", h2);
    	args.putInt("end_minute", m2);
    	args.putString("name_of_location",placename);
    	
    	returnIntent.putExtra("bundle",args);
    	 setResult(RESULT_OK,returnIntent);     
    	 finish();
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selector, menu);
		return true;
	}*/

}
