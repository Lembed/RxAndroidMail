package org.lembed.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.lembed.rxandroidmail.RxMail;
import org.lembed.rxandroidmail.internal.RxMailBuilder;

public class MainActivity extends AppCompatActivity {

    private  RxMail rxMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxMail = RxMail.create();

        Button mail = (Button) findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RxMailBuilder rxMailBuilder = new RxMailBuilder(getApplicationContext());
                rxMailBuilder.setType("text/html");
                rxMailBuilder.setBody("body");
                rxMailBuilder.setSubject("subject");
                rxMailBuilder.setUsername("your@gmail.com");
                rxMailBuilder.setPassword("password");
                rxMailBuilder.setHost("smtp.gmail.com");
                rxMailBuilder.setPort("465");
                rxMailBuilder.setMailTo("another@gmail.com");
                rxMailBuilder.addAttachments("/data/files/email.jpg");

                rxMail.push(rxMailBuilder);
                rxMail.push(rxMailBuilder.clone().setMailTo("your@gmail.com"));

            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        rxMail.finish();
    }
}
