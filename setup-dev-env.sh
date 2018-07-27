#!/bin/bash

# Do a compile of the Java code which will fetch dependencies
mvn compile -DskipTests

# Install documentation too
cd documentation/website
yarn install
cd -