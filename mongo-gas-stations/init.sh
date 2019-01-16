#!/bin/bash
mongoimport --host mongo --db gasStations --collection places --type json --file /init.json --jsonArray
mongo gasStations --host mongo --eval 'db.places.createIndex( { location: "2dsphere" } )'