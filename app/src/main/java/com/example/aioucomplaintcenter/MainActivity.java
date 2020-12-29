package com.example.aioucomplaintcenter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {
	
	private FirebaseAuth mAuth;
	
	private EditText txtEmail
			,txtPsd
			;
	
	private Button btnLogin
			,btnCredit
			,btnResetPassword
			;
	
	private String strEmail
			,strPsd
			;
	
	private String fPsd
			,fEmail
			;
	
	private String efName
			,pfName
			;
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		callFindViewById();
		
		efName = getFilesDir().toString() + "fEmail";
		pfName = getFilesDir().toString() + "fPsd";
		
		mAuth = FirebaseAuth.getInstance();
		
		btnCredit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Credit.class));
			}
		});
		
//		if (mAuth.getCurrentUser() != null) {
//
//			fEmail = readFromFile(efName);
//			fPsd = readFromFile(pfName);
//
//			sighnIn(fEmail,fPsd);
//		}
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onClick(View v) {
				btnLoginOnClickListener();
			}
		});
		
		btnResetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),ResetPassword.class));
			}
		});
		
	}
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	private void btnLoginOnClickListener() {
		
		callStringAssignment();
		
		try {
			if (!strEmail.isEmpty()  && !strPsd.isEmpty()) {
				
				sighnIn(strEmail, strPsd);
				
			} else {
				Toast.makeText(this,
						"All fields must be filled and record must exist in database...",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this,
					"Exception occured", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private void sighnIn(String fE, String fP) {
		mAuth.signInWithEmailAndPassword(fE,fP)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						
						if (task.isSuccessful()) {
							startActivity(new Intent(getApplicationContext(),
									OperationSelection.class));
						} else {
							Toast.makeText(MainActivity.this,
									"Authentication error...", Toast.LENGTH_LONG).show();
							
						}
						
					}
				});
	}
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	private void callStringAssignment() {
		strEmail = txtEmail.getText().toString().trim();
		strPsd = txtPsd.getText().toString().trim();
		
		writeToFile(efName,strEmail);
		writeToFile(pfName,strPsd);
		
		fEmail = readFromFile(efName);
		fPsd = readFromFile(pfName);
		
	}
	
	private void callFindViewById() {
		txtEmail = findViewById(R.id.txtEmail);
		txtPsd = findViewById(R.id.txtPsd);
		
		btnResetPassword = findViewById(R.id.btn_reset);
		
		btnLogin = findViewById(R.id.btnLogin);
		btnCredit = findViewById(R.id.btnCredits);
	}
	
	public static void writeToFile(String fileName, String lines) {
		
		try (FileWriter fw = new FileWriter(new File(fileName));
		     BufferedWriter writer = new BufferedWriter(fw)) {
			
			writer.write(lines);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	public static String readFromFile(String fileName) {
		
		Path path = Paths.get(fileName);
		
		String str = "";
		
		try (FileReader fr = new FileReader(fileName);
		     BufferedReader reader = new BufferedReader(fr)) {
			
			str = reader.readLine();
			return str;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return str;
		
		
	}
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onStart() {
		super.onStart();
		
		if (mAuth.getCurrentUser() != null) {
			
			fEmail = readFromFile(efName);
			fPsd = readFromFile(pfName);
			
			if (!(fEmail.isEmpty()
					&& fPsd.isEmpty())
			) {
				sighnIn(fEmail, fPsd);
			}
		}
		
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
		finish();
	}
}