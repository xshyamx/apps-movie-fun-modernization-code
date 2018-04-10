# Movie Fun!

Smoke Tests require server running on port 8080 by default.

## Build JAR ignoring tests

```bash
$ ./gradlew clean build -xtest
```

## Run Smoke Tests against specific URL

```bash
$ MOVIE_FUN_URL=http://moviefun.example.com ./gradlew test
```

## After deploying to CF

```sh
cf env movie-fun-app
```

Populate the values from the above command 

```sh
cf set-env movie-fun-app S3_ACCESSKEY "AKIAJTPQLEXPN3ELEVWQ" 
cf set-env movie-fun-app S3_SECRETKEY  "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"
cf set-env movie-fun-app S3_BUCKETNAME "cf-b54053b4-2206-441e-ad42-8646849043b2"
```
