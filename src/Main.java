import lib.Matrix;

public class Main {
    public static void main(String[] args) {
        int k;
        k=-1;
        Matrix m = new Matrix(2, 2);
        m.setElmt(0, 0, 1);
        m.setElmt(0, 1, 1);
        m.setElmt(1, 0, 1);
        m.setElmt(1, 1, 1);
        m = m.getInverseMatrix();
        for (int i = 0; i<=m.getNRow()-1; i++) {
            for (int j = 0; j<= m.getNCol()-2; j++) {
                System.out.printf("%.2f ", m.getElmt(i, j));
                k = j;
            }
            System.out.printf("%.2f\n", m.getElmt(i, k+1));
        }
    }
}
