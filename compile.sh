#!/bin/bash

cd src
javac -d ../bin ./*.java
[ -d "bin" ] || mkdir bin
cd ../bin
jar -cvfm matrix.jar ../src/META-INF/MANIFEST.MF *