package com.dbconnection;


import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.concurrent.ExecutionException;
//import java.net.*;



	
public class MainActivity extends Activity implements OnClickListener {
	
	private TextView mesg;	
	private Button connectButton;
	private InputStreamReader input;
	private BufferedReader reafBuf;
	private String str2;
	private final String HTTP_URL = "http://118.169.189.73/DBcon.php";
	
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
		ConnectionTask connect = new ConnectionTask();
		connect.execute(HTTP_URL);
		try {
			mesg.setText(connect.get().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public class ConnectionTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return connect2Internet(params[0]);
		}
		
		private String connect2Internet(String linkURL)
		{
			client = new DefaultHttpClient();
			try 
			{
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
				request = new HttpPost(linkURL);
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
						str2 = "JSONException Occurred. " + e.getMessage();
						e.printStackTrace();
					}	
				}
			} 
			catch(ClientProtocolException e)
			{
				str2 = "ClientProtocolException Ocurred. " + e.getMessage();
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				// TODO: handle exception
				str2 = "IOException Occurred.\n" + e.getMessage();
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				str2 = "Other Exception Occurred." + e.getMessage();
				e.printStackTrace();
			}
			
			return str2;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(getBaseContext(), "解析成功~!!", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO 自動產生的方法 Stub
			Toast.makeText(getBaseContext(), "開始中....", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO 自動產生的方法 Stub
			Toast.makeText(getBaseContext(), "解析JSON中.....",Toast.LENGTH_LONG).show();
			super.onProgressUpdate(values);
		}		
	}
}
