package com.example.aioucomplaintcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
	
	private FirebaseAuth mAuth;
	
	private EditText eMail;
	
	private Button btnResetPsd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		callFB();
		
		mAuth = FirebaseAuth.getInstance();
		
		btnResetPsd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (!eMail.getText().toString().trim().isEmpty()) {
						mAuth.sendPasswordResetEmail(eMail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if (task.isSuccessful()) {
									Toast.makeText(ResetPassword.this,
											"Reset Email Sent successfully...", Toast.LENGTH_SHORT).show();
									finish();
									startActivity(new Intent(getApplicationContext(),MainActivity.class));
								}
								else {
									Toast.makeText(ResetPassword.this,
											"some error occured", Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
					else {
						Toast.makeText(ResetPassword.this,
								"please enter an email in the field", Toast.LENGTH_SHORT).show();
					}
				}
				catch (Exception e) {
					Toast.makeText(ResetPassword.this,
							"This Exception Occured: " + e.toString()
							, Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	private void callFB() {
		eMail = findViewById(R.id.forgot_mail);
		btnResetPsd = findViewById(R.id.btn_send_reset_mail);
	}
}