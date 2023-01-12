package Project;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int time = 8000;
        int timeBeforeAuctionEnd = 5000;
        Date startDate = new Date();

        Thread.sleep(2500);

        int currentTime = (int) ((new Date().getTime() - startDate.getTime()) / time + 1);
        if (currentTime > 24) {
            startDate = new Date();
            currentTime = 1;
        }




        System.out.println(currentTime);

    }



}
