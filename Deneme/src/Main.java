public class Main {

    public static final String EMPTY_STRING = "";

    public static void main(String[] args) {
        Date date = new Date(2020, 8, 19);
        Date other = new Date(2021, 8, 19);
        System.out.println(date.getDaysTo(other));
    }
}
