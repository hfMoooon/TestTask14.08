package org.example;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


public class First {
    // Проверка даты, является ли она рабочим днем
    public static void main(String[] args) {
        getNextPostDate();
    }

    public static Date getVacCheck(Date modDate) {
        return new Date(modDate.getTime());
    }

    public static Timestamp getNextPostDate() {
        Calendar currentDay = Calendar.getInstance();

        int[] workingDays = {1, 10, 20};
        Calendar draftPostDate = (Calendar) currentDay.clone();
        draftPostDate.set(Calendar.HOUR_OF_DAY, 18);
        draftPostDate.set(Calendar.MINUTE, 0);
        draftPostDate.set(Calendar.SECOND, 0);
        draftPostDate.set(Calendar.MILLISECOND, 0);

        boolean isFound = false;
        for (int day : workingDays) {
            draftPostDate.set(Calendar.DAY_OF_MONTH, day);
            if (draftPostDate.after(currentDay)) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            // Если не нашли подходящую дату в текущем месяце, переходим на следующий
            draftPostDate.add(Calendar.MONTH, 1);
            draftPostDate.set(Calendar.DAY_OF_MONTH, workingDays[0]);
        }

        // Проверяем, является ли дата рабочим днем
        Date tentativeDate = new Date(draftPostDate.getTimeInMillis());
        Date workingDate = getVacCheck(tentativeDate);

        if (!tentativeDate.equals(workingDate)) {
            Calendar workingSubmissionDate = Calendar.getInstance();
            workingSubmissionDate.setTime(workingDate);

            workingSubmissionDate.set(Calendar.HOUR_OF_DAY, 18);
            workingSubmissionDate.set(Calendar.MINUTE, 0);
            workingSubmissionDate.set(Calendar.SECOND, 0);
            workingSubmissionDate.set(Calendar.MILLISECOND, 0);
            return new Timestamp(workingSubmissionDate.getTimeInMillis());
        }

        return new Timestamp(draftPostDate.getTimeInMillis());
    }
}