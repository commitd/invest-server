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
mkdir -p invest-plugins/invest-plugin-ui-actiondev/src/main/resources/ui/dev-action/
rm -rf invest-plugins/invest-plugin-ui-actiondev/src/main/resources/ui/dev-action/*
cp -a $1/plugins/invest-plugin-actiondev/build/. invest-plugins/invest-plugin-ui-actiondev/src/main/resources/ui/dev-action/.

echo "Copying invest.js library into place"
mkdir -p invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs
rm -rf invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs/invest.js
cp $1/bundles/invest-js/build/invest.js invest-plugins/invest-plugin-ui-libs/src/main/resources/ui/libs/invest.js

./build-java.sh
