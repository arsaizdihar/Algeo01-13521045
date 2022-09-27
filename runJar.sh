#!/bin/bash

if test -f "bin/matrix.jar"; then
  cd bin
  java -jar matrix.jar
else
  echo "No jar file found. Please compile first by running 'sh compile.sh'"
fi