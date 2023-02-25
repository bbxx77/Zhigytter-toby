import controllers.BuyerController;
import controllers.ProductController;
import controllers.UserController;
import data.interfaces.IDB;
import data.PostgresDB;
import entities.Buyer;
import entities.Product;
import repositories.BuyerRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import repositories.auth.Authentification;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        while (true) {
            Buyer buyer;

            do {
                buyer = new Authentification(db).auth();
            } while (buyer.getId() == -1);

            ProductController productController = new ProductController(new ProductRepository(db, new Product()));
            BuyerController buyerController = new BuyerController(new BuyerRepository(db, buyer));
            UserController userController = new UserController(new UserRepository(db, buyer));

            MyApplication app = new MyApplication(userController, buyerController, productController);
            app.start();
        }
    }
}


