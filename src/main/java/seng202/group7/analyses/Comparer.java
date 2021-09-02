package seng202.group7.analyses;

import seng202.group7.Report;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Comparer {

    public static ArrayList timeDifference(Report reportOne, Report reportTwo) {

        LocalDateTime firstDate = null;
        LocalDateTime lastDate = null;
        Date start = null;
        Date end = null;
        ArrayList<Integer> timeDifferences = new ArrayList<>();

        try {
            if (reportOne.getDate().isEqual(reportTwo.getDate())) {
                for(int i = 0; i < 5; i++) { timeDifferences.add(0); }
                return timeDifferences;
            } else if (reportOne.getDate().isBefore(reportTwo.getDate())) {
                firstDate = reportOne.getDate();
                lastDate = reportTwo.getDate();
            } else {
                firstDate = reportTwo.getDate();
                lastDate = reportOne.getDate();
            }
        } catch (NullPointerException e) {
            System.out.print(e);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
             start = sdf.parse(String.valueOf(firstDate.getDayOfMonth()) + "-" + String.valueOf(firstDate.getMonthValue())
                    + "-" + String.valueOf(firstDate.getYear()) + " " + String.valueOf(firstDate.getHour()) + ":" +
                    String.valueOf(firstDate.getMinute()) + ":" + String.valueOf(firstDate.getSecond()));
             end = sdf.parse(String.valueOf(lastDate.getDayOfMonth()) + "-" + String.valueOf(lastDate.getMonthValue())
                     + "-" + String.valueOf(lastDate.getYear()) + " " + String.valueOf(lastDate.getHour()) + ":" +
                     String.valueOf(lastDate.getMinute()) + ":" + String.valueOf(lastDate.getSecond()));

             long timeDifference = end.getTime() - start.getTime();
             timeDifferences.add((int) (TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60));
             timeDifferences.add((int) (TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60));
             timeDifferences.add((int) (TimeUnit.MILLISECONDS.toHours(timeDifference) % 24));
             timeDifferences.add((int) (TimeUnit.MILLISECONDS.toDays(timeDifference) % 365));
             timeDifferences.add((int) (TimeUnit.MILLISECONDS.toDays(timeDifference) / 365));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Difference in seconds: " + String.valueOf(timeDifferences.get(0)));
        System.out.println("Difference in minutes: " + String.valueOf(timeDifferences.get(1)));
        System.out.println("Difference in Hours: " + String.valueOf(timeDifferences.get(2)));
        System.out.println("Difference in days: " + String.valueOf(timeDifferences.get(3)));
        System.out.println("Difference in years: " + String.valueOf(timeDifferences.get(4)));
        return timeDifferences;
    }

    public static double locationDifference(Report reportOne, Report reportTwo) {
        double xDifference = reportOne.getXCoord() - reportTwo.getXCoord();
        double yDifference = reportOne.getYCoord() - reportTwo.getYCoord();
        if (xDifference == 0) {
            return yDifference;
        } else if (yDifference == 0) {
            return xDifference;
        } else {
            return Math.sqrt((xDifference*xDifference) + (yDifference*yDifference));
        }
    }
}
