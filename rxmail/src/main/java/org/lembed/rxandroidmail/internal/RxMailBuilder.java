package org.lembed.rxandroidmail.internal;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;

public  class RxMailBuilder implements Cloneable{
    public Context context;

    public String username;
    public String password;

    public String mailTo;
    public String subject;
    public String body;

    public String port;
    public String host;

    public String mimeType;
    public ArrayList<String> attachments = new ArrayList<>();


    public RxMailBuilder(Context context) {
        this.context = context;
    }

    public RxMailBuilder(@NonNull Context context,
                         @NonNull String username,
                         @NonNull String password,
                         @NonNull String mailTo,
                         @NonNull String subject,
                         @NonNull String body,
                         @NonNull String port,
                         @NonNull String host) {
        this.context = context;
        this.username = username;
        this.password = password;
        this.mailTo = mailTo;
        this.subject = subject;
        this.body = body;
        this.port = port;
        this.host = host;
    }

    public RxMailBuilder setUsername(@NonNull String username) {
        this.username = username;
        return this;
    }

    public RxMailBuilder setPassword(@NonNull String password) {
        this.password = password;
        return this;
    }

    public RxMailBuilder setPassword(@StringRes int passwordRes) {
        this.password = context.getResources().getString(passwordRes);
        return this;
    }

    public RxMailBuilder setMailTo(@NonNull String mailTo) {
        this.mailTo = mailTo;
        return this;
    }


    public RxMailBuilder setSubject(@NonNull String subject) {
        this.subject = subject;
        return this;
    }

    public RxMailBuilder setBody(@NonNull String body) {
        this.body = body;
        return this;
    }

    public RxMailBuilder setHost(@NonNull String host) {
        this.host = host;
        return this;
    }

    public RxMailBuilder setPort(@NonNull String port) {
        this.port = port;
        return this;
    }

    public RxMailBuilder addAttachments(String path) {
        this.attachments.add(path);
        return this;
    }

    public String getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public Context getContext() {
        return context;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getType() {
        return mimeType;
    }

    public void setType(String type) {
        this.mimeType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RxMailBuilder that = (RxMailBuilder) o;

        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        if (getMailTo() != null ? !getMailTo().equals(that.getMailTo()) : that.getMailTo() != null)
            return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null)
            return false;
        if (getBody() != null ? !getBody().equals(that.getBody()) : that.getBody() != null)
            return false;
        if (!getPort().equals(that.getPort())) return false;
        if (!getHost().equals(that.getHost())) return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;

    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getMailTo() != null ? getMailTo().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        result = 31 * result + getPort().hashCode();
        result = 31 * result + getHost().hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RxMailBuilder{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", mailTo='" + mailTo + '\'' +
               ", subject='" + subject + '\'' +
               ", body='" + body + '\'' +
               ", port='" + port + '\'' +
               ", host='" + host + '\'' +
               ", type='" + mimeType + '\'' +
               '}';
    }

    @Override
    public RxMailBuilder clone() {
        RxMailBuilder rxMailBuilder = null;
        try{
            rxMailBuilder = (RxMailBuilder)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return rxMailBuilder;
    }

}