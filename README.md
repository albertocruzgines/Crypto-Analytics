# Crypto analytics 

### How to run the application

This application has been written in scala and tested through the IDE Intellij Community Edition 2018.3.

It will require to have an elastic server running on localhost:9200 or change its value in **CryptoAnalyiticUtils** object, inside **elasticLoad** method.

Let me know if you have any issues checking the data in Elastic, I have had some problems where the data was not populating.

The application can be run directly from the executable object **BlockRequester**. It will also print some status logs on the "Event Log".

### Structure

The application is divided into the following packages:

* com.ferran.projects.crypto.analytics.clients: Here it´s defined some methods that will ease the app to make HTTP calls. It also contains a method that helps dealing with the retry with backoff. In RestScalaClient there is also a helper to authenticate and get the access_token of the API although is not used on this exercice.
* com.ferran.projects.crypto.analytics.model: It´s defined the model for handling responses from the API.
* com.ferran.projects.crypto.analytics.utils: On the **package object** can be found circe encoder and decoders. On **CryptoAnalyticUtils** object there are methods to deal with the elastic
* **BlockRequester** is the main method on which is executed the application

### Next steps

If I have had more time to finish the assignment, I would have completed the following tasks in order:

 1. Implement a workflow for requeuing non-complete or error responses from the Shapeshift API
 2. Create more test classes
 3. Create a Scheduler for the app. I would have gone for Monix.Scheduler
 2. Containerising the application through Docker

### Self assessment

I have spent around 30 minutes to both understanding the problem and playing around the API´s to get a better idea of the data model that had to be implemented.
The code implementation has taken me around 4-5 hours.
