package org.lembed.app;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.lembed.rxandroidmail.RxMail;
import org.lembed.rxandroidmail.internal.RxMailBuilder;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private  RxMail rxMail;
    EditText textViewType;
    EditText textViewBody;
    EditText textViewSubject;
    EditText textViewUsername;
    EditText textViewPassword;
    EditText textViewHost;
    EditText textViewPort;

    EditText textViewMailTo;
    EditText textViewAttachment;

    TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxMail = RxMail.create();

        textViewType = (EditText)findViewById(R.id.type);
        textViewBody = (EditText)findViewById(R.id.body);
        textViewSubject = (EditText)findViewById(R.id.subject);
        textViewUsername = (EditText)findViewById(R.id.user_name);
        textViewPassword = (EditText)findViewById(R.id.password);
        textViewHost = (EditText)findViewById(R.id.host);
        textViewPort = (EditText)findViewById(R.id.port);
        textViewMailTo = (EditText)findViewById(R.id.mail_to);
        textViewAttachment = (EditText)findViewById(R.id.attachments);

        statusView = (TextView)findViewById(R.id.status);

        Button send = (Button) findViewById(R.id.mail);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RxMailBuilder rxMailBuilder = new RxMailBuilder(getApplicationContext());
                rxMailBuilder.setType(textViewType.getText().toString());
                rxMailBuilder.setBody(textViewBody.getText().toString());
                rxMailBuilder.setSubject(textViewSubject.getText().toString());
                rxMailBuilder.setUsername(textViewUsername.getText().toString());
                rxMailBuilder.setPassword(textViewPassword.getText().toString());
                rxMailBuilder.setHost(textViewHost.getText().toString());
                rxMailBuilder.setPort(textViewPort.getText().toString());
                rxMailBuilder.setMailTo(textViewMailTo.getText().toString());
                rxMailBuilder.addAttachments(textViewAttachment.getText().toString());

                rxMail.push(rxMailBuilder);
                rxMail.push(rxMailBuilder.clone().setMailTo("your@gmail.com"));
            }

        });

        rxMail.getStatus().doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                rxMail.release();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if(aBoolean) {
                    statusView.setText("send success");
                }else{
                    statusView.setText("send error");
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
