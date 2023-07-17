package club.yishiyuntuan.async.work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskManager {
    private ExecutorService executorService;

    public TaskManager() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public CompletableFuture<Void> executeTaskList(List<Task> tasks) {
        List<CompletableFuture<Void>> taskFutures = new ArrayList<>();

        for (Task task : tasks) {
            CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> executeTask(task), executorService);
            taskFutures.add(taskFuture);
        }

        return CompletableFuture.allOf(taskFutures.toArray(new CompletableFuture[0]));
    }

    private void executeTask(Task task) {
        CountDownLatch latch = new CountDownLatch(task.getSubTasks().size());

        for (SubTask subTask : task.getSubTasks()) {
            CompletableFuture.runAsync(() -> executeSubTask(subTask, latch), executorService);
        }

        try {
            latch.await(); // 等待所有子任务完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeSubTask(SubTask subTask, CountDownLatch latch) {
        // 第一步逻辑
        System.out.println(subTask.getName() + " - Step 1 completed");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 等待外部通知
        waitForExternalNotification();

        // 第二步逻辑
        System.out.println(subTask.getName() + " - Step 2 completed");

        latch.countDown(); // 子任务完成
    }

    private void waitForExternalNotification() {
        // 模拟等待外部通知的操作
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        List<Task> taskList1 = new ArrayList<>();
        taskList1.add(new Task("Task 1", List.of(new SubTask("SubTask 1.1"), new SubTask("SubTask 1.2"))));
        taskList1.add(new Task("Task 2", List.of(new SubTask("SubTask 2.1"), new SubTask("SubTask 2.2"))));

        // 异步执行任务列表
        CompletableFuture<Void> executionFuture1 = taskManager.executeTaskList(taskList1);

        // 添加新的任务列表，并行执行
        List<Task> taskList2 = new ArrayList<>();
        taskList2.add(new Task("Task 3", List.of(new SubTask("SubTask 3.1"), new SubTask("SubTask 3.2"))));
        taskList2.add(new Task("Task 4", List.of(new SubTask("SubTask 4.1"), new SubTask("SubTask 4.2"))));

        // 异步执行新的任务列表
        CompletableFuture<Void> executionFuture2 = taskManager.executeTaskList(taskList2);

        // 等待所有任务完成
        CompletableFuture.allOf(executionFuture1, executionFuture2).join();

        // 关闭任务管理器
        taskManager.shutdown();
    }

    public void shutdown() {
        executorService.shutdown();
    }

    static class Task {
        private String name;
        private List<SubTask> subTasks;

        public Task(String name, List<SubTask> subTasks) {
            this.name = name;
            this.subTasks = subTasks;
        }

        public String getName() {
            return name;
        }

        public List<SubTask> getSubTasks() {
            return subTasks;
        }
    }

    static class SubTask {
        private String name;

        public SubTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
