# Tubes 1 Aljabar Linear & Geometri: Sistem Persamaan Linier, Determinan, dan Aplikasinya [Mathtricks]
##  Table of contents

- <a href="#description">Deskripsi</a>
- <a href="#how-to-run">Cara Menggunakan Program</a>

<h2 id="description">Deskripsi</h2>
Tugas besar ini berisi program dalam bahasa Java yang berisi fungsi-fungsi eliminasi Gauss, eliminasi Gauss-Jordan, menentukan balikan matriks, menghitung determinan, kaidah Cramer (kaidah Cramer khusus untuk SPL dengan n peubah dan n persamaan), interpolasi polinomial, interpolasi bicubic, dan regresi linier berganda. Detail terkait program ini bisa dilihat di <a href="doc/Algeo01-13521045.pdf">file laporan</a>.

<h2 id="how-to-run">Cara Menggunakan Program</h2>

### *Compile*
*Compile* program Java bisa dilakukan dengan cara:
```bash
sh compile.sh
```
Atau, untuk *compile* program ke bytecode saja di folder bin bisa dengan:
```bash
cd src
javac -d ../bin ./*.java
```
Lalu, untuk *compile* hasil bytecode ke file .jar di folder bin bisa dengan:
```bash
cd bin
jar -cvfm matrix.jar ../src/META-INF/MANIFEST.MF *
```

### *Run*
Untuk menjalankan program yang telah di-*compile*, bisa dengan menjalankan file .jar ataupun bytecode.

#### Bytecode
Untuk menjalankan program dari bytecode bisa dengan:
```bash
sh run.sh
```
Atau
```
cd bin
java Main
```
#### File .jar
Sedangkan untuk menjalankan program dari file .jar bisa dengan:
```bash
sh runJar.sh
```
atau
```bash
cd bin
java -jar matrix.jar
```