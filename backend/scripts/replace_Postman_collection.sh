#!/bin/bash

# set variables
OPEN_API_DATA_FILE='OPEN_API_DATA_FILE.json'
OUTPUT_FILE='../tests/postman/Quiz.postman_collection.json'
GUID_REGEX='[0-9a-fA-F]\{8\}-[0-9a-fA-F]\{4\}-[0-9a-fA-F]\{4\}-[0-9a-fA-F]\{4\}-[0-9a-fA-F]\{12\}'

# fetch Open API data from running application
curl http://localhost:8080/api/v1/docs-json >> $OPEN_API_DATA_FILE

# convert Open API definition to Postman collection using openapi2postmanv2 npm module
openapi2postmanv2 -s $OPEN_API_DATA_FILE -o $OUTPUT_FILE -p -O folderStrategy=Tags,includeAuthInfoInExample=false

# remove all auto generated Postman guids
sed -i '/"id": "'"$GUID_REGEX"'",/d' $OUTPUT_FILE
sed -i '/"_postman_id": "'"$GUID_REGEX"'",/d' $OUTPUT_FILE

# add post request procedure - setting csrf token environmental variable
jq '.event += [{"listen": "test", "script": {"type": "text/javascript", "exec": ["pm.environment.set(\"csrfToken\", pm.cookies.get(\"XSRF-TOKEN\"));"]}}]' $OUTPUT_FILE > temp && mv temp $OUTPUT_FILE

# add pre request procedure - setting csrf token as appropriate header
jq '.event += [		{"listen": "prerequest","script": {"type": "text/javascript","exec":["pm.request.headers.add(\"X-XSRF-TOKEN: \" + pm.environment.get(\"csrfToken\"));"]}}]' $OUTPUT_FILE > temp && mv temp $OUTPUT_FILE

# clean up
rm $OPEN_API_DATA_FILE
