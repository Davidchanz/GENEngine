package org.gn.GENMath.vector;

public class GdxRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6735854402467673117L;

    public GdxRuntimeException (String message) {
        super(message);
    }

    public GdxRuntimeException (Throwable t) {
        super(t);
    }

    public GdxRuntimeException (String message, Throwable t) {
        super(message, t);
    }
}
