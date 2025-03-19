package org.example.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class LocalDatesMain {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.now();

        System.out.println("Dia semana: " + date.getDayOfWeek());
        System.out.println("Mês: " + date.getMonth());
        System.out.println("Mês value: " + date.getMonthValue());
        System.out.println("Ano: " + date.getYear());
        System.out.println("Dia: " + date.getDayOfMonth());
        System.out.println("Hora: " + date.getHour());
        System.out.println("Minutos: " + date.getMinute());
        System.out.println("Segundos: " + date.getSecond());

        LocalDate birthday = LocalDate.of(2025, Month.JANUARY, 31);
        System.out.println("birthday: " + birthday);
        // se o ano é bissexto
        System.out.println("Bissexo: " + birthday.isLeapYear());
        // quantidade de dias do mês
        System.out.println("Tamanho do mês: " + birthday.lengthOfMonth());

        LocalDateTime dateWtihTime = LocalDateTime.of(2025, Month.JANUARY, 31, 23, 59, 59);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(dateWtihTime.format(fmt));

        DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("dd, MMMM - yyyy");
        System.out.println(dateWtihTime.format(fmt2));
    }
}
