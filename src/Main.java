import lib.Matrix;

public class Main {
    public static void main(String[] args) {
        int k;
        k=-1;
        Matrix m = new Matrix(3, 3);
        m.setElmt(0, 0, 1);
        m.setElmt(0, 1, 2);
        m.setElmt(0, 2, 1);
        m.setElmt(1, 0, 2);
        m.setElmt(1, 1, 4);
        m.setElmt(1, 2, 2);
        m.setElmt(2, 0, 3);
        m.setElmt(2, 1, 4);
        m.setElmt(2, 2, 2);

        m = m.getAugmentedMatrixByIdentity();
        m = m.getReducedForm(0, 5);
        for (int i = 0; i<=m.getNRow()-1; i++) {
            for (int j = 0; j<= m.getNCol()-2; j++) {
                System.out.printf("%.2f ", m.getElmt(i, j));
                k = j;
            }
            System.out.printf("%.2f\n", m.getElmt(i, k+1));
        }
    }
}
