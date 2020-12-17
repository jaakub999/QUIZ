package api;

import java.io.Serializable;

public class Answer implements Serializable {
    public boolean a, b, c, d;
    public String A, B, C, D;

    public Answer(String A, String B, String C, String D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;

        a = false;
        b = false;
        c = false;
        d = false;
    }

    public void setA() {
        a = true;
    }

    public void setB() {
        b = true;
    }

    public void setC() {
        c = true;
    }

    public void setD() {
        d = true;
    }
}
