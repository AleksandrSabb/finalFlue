package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int yearPresent = 2022;
        double fuelConsumption = 7.5;  // Расход топлива на 100 км (в литрах)
        double startingTankLevel = 15.0;  // Остаток топлива в баке в начале года (в литрах)

        Year year = new Year(yearPresent);  //Создаем лист дней без выходных
        List<String> days = year.getDays();
        Map<String, Double> map = new HashMap<>();
        for (String day : days) {
            map.put(day, (double) 0);
        }

        TreeMap<String, Double> sortedMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] parts1 = o1.split("\\.");
                String[] parts2 = o2.split("\\.");
                int month1 = Integer.parseInt(parts1[1]);
                int day1 = Integer.parseInt(parts1[0]);
                int month2 = Integer.parseInt(parts2[1]);
                int day2 = Integer.parseInt(parts2[0]);
                if (month1 == month2) {
                    return Integer.compare(day1, day2);
                }
                return Integer.compare(month1, month2);
            }
        }); //сорируем в три
        sortedMap.putAll(map);

        Map<String, Double> newMap = new HashMap<>(); // мапа заправок. дата, литры
        newMap.put(sortedMap.firstKey(), startingTankLevel);
        newMap.put("05.01", 45.00);
        newMap.put("17.01", 45.00);
        newMap.put("08.02", 45.00);
        newMap.put("17.02", 45.00);
        newMap.put("08.03", 45.00);
        newMap.put("17.03", 45.00);
        newMap.put("05.04", 45.00);
        newMap.put("20.04", 45.00);
        newMap.put("05.05", 45.00);
        newMap.put("17.05", 45.00);
        newMap.put("08.06", 45.00);
        newMap.put("17.06", 45.00);
        newMap.put("05.07", 45.00);
        newMap.put("20.07", 45.00);
        newMap.put("05.08", 45.00);
        newMap.put("17.08", 45.00);
        newMap.put("05.09", 45.00);
        newMap.put("20.09", 45.00);
        newMap.put("05.10", 45.00);
        newMap.put("17.10", 45.00);
        newMap.put("08.11", 45.00);
        newMap.put("17.11", 45.00);
        newMap.put("05.12", 45.00);
        newMap.put("16.12", 45.00);


        Set<String> sortedKeys = sortedMap.keySet();
        Set<String> newKeys = newMap.keySet();

        for (String key : newKeys) {
            if (!sortedKeys.contains(key)) {
                System.out.println("Key " + key + " is present in newMap, but not in sortedMap.");
            }
        } //проверка чтоб по выходным не заправлялись


        List<String> indexes = new ArrayList<>();
        for (String key : newKeys) {
            if (sortedKeys.contains(key)) {
                indexes.add(key);
            }
        }
        indexes.sort(Comparator.comparing((String s) -> s.substring(3)).thenComparing(s -> s.substring(0, 2)));
        indexes.add("99.99");
        sortedMap.put("99.99", 0.0);

        TreeMap<String, Double>[] mapArray = new TreeMap[indexes.size() - 1];
        for (int i = 0; i < indexes.size() - 1; i++) {
            mapArray[i] = new TreeMap<>(sortedMap.subMap(indexes.get(i), indexes.get(i + 1)));
        } //разбили на разные мапи по цыклам заправки для подсчета дней цыкла заправки


        for (int i = 0; i < mapArray.length; i++) { // перебираем массив мапов и вносим пройденый километраж к дате
            for (String key : mapArray[i].keySet()) {
                Random random = new Random();
                double km = //начинаем считать
                        (newMap.get(mapArray[i].firstKey()) - 7) //значение из мапи заправки по ключу из первого числа цикла
                                / mapArray[i].size() //делим на количество дней цикла
                                * fuelConsumption //умножаем на расход
                                + random.nextInt(21) - 10; //добавляем +-10 для рандома
                sortedMap.put(key, (double) Math.round(km));//округляем и записываем
            }

        }
        System.out.println(sortedMap);

        TreeMap<String, String> stringTreeMap = new TreeMap<>();
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Double doubleValue = entry.getValue();
            int intValue = doubleValue.intValue();
            stringTreeMap.put(key, String.valueOf(intValue));
        }

        stringTreeMap.remove("99.99"); //этот ключ нужне был только для создания периодов. больше не нужен
        stringTreeMap.put("00.01", "Січень");
        stringTreeMap.put("00.02", "Лютий");
        stringTreeMap.put("00.03", "Березень");
        stringTreeMap.put("00.04", "Квітень");
        stringTreeMap.put("00.05", "Травень");
        stringTreeMap.put("00.06", "Червень");
        stringTreeMap.put("00.07", "Липень");
        stringTreeMap.put("00.08", "Серпень");
        stringTreeMap.put("00.09", "Вересень");
        stringTreeMap.put("00.10", "Жовтень");
        stringTreeMap.put("00.11", "Листопад");
        stringTreeMap.put("00.12", "Грудень"); //добавили разделители


        TreeMap<String, String> finalMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] parts1 = o1.split("\\.");
                String[] parts2 = o2.split("\\.");
                int month1 = Integer.parseInt(parts1[1]);
                int day1 = Integer.parseInt(parts1[0]);
                int month2 = Integer.parseInt(parts2[1]);
                int day2 = Integer.parseInt(parts2[0]);
                if (month1 == month2) {
                    return Integer.compare(day1, day2);
                }
                return Integer.compare(month1, month2);
            }
        }); //сорируем в три

        finalMap.putAll(stringTreeMap);
        System.out.println(finalMap);
        for (Map.Entry<String, String> entry : finalMap.entrySet()) {
            String value = entry.getValue();
            System.out.println();
            System.out.println(value);
        }
    }
}
