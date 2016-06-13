package org.lembed.rxandroidmail.internal;

import android.support.annotation.NonNull;


import org.lembed.rxandroidmail.RxMail;
import org.lembed.rxandroidmail.util.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;


public class RxMailImpl extends RxMail {

    PublishSubject<RxMailBuilder> rzMailBuilderSubject = PublishSubject.create();
    BehaviorSubject<String> statusSubject = BehaviorSubject.create();

    public static RxMailImpl getInstance() {
        return new RxMailImpl();
    }

    public RxMailImpl() {
        rzMailBuilderSubject.asObservable()
        .observeOn(Schedulers.newThread())
        .filter(new Func1<RxMailBuilder, Boolean>() {
            @Override
            public Boolean call(RxMailBuilder rxMailBuilder) {
                return validate(rxMailBuilder);
            }
        })
        .map(new Func1<RxMailBuilder, RxMailSender>() {
            @Override
            public RxMailSender call(RxMailBuilder rxMailBuilder) {
                RxMailSender rxMailSender = new RxMailSender(rxMailBuilder).build();
                ArrayList<String> lists = rxMailBuilder.getAttachments();

                if (!lists.isEmpty()) {
                    for (int i = 0; i < lists.size(); i++) {
                        if (!lists.get(i).isEmpty()) {
                            try {
                                rxMailSender.addAttachment(lists.get(i));
                            } catch ( Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return rxMailSender;
            }
        }).map(new Func1<RxMailSender, String>() {
            @Override
            public String call(RxMailSender rxMailSender) {
                try {
                    rxMailSender.sendMail();
                    return "success";
                } catch ( Exception e) {
                    e.printStackTrace();
                    return "fail";
                }
            }
        })
        .subscribe(statusSubject);
    }

    public void  push (@NonNull RxMailBuilder rxMailBuilder) {
        rzMailBuilderSubject.onNext(rxMailBuilder);
    }

    public void  finish () {
        rzMailBuilderSubject.onNext(null);
        rzMailBuilderSubject = null;
        statusSubject = null;
    }

    public boolean validate(RxMailBuilder rxMailBuilder) {
        if (rxMailBuilder == null){
            return false;
        }

        if (rxMailBuilder.getUsername().isEmpty() ||
                rxMailBuilder.getPassword().isEmpty() ||
                rxMailBuilder.getMailTo().isEmpty() ||
                rxMailBuilder.getBody().isEmpty() ||
                rxMailBuilder.getSubject().isEmpty()) {
            return false;
        }

        if (!Utils.isNetworkAvailable(rxMailBuilder.getContext())) {
            return false;
        }
        return true;
    }

    public Observable<String> getStatusObservable() {
        return statusSubject.asObservable();
    }

}
