# ECS Spring Cloud Connectors Demo

This is a simple example of using EMC ECS for asset storage. It is an image catalog to which you can upload images and
see them on the main page.  It is based off of this [Cloud Foundry S3 Demo](https://github.com/cloudfoundry-samples/cf-s3-demo)
with some modifications to the connectors to support EMC ECS through
[Orange OpenSource's Spring Cloud S3 Connectors](https://github.com/Orange-OpenSource/spring-cloud-s3-connectors) and
the [jclouds Blobstore client](http://jclouds.apache.org).

To build just use gradle:
```
$ ./gradlew assemble
```

Make sure that a database & bucket are created in Cloud Foundry.  So long as there named the same as the manifest,
CF will take care of binding them to the app.  What's _really_ cool, though, is that any matching database or blobstore
service will work.  So long as it's MySQL compatible or jclouds compatible (like ECS) the Spring Cloud Connector will
figure everything out.
```
$ cf create-service p-mysql 100mb-dev demo-db 
Creating service instance demo-db in org myorg / space development as cfuser...
OK
$ cf create-service ecs-bucket 5gb demo-bucket
Creating service instance demo-bucket in org myorg / space development as cfuser...
OK
$ cf push ecs-scc-demo
Using manifest file <...>/ecs-scc-demo/manifest.yml

Updating app ecs-scc-demo in org myorg / space development as admin...
OK

Using route ecs-scc-demo.example.com
Uploading ecs-scc-demo...
Uploading app files from: <...>/emc/ecs-scc-demo/build/libs/ecs-scc-demo-0.0.1-SNAPSHOT.jar
Uploading 1.2M, 138 files
Done uploading               
OK
Binding service demo-bucket to app ecs-scc-demo in org myorg / space development as admin...
OK
Binding service demo-db to app ecs-scc-demo in org myorg / space development as admin...
OK

Stopping app ecs-scc-demo in org myorg / space development as admin...
OK

Starting app ecs-scc-demo in org myorg / space development as admin...

<lots of startup details here>

     state     since                    cpu    memory         disk           details   
#0   running   2016-03-10 08:40:06 PM   0.0%   477.2M of 1G   171.2M of 1G
```

Feel free to peruse the code to see how this all works.  Here are some places to look:
* Check out the [cloud config for the blobstore](https://github.com/spiegela/ecs-scc-demo/blob/master/src/main/java/com/emc/ecs/config/CloudConfig.java#L24) that's where your S3 client actually get's created.
* The Spring MVC Controller [uses jclouds s3 blobstore](https://github.com/spiegela/ecs-scc-demo/blob/master/src/main/java/com/emc/ecs/web/DemoController.java#L56-L62) to manipulate the files
  - In fact, you can change the jclouds blobstore can work with S3, Swift & others if you decide to switch between backends

The rest of the app is pretty much vanilla.  Have fun!