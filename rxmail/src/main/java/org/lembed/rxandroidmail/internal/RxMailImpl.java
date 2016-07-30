package org.lembed.rxandroidmail.internal;

import android.support.annotation.NonNull;


import org.lembed.rxandroidmail.RxMail;
import org.lembed.rxandroidmail.util.Utils;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;


public class RxMailImpl extends RxMail {

    PublishSubject<RxMailBuilder> rzMailBuilderSubject = PublishSubject.create();
    BehaviorSubject<Boolean> statusSubject = BehaviorSubject.create();
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static RxMailImpl getInstance() {
        return new RxMailImpl();
    }

    public RxMailImpl() {

        compositeSubscription.add(
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
                return new RxMailSender(rxMailBuilder);
            }
        }).map(new Func1<RxMailSender, Boolean>() {
            @Override
            public Boolean call(RxMailSender rxMailSender) {
                try {
                    rxMailSender.send();
                    return true;
                } catch ( Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }).retry()
        .doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                rzMailBuilderSubject.onNext(null);
                rzMailBuilderSubject = null;
                statusSubject = null;
            }
        })
        .subscribe(statusSubject));
    }

    public void  push (@NonNull RxMailBuilder rxMailBuilder) {
        rzMailBuilderSubject.onNext(rxMailBuilder);
    }

    @Override
    public void release() {
        compositeSubscription.clear();
    }

    private boolean validate(RxMailBuilder rxMailBuilder) {
        if (rxMailBuilder == null) {
            return false;
        }

        if (rxMailBuilder.getUsername().isEmpty() ||
                rxMailBuilder.getPassword().isEmpty() ||
                rxMailBuilder.getMailTo().isEmpty() ||
                rxMailBuilder.getBody().isEmpty() ||
                rxMailBuilder.getSubject().isEmpty()) {
            return false;
        }

        for (String attachment : rxMailBuilder.getAttachments()) {
            File attachmentFile = new File(attachment);
            if (!attachmentFile.exists()) {
                return false;
            }
        }

        if (!Utils.isNetworkAvailable(rxMailBuilder.getContext())) {
            return false;
        }
        return true;
    }

    @Override
    public Observable<Boolean> getStatus() {
        return statusSubject.asObservable();
    }

}
