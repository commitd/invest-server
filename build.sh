#!/bin/bash

echo "Downloading new UI libs"
cd invest-plugins/invest-plugin-ui-libs
./download.sh
cd -


echo "Build with Maven (without testing)"
mvn clean package install -DskipTests

echo "Moving artifacts to the build dir"
rm -rf build
mkdir -p build/plugins
# Copy server
cp invest-server/invest-server-app/target/*.jar build/.
# Copy plugins
cp invest-plugins/invest-plugin-graphql/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-graphql-ui/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-server-audit/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-server-auth/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-app/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-graphiql/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-libs/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-livedev/target/*.jar build/plugins/.
cp invest-plugins/invest-plugin-ui-actiondev/target/*.jar build/plugins/.


echo "java -Dloader.path=plugins/ -jar invest-server-app-*.jar" > build/run.sh
chmod +x build/run.sh
