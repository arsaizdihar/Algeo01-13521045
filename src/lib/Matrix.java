package lib;

public class Matrix {
    private double[][] contents;

    /**
     * Matriks konstruktor. Menginisiasi matriks dengan ukuran baris x kolom.
     * 
     * @param nRow jumlah baris
     * @param nCol jumlah kolom
     * @return matriks baru dengan ukuran nRow x nCol
     */
    public Matrix(int nRow, int nCol) {
        // ALGORITMA
        this.contents = new double[nRow][nCol];
    }

    /*** SELEKTOR ***/

    /**
     * 
     * @return jumlah baris pada matriks
     */
    public int getNRow() {
        // ALGORITMA
        return contents.length;
    }

    /**
     * 
     * @return jumlah kolom pada matriks, 0 jika matriks kosong
     */
    public int getNCol() {
        if (getNRow() == 0)
            return 0;
        return contents[0].length;
    }

    /**
     * 
     * @param rowIdx indeks baris yang ingin diakses
     * @return baris indeks ke-rowIdx
     */
    public double[] getRow(int rowIdx) {
        return contents[rowIdx];
    }

    /**
     * 
     * @param colIdx indeks kolom yang ingin diakses
     * @return kolom indeks ke-colIdx
     */
    public double[] getCol(int colIdx) {
        // KAMUS LOKAL
        double[] col;

        // ALGORITMA
        col = new double[getNRow()];
        for (int i = 0; i < getNRow(); i++) {
            col[i] = contents[i][colIdx];
        }
        return col;
    }

    /**
     * 
     * @param rowIdx indeks baris yang ingin diakses
     * @param colIdx indeks kolom yang ingin diakses
     * @return elemen matriks pada indeks (rowIdx, colIdx)
     */
    public double getElmt(int rowIdx, int colIdx) {
        // ALGORITMA
        return contents[rowIdx][colIdx];
    }

    /**
     * Mengubah elemen matriks pada indeks (rowIdx, colIdx) menjadi val
     * <p>
     * I.S: matriks terdefinisi, rowIdx dan colIdx merupakan indeks yang valid
     * <p>
     * F.S: elemen matriks pada indeks (rowIdx, colIdx) menjadi val
     * 
     * @param rowIdx indeks baris yang ingin diubah
     * @param colIdx indeks kolom yang ingin diubah
     * @param val    nilai baru
     */
    public void setElmt(int rowIdx, int colIdx, double val) {
        contents[rowIdx][colIdx] = val;
    }

    /*** PENGECEKAN MATRIKS ***/
    /**
     * Mengecek apakah matriks merupakan matriks SPL yang valid (berukuran n x
     * (n+1))
     * 
     * @return true jika matriks merupakan matriks SPL yang valid, false jika tidak
     */
    public boolean isSPL() {
        // ALGORITMA
        return getNCol() == getNRow() + 1;
    }

    /**
     * 
     * @return true jika matriks merupakan matriks persegi, false jika tidak
     */
    public boolean isSquare() {
        return getNRow() == getNCol();
    }

    /**
     * 
     * @param rowIdx indeks baris yang ingin dicek
     * @return true jika semua elemen pada baris ke-rowIdx bernilai 0, false jika
     */
    public boolean isRowEmpty(int rowIdx) {
        // KAMUS LOKAL
        boolean isEmpty;

        // ALGORITMA
        isEmpty = true;
        for (int i = 0; i < getNCol() && isEmpty; i++) {
            if (getElmt(rowIdx, i) != 0) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    /*** OPERATOR PADA DIRI SENDIRI ***/

    /**
     * Menukar baris ke-idx1 dengan baris ke-idx2
     * <p>
     * I.S. matriks terdefinisi, idx1 dan idx2 merupakan indeks yang valid
     * <p>
     * F.S. baris ke-idx1 menjadi baris ke-idx2 dan sebaliknya
     * 
     * @param idx1 indeks baris pertama yang ingin ditukar
     * @param idx2 indeks baris kedua yang ingin ditukar
     */
    public void swapRow(int idx1, int idx2) {
        // KAMUS LOKAL
        double[] temp;

        // ALGORITMA
        temp = contents[idx1];
        contents[idx1] = contents[idx2];
        contents[idx2] = temp;
    }

    /**
     * Mentranspose matriks
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. matriks menjadi transpose dari matriks sebelumnya (a[i][j] menjadi
     * a[j][i])
     */
    public void transpose() {
        // KAMUS LOKAL
        double[][] temp;

        // ALGORITMA
        temp = new double[getNCol()][getNRow()];
        for (int i = 0; i < getNRow(); i++) {
            for (int j = 0; j < getNCol(); j++) {
                temp[j][i] = contents[i][j];
            }
        }
        contents = temp;
    }

    /**
     * Mengalikan baris ke-rowIdx dengan skalar
     * <p>
     * I.S. matriks terdefinisi, rowIdx merupakan indeks yang valid
     * <p>
     * F.S. baris ke-rowIdx menjadi baris ke-rowIdx dikali skalar
     * 
     * @param rowIdx indeks baris yang ingin dikalikan
     * @param scalar skalar yang akan mengali baris
     */
    public void multiplyRowScalar(int rowIdx, double scalar) {
        // ALGORITMA
        for (int i = 0; i < getNCol(); i++) {
            contents[rowIdx][i] *= scalar;
        }
    }

    /**
     * Mengalikan matriks dengan skalar
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. semua elemen pada matriks dikalikan dengan skalar
     * 
     * @param scalar skalar yang akan mengali matriks
     */
    public void multiplyScalar(double scalar) {
        // ALGORITMA
        for (int i = 0; i < getNRow(); i++) {
            multiplyRowScalar(i, scalar);
        }
    }

    /**
     * Mengecek apakah dua matriks memiliki dimensi yang sama
     * <p>
     * I.S. dua buah matriks terdefinisi.
     * <p>
     * F.S. dikembalikan apakah kedua matriks memiliki dimensi yang sama.
     * matriks
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks1 matriks kedua yang ingin dicek
     */
    public static boolean isDimensionSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNRow() == matriks2.getNRow() && matriks1.getNCol() == matriks2.getNCol();
    }

    /**
     * Mengecek apakah dua matriks memiliki kolom yang sama. Digunakan untuk
     * mengecek jika dua matriks bisa dikalikan
     * <p>
     * I.S. dua buah matriks terdefinisi.
     * <p>
     * F.S. dikembalikan apakah kedua matriks memiliki kolom yang sama.
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks1 matriks kedua yang ingin dicek
     */
    public static boolean isColumnSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNCol() == matriks2.getNCol();
    }

