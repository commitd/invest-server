#!/bin/bash

echo "Using UI from $1"

echo "Building UI"
cd $1
yarn build
cd -

echo "Copy main UI app into place"
mkdir -p vessel-plugins/vessel-plugin-ui-app/src/main/resources/ui/app
rm -rf vessel-plugins/vessel-plugin-ui-app/src/main/resources/ui/app/*
cp -a $1/apps/vessel-app/build/. vessel-plugins/vessel-plugin-ui-app/src/main/resources/ui/app/.

echo "Copying vessel.js library into place"
mkdir -p vessel-plugins/vessel-plugin-ui-libs/src/main/resources/ui/libs
rm -rf vessel-plugins/vessel-plugin-ui-libs/src/main/resources/ui/libs/vessel.js
cp $1/bundles/vessel-js/build/vessel.js vessel-plugins/vessel-plugin-ui-libs/src/main/resources/ui/libs/vessel.js

echo "Downloading new UI libs"
cd vessel-plugins/vessel-plugin-ui-libs
./download.sh
cd -

echo "Build with Maven (without testing)"
mvn clean package -DskipTests

echo "Moving artifacts to the build dir"
rm -rf build
mkdir -p build/plugins
# Copy server
cp vessel-server/vessel-server-app/target/*.jar build/.
# Copy plugins
cp vessel-plugins/vessel-plugin-examples/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-graphql/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-graphql-ui/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-server-audit/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-server-auth/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-ui-app/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-ui-graphiql/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-ui-libs/target/*.jar build/plugins/.
cp vessel-plugins/vessel-plugin-ui-livedev/target/*.jar build/plugins/.


echo "java -Dloader.path=plugins/ -jar vessel-server-app-1.0-SNAPSHOT.jar" > build/run.sh
chmod +x build/run.sh
