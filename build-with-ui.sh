#!/bin/bash

echo "Using UI from $1"

echo "Building UI"
cd $1
yarn build
cd -

echo "Copy main UI app into place"
mkdir -p invest-plugins/invest-plugin-ui-app/src/main/resources/ui/app
rm -rf invest-plugins/invest-plugin-ui-app/src/main/resources/ui/app/*
cp -a $1/apps/invest-app/build/. invest-plugins/invest-plugin-ui-app/src/main/resources/ui/app/.

echo "Copy UI plugins into place"
mkdir -p invest-plugins/invest-plugin-ui-actiondev/src/main/resources/ui/dev-action
rm -rf invest-plugins/invest-plugin-ui-actiondev/src/main/resources/ui/dev-action/*
cp -a $1/plugins/ingest-plugin-actiondev/build/. invest-plugins/invest-plugin-actiondev/src/main/resources/ui/dev-action/.

echo "Copying invest.js library into place"
mkdir -p invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs
rm -rf invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs/invest.js
cp $1/bundles/invest-js/build/invest.js invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs/invest.js

echo "Downloading new UI libs"
cd invest-plugins/invest-plugin-ui-libs
./download.sh
cd -


echo "Build with Maven (without testing)"
mvn clean package -DskipTests

echo "Moving artifacts to the build dir"
rm -rf build
mkdir -p build/plugins
# Copy server
cp invest-server/invest-server-app/target/*.jar build/.
# Copy plugins
cp invest-plugins/invest-plugin-examples/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-graphql/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-graphql-ui/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-server-audit/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-server-auth/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-app/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-graphiql/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-libs/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-livedev/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-actiondev/target/*.jar build/plugins/.


echo "java -Dloader.path=plugins/ -jar invest-server-app-1.0-SNAPSHOT.jar" > build/run.sh
chmod +x build/run.sh
