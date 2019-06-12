public abstract class Algorithms {

    protected final QAP qap;

    protected final int size;

    public Algorithms(QAP qap) throws Exception {
        if (qap.getSize() <= 1)
            throw new Exception("Size should be higher than 1");
        this.qap = qap;
        this.size = qap.getSize();
    }

    public QAP getQap() {
        return qap;
    }

    public int getSize() {
        return size;
    }
}
