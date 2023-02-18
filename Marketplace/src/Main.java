@SuppressWarnings("ALL")
public class Main {
    public static void main(String[] args)  {
        while (true) {
            Buyer buyer;
            do {
                buyer = Auth.auth();
            } while (buyer.getId() == -1);
            Run.main(buyer);
        }
    }
}
