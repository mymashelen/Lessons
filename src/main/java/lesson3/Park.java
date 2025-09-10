package lesson3;

public class Park {
    private final String parkName;
    private final String parkAddress;
    private final Attraction[] attractions;
    public Park(String parkName, String parkAddress, Attraction[] attractions) {
        this.parkName = parkName;
        this.parkAddress = parkAddress;
        this.attractions = attractions;
    }
    public void infoPark() {
        System.out.println("Название парка: " + parkName + ";\nАдресс парка: " + parkAddress + ";");
        for (Attraction attraction : attractions) {
            attraction.infoAttraction();
            System.out.println();
        }
    }
    public static class Attraction {
        private final String nameAttraction;
        private final String workTimeAttraction;
        private final double priceAttraction;
        public Attraction(String nameAttraction, String workTimeAttraction, double priceAttraction) {
            this.nameAttraction = nameAttraction;
            this.workTimeAttraction = workTimeAttraction;
            this.priceAttraction = priceAttraction;
        }
        public void infoAttraction() {
            System.out.println("Название аттракциона: " + nameAttraction + ";\nВремя работы аттракциона: " +
                    workTimeAttraction + ";\nСтоимость аттракциона: " + priceAttraction);
        }
    }
}
