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
     * Gets the status observable.
     *
     * @return     The status observable.
     */
    public abstract Observable<String> getStatusObservable();

    /**
     * release the resource used by RxMail
     */
    public abstract void  finish ();

    /**
     * check the rxMailBuilder elements
     *
     * @param      rxMailBuilder  The receive mail builder
     *
     * @return     return true if the parameters is ok
     */
    public abstract boolean validate(RxMailBuilder rxMailBuilder);

}
