
:: Delete old documentation
rmdir /q  build\documentation

:: build new documenentation
cd documentation\website
call yarn build
cd ..\..

:: Copy the documentation into place
mkdir build
copy -r documentation\website\build\ build\documentation\.