    /**
     * Mengalikan 2 buah matriks
     * <p>
     * I.S. dua buah matriks yang ingin dikalikan terdefinisi. matriks pertama harus
     * punya jumlah kolom sejumlah baris matriks kedua
     * <p>
     * F.S. dikembalikan sebuah matriks baru yang merupakan hasil perkalian dua buah
     * matriks
     * 
     * @param matriks1 matriks pertama yang ingin dikalikan
     * @param matriks1 matriks kedua yang ingin dikalikan
     */
    public static Matrix multiply(Matrix matriks1, Matrix matriks2) throws Exception {
        // ALGORITMA
        if (!isColumnSame(matriks1, matriks2)) {
            throw new Exception("Kolom kedua matriks tidak berjumlah sama.");
        } else {

            Matrix hasilMatrix = new Matrix(matriks1.getNRow(), matriks2.getNCol());

            for (int i = 0; i < hasilMatrix.getNRow(); i++) {
                for (int j = 0; j < hasilMatrix.getNCol(); j++) {
                    double hasilCell = 0;
                    for (int k = 0; k < matriks1.getNCol(); k++) {
                        hasilCell = hasilCell + matriks1.getElmt(i, k) * matriks2.getElmt(k, j);
                    }
                    hasilMatrix.setElmt(i, j, hasilCell);
                }
            }
            return hasilMatrix;
        }
    }

    /**
     * Menjumlahkan 2 buah matriks
     * <p>
     * I.S. dua buah matriks yang ingin dijumlahkan terdefinisi. matriks pertama
     * harus
     * punya jumlah kolom dan baris sejumlah baris matriks kedua
     * <p>
     * F.S. dikembalikan sebuah matriks baru yang merupakan hasil penjumlahan dua
     * buah
     * matriks
     * 
     * @param matriks1 matriks pertama yang ingin dijumlahkan
     * @param matriks1 matriks kedua yang ingin dijumlahkan
     */
    public static Matrix add(Matrix matriks1, Matrix matriks2) throws Exception {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Exception("Dimensi kedua matriks tidak sama.");
        } else {
            Matrix hasilMatrix = new Matrix(matriks1.getNRow(), matriks2.getNCol());

            for (int i = 0; i < hasilMatrix.getNRow(); i++) {
                for (int j = 0; j < hasilMatrix.getNCol(); j++) {
                    double hasilCell = matriks1.getElmt(i, j) + matriks2.getElmt(i, j);
                    hasilMatrix.setElmt(i, j, hasilCell);
                }
            }
            return hasilMatrix;
        }

    }

    /**
     * Menjumlahkan 2 buah matriks
     * <p>
     * I.S. dua buah matriks yang ingin dioperasikan terdefinisi. matriks pertama
     * harus
     * punya jumlah kolom dan baris sejumlah baris matriks kedua
     * <p>
     * F.S. dikembalikan sebuah matriks baru yang merupakan hasil pengurangan
     * matriks pertama oleh matriks kedua
     * 
     * @param matriks1 matriks pertama yang ingin dikurangkan
     * @param matriks1 matriks kedua yang akan mengurangkan
     */
    public static Matrix subtract(Matrix matriks1, Matrix matriks2) throws Exception {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Exception("Dimensi kedua matriks tidak sama.");
        } else {
            Matrix hasilMatrix = new Matrix(matriks1.getNRow(), matriks2.getNCol());

            for (int i = 0; i < hasilMatrix.getNRow(); i++) {
                for (int j = 0; j < hasilMatrix.getNCol(); j++) {
                    double hasilCell = matriks1.getElmt(i, j) - matriks2.getElmt(i, j);
                    hasilMatrix.setElmt(i, j, hasilCell);
                }
            }
            return hasilMatrix;
        }

    }
}
