#!/bin/sh

echo "On OSX first run: export GPG_TTY=$(tty)"
./build-with-ui.sh
mvn  deploy -P release

