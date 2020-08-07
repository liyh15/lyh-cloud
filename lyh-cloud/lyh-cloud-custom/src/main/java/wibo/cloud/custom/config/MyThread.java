package wibo.cloud.custom.config;

/**
 * @Classname MyThread
 * @Description TODO
 * @Date 2020/7/31 10:29
 * @Created by lyh
 */
public class MyThread extends Thread {

    /**
     * 自定义join方法
     * @param
     * @return
     * @throws
     * @description
     * @author liyuanhao
     * @date 2020/7/31 10:30
     */
    public void myJoin(long milions) throws InterruptedException {
        this.join();
        long now = System.currentTimeMillis();
        if (milions < 0) {
            while (true) {
                if (!this.isAlive()) {
                    break;
                }
                wait();
            }
        } else {
            while (true) {
                if (!this.isAlive()) {
                    break;
                }
                long a = System.currentTimeMillis();
                if ((a - now) >= milions) {
                    break;
                }
                wait(a - now);
            }
        }
    }
}
