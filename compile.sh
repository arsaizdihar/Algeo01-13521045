cd src
javac -d ../bin ./*.java
cd ../bin
jar -cvfm matriks.jar ../src/META-INF/MANIFEST.MF *