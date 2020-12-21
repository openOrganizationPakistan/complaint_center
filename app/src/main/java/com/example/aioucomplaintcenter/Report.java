package com.example.aioucomplaintcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Report extends AppCompatActivity {
	private EditText txtRegNum;
	private EditText txtRollNum;
	private EditText txtTitle;
	private EditText txtProblem;
	
	private Button btnSendReport;
	
	private String strRegNum;
	private String strRollNum;
	private String strTitle;
	private String strProblem;
	private String strPhnNumber;
	private String strStdName;
	private String strMailTo;
	
	
	private FirebaseDatabase database;
	private DatabaseReference dbRef;
	private FirebaseAuth mAuth;
	private FirebaseUser currentUser;
	
	private DataObj dataObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		sEmail = "aioucomplaintcenter@gmail.com";
//		sPsd = "X48n6W6eNg9q3XC";
		strMailTo = "baqar.hussain@aiou.edu.pk";
		
		setContentView(R.layout.activity_report);
		
		database = FirebaseDatabase.getInstance();
		
		mAuth = FirebaseAuth.getInstance();
		currentUser = mAuth.getCurrentUser();
		
		dbRef = database.getReference(currentUser.getUid());
		
		callFindViewById();
		
		btnSendReport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				callGetStringValues();
				
				dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshot) {
						
						try {
							boolean rollCnfrm = false;
							boolean regCnfrm = false;
							
							if (!(strProblem.isEmpty()
									&& strRegNum.isEmpty()
									&& strRollNum.isEmpty()
									&& strTitle.isEmpty())) {
								
								
								for (DataSnapshot data : snapshot.getChildren()) {
									
									try {
										switch (data.getKey()) {
											
											case "phn number": {
												strPhnNumber = data.getValue().toString().trim();
											} case "reg number": {
												if (data.getValue().toString().equals(strRegNum.toLowerCase())) {
													regCnfrm = true;
												}
											} case "roll number": {
												if (data.getValue().toString().equals(strRollNum.toLowerCase())) {
													rollCnfrm = true;
												}
											} case "name": {
												strStdName = data.getValue().toString().trim();
											}
											
										}
										
										
										
										if (regCnfrm
												&& rollCnfrm) {
											
											dataObj = new DataObj(strTitle, strProblem, new Date().toString());
											
											DatabaseReference issueRef = dbRef.child("issues");
											
											issueRef.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot snapshot) {
													
													long instance = snapshot.getChildrenCount();
													
													
													sendEmail();
													
													issueRef.child(String.valueOf(instance + 1)).setValue(dataObj);
												
												}
												
												@Override
												public void onCancelled(@NonNull DatabaseError error) {
												
												}
											});
											
										}
										
									}
									
									catch (Exception e) {
										Toast.makeText(Report.this,
												"please be sure to enter correct credentials...", Toast.LENGTH_SHORT).show();
									}
								}
								
							} else {
								Toast.makeText(Report.this,
										"Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
							}
							
							if (!(regCnfrm && rollCnfrm)) {
								
								Toast.makeText(Report.this,
										"please fill in all detail correctly....", Toast.LENGTH_SHORT).show();
							}
							
						}
						catch (Exception e) {
							
							Toast.makeText(Report.this,
									"Exception Occured Refill all fields and try again please..", Toast.LENGTH_LONG).show();
							
						}
						
						
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
					
					}
				});
				
			}
		});
		
		
	}
	
	private void sendEmail() {
		
		JavaMailAPI javaMailAPI = new JavaMailAPI(this, strMailTo , strTitle,
				"Your student "+strStdName + "\nwith email "
				+currentUser.getEmail() + "\nwith user id in database "
				+ currentUser.getUid()
				+"\nwith phone number " + strPhnNumber
				+"\nis having following problem\n" + strProblem +"\n"
				+"\n"
				+"mail generated by AIOU Complaint Center "
				+"\napp developed by Abdul Rauf AIOUBWP..."
//				+ "\nBO460872"
//				+"\n17PBR03524"
//				+"\n03042816024"
		);
		
		javaMailAPI.execute();
		
		Toast.makeText(this,
				"Problem Repoted Successfully...", Toast.LENGTH_SHORT).show();
		
	}
	
	
	private void callGetStringValues() {
		strProblem = txtProblem.getText().toString();
		strTitle = txtTitle.getText().toString();
		strRegNum = txtRegNum.getText().toString();
		strRollNum = txtRollNum.getText().toString();
	}
	
	private void callFindViewById() {
		txtProblem = findViewById(R.id.txtProblem);
		txtRegNum = findViewById(R.id.txtRegNum);
		txtRollNum = findViewById(R.id.txtRollNum);
		txtTitle = findViewById(R.id.txtTitle);
		
		btnSendReport = findViewById(R.id.btnSendReport);
		
	}
	
}