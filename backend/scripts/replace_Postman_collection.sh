#!/bin/bash

OPEN_API_DATA=$(curl http://localhost:8080/api/v1/docs-json)
TEMP_FILE=temporary_open_api_data.json
echo $TEMP_FILE
echo $OPEN_API_DATA >> TEMP_FILE

openapi2postmanv2 -s TEMP_FILE -o ../tests/postman/'Quiz API documentation.postman_collection.json' -p -O folderStrategy=Tags,includeAuthInfoInExample=false
rm TEMP_FILE
