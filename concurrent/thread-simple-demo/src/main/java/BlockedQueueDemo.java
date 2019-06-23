import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: BlockedQueueDemo
 * @projectName java-hub
 * @description: 利用ArrayBlockingQueue阻塞队列完成消息的发送和消息的接收。
 * 消息的队列的容量为默认的16
 * 当消息超过16，发送消息的方法将阻塞。
 * 当队列为空，接收消息的队列阻塞
 */
public class BlockedQueueDemo {

    private ArrayBlockingQueue<Msg> msgQueue = new ArrayBlockingQueue<Msg>(16);

    public void send(Msg msg) throws InterruptedException {
        msgQueue.put(msg);
        return;
    }

    public Msg receive() throws InterruptedException {
        return msgQueue.take();
    }

    class Msg {
        private String messageId;
        private String type;
        private String content;

        public Msg(String messageId, String type, String content) {
            this.messageId = messageId;
            this.type = type;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "messageId='" + messageId + '\'' +
                    ", type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        BlockedQueueDemo demo = new BlockedQueueDemo();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Msg msg = demo.new Msg((i + 1) + "", "type", "content : " + i);
                try {
                    demo.send(msg);
                    System.out.println("发送消息完成 " + msg);
                    TimeUnit.SECONDS.sleep(1 & System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            Msg msg = null;
            try {
                while ((msg = demo.receive()) != null) {
                    System.out.println("收到消息" + msg);
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }).start();
    }
}
