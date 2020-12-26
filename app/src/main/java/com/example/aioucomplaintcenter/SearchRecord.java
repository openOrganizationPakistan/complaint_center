package com.example.aioucomplaintcenter;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SearchRecord extends AppCompatActivity {
	
	
	private FirebaseAuth mAuth;
	private FirebaseUser currentUser;
	private FirebaseDatabase database;
	private DatabaseReference dbRef;
	
	private EditText txtRecNum;
	
	private TextView txtDate
			,txtTitle
			,txtIssue
			,txtRecords;
	
	private Button btnSearch;
	
	private String strDate
			,strTitle
			,strIssue
			,strRecords
			,strRecNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_record);
		
		callFindViewById();
		database = FirebaseDatabase.getInstance();
		mAuth = FirebaseAuth.getInstance();
		currentUser = mAuth.getCurrentUser();
		dbRef = database.getReference(currentUser.getUid() + "/issues");
		
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshotData) {
					
						setValuesToString();
						
						if (strRecNum.isEmpty()) {
							
							long instances = snapshotData.getChildrenCount();
							strRecords = String.valueOf(instances);
						}
						else {
							
							txtRecords.setText("Total Records: " + strRecords);
							
							DatabaseReference searchRef = dbRef.child(strRecNum);
							searchRef.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot snapshot) {
									
									for (DataSnapshot data: snapshot.getChildren()) {
										
										switch (Objects.requireNonNull(data.getKey())) {
											
											case "issue": {
												strIssue = Objects.requireNonNull(data.getValue()).toString();
												txtIssue.setText("Issue: " + strIssue);
												break;
											} case "title": {
												strTitle = Objects.requireNonNull(data.getValue()).toString();
												txtTitle.setText("Title: " + strTitle);
												break;
											} case "date": {
												strDate = Objects.requireNonNull(data.getValue()).toString();
												txtDate.setText("Date: "+ strDate);
												break;
											} default:
												break;
												
										}
										
									}
									
									
									
								}
								
								@Override
								public void onCancelled(@NonNull DatabaseError error) {
								
								}
							});
						}
						
						setValuesToTextViews();
						
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
					
					}
				});
				
			}
		});
		
		
	}
	
	private void setValuesToTextViews() {
		
		txtRecords.setText("Total Records: " + strRecords);
		txtDate.setText("Date: "+ strDate);
		txtIssue.setText("Issue: " + strIssue);
		txtTitle.setText("Title: " + strTitle);
		
	}
	
	private void setValuesToString() {
		
		strRecNum = txtRecNum.getText().toString();
		strIssue = "";
		strDate = "";
		strTitle = "";
		strRecords = "";
		
	}
	
	private void callFindViewById() {
		txtDate = findViewById(R.id.txtDate);
		txtRecNum = findViewById(R.id.txtRecNum);
		txtTitle = findViewById(R.id.txtTitle);
		txtIssue = findViewById(R.id.txtIssue);
		txtRecords = findViewById(R.id.txtRecords);
		
		btnSearch = findViewById(R.id.btnSearch);
	}
	
	
}