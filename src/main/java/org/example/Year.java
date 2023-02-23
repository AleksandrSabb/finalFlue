package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Year {
    private int year;

    public Year(int year) {
        this.year = year;
    }

    public List<String> getDays() {
        List<String> days = new ArrayList<>();
        LocalDate currentDate = LocalDate.of(year, 1, 1);

        // Перебираем все дни года
        while (currentDate.getYear() == year) {
            // Проверяем, является ли текущий день рабочим днем
            if (currentDate.getDayOfWeek().getValue() < 6) {
                days.add(String.format("%02d.%02d", currentDate.getDayOfMonth(), currentDate.getMonthValue()));
            }
            currentDate = currentDate.plusDays(1);
        }

        // Сортируем массив дней
        days.sort((day1, day2) -> {
            String[] day1Parts = day1.split("\\.");
            String[] day2Parts = day2.split("\\.");
            int monthComparison = day1Parts[1].compareTo(day2Parts[1]);
            if (monthComparison == 0) {
                return day1Parts[0].compareTo(day2Parts[0]);
            }
            return monthComparison;
        });

        return days;
    }
}
