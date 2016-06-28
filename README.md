# RxAndroidMail
A tiny rxjava wrapped android smtp client mail library

[![RxAndroidMail](https://img.shields.io/badge/build-passing-blue.svg)]()
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/Lembed/RxAndroidMail/master/LICENSE)
[![RxAndroidMail](https://img.shields.io/badge/version-1.0-yellow.svg)]()


## Usage

Obtaining the instance

```java

RxMail rxMail = RxMail.create();
```

Build the mail configure

```java

 RxMailBuilder rxMailBuilder = new RxMailBuilder(getApplicationContext());
                rxMailBuilder.setType("text/html");
                rxMailBuilder.setBody("body");
                rxMailBuilder.setSubject("subject");
                rxMailBuilder.setUsername("your@gmail.com");
                rxMailBuilder.setPassword("password");
                rxMailBuilder.setHost("stmp.gmail.com");
                rxMailBuilder.setPort("465");
                rxMailBuilder.setMailTo("another@gmail.com");
                rxMailBuilder.addAttachments("/data/files/email.jpg");
```

Send the mail

```java

   rxMail.push(rxMailBuilder);
   rxMail.push(rxMailBuilder.clone().setMailTo("your@gmail.com"));

```

Release

```java
rxMail.finish();
```


## Contributing
If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.


## License
[MIT](https://github.com/Lembed/RxAndroidMail/blob/master/LICENSE)
