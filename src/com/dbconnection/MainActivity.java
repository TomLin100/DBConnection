package com.dbconnection;


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
//import java.net.*;



	
public class MainActivity extends Activity implements OnClickListener {
	
	private TextView mesg;	
	private Button connectButton;
	private InputStreamReader input;
	private BufferedReader reafBuf;
	private String str2;
	
	private HttpClient client;
	private HttpPost request;
	private HttpResponse response;
	private HttpEntity entity;
	private JSONObject jsonString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mesg = (TextView)findViewById(R.id.mesgTextView);
		
		connectButton = (Button)findViewById(R.id.connectButton);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	    .detectDiskReads()
	    .detectDiskWrites()
	    .detectNetwork()   // or .detectAll() for all detectable problems
	    .penaltyLog()
	    .build());
		
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects()   
        .penaltyLog()  
        .penaltyDeath()  
        .build());  
		
		connectButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		stringQuery("http://118.169.189.73/DBcon.php");
	}
	
	private void stringQuery(String url)
	{
		client = new DefaultHttpClient();
		try 
		{
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			request = new HttpPost(url);
			response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			{
				String str1 = EntityUtils.toString(response.getEntity());
				try
				{
					str2 = new JSONObject(str1).getString("message");
				}
				catch(JSONException e)
				{
					mesg.setText("JSONException Occurred. " + e.getMessage());
					e.printStackTrace();
				}	
				
				mesg.setText(str2);
			}
		} 
		catch(ClientProtocolException e)
		{
			mesg.setText("ClientProtocolException Ocurred. ");
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO: handle exception
			mesg.setText("IOException Occurred.\n" + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			mesg.setText("Other Exception Occurred.");
			e.printStackTrace();
		}
	}
}
