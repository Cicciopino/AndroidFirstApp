package org.elis.jp4application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class WelcomeActivity extends AppCompatActivity implements OnClickListener {

    String mail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_welcome);

        TextView welcomeTW= findViewById(R.id.welcome_tv);

        String mail= getIntent().getStringExtra(MainActivity.WELCOME);

        welcomeTW.setText(getString(R.string.welcome)+" "+mail);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String mail= getIntent().getStringExtra(MainActivity.WELCOME);
        String[] to = {mail, ""};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "DAVIDE GAY");
        intent.putExtra(Intent.EXTRA_TEXT, "DAVIDE SUPER GAY");
        intent.setType("message/rfc822");
        Intent chooser = Intent.createChooser(intent, "INVIA EMAIL");
        startActivity(chooser);
    }
}


