package club.yishiyuntuan.async.work;

/**
 * @author wuweifeng wrote on 2019-12-13
 * @version 1.0
 */
public class Bootstrap {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        //new work 10000ms后返回hell oworld
        Worker worker = bootstrap.newWorker();
        Wrapper wrapper = new Wrapper();
        wrapper.setWorker(worker);
        wrapper.setParam("hello");

        bootstrap.doWork(wrapper).addListener(new CallBack() {
            @Override
            public void result(Object result) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(result);
            }
        });

        System.out.println("---->"+Thread.currentThread().getName());

    }

    private Wrapper doWork(Wrapper wrapper) {
        new Thread(() -> {
            Worker worker = wrapper.getWorker();
            String result = worker.action(wrapper.getParam());
            wrapper.getListener().result(result);
        }).start();

        return wrapper;
    }

    private Worker newWorker() {
        return new Worker() {
            @Override
            public String action(Object object) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return object + " world";
            }
        };
    }

}