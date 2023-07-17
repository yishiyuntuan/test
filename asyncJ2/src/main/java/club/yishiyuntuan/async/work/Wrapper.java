package club.yishiyuntuan.async.work;

public class Wrapper {
    private Object param;
    private Worker worker;
    private CallBack listener;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public CallBack getListener() {
        return listener;
    }

    public void addListener(CallBack listener) {
        this.listener = listener;
    }
}