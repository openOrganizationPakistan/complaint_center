package com.example.aioucomplaintcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class OperationSelection extends AppCompatActivity {
	
	private Button btnReport;
	private Button btnSearchRecords;
	private Button btnSignOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operation_selection);
		
		callViewById();
		
		btnReport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Report.class));
			}
		});
		
		btnSearchRecords.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),SearchRecord.class));
			}
		});
		
		btnSignOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				FirebaseAuth.getInstance().signOut();
				startActivity(new Intent(getApplicationContext(),MainActivity.class));
				
			}
		});
		
	}
	
	private void callViewById() {
		btnReport = findViewById(R.id.btnReport);
		btnSearchRecords = findViewById(R.id.btnSearchRecords);
		btnSignOut = findViewById(R.id.btnSignOut);
	}
	
	
}