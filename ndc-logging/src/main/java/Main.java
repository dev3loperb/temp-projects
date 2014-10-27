import org.slf4j.NDC;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        NDC.push("A");
        NDC.push("B");
        NDC.pop();
        NDC.pop();
    }
}
