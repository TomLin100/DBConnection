package com.dbconnection;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

import java.io.*;




public class MainActivity extends Activity implements OnClickListener {
	
	private TextView mesg;	
	private Button connectButton;
	private InputStreamReader input;
	private BufferedReader reafBuf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mesg = (TextView)findViewById(R.id.mesgTextView);
		connectButton = (Button)findViewById(R.id.connectButton);
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
		mesg.setText(stringQuery("http://localhost/DBCon.php"));
	}
	
	private String stringQuery(String url)
	{
		try 
		{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			//if(entity != null) return EntityUtils.toString(entity);
			//else return "NO Entity !!";
			
			input = new InputStreamReader(entity.getContent());
			reafBuf = new BufferedReader(input);
			String str1 = null;
			while(reafBuf.readLine() != null)
			{
				str1 = reafBuf.readLine();
			}
			reafBuf.close();
			return str1;
		} catch (Exception e) 
		{
			e.printStackTrace();
			return "Other Exception Occurred.";
		}
	}
}
