#!/bin/sh
./build-with-ui.sh
export GPG_TTY=$(tty)
mvn  deploy -P release

