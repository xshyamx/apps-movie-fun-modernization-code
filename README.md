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


## Create services

```sh
cf create-service p-mysql p-mysql albums-database
cf create-service p-mysql p-mysql movies-database
cf create-service aws-s3 standard movies-s3
```

## After deploying to CF


```sh
for appname in movie-fun-app album-service; do
  app_guid=$(cf app "$appname" --guid)
  cf curl "/v2/apps/${app_guid}/env" > env.json
  jq '.system_env_json.VCAP_SERVICES["aws-s3"][0].credentials' env.json > creds.json
  cf set-env movie-fun-app S3_ACCESSKEY "$(jq .access_key_id creds.json)"
  cf set-env movie-fun-app S3_SECRETKEY  "$(jq .secret_access_key creds.json)"
  cf set-env movie-fun-app S3_BUCKETNAME "$(jq .bucket creds.json)"
  rm -f env.json creds.json
done
```
