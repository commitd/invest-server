#!/bin/sh

echo "On OSX first run: export GPG_TTY=$(tty)"
mvn  deploy -P release

