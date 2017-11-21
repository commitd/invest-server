#!/bin/bash

mkdir -p src/main/resources/ui/libs

cd src/main/resources/ui/libs

curl -o react-16.0.js  https://unpkg.com/react@16.1.1/umd/react.production.min.js
curl -o react-dom-16.0.js  https://unpkg.com/react-dom@16.0.0/umd/react-dom.production.min.js
curl -o semantic-2.2.js https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js
curl -o semantic-2.2.css https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css
curl -o jquery-3.2.js https://code.jquery.com/jquery-3.2.1.min.js

cd -