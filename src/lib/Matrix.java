package lib;

import lib.Errors.DifferentColumnSize;
import lib.Errors.DifferentMatrixDimension;
import lib.Errors.InvalidMatrixSizeException;
import lib.Errors.InvalidMatrixSquareException;
import lib.Errors.NoSolutionException;

public class Matrix {
    private double[][] contents;

    /*** KONSTRUKTOR ***/

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
     * Mengubah baris indeks ke-rowIdx dengan baris baru
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. baris indeks ke-rowIdx diubah dengan baris baru
     * 
     * @param rowIdx indeks baris yang akan diubah
     * @param row    baris baru
     */
    public void setRow(int rowIdx, double[] row) {
        contents[rowIdx] = row;
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
     * @return konten matriks
     */
    public double[][] getContents() {
        return contents;
    }

    /**
     * Mengubah keseluruhan matriks
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. matriks berubah sesuai dengan contents
     * 
     * @param contents isi konten matriks baru
     */
    public void setContents(double[][] contents) {
        this.contents = contents;
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
     * Mengecek apakah matriks merupakan matriks SPL yang valid (minimal baris
     * sejumlah kolom - 1)
     * 
     * @return true jika matriks merupakan matriks SPL yang valid, false jika tidak
     */
    public boolean isSPL() {
        // ALGORITMA
        return getNRow() >= getNCol() - 1;
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

    /*** OPERATOR LAINNYA ***/

    /**
     * 
     * @param rowIdx
     * @param colIdx
     * @return matriks dengan baris ke rowIdx dan kolom ke colIdx dihilangkan
     */
    public Matrix getDeletedRowCol(int rowIdx, int colIdx) {
        // KAMUS LOKAL
        Matrix res;
        int iRes, jRes;

        // ALGORITMA
        res = new Matrix(getNRow() - 1, getNCol() - 1);

        for (int i = 0; i < getNRow(); i++) {
            for (int j = 0; j < getNCol(); j++) {
                if (i != rowIdx && j != colIdx) {
                    // menentukan indeks baru
                    iRes = i < rowIdx ? i : i - 1;
                    jRes = j < colIdx ? j : j - 1;

                    res.setElmt(iRes, jRes, getElmt(i, j));
                }
            }
        }
        return res;
    }

    /**
     * Mengubah matriks menjadi matriks SPL yang valid dengan membuat baris baru
     * dengan elemen 0 hingga jumlah baris sama dengan jumlah kolom - 1
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. matriks merupakan matriks SPL yang valid
     */
    public void toValidSPL() {
        // KAMUS LOKAL
        Matrix temp;

        // ALGORITMA
        if (isSPL()) {
            return;
        }

        temp = new Matrix(getNCol() - 1, getNCol());
        for (int i = 0; i < getNRow(); i++) {
            if (i < getNRow()) {
                temp.setRow(i, getRow(i));
            } else {
                for (int j = 0; j < getNCol(); j++) {
                    temp.setElmt(i, j, 0);
                }
            }
        }

        setContents(temp.getContents());
    }

    /**
     * Mencari determinan matriks dengan metode kofaktor, throw error jika bukan
     * matriks persegi
     * 
     * @return determinan matriks
     * @throws InvalidMatrixSquareException
     */
    public double getDeterminantCofactor() throws InvalidMatrixSquareException {
        // KAMUS LOKAL
        double res;
        double multiplier;

        // ALGORITMA
        // basis, matriks 1x1
        if (!isSquare()) {
            throw new Errors.InvalidMatrixSquareException();
        }
        if (getNRow() == 1) {
            return getElmt(0, 0);
        } else {
            res = 0;
            for (int i = 0; i < getNCol(); i++) {
                multiplier = i % 2 == 0 ? 1 : -1;
                res += getElmt(0, i) * multiplier * getDeletedRowCol(0, i).getDeterminantCofactor();
            }
            return res;
        }
    }

    /**
     * Mencari solusi SPL dengan metode cramer
     * 
     * @return matriks solusi SPL (kolom berjumlah 1)
     * @throws InvalidMatrixSizeException
     * @throws NoSolutionException
     */
    public Matrix getSolCramer() throws Errors.NoSolutionException, InvalidMatrixSizeException {
        // KAMUS LOKAL
        Matrix res, temp, square;
        double det;

        // ALGORITMA
        if (!isSPL()) {
            throw new Errors.NoSolutionException();
        }

        if (getNRow() >= getNCol()) {
            throw new Errors.InvalidMatrixSizeException();
        }

        // ALGORITMA
        res = new Matrix(getNCol() - 1, 1);
        square = new Matrix(getNRow(), getNRow());

        for (int i = 0; i < getNRow(); i++) {
            for (int j = 0; j < getNRow(); j++) {
                square.setElmt(i, j, getElmt(i, j));
            }
        }

        try {
            det = square.getDeterminantCofactor();
        } catch (InvalidMatrixSquareException e) {
            throw new RuntimeException(e);
        }

        if (det == 0) {
            throw new Errors.NoSolutionException();
        }

        for (int i = 0; i < getNCol() - 1; i++) {
            temp = new Matrix(getNRow(), getNRow());
            for (int j = 0; j < getNRow(); j++) {
                for (int k = 0; k < getNCol() - 1; k++) {
                    if (k == i) {
                        temp.setElmt(j, k, getElmt(j, getNCol() - 1));
                    } else {
                        temp.setElmt(j, k, getElmt(j, k));
                    }
                }
            }
            try {
                res.setElmt(i, 0, temp.getDeterminantCofactor() / det);
            } catch (InvalidMatrixSquareException e) {
                throw new RuntimeException(e);
            }
        }

        return res;
    }

    /**
     * Mengecek apakah dua matriks memiliki jumlah kolom yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks1 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki jumlah kolom yang sama.
     */
    public static boolean isNColumnSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNCol() == matriks2.getNCol();
    }

    /**
     * Mengecek apakah dua matriks memiliki jumlah baris yang sama.
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks1 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki jumlah baris yang sama.
     */
    public static boolean isNRowSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNRow() == matriks2.getNRow();
    }

    /**
     * Mengecek apakah dua matriks memiliki dimensi yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks1 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki dimensi yang sama.
     */
    public static boolean isDimensionSame(Matrix matriks1, Matrix matriks2) {
        return isNRowSame(matriks1, matriks2) && isNColumnSame(matriks1, matriks2);
    }

    /**
     * Mengalikan 2 buah matriks
     * Fungsi akan throw error jika matriks yang diberikan tidak memiliki jumlah
     * kolom yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dikalikan
     * @param matriks1 matriks kedua yang ingin dikalikan
     * @return matriks baru yang merupakan hasil perkalian kedua matriks yang
     *         diberikan
     * @throws DifferentColumnSize
     */
    public static Matrix multiply(Matrix matriks1, Matrix matriks2) throws DifferentColumnSize {
        // ALGORITMA
        if (!isNColumnSame(matriks1, matriks2)) {
            throw new Errors.DifferentColumnSize();
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
     * Fungsi akan throw error jika matriks yang diberikan tidak memiliki dimensi
     * yang sama
     * 
     * 
     * @param matriks1 matriks pertama yang ingin dijumlahkan
     * @param matriks1 matriks kedua yang ingin dijumlahkan
     * @return matriks baru yang merupakan hasil penjumlahan dua buah matriks
     * @throws DifferentMatrixDimension
     * 
     */
    public static Matrix add(Matrix matriks1, Matrix matriks2) throws DifferentMatrixDimension {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Errors.DifferentMatrixDimension();
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
     * Mengurangkan 2 buah matriks. Matriks pertama dikurangi matriks kedua.
     * Fungsi akan throw error jika matriks yang diberikan tidak memiliki dimensi
     * yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dikurangkan
     * @param matriks1 matriks kedua yang akan mengurangkan
     * @return matriks baru yang merupakan hasil pengurangan matriks pertama oleh
     *         matriks kedua
     * @throws DifferentMatrixDimension
     * 
     */
    public static Matrix subtract(Matrix matriks1, Matrix matriks2) throws DifferentMatrixDimension {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Errors.DifferentMatrixDimension();
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
