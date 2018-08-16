package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 【ForkJoin框架】
 *  ForkJoin框架工作原理：
 *  把大任务分解成若干个小任务（fork），最终把若干个小任务进行汇总（join）得到大任务结果的框架，主要是使用了工作窃取的算法，
 *  优点：充分利用线程进行并行计算，减少线程竞争，但是在某种情况下存在竞争，消耗更多的系统资源；
 *  把子任务放在不同的队列中，为每个队列分配创建单独的线程来处理，为了防止窃取任务线程和被窃取任务线程的竞争，通常情况下采用双端队列；
 *
 *  局限性：
 *  1，任务只能通过fork和join来实现同步机制，如果使用其他的同步机制，那么该线程就不能执行其他任务；
 *  2，拆分的任务不能执行IO操作；
 *  3，任务不能抛出异常，必须在内部处理；
 *
 *  Fork/join框架的核心组成：ForkJoinPool和ForkJoinTask
 *  ForkJoinPool：管理工作线程，提供任务执行的状态和执行信息；
 *  ForkJoinTask：实现对大任务的fork操作和join操作的机制；
 *
 */
@Slf4j
public class ForkJoinTaskExample extends RecursiveTask<Integer> {

    public static final int threshold = 2;
    private int start;
    private int end;

    public ForkJoinTaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start, middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkjoinPool = new ForkJoinPool();

        //生成一个计算任务，计算1+2+3+4
        ForkJoinTaskExample task = new ForkJoinTaskExample(1, 100);

        //执行一个任务
        Future<Integer> result = forkjoinPool.submit(task);

        try {
            log.info("result:{}", result.get());
        } catch (Exception e) {
            log.error("exception", e);
        }
    }
}
