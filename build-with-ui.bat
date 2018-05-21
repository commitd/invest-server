
echo "Using UI from $1"

echo "Copy main UI app into place"
mkdir invest-plugins\invest-plugin-ui-app\src\main\resources\ui\app
rmdir /s /q invest-plugins\invest-plugin-ui-app\src\main\resources\ui\app\*
robocopy /s /e %1\apps\invest-app\build\. invest-plugins\invest-plugin-ui-app\src\main\resources\ui\app\.

echo "Copy UI plugins into place"
mkdir invest-plugins\invest-plugin-ui-actiondev\src\main\resources\ui\dev-action\
rmdir /s /q invest-plugins\invest-plugin-ui-actiondev\src\main\resources\ui\dev-action\*
robocopy /s /e %1\plugins\invest-plugin-actiondev\build\. invest-plugins\invest-plugin-ui-actiondev\src\main\resources\ui\dev-action\.

echo "Copying invest.js library into place"
mkdir invest-plugins\invest-plugin-ui-libs\src\main\resources\ui\libs
rmdir /s /q invest-plugins\invest-plugin-ui-libs\src\main\resources\ui\libs\invest.js
copy %1\bundles\invest-js\build\invest.js invest-plugins\invest-plugin-ui-libs\src\main\resources\ui\libs\invest.js

call build-java.sh
