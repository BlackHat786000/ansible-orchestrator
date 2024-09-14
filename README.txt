ANSIBLE PLAYBOOK GENERATOR
===========================

- playbook generation payload is consistent, so can be easily documented
- validation supported, avoid playbook syntax failures
- module params can be provided as both: string literals and variables (using jinja templatization)
- any module support can be easily added


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
            "name": "Perform a GET request",
            "module": "uri",
            "moduleParams": {
                "url": "{{ url }}",
                "method": "GET"
            },
            "register": "get_response"
        },
        {
            "name": "Print GET response",
            "module": "debug",
            "moduleParams": {
                "msg": "GET_RESPONSE: {{ get_response }}"
            }
        },
        {
            "name": "Produce kafka event",
            "module": "kafka_producer",
            "moduleParams": {
                "body": {
					"title": "foo",
					"body": "{{ get_response.json[0].body }}",
					"userId": "{{ get_response.json[0].userId }}"
				}
            },
            "register": "post_response"
        },
        {
            "name": "Print POST response",
            "module": "debug",
            "moduleParams": {
                "msg": "POST_RESPONSE: {{ post_response }}"
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
        },
        {
            "name": "Run a command",
            "module": "command",
            "moduleParams": "ls"
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


Another curl to save metadata in mongo (google_secret_access and mssql_script)
===============================================================================

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
            "name": "Retrieve google secrets",
            "module": "google_secret_access",
            "moduleParams": {
            },
            "register": "secrets"
        },
        {
            "name": "Query usernames",
            "module": "mssql_script",
            "moduleParams": {
                "login_host": "{{ server_1 }}", // server_1 coming from ansible extra_vars/inputs
                "login_user": "{{ secrets.user_1 }}", // referenced from secrets registered in above task
                "login_password": "{{ secrets.pass_1 }}", // referenced from secrets registered in above task
                "db": "db_1", // raw string value
                "script": "Select * from username_table" // raw string value
            },
            "register": "usernames"
        },
        {
            "name": "Another SQL Query on another server",
            "module": "mssql_script",
            "moduleParams": {
                "login_host": "host given as raw string", // raw string value
                "login_user": "{{ secrets.user_2 }}", // another server username set from secrets
                "login_password": "{{ secrets.pass_2 }}", // another server password set from secrets
                "db": "db_2", // raw string value
                "script": "Select * from data_table" // raw string value
            },
            "register": "data"
        },
        {
            "name": "Perform a GET request",
            "module": "uri",
            "moduleParams": {
                "url": "{{ url }}",
                "method": "GET"
            },
            "register": "get_response"
        },
        {
            "name": "Print GET response",
            "module": "debug",
            "moduleParams": {
                "msg": "GET_RESPONSE: {{ get_response }}"
            }
        },
        {
            "name": "Produce kafka event",
            "module": "kafka_producer",
            "moduleParams": {
                "body": {
					"title": "foo",
					"body": "{{ get_response.json[0].body }}",
					"userId": "{{ get_response.json[0].userId }}"
				}
            },
            "register": "post_response"
        },
        {
            "name": "Print POST response",
            "module": "debug",
            "moduleParams": {
                "msg": "POST_RESPONSE: {{ post_response }}"
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
        },
        {
            "name": "Run a command",
            "module": "command",
            "moduleParams": "ls"
        }
    ]
}'
