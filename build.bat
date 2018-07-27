echo "Downloading new UI libs"
cd invest-plugins\invest-plugin-ui-libs
call .\download.bat
cd ..\..


echo "Build with Maven (without testing)"
call mvn clean package install -DskipTests

echo "Moving artifacts to the build dir"
rmdir /s build
mkdir build\plugins
:: Copy server
copy /s /e invest-server\invest-server-app\target\*.jar build\.
:: Copy plugins
copy invest-plugins\invest-plugin-graphql\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-graphql-ui\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-server-audit\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-server-auth\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-ui-app\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-ui-graphiql\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-ui-libs\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-ui-livedev\target\*.jar build\plugins\.
copy invest-plugins\invest-plugin-ui-actiondev\target\*.jar build\plugins\.


echo "java -Dloader.path=plugins\ -jar invest-server-app-0.6.0-SNAPSHOT.jar" > build\run.sh
