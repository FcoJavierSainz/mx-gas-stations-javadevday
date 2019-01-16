# mx-gas-stations-javadevday

## Install with docker and apps on local
1. Run command `docker-compose up`
1. Modify the key in the line `api-key: <My Google maps key>` from file `app/src/main/resources/application.yml`
1. Run all main Class from  `App`, `gas-locations-service` and `gas-prices-services`
1. Endpoint `http://localhost:8080/api/v1/gasStations/near?longitude=-103.39282&latitude=20.67524&radio=1500`


## Deploy to K8

### Build Java Apps
1. Run gradle
```bash
./gradlew build
``

### Build images
```bash
docker build mongo-gas-stations -t <docker hub id>/gas-stations-data
docker build prices-db -t <docker hub id>/prices-db
docker build gas-prices-service -t <docker hub id>/gas-prices-service
docker build gas-locations-service -t <docker hub id>/gas-locations-service
docker build app -t <docker hub id>/app
```

### Login to Docker hub
```bash
# info 
docker login
```
### Publish the Images to Docker Hub

```bash
docker build mongo-gas-stations -t <docker hub id>/gas-stations-data
docker build prices-db -t <docker hub id>/prices-db
docker build gas-prices-service -t <docker hub id>/gas-prices-service
docker build gas-locations-service -t <docker hub id>/gas-locations-service
docker build app -t <docker hub id>/app
```