package lib;

public class Errors {
  static public class NoSolutionException extends Exception {
    public NoSolutionException() {
      super("No solution found");
    }
  }

  static public class NoInverseException extends Exception {
    public NoInverseException() {
      super("Matrix dont have inverse");
    }
  }

  static public class InvalidMatrixSquareException extends Exception {
    public InvalidMatrixSquareException() {
      super("Matrix is not square");
    }
  }

  static public class InvalidMatrixSizeException extends Exception {
    public InvalidMatrixSizeException() {
      super("Matrix size is invalid");
    }
  }

}