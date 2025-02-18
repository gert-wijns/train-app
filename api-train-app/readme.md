# General

Contains the API exposed by the application.
Currently implemented as openapi-specification which is used to generate code.

The yaml files are used to generate specifications which will be implemented in the backend
and the same yamls can be used on a frontend to generate backend apis.

# Setup

## Prerequisites
* java 23

## Steps
1. clean install
2. Mark /target/generated-sources/openapi/src/main/java as "Generated sources root"
3. clean install again (from now on the built jar will contain all generated api classes)
