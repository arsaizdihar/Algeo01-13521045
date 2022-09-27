#!/bin/bash

if test -f "bin/Main.class"; then
  cd bin
  java Main
else
  echo "No java Main class byte code found. Please compile first by running 'sh compile.sh'"
fi