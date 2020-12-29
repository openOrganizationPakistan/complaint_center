package com.example.aioucomplaintcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Credit extends AppCompatActivity {

	private ImageButton btn_sir_mail
			,btn_me_fb
			,btn_me_mail
			;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit);

		callFB();

		btn_sir_mail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] mail = {"smbaqarhamdani@gmail.com"};
				composeEmail(mail,null,null);
			}
		});

		btn_me_mail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] mail = {"abdulroufsidhu@gmail.com"};
				composeEmail(mail,null,null);
			}
		});

		btn_me_fb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent facebookIntent = getOpenFacebookIntent(Credit.this);
				startActivity(facebookIntent);
			}
		});



	}

	private void callFB() {
		btn_me_fb  = findViewById(R.id.btn_me_fb);
		btn_me_mail  = findViewById(R.id.btn_me_mail);
		btn_sir_mail  = findViewById(R.id.btn_sir_mail);
	}

	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager()
					.getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/abdul.roufsiddhu")); //Trys to make intent with FB's URI
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/abdul.roufsiddhu")); //catches and opens a url to the desired page
		}
	}

	public void composeEmail(String[] addresses, String subject, Uri attachment) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setData(Uri.parse(addresses[0]));
		intent.setType("*/*");
		intent.putExtra(Intent.EXTRA_EMAIL, addresses);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_STREAM, attachment);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}

	}

}