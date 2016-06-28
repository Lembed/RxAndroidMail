package org.lembed.rxandroidmail;

import android.support.annotation.NonNull;


import org.lembed.rxandroidmail.internal.RxMailBuilder;
import org.lembed.rxandroidmail.internal.RxMailImpl;

import rx.Observable;


public abstract class RxMail {


    public RxMail() {
    }

    /**
     * allocate new RxMail instance
     *
     * @return     RxMail implement instance
     */
    public static RxMail create() {
        return RxMailImpl.getInstance();
    }


    /**
     * accept the RxMail builder and trigger the send process
     *
     * @param      rxMailBuilder  The receive mail builder
     */
    public abstract void  push (@NonNull RxMailBuilder rxMailBuilder);

    /**
     * release all resource
     */
    public abstract void release();


    /**
     * Gets the status observable.
     *
     * @return     The status observable.
     */
    public abstract Observable<Boolean> getStatus();

}
