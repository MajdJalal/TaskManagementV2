package com.digitinary.taskmanagement2.runnable;


import com.digitinary.taskmanagement2.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RunnableTask implements Runnable{
    private static final Object lock = new Object();
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private int priority;
    private LocalDate dueDate;
    @Override
    public void run() {
        synchronized (RunnableTask.lock) {
            System.out.println(this.getId());
            System.out.println(this.getDescription());
            System.out.println(this.getStatus());
            System.out.println(this.getTitle());
            System.out.println(this.getPriority());
            System.out.println(this.getDueDate());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.setStatus(TaskStatus.DONE);
            System.out.println("Task " + this.getId() + " is completed on thread " + Thread.currentThread().getName());
        }
    }
}
