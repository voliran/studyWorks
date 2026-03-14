package org.example.patterns;

public class TripFactory {
    public Trip createTrip(String type) {
        switch (type) {
            case "бизнес" -> {
                return new BusinessTrip();
            }
            case "отпуск" -> {
                return new VacationTrip();
            }
            default -> {
                throw  new RuntimeException("нет доступного типа");
            }
        }
    }
}
