package block3.cp.mapreduce;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to demonstrate how to use CyclicBarrier in Java. CyclicBarrier is a
 * new Concurrency Utility added in Java 5 Concurrent package.
 *
 * @author Javin Paul
 */

public class Test {

    //Runnable task for each thread
    private static class Task implements Runnable {

        private CyclicBarrier barrier;
        MapReduceImpl mp;
        Employee e;

        public Task(CyclicBarrier barrier, MapReduceImpl mp, Employee e) {
            this.e = e;
            this.mp = mp;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                mp.list.add(mp.map(e));
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main (String args[]) {

        MapReduceImpl mapReduce = new MapReduceImpl();
        List<Employee> peoples = new ArrayList<>();
        peoples.add(new Employee(101, "Victor", 23));
        peoples.add(new Employee(102, "Rick", 21));
        peoples.add(new Employee(103, "Sam", 25));
        peoples.add(new Employee(104, "John", 27));
        peoples.add(new Employee(105, "Grover", 23));
        peoples.add(new Employee(106, "Adam", 22));
        peoples.add(new Employee(107, "Samy", 224));
        peoples.add(new Employee(108, "Duke", 29));
        peoples.add(new Employee(109, "Grover", 23));
        peoples.add(new Employee(110, "Adam", 22));
        peoples.add(new Employee(111, "Samy", 224));
        peoples.add(new Employee(112, "Grover", 23));
        peoples.add(new Employee(113, "Adam", 22));
        peoples.add(new Employee(114, "Samy", 224));
        peoples.add(new Employee(115, "Grover", 23));
        peoples.add(new Employee(116, "Adam", 22));
        peoples.add(new Employee(117, "Samy", 224));
        peoples.add(new Employee(118, "Grover", 23));
        peoples.add(new Employee(119, "Adam", 22));
        peoples.add(new Employee(120, "Samy", 224));

        //creating CyclicBarrier with 3 parties i.e. 3 Threads needs to call await()
        final CyclicBarrier cb = new CyclicBarrier(20, new Runnable(){
            @Override
            public void run(){
                //This task will be executed once all thread reaches barrier
                System.out.println("All parties are arrived at barrier, lets play.       age- " + mapReduce.reduce(mapReduce.list).toString());
            }
        });

        int i =0;
        for(Employee e : peoples){
            i++;
            Thread t = new Thread(new Task(cb,mapReduce, e), ("Thread " + i));
            t.start();
        }

    }
}
class Employee {
    private final int id;
    private final String name;
    private final int age;

    public Employee(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}