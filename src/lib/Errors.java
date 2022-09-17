package lib;

public class Errors {
  static public class NoSolutionException extends Exception {
    public NoSolutionException() {
      super("No solution found");
    }
  }

  static public class InvalidMatrixSquareException extends Exception {
    public InvalidMatrixSquareException() {
      super("Matrix is not square");
    }
  }

  static public class SolutionNotUniqueException extends Exception {
    public SolutionNotUniqueException() {
      super("Solution is not unique");
    }
  }

  static public class InvalidMatrixSizeException extends Exception {
    public InvalidMatrixSizeException() {
      super("Matrix size is invalid");
    }
  }

  static public class DifferentMatrixDimension extends Exception {
    public DifferentMatrixDimension() {
      super("Matrix dimension is not the same");
    }
  }

}