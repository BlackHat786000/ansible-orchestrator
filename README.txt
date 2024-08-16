curl to save metadata in mongo
===============================

curl --location 'http://localhost:8080/orchestrator/metadata/create' \
--header 'Content-Type: application/json' \
--data '{
    "componentName": "RECRUITING",
    "componentCode": "recruiting",
    "componentType": "service/product",
    "dependsOn": [
        "proCore"
    ],
    "supportedLifeCycleActions": [
        "create",
        "clone",
        "refresh",
        "update",
        "delete",
        "reset"
    ],
    "dataCenters": [
        "us-east-1",
        "us-east-4"
    ],
    "invocationStyle": "",
    "callbackMechanism": "",
    "authenticationMechanism": "OAuth2",
    "authenticationServiceCode": "AUTH123",
    "create": [
        {
            "module": "uri",
            "moduleParams": {
                "url": "{{ url }}",
                "method": "GET"
            }
        },
        {
            "name": "Print name",
            "module": "debug",
            "moduleParams": {
                "msg": "NAME: {{ username }}"
            }
        },
        {
            "name": "Print age",
            "module": "debug",
            "moduleParams": {
                "msg": "AGE: {{ age }}"
            }
        }
    ]
}'




curl to trigger matrix-poc workflow
====================================

curl --location 'http://localhost:8080/orchestrator/trigger/matrix' \
--header 'Content-Type: application/json' \
--data-raw '{
    "componentCode": "recruiting",
    "action": "create",
    "inputs": {
        "username": "udit yadav",
        "age": 26,
        "gender": "male",
        "email": "yadavudit786@gmail.com",
        "address": "ghaziabad",
        "url": "https://jsonplaceholder.typicode.com/posts"
    }
}'

