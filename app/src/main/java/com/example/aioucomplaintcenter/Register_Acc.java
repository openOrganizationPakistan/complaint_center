package com.example.aioucomplaintcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_Acc extends AppCompatActivity {
	
	private FirebaseAuth mAuth;
	private FirebaseDatabase database;
	private DatabaseReference dbRef;
	
	
	private EditText eMail
			,name
			,rollNum
			,regNum
			,program
			,phoneNum
			,password
			,passwordConfirm
			;

//    private String strMail
//            ,strName
//            ,strRollNum
//            ,strRegNum
//            ,strProgram
//            ,strPhoneNum
//            ;
	
	private Button btnCreatAccount;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register__acc);
		
		
		
		callFindViewById();
		
		mAuth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		
		btnCreatAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					
					if ( !(
							name.getText().toString().trim().isEmpty() &&
							eMail.getText().toString().trim().isEmpty() &&
							password.getText().toString().trim().isEmpty() &&
							passwordConfirm.getText().toString().trim().isEmpty() &&
							rollNum.getText().toString().trim().isEmpty() &&
							regNum.getText().toString().trim().isEmpty() &&
							program.getText().toString().trim().isEmpty()
							
					)) {
						
						if (password.getText().toString().trim().equals(passwordConfirm.getText().toString().trim())) {
							
							mAuth.createUserWithEmailAndPassword(eMail.getText().toString().trim(), password.getText().toString().trim())
									.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
										@Override
										public void onComplete(@NonNull Task<AuthResult> task) {
											if (task.isSuccessful()) {
												FirebaseUser cUser = mAuth.getCurrentUser();
												dbRef = database.getReference("users/" + cUser.getUid());
												
												dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
													@Override
													public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
														RegDataObj dataObj = new RegDataObj(name.getText().toString().toLowerCase().trim()
																, phoneNum.getText().toString().trim()
																, program.getText().toString().trim()
																, regNum.getText().toString().toLowerCase().trim()
																, rollNum.getText().toString().toLowerCase().trim()
														);
														
														dbRef.setValue(dataObj);
														
													}
													
													@Override
													public void onCancelled(@NonNull DatabaseError databaseError) {
													
													}
												});
												
												
											} else {
												// If sign in fails, display a message to the user.
												
												Toast.makeText(getApplicationContext(), "Authentication failed.",
														Toast.LENGTH_SHORT).show();
											}
											
											// ...
										}
									});
							Toast.makeText(getApplicationContext(),
									"Success adding account and details to database...", Toast.LENGTH_SHORT).show();
							
							startActivity(new Intent(getApplicationContext(), OperationSelection.class));
							
						}
						else {
							Toast.makeText(Register_Acc.this, "password mismatch", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						Toast.makeText(Register_Acc.this, "Some Fields are missing\nPlease ensure to fill all fields", Toast.LENGTH_SHORT).show();
					}
					
				}
				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"This exception occured:\n\n" + e.toString(), Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		
	}
	
	private void callFindViewById() {
		eMail = findViewById(R.id.edtTxtEmail);
		name = findViewById(R.id.edtTxtName);
		rollNum = findViewById(R.id.edtTxtRollNum);
		regNum = findViewById(R.id.edtTxtRegNum);
		program = findViewById(R.id.edtTxtProgram);
		phoneNum = findViewById(R.id.edtTxtPhoneNum);
		password = findViewById(R.id.edtTxtPassword);
		passwordConfirm = findViewById(R.id.edtTxtPasswordConfirm);
		
		btnCreatAccount = findViewById(R.id.btnCreatAccount);
	}
	
	
	
	
}