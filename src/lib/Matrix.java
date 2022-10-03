package lib;

import java.text.NumberFormat;
import java.util.ArrayList;

import lib.Errors.InvalidMatrixSizeException;
import lib.Errors.InvalidMatrixSquareException;
import lib.Errors.NoInverseException;
import lib.Errors.NoSolutionException;

public class Matrix {
    private double[][] contents;
    static final Matrix inverseBicubicCoefficientMatrix = getInverseBicubicCoefficient();

    /*** KONSTRUKTOR ***/

    /**
     * Matriks konstruktor. Menginisiasi matriks dengan ukuran baris x kolom.
     * <p>
     * Prekondisi: nRow, nCol >= 1
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
     * Mengubah baris indeks ke-rowIdx dengan baris baru.
     * <p>
     * Prekondisi: baris baru memiliki jumlah kolom sama dengan matriks.
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
     * Prekondisi: contents merupakan array dengan panjang >= 1 yang seluruh elemennya merupakan array dengan panjang sama dan >= 1.
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
     * @return true jika matriks merupakan kumpulan point (satu baris terdiri atas
     *         dua kolom x dan y)
     */
    public boolean isPoints() {
        return getNCol() == 2;
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
     * Mengisi seluruh elemen pada matriks dengan nol
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. seluruh elemen pada matriks menjadi nol
     */
    public void fillZero() {
        for (int i = 0; i < getNRow(); i++) {
            for (int j = 0; j < getNCol(); j++) {
                setElmt(i, j, 0);
            }
        }
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
    /*** OPERATOR LAINNYA ***/

    /**
     * Prekondisi: startColIdx dan endColIdx merupakan indeks kolom yang valid dan endColIdx > startColdIdx
     * 
     * @param startColIdx indeks kolom awal yang ingin di salin
     * @param endColIdx   indeks kolom akhir yang ingin di salin
     * @return matriks baru yang merupakan salinan matriks dari kolom startColIdx sampai kolom endColIdx
     */
    public Matrix getCopyMatrixByColumn(int startColIdx, int endColIdx) {
        // KAMUS LOKAL
        Matrix resultMatrix;

        // ALGORITMA
        resultMatrix = new Matrix(this.getNRow(), endColIdx - startColIdx + 1);
        for (int rowIdx = 0; rowIdx <= this.getNRow() - 1; rowIdx++) {
            for (int colIdx = startColIdx; colIdx <= endColIdx; colIdx++) {
                resultMatrix.setElmt(rowIdx, colIdx - startColIdx, this.getElmt(rowIdx, colIdx));
            }
        }

        return resultMatrix;
    }

    /**
     * Prekondisi: startRowIdx dan endRowIdx merupakan indeks baris yang valid dan endRowIdx > startRowIdx. colIdx merupakan 
     * indeks kolom yang valid
     * @param startRowIdx index baris awal yang ingin dicari
     * @param endRowIdx   index baris akhir yang ingin dicari
     * @param colIdx      index kolom ingin dicari
     * @return mengembalikan idx baris ditemukan pertama kali yang tidak nol dalam
     *         satu kolom. Jika tidak ditemukan, akan mengembalikan (-1)
     */
    public int getNonZeroRowIdx(int startRowIdx, int endRowIdx, int colIdx) {
        // KAMUS LOKAL
        boolean isFound;
        int foundRowIdx, currRowIdx;

        // ALGORITMA
        isFound = false;
        foundRowIdx = -1;
        currRowIdx = startRowIdx;

        while (!(isFound) && (currRowIdx <= endRowIdx)) {
            if (getElmt(currRowIdx, colIdx) != 0) {
                isFound = true;
                foundRowIdx = currRowIdx;
            } else {
                currRowIdx++;
            }
        }
        return foundRowIdx;
    }

    /**
     * I.S. nilai dari matriks pada index startRowIdx dan rowIdx sudah bernilai 1
     * <p>
     * F.S> satu kolom dari matriks dari startRowIdx hingga endRowIdx memiliki nilai
     * nol
     * <p>
     * 
     * @param startRowIdx index baris awal yang ingin dibat sebagai leading one
     * @return mengembalikan idx baris ditemukan pertama kali yang tidak nol dalam
     *         satu kolom. Jika tidak ditemukan, akan mengembalikan (-1)
     */
    public void makeColumnZero(int leadingOneRowIdx, int startRowIdx, int endRowIdx, int mainColIdx) {
        // KAMUS LOKAL
        double anchorElmt;
        // ALGORITMA
        for (int rowIdx = startRowIdx; rowIdx <= endRowIdx; rowIdx++) {
            anchorElmt = getElmt(rowIdx, mainColIdx);
            for (int colIdx = 0; colIdx <= getNCol() - 1; colIdx++) {
                setElmt(rowIdx, colIdx, getElmt(rowIdx, colIdx) - anchorElmt * getElmt(leadingOneRowIdx, colIdx));
            }
        }
    }
    /**
     * Prekondisi: startColIdx dan endColIdx merupakan indeks kolom yang valid dan endColIdx > startColdIdx
     * @param startColIdx index baris awal yang ingin dibuat sebagai leading one
     * @param endColIdx   index baris terakhir yang ingin diubah menjadi bentuk
     *                    echelon
     * @return pair berisi bentuk matriks eselon dari startColIdx hingga
     * endColIdx dan total perkalian yang dilakukan
     */
    public Pair<Matrix, Double> getEchelonForm(int startColIdx, int endColIdx) {
        // KAMUS LOKAL
        Matrix hasil;
        int rowIdx, rowNonZeroIdx;
        double multiplier;

        // ALGORITMA
        hasil = getCopyMatrixByColumn(0, this.getNCol() - 1);

        rowIdx = 0;
        multiplier = 1;
        for (int colIdx = startColIdx; colIdx <= endColIdx; colIdx++) {
            rowNonZeroIdx = hasil.getNonZeroRowIdx(rowIdx, hasil.getNRow() - 1, colIdx);
            if (rowNonZeroIdx == -1) {
                continue;
            } else {
                hasil.swapRow(rowIdx, rowNonZeroIdx);
                multiplier *= hasil.getElmt(rowIdx, colIdx) * (-1);
                hasil.multiplyRowScalar(rowIdx, 1 / hasil.getElmt(rowIdx, colIdx));
                hasil.makeColumnZero(rowIdx, rowIdx + 1, hasil.getNRow() - 1, colIdx);
                rowIdx++;
            }
        }
        return new Pair<Matrix, Double>(hasil, multiplier);
    }
    /**
     * Prekondisi: startColIdx dan endColIdx merupakan indeks kolom yang valid dan endColIdx > startColdIdx
     * 
     * @param startColIdx Index kolom awal dari matriks yang ingin direduksi
     * @param endColIdx Index kolom akhir dari matriks yang ingin direduksi
     * @return Matriks dengan kolom yang terambil sudah dalam bentuk baris eselon tereduksi
     */
    public Matrix getReducedForm(int startColIdx, int endColIdx) {
        // KAMUS LOKAL
        Matrix hasil;
        int rowIdx, rowNonZeroIdx;

        // ALGORITMA
        hasil = this.getEchelonForm(startColIdx, endColIdx).first;
        rowIdx = 0;
        for (int colIdx = startColIdx; colIdx <= endColIdx; colIdx++) {
            rowNonZeroIdx = hasil.getNonZeroRowIdx(rowIdx, hasil.getNRow() - 1, colIdx);
            if (rowNonZeroIdx == -1) {
                continue;
            } else {
                hasil.makeColumnZero(rowIdx, 0, rowIdx - 1, colIdx);
                rowIdx++;
            }
        }
        return hasil;
    }

    /**
     * Prekondisi: jumlah kolom matriks >= 2
     * 
     * @return Matriks dengan matriks format solusi yang dapat ditampilkan, termasuk
     *         dengan variabel parametrik
     * @throws NoSolutionException ketika matriks SPL tidak memiliki solusi
     */
    public Matrix getSolG() throws NoSolutionException {
        Matrix hasil = this.getEchelonForm(0, this.getNCol() - 2).first;

        int rowIdx;
        boolean isFoundRowNoSolution;
        Matrix testMatrix = hasil.getCopyMatrixByColumn(0, getNCol() - 2);
        rowIdx = 0;
        isFoundRowNoSolution = false;
        while (!isFoundRowNoSolution && (rowIdx <= testMatrix.getNRow() - 1)) {
            if (testMatrix.isRowEmpty(rowIdx) && (hasil.getElmt(rowIdx, getNCol() - 1) != 0)) {
                isFoundRowNoSolution = true;
            } else {
                rowIdx++;
            }
        }

        if (isFoundRowNoSolution) {
            throw new Errors.NoSolutionException();
        }

        Matrix solusi = new Matrix(hasil.getNCol() - 1, hasil.getNCol() + 1);

        // isi semua elemen solusi dengan 0
        solusi.fillZero();

        /*
         * membuat matriks solusi menjadi bentuk x1 = ax2 + bx3 + ... + C dst.
         * Misal: hasil matriks eselon:
         * 1 2 3 1 5
         * 0 1 4 0 2
         * 0 0 1 1 0
         * 0 0 0 0 0
         * 
         * Setelah proses loop di bawah: matriks solusi akan menjadi
         * 0 -2 -3 1 5
         * 0 0 -4 0 2
         * 0 0 0 1 0
         * 0 0 0 0 0
         * 
         * yaitu
         * x1 = -2*x2 - 3*x3 + x4 + 5
         * x2 = -4*x3 + 2
         * x3 = 1
         * x4 = t (parameter)
         */
        for (int i = 0; i < hasil.getNRow(); i++) {
            int leadingOneIdx = -1;
            for (int j = i; j < hasil.getNCol() - 1 && leadingOneIdx == -1; j++) {
                if (hasil.getElmt(i, j) == 1.0) {
                    leadingOneIdx = j;
                }
            }

            if (leadingOneIdx == -1)
                break;
            solusi.setElmt(leadingOneIdx, solusi.getNCol() - 1, 1);
            // mengisi variable
            for (int j = leadingOneIdx + 1; j < hasil.getNCol(); j++) {
                double el = hasil.getElmt(i, j);
                if (j != hasil.getNCol() - 1)
                    el *= -1;
                solusi.setElmt(leadingOneIdx, j, el);
            }
        }

        /*
         * SUBSTITUSI nilai variable lain (kecuali jika variabel tersebut dijadikan
         * parameter)
         * Berdasarkan contoh sebelumnya, hasil solusi akan menjadi
         * x1 = t + 6
         * x2 = -2
         * x3 = 1
         * x4 = t
         */
        for (int i = 0; i < solusi.getNRow() - 1; i++) {
            for (int j = i + 1; j < solusi.getNCol() - 2; j++) {
                double variableConstant = solusi.getElmt(i, j);

                // jika nilai variabel -0 atau variabel merupakan variabel parametrik, tidak
                // perlu melakukan proses
                if (variableConstant == 0 || solusi.getElmt(j, solusi.getNCol() - 1) == 0)
                    continue;

                for (int k = j + 1; k < hasil.getNCol(); k++) {
                    double el = solusi.getElmt(j, k);
                    solusi.setElmt(i, k, solusi.getElmt(i, k) + variableConstant * el);
                }
                solusi.setElmt(i, j, 0);
            }
        }
        return solusi;
    }

    /**
     * Prekondisi: jumlah kolom matriks >= 2
     * 
     * @return Matriks dengan matriks format solusi yang dapat ditampilkan, termasuk
     *         dengan variabel parametrik
     * @throws NoSolutionException ketika matriks SPL tidak memiliki solusi
     */
    public Matrix getSolGJ() throws NoSolutionException {
        Matrix hasil = this.getReducedForm(0, this.getNCol() - 2);

        int rowIdx;
        boolean isFoundRowNoSolution;
        Matrix testMatrix = hasil.getCopyMatrixByColumn(0, this.getNCol() - 2);
        rowIdx = 0;
        isFoundRowNoSolution = false;
        while (!isFoundRowNoSolution && (rowIdx <= testMatrix.getNRow() - 1)) {
            if (testMatrix.isRowEmpty(rowIdx) && (hasil.getElmt(rowIdx, this.getNCol() - 1) != 0)) {
                isFoundRowNoSolution = true;
            } else {
                rowIdx++;
            }
        }

        if (isFoundRowNoSolution) {
            throw new Errors.NoSolutionException();
        }

        Matrix solusi = new Matrix(hasil.getNCol() - 1, hasil.getNCol() + 1);

        // isi semua elemen solusi dengan 0
        solusi.fillZero();

        /*
         * membuat matriks solusi menjadi bentuk x1 = ax2 + bx3 + ... + C dst.
         * Misal: hasil matriks eselon tereduksi:
         * 1 0 0 1 5
         * 0 1 0 0 2
         * 0 0 1 3 1
         * 0 0 0 0 0
         * 
         * Setelah proses loop di bawah: matriks solusi akan menjadi
         * 0 0 0 -1 5 1
         * 0 0 0 0 2 1
         * 0 0 0 -3 1 1
         * 0 0 0 0 0 0
         * 
         * yaitu
         * x1 = -x4 + 5
         * x2 = 2
         * x3 = -3*x4 + 1
         * x4 = t (parameter)
         */
        for (int i = 0; i < hasil.getNRow(); i++) {
            int leadingOneIdx = -1;
            for (int j = i; j < hasil.getNCol() - 1 && leadingOneIdx == -1; j++) {
                // hampir = 1
                if (Math.abs(hasil.getElmt(i, j) - 1.0) < 1E-6) {
                    leadingOneIdx = j;
                }
            }

            if (leadingOneIdx == -1)
                break;
            solusi.setElmt(leadingOneIdx, solusi.getNCol() - 1, 1);

            // mengisi variable
            for (int j = leadingOneIdx + 1; j < hasil.getNCol(); j++) {
                double el = hasil.getElmt(i, j);
                if (j != hasil.getNCol() - 1)
                    el *= -1;
                solusi.setElmt(leadingOneIdx, j, el);
            }
        }
        return solusi;
    }

    /**
     * Prekondisi: jumlah kolom matriks >= 2
     * 
     * @return Matriks solusi SPL dengan metode pengalian dengan inverse (kolom 1)
     * @throws NoSolutionException ketika matriks variabel pada SPL tidak memiliki inverse
     */
    public Matrix getSolInverse() throws NoInverseException {
        Matrix inversedMatrix, rightMostColumnMatrix, solutionMatrix;

        try {
            inversedMatrix = this.getCopyMatrixByColumn(0, this.getNCol() - 2).getInverseAdjoin();
        } catch (NoInverseException e) {
            throw new NoInverseException();
        }
        rightMostColumnMatrix = this.getCopyMatrixByColumn(this.getNCol() - 1, this.getNCol() - 1);

        try {
            solutionMatrix = multiply(inversedMatrix, rightMostColumnMatrix);
        } catch (InvalidMatrixSizeException e) {
            throw new RuntimeException(e);
        }

        return solutionMatrix;
    }

    /**
     * prekondisi: matriks merupakan maktriks square
     * 
     * @return mengembalikan matriks yang telah di augmentasi dengan matriks
     *         identitas
     * 
     */
    public Matrix getAugmentedMatrixByIdentity() {
        // KAMUS LOKAL
        Matrix augmentedMatrix;

        // ALGORITMA
        augmentedMatrix = new Matrix(this.getNRow(), 2 * this.getNRow());
        for (int rowIdx = 0; rowIdx <= this.getNRow() - 1; rowIdx++) {
            for (int colIdx = 0; colIdx <= this.getNCol() - 1; colIdx++) {
                augmentedMatrix.setElmt(rowIdx, colIdx, this.getElmt(rowIdx, colIdx));
            }
            for (int colIdx = this.getNCol(); colIdx <= 2 * this.getNCol() - 1; colIdx++) {
                if (colIdx == this.getNRow() + rowIdx) {
                    augmentedMatrix.setElmt(rowIdx, colIdx, 1);
                } else {
                    augmentedMatrix.setElmt(rowIdx, colIdx, 0);
                }

            }
        }
        return augmentedMatrix;
    }

    /**
     * 
     * @return mengembalikan invers matriks dengan metode OBE
     * @throws NoInverseException jka matriks tidak memiliki inverse
     */
    public Matrix getInverseOBE() throws NoInverseException {
        // KAMUS LOKAL
        int rowIdx;
        boolean isFoundRowEmpty;
        Matrix reducedMatrix, inversedMatrix, augmentedMatrix, testMatrix;

        // ALGORITMA
        augmentedMatrix = this.getAugmentedMatrixByIdentity();
        reducedMatrix = augmentedMatrix.getReducedForm(0, this.getNRow() - 1);
        testMatrix = reducedMatrix.getCopyMatrixByColumn(0, this.getNRow() - 1);

        rowIdx = 0;
        isFoundRowEmpty = false;
        while (!isFoundRowEmpty && (rowIdx <= testMatrix.getNRow() - 1)) {
            if (testMatrix.isRowEmpty(rowIdx)) {
                isFoundRowEmpty = true;
            } else {
                rowIdx++;
            }
        }

        if (isFoundRowEmpty) {
            throw new Errors.NoInverseException();
        }

        inversedMatrix = reducedMatrix.getCopyMatrixByColumn(getNRow(), 2 * getNRow() - 1);
        return inversedMatrix;
    }

    /**
     * 
     * @return mengembalikan invers matriks dengan metode adjoin
     * @throws NoInverseException jka matriks tidak memiliki inverse
     */
    public Matrix getInverseAdjoin() throws NoInverseException {
        // KAMUS
        Matrix cofactorMatrix;
        int i, j, sign;
        double determinant;

        // ALGORITMA
        try {
            determinant = getDeterminantCofactor();
        } catch (InvalidMatrixSquareException e) {
            throw new Errors.NoInverseException();
        }

        if (determinant == 0) {
            throw new Errors.NoInverseException();
        }

        cofactorMatrix = new Matrix(this.getNRow(), this.getNCol());

        try {
            for (i = 0; i <= this.getNRow() - 1; i++) {
                for (j = 0; j <= this.getNCol() - 1; j++) {
                    sign = (-2 * ((i + j) % 2) + 1);
                    cofactorMatrix.setElmt(i, j, this.getMinor(i, j).getDeterminantCofactor() * sign);
                }
            }
        } catch (InvalidMatrixSquareException e) {
            throw new RuntimeException(e);
        }

        cofactorMatrix.transpose();
        cofactorMatrix.multiplyScalar(1 / determinant);

        return cofactorMatrix;
    }

    /**
     * 
     * @return Mengembalikan nilai determinan matriks dengan metode segitiga atas
     * @throws InvalidMatrixSquareException ketika matriks masukan bukanlah matrix persegi
     */
    public double getDeterminantTriangle() throws InvalidMatrixSquareException {
        // KAMUS
        Matrix echelonMatrix;
        double multiplier, diagonalProduct, determinant;
        int i;
        // ALGORITMA
        if (!this.isSquare()) {
            throw new Errors.InvalidMatrixSquareException();
        }
        echelonMatrix = this.getEchelonForm(0, this.getNCol() - 1).first;
        multiplier = this.getEchelonForm(0, this.getNCol() - 1).second;

        diagonalProduct = 1;
        for (i = 0; i <= this.getNCol() - 1; i++) {
            diagonalProduct *= echelonMatrix.getElmt(i, i);
        }

        determinant = diagonalProduct * multiplier;
        return determinant;
    }

    /**
     * Prekondisi: (rowIdx, colIdx) merupakan indeks yang valid
     * @param rowIdx
     * @param colIdx
     * @return matriks dengan baris ke rowIdx dan kolom ke colIdx dihilangkan
     */
    public Matrix getMinor(int rowIdx, int colIdx) {
        // KAMUS LOKAL
        Matrix res;
        int iRes, jRes;

        // ALGORITMA
        res = new Matrix(this.getNRow() - 1, this.getNCol() - 1);

        for (int i = 0; i < this.getNRow(); i++) {
            for (int j = 0; j < this.getNCol(); j++) {
                if (i != rowIdx && j != colIdx) {
                    // menentukan indeks baru
                    iRes = i < rowIdx ? i : i - 1;
                    jRes = j < colIdx ? j : j - 1;

                    res.setElmt(iRes, jRes, this.getElmt(i, j));
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
        if (this.isSPL()) {
            return;
        }

        temp = new Matrix(this.getNCol() - 1, this.getNCol());
        for (int i = 0; i < this.getNRow(); i++) {
            if (i < this.getNRow()) {
                temp.setRow(i, this.getRow(i));
            } else {
                for (int j = 0; j < this.getNCol(); j++) {
                    temp.setElmt(i, j, 0);
                }
            }
        }

        this.setContents(temp.getContents());
    }

    /**
     * Mencari determinan matriks dengan metode kofaktor, throw error jika bukan
     * matriks persegi
     * 
     * @return determinan matriks
     * @throws InvalidMatrixSquareException jika bukan matriks persegi
     */
    public double getDeterminantCofactor() throws InvalidMatrixSquareException {
        // KAMUS LOKAL
        double res;
        double multiplier;

        // ALGORITMA
        if (!this.isSquare()) {
            throw new Errors.InvalidMatrixSquareException();
        }

        // basis, matriks 1x1
        if (this.getNRow() == 1) {
            return this.getElmt(0, 0);
        } else {
            res = 0;
            for (int i = 0; i < this.getNCol(); i++) {
                multiplier = i % 2 == 0 ? 1 : -1;
                if (this.getElmt(0, i) == 0)
                    continue;
                res += this.getElmt(0, i) * multiplier * this.getMinor(0, i).getDeterminantCofactor();
            }
            return res;
        }
    }

    /**
     * Mencari solusi SPL dengan metode cramer
     * 
     * @return matriks solusi SPL unik (kolom berjumlah 1)
     * @throws NoSolutionException jika SPL tidak memiliki solusi yang unik
     */
    public Matrix getSolCramer() throws Errors.NoSolutionException {
        // KAMUS LOKAL
        Matrix res, temp, square;
        double det;

        // ALGORITMA
        if (!this.isSPL() || this.getNRow() >= this.getNCol()) {
            throw new Errors.NoSolutionException();
        }

        // ALGORITMA
        res = new Matrix(this.getNCol() - 1, 1);
        square = new Matrix(this.getNRow(), this.getNRow());

        for (int i = 0; i < this.getNRow(); i++) {
            for (int j = 0; j < this.getNRow(); j++) {
                square.setElmt(i, j, this.getElmt(i, j));
            }
        }

        try {
            det = square.getDeterminantTriangle();
        } catch (InvalidMatrixSquareException e) {
            throw new RuntimeException(e);
        }

        if (det == 0) {
            throw new Errors.NoSolutionException();
        }

        for (int i = 0; i < this.getNCol() - 1; i++) {
            temp = new Matrix(this.getNRow(), this.getNRow());
            for (int j = 0; j < this.getNRow(); j++) {
                for (int k = 0; k < this.getNCol() - 1; k++) {
                    if (k == i) {
                        temp.setElmt(j, k, this.getElmt(j, this.getNCol() - 1));
                    } else {
                        temp.setElmt(j, k, this.getElmt(j, k));
                    }
                }
            }
            try {
                res.setElmt(i, 0, temp.getDeterminantTriangle() / det);
            } catch (InvalidMatrixSquareException e) {
                throw new RuntimeException(e);
            }
        }

        return res;
    }

    /**
     * Fungsi untuk mendapatkan solusi dari interpolasi polinomial
     * <p>
     * Prekondisi: matriks berupa matriks point yaitu dengan kolom berjumlah 2
     * 
     * @return matriks solusi interpolasi polinomial derajat n yaitu matriks (n+1) x
     *         1
     * @throws NoSolutionException jika SPL dari polinom tidak memiliki solusi yang unik
     */
    public Matrix getPolinomialFunction() throws NoSolutionException {
        Matrix polinomSPL = new Matrix(this.getNRow(), this.getNRow() + 1);

        for (int i = 0; i < polinomSPL.getNRow(); i++) {
            for (int j = 0; j < polinomSPL.getNCol() - 1; j++) {
                polinomSPL.setElmt(i, j, Math.pow(this.getElmt(i, 0), j));
            }
            polinomSPL.setElmt(i, polinomSPL.getNCol() - 1, this.getElmt(i, 1));
        }
        Matrix solution = polinomSPL.getSolGJ();
        Matrix res = new Matrix(solution.getNRow(), 1);

        for (int i = 0; i < solution.getNRow(); i++) {
            if (solution.getElmt(i, solution.getNCol() - 1) == 0) {
                throw new NoSolutionException();
            }
            res.setElmt(i, 0, solution.getElmt(i, solution.getNCol() - 2));
        }
    
        return res;
    }

    /**
     * Fungsi untuk mendapatkan hasil estimasi nilai x pada interpolasi polinomial
     * <p>
     * Prekondisi: matriks berupa hasil return dari getPolinomialFunction
     * 
     * @param x nilai yang ingin diestimasi
     * @return hasil estimasi
     */
    public double getValuePolinomial(double x) {
        double res = 0;
        for (int i = 0; i < this.getNRow(); i++) {
            res += this.getElmt(i, 0) * Math.pow(x, i);
        }
        return res;
    }

    /**
     * Fungsi untuk mendapatkan solusi dari interpolasi polinomial
     * <p>
     * Prekondisi: matriks berupa matriks point yaitu dengan kolom berjumlah 2
     * 
     * @param y parameter y dari nilai f(x,y) yang ingin dicari interpolasinya di
     *          titik tersebut
     * @param x parameter x dari nilai f(x,y) yang ingin dicari interpolasinya di
     *          titik tersebut
     * 
     * @return nilai f(x,y) yang telah di interpolasi
     */
    public double getValueBicubicSpecific(double y, double x) {
        Matrix functionCoefficientMatrix;
        double result;

        functionCoefficientMatrix = this.getBicubicFunction(0, 0);

        result = functionCoefficientMatrix.getValueBicubic(x, y, 0, 0);
        return result;
    }

    /**
     * Prekondisi: rowStart + 3 < this.getNRow() dan colStart + 3 < this.getNCol()
     * 
     * @param rowStart baris awal yang ingin diinterpolasi
     * @param colStart kolom awal yang ingin diinterpolasi
     * @return matriks yang berisi koefisien2 dari interpolasi bicubic
     */
    public Matrix getBicubicFunction(int rowStart, int colStart) {
        Matrix pointValueMatrix, functionCoefficientMatrix;
        int i, j, rowIdx;

        pointValueMatrix = new Matrix(16, 1);
        rowIdx = 0;
        for (i = rowStart; i <= rowStart + 3; i++) {
            for (j = colStart; j <= colStart + 3; j++) {
                pointValueMatrix.setElmt(rowIdx, 0, getElmt(i, j));
                rowIdx++;
            }
        }

        functionCoefficientMatrix = new Matrix(16, 1);
        try {
            functionCoefficientMatrix = multiply(inverseBicubicCoefficientMatrix, pointValueMatrix);
        } catch (InvalidMatrixSizeException e) {
            throw new RuntimeException(e);
        }

        return functionCoefficientMatrix;
    }

    /**
     * Fungsi untuk mendapatkan nilai interpolasi bicubic dari matriks solusi
     * bicubic
     * <p>
     * Prekondisi: matriks merupakan matriks hasil dari this.getBicubicFunction
     * 
     * @param a        parameter a dari nilai f(a,b) yang ingin dicari
     *                 interpolasinya
     * @param b        parameter b dari nilai f(a,b) yang ingin dicari
     *                 interpolasinya
     * @param startRow baris awal dari matriks solusi bicubic
     * @param startCol kolom awal dari matriks solusi bicubic
     * @return nilai f(a, b) yang telah di interpolasi
     */
    public double getValueBicubic(double a, double b, int startRow, int startCol) {
        int rowIdx = 0;
        double result = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                result += getElmt(rowIdx, 0) * Math.pow(a - startRow, i) * Math.pow(b - startCol, j);
                rowIdx++;
            }
        }
        return result;
    }

    /** 
     * Fungsi untuk mendapatkan solusi dari interpolasi bilinear
     * <p>
     * 
     * @param rowStart baris awal dari matriks solusi bilinear
     * @param colStart kolom awal dari matriks solusi bilinear
     * @param a parameter a dari nilai f(a,b) yang ingin dicari interpolasinya di
     *          titik tersebut
     * @param b parameter b dari nilai f(a,b) yang ingin dicari interpolasinya di
     *          titik tersebut
     * @return nilai f(a,b) yang telah di interpolasi
     */
    public double getValueBilinear(int rowStart, int colStart, double a, double b) {
        Matrix coefficientMatrix, pointValueMatrix, functionCoefficientMatrix;
        int i, j, x, y, rowIdx, colIdx;
        double result;

        rowIdx = 0;

        coefficientMatrix = new Matrix(4, 4);
        for (x = rowStart; x <= rowStart + 1; x++) {
            for (y = colStart; y <= colStart + 1; y++) {
                colIdx = 0;
                for (i = 0; i <= 1; i++) {
                    for (j = 0; j <= 1; j++) {
                        coefficientMatrix.setElmt(rowIdx, colIdx, Math.pow(x, i) * Math.pow(y, j));
                        colIdx++;
                    }
                }
                rowIdx++;
            }
        }
        pointValueMatrix = new Matrix(4, 1);
        rowIdx = 0;
        for (i = rowStart; i <= rowStart + 1; i++) {
            for (j = colStart; j <= colStart + 1; j++) {
                pointValueMatrix.setElmt(rowIdx, 0, getElmt(i, j));
                rowIdx++;
            }
        }

        functionCoefficientMatrix = new Matrix(4, 1);
        try {
            functionCoefficientMatrix = multiply(coefficientMatrix.getInverseOBE(), pointValueMatrix);
        } catch (NoInverseException e) {
            throw new RuntimeException(e);
        } catch (InvalidMatrixSizeException e) {
            throw new RuntimeException(e);
        }

        rowIdx = 0;
        result = 0;
        for (i = 0; i <= 1; i++) {
            for (j = 0; j <= 1; j++) {
                result += functionCoefficientMatrix.getElmt(rowIdx, 0) * Math.pow(a, i) * Math.pow(b, j);
                rowIdx++;
            }
        }
        return result;
    }

    /**
     * 
     * @param scalingFactor berapa kali lipat perbesaran dengan scalingFactor
     *                      merupakan bilangan bulat positif
     * @return Matriks yang sudah diperbesar scalingFactor kali dengan interpolasi
     *         bicubic dan interpolasi bilinier pada edges nya (digunakan untuk memperbesar gambar)
     */
    public Matrix getNTimesSizeMatrix(int scalingFactor) {
        Matrix resultMatrix;
        double di, dj;

        di = (double) (this.getNRow() - 1) / (double) (this.getNRow() * scalingFactor - 1);
        dj = (double) (this.getNCol() - 1) / (double) (this.getNCol() * scalingFactor - 1);
        resultMatrix = new Matrix(scalingFactor * this.getNRow(), scalingFactor * this.getNCol());

        ArrayList<int[]> points = new ArrayList<int[]>(this.getNRow() * this.getNCol());
        for (int i = 0; i < this.getNRow(); i++) {
            for (int j = 0; j < this.getNCol(); j++) {
                points.add(new int[] { i, j });
            }
        }

        points.parallelStream().forEach(point -> {
            int lastFloorX = -1, lastFloorY = -1;
            Matrix bicubicFunction = null;
            for (int i = 0; i < scalingFactor; i++) {
                for (int j = 0; j < scalingFactor; j++) {
                    int desX = point[0] * scalingFactor + i;
                    int desY = point[1] * scalingFactor + j;
                    double x = (double) (desX) * di;
                    double y = (double) (desY) * dj;
                    int floorX = (int) x;
                    int floorY = (int) y;

                    if (floorX == this.getNRow() - 1) {
                        floorX--;
                    }
                    if (floorY == this.getNCol() - 1) {
                        floorY--;
                    }

                    if (floorX == 0 || floorX == this.getNRow() - 2 || floorY == 0 || floorY == this.getNCol() - 2) {
                        resultMatrix.setElmt(desX, desY, this.getValueBilinear(floorX, floorY, x, y));
                    } else {
                        if (floorX != lastFloorX || floorY != lastFloorY) {
                            bicubicFunction = this.getBicubicFunction(floorX - 1, floorY - 1);
                            lastFloorX = floorX;
                            lastFloorY = floorY;
                        }
                        resultMatrix.setElmt(desX, desY, bicubicFunction.getValueBicubic(x, y, floorX, floorY));
                    }
                }
            }
        });

        return resultMatrix;
    }

    /**
     * Menghasilkan panjang dari angka paling panjang dalam matriks setelah diformat
     * 
     * @param digitAfterComma berapa angka di belakang koma yang diperlukan
     * @return panjang dari angka paling panjang dalam matriks setelah diformat
     */
    public int getMostDigit(int digitAfterComma) {

        NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMaximumFractionDigits(digitAfterComma);
        int mostDigit = 0;
        for (int i = 0; i < getNRow(); i++) {
            for (int j = 0; j < getNCol(); j++) {
                String inspectedCell = numberFormatter.format(getElmt(i, j));
                if (inspectedCell.length() > mostDigit) {
                    mostDigit = inspectedCell.length();
                }

            }
        }
        return mostDigit;
    }

    /**
     * Menghasilkan panjang dari angka paling panjang dalam matriks setelah diformat
     * 
     * @return panjang dari angka paling panjang dalam matriks setelah diformat
     *         dengan 2 angka di belakang koma
     */
    public int getMostDigit() {
        return getMostDigit(2);
    }

    /**
     * Mencari rata-rata dari sebuah bagian matriks. Index inklusif.
     * <p>
     * Prekondisi: 0 <= rowStart <= rowEnd < getNRow() && 0 <= colStart <= colEnd < getNCol()
     * 
     * @param startRowIdx baris awal pencarian rata-rata
     * @param endRowIdx   baris akhir pencarian rata-rata
     * @param startColIdx kolom awal pencarian rata-rata
     * @param endColIdx   kolom akhir pencarian rata-rata
     * @return rata-rata dari nilai sel pada bagian yang diberikan
     */
    public double getAverageByColumnAndRow(int startRowIdx, int endRowIdx, int startColIdx, int endColIdx) {
        double sum = 0;
        int cellCount = (endRowIdx - startRowIdx + 1) * (endColIdx - startColIdx + 1);
        for (int i = startRowIdx; i <= endRowIdx; i++) {
            for (int j = startColIdx; j <= endColIdx; j++) {
                sum += getElmt(i, j);
            }
        }

        return sum / cellCount;
    }

    /**
     * Mencari rata-rata dari sebagian matriks dari kolom sekian sampai kolom
     * sekian. Baris yang dihitung adalah seluruh baris. Index inklusif.
     * Prekondisi: 0 <= colStart <= colEnd < getNCol()
     * 
     * @param startColIdx kolom awal pencarian rata-rata
     * @param endColIdx   kolom akhir pencarian rata-rata
     * @return rata-rata dari nilai sel pada bagian yang diberikan
     */
    public double getAverageByColumn(int startColIdx, int endColIdx) {
        return getAverageByColumnAndRow(0, getNRow() - 1, startColIdx, endColIdx);
    }

    /**
     * Mencari rata-rata dari salah satu kolom pada matrix.
     * Prekondisi: colIdx merupakan index kolom yang valid
     * 
     * @param colIdx kolom yang ingin dicari rata-ratanya
     * @return rata-rata dari nilai sel pada kolom yang diberikan
     */
    public double getAverageOfColumn(int colIdx) {
        return getAverageByColumn(colIdx, colIdx);
    }

    /**
     * Mencari rata-rata dari sebagian matriks dari baris sekian sampai baris
     * sekian. Kolom yang dihitung adalah seluruh kolom. Index inklusif.
     * Prekondisi: 0 <= startRowIdx <= endRowIdx < getNRow()
     * 
     * @param startRowIdx baris awal pencarian rata-rata
     * @param endRowIdx   baris akhir pencarian rata-rata
     * @return rata-rata dari nilai sel pada bagian yang diberikan
     */
    public double getAverageByRow(int startRowIdx, int endRowIdx) {
        return getAverageByColumnAndRow(startRowIdx, endRowIdx, 0, getNCol() - 1);
    }

    /**
     * Mencari rata-rata dari salah satu baris pada matrix.
     * Prekondisi: rowIdx merupakan index baris yang valid
     * 
     * @param rowIdx baris yang ingin dicari rata-ratanya
     * @return rata-rata dari nilai sel pada baris yang diberikan
     */
    public double getAverageOfRow(int rowIdx) {
        return getAverageByRow(rowIdx, rowIdx);
    }

    /**
     * Mengecek apakah dua matriks memiliki jumlah kolom yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks2 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki jumlah kolom yang sama.
     */
    public static boolean isNColumnSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNCol() == matriks2.getNCol();
    }

    /**
     * Mengecek apakah dua matriks memiliki jumlah baris yang sama.
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks2 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki jumlah baris yang sama.
     */
    public static boolean isNRowSame(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNRow() == matriks2.getNRow();
    }

    /**
     * Mengecek apakah dua matriks memiliki dimensi yang sama
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks2 matriks kedua yang ingin dicek
     * @return apakah kedua matriks memiliki dimensi yang sama.
     */
    public static boolean isDimensionSame(Matrix matriks1, Matrix matriks2) {
        return isNRowSame(matriks1, matriks2) && isNColumnSame(matriks1, matriks2);
    }

    /**
     * Mengecek apakah dua matriks dapat dikalikan
     * 
     * @param matriks1 matriks pertama yang ingin dicek
     * @param matriks2 matriks kedua yang ingin dicek
     * @return apakah matriks pertama punya jumlah kolom sebanyak matriks kedua.
     */
    public static boolean isMultipliable(Matrix matriks1, Matrix matriks2) {
        return matriks1.getNCol() == matriks2.getNRow();
    }

    /**
     * Mengalikan 2 buah matriks
     * 
     * @param matriks1 matriks pertama yang ingin dikalikan
     * @param matriks2 matriks kedua yang ingin dikalikan
     * @return matriks baru yang merupakan hasil perkalian kedua matriks yang
     *         diberikan
     * @throws InvalidMatrixSizeException ketika kedua matriks tidak multipliable
     */
    public static Matrix multiply(Matrix matriks1, Matrix matriks2) throws InvalidMatrixSizeException {
        // ALGORITMA
        if (!isMultipliable(matriks1, matriks2)) {
            throw new Errors.InvalidMatrixSizeException();
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
     * 
     * @param matriks1 matriks pertama yang ingin dijumlahkan
     * @param matriks2 matriks kedua yang ingin dijumlahkan
     * @return matriks baru yang merupakan hasil penjumlahan dua buah matriks
     * @throws InvalidMatrixSizeException ketika kedua matriks tidak memiliki dimensi yang sama
     * 
     */
    public static Matrix add(Matrix matriks1, Matrix matriks2) throws InvalidMatrixSizeException {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Errors.InvalidMatrixSizeException();
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
     * 
     * @param matriks1 matriks pertama yang ingin dikurangkan
     * @param matriks2 matriks kedua yang akan mengurangkan
     * @return matriks baru yang merupakan hasil pengurangan matriks pertama oleh
     *         matriks kedua
     * @throws InvalidMatrixSizeException ketika kedua matriks tidak memiliki dimensi yang sama
     * 
     */
    public static Matrix subtract(Matrix matriks1, Matrix matriks2) throws InvalidMatrixSizeException {
        // ALGORITMA
        if (!isDimensionSame(matriks1, matriks2)) {
            throw new Errors.InvalidMatrixSizeException();
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

    /**
     * 
     * @return matriks yang merupakan hasil inverse dari koefisien bicubic
      */
    private static Matrix getInverseBicubicCoefficient() {
        Matrix coefficientMatrix = new Matrix(16, 16);

        int rowIdx = 0;
        for (int x = -1; x <= 2; x++) {
            for (int y = -1; y <= 2; y++) {
                int colIdx = 0;
                for (int i = 0; i <= 3; i++) {
                    for (int j = 0; j <= 3; j++) {
                        coefficientMatrix.setElmt(rowIdx, colIdx, Math.pow(x, i) * Math.pow(y, j));
                        colIdx++;
                    }
                }
                rowIdx++;
            }
        }
        try {
            return coefficientMatrix.getInverseOBE();
        } catch (NoInverseException e) {
            throw new RuntimeException(e);
        }
    }
}
