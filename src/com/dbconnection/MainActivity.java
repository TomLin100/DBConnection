package com.dbconnection;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import java.io.*;
import java.net.*;

public class MainActivity extends Activity implements OnClickListener {
	
	private TextView mesg;
	private Button connectButton;
	private InetAddress serverIP;
	private int serverPort;
	private Socket clientSocket;

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
	public void onClick(View v) {
		// TODO 自動產生的方法 Stub
		
		try {
			serverIP = InetAddress.getByName("104.126.11.153");
			serverPort = 5050;
			clientSocket = new Socket(serverIP, serverPort);
			mesg.setText("連線成功~！！");
			clientSocket.close();
			
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			//e.printStackTrace();
			mesg.setText("IO發生錯誤！！");
		}
		
	}

}
