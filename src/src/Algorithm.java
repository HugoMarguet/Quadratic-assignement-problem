
public class Algorithm {

    private QAP qap;

    public Algorithm(QAP qap) {
        this.qap = qap;
    }

    public QAP aleaWalk() {
        QAP qap = this.qap.clone();

        return qap;
    }

    public QAP tabou() {
        return qap.clone();
    }
}
