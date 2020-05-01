# The cats

This is demonstration project.

Implemented in kotlin

External tools: Retrofit2 (OkHttp3) for requests, Gson for json convertation, RxJava2 for threading, Dagger2 for DI

Written in clean architecture (single module)

Application structure is Single activity

##Project Structure

The packages: 
    application - as name suggest is application classes
    di - dagger code
    features - the all features/screens of application
        features.common - all utility classes
        features.shared - the shared code between features
    main - the activity class

##Usage

Add 'api_key' variable in local.properties file with value of apiKey from thecatapi.com 