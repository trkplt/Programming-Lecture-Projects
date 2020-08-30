import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    static TreeSet<Dene> nos = new TreeSet<>();

    public static void main(String[] args) {
        nos.add(new Dene(1));
        nos.add(new Dene(2));
        nos.add(new Dene(3));

        Dene dene = getDene();
        dene.changeNo(4);

        nos.add(new Dene(5));

        for (Dene current : nos) {
            System.out.println(current.getNo());
        }
    }

    static Dene getDene() {
        Iterator<Dene> iterator = nos.iterator();
        iterator.next();
        Dene dene = iterator.next();
        return dene;
    }
}
