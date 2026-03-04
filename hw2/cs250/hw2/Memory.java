package cs250.hw2;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Memory {
    public static void main(String[] args) {
        Integer size = 0;
        Integer experments = 0;
        Integer seed = 0;
        try{
            size = Integer.parseInt(args[0]);
            experments = Integer.parseInt(args[1]);
            seed = Integer.parseInt(args[2]);
        }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
            System.out.println("you either didnt have the correct number of args or they were not all ints");
            return;
        }

        //task 1
        Task1 firsttask = new Task1(size, experments);
        firsttask.run();

        //task 2
        Task2 secondtask = new Task2(size, experments, seed);
        secondtask.run();


        System.out.println("Task 3");
    }

    public static class Task1 {
        private int size;
        private int experments;
        private volatile int volatile_i;
        public Task1(int size, int experments){
            this.size = size;
            this.experments = experments;
        }
        public void run(){
            System.out.println("Task 1");
            long non_vol_total_time = 0;
            long average_non_vol_sum = 0;
            for(int e = 0; e<experments; e++){
                long start = System.nanoTime();
                average_non_vol_sum = loop(size);
                long end = System.nanoTime();
                non_vol_total_time +=(end - start);
            }
            long non_vol_average = non_vol_total_time/experments;

            long vol_total_time = 0;
            long average_vol_sum = 0;
            for(int e = 0; e<experments; e++){
                long start = System.nanoTime();
                average_vol_sum = vol_loop(size);
                long end = System.nanoTime();
                vol_total_time +=(end - start);
            }
            long vol_average = vol_total_time/experments;
            double regular_seconds = non_vol_average / 1000000000.0;
            double vol_seconds = vol_average / 1000000000.0;

            System.out.println("Regular: " + regular_seconds + " seconds");
            System.out.println("Volatile: " + vol_seconds + " seconds");
            System.out.println("Avg regular sum: " + average_non_vol_sum);
            System.out.println("Avg volatile sum: " + average_vol_sum);
        }

        public long loop(Integer size){
            //main loop
            long running_total = 0;

            for(int i=0; i < size; i++){
                if(i%2 != 0){
                    running_total -= i;
                }else{
                    running_total += i;
                }
            }
            return running_total;
        }
        public long vol_loop(Integer size){
            //main loop
            long running_total = 0;

            for(volatile_i=0; volatile_i < size; volatile_i++){
                if(volatile_i%2 != 0){
                    running_total -= volatile_i;
                }else{
                    running_total += volatile_i;
                }
            }
            return running_total;
        }
        
    }

    public static class Task2{
        private int size;
        private int experments;
        private int seed;
        public Task2(int size, int experments, int seed){
            this.size = size;
            this.experments = experments;
            this.seed = seed;
        }
        public void run(){
            System.out.println("Task 2");

            Integer[] int_of_size = new Integer[size];
            Random rand = new Random(seed);

            for(int i = 0; i < size; i++){
                int_of_size[i] = rand.nextInt();
            }

            int first_10 = Math.max(1, size/10);
            int last_10_start_index = size - first_10;

            long total_first_time = 0;
            long total_random_time = 0;
            long total_sum = 0;

            

            for(int e = 0; e<experments; e++){
                for(int i =0; i<first_10; i++){
                    long start = System.nanoTime();
                    int value = int_of_size[i];
                    long end = System.nanoTime();

                    total_first_time+= (end - start);
                    total_sum += value;
                }

                int random_index = last_10_start_index + rand.nextInt(first_10);
                long start = System.nanoTime();
                int value = int_of_size[random_index];
                long end = System.nanoTime();

                total_random_time += (end - start);
                total_sum += value;

            }

            double avg_first_time = total_first_time / (double)(experments * first_10);
            double avg_random_time = total_random_time / (double)experments;
            double avg_sum = total_sum/(double)experments;

            System.out.printf("Avg time to access known element: %.2f nanoseconds%n", avg_first_time);
            System.out.printf("Avg time to access random element: %.2f nanoseconds%n", avg_random_time);
            System.out.printf("Sum: %.2f%n", avg_sum);

        }

    }
    
}
