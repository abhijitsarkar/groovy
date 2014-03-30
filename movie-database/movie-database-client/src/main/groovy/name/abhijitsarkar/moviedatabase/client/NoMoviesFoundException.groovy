package name.abhijitsarkar.moviedatabase.client

/**
 * @author Abhijit Sarkar
 */
class NoMoviesFoundException extends RuntimeException {
    NoMoviesFoundException() {
    }

    NoMoviesFoundException(String s) {
        super(s)
    }

    NoMoviesFoundException(String s, Throwable throwable) {
        super(s, throwable)
    }

    NoMoviesFoundException(Throwable throwable) {
        super(throwable)
    }

    NoMoviesFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1)
    }
}
