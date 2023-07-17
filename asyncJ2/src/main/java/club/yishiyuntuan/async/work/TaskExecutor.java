package club.yishiyuntuan.async.work;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1"),
                new Task("Task 2"),
                new Task("Task 3")
        );

        CompletableFuture<Void>[] taskFutures = new CompletableFuture[tasks.size()];

        // 并行执行任务
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> {
                for (SubTask subTask : task.getSubTasks()) {
                    CompletableFuture<Void> subTaskFuture = CompletableFuture.runAsync(() -> {
                        // 第一步逻辑
                        System.out.println(task.getName() + ": " + subTask.getName() + " - Step 1 completed");

                        // 等待外部通知
                        waitForExternalNotification();

                        // 第二步逻辑
                        System.out.println(task.getName() + ": " + subTask.getName() + " - Step 2 completed");
                    });

                    subTaskFuture.join(); // 等待子任务完成
                }
            });

            taskFutures[i] = taskFuture;
        }

        CompletableFuture.allOf(taskFutures).join(); // 等待所有任务完成
    }

    static void waitForExternalNotification() {
        // 模拟等待外部通知的操作
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Task {
        private String name;
        private List<SubTask> subTasks;

        public Task(String name) {
            this.name = name;
            this.subTasks = Arrays.asList(
                    new SubTask("SubTask 1"),
                    new SubTask("SubTask 2"),
                    new SubTask("SubTask 3")
            );
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
