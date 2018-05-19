:: Install documentation too
cd documentation\website
call yarn install
cd ..\..
::
:: Do a compile of the Java code which will fetch dependencies
call mvn compile -DskipTests

