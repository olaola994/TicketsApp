import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application {
    public void init() {
        System.out.println("init: " + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("start begin: " + Thread.currentThread().getName());
        View view = new View();
        Scene scene = new Scene(view.getRootNode(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Tickets");
        stage.show();
    }
    public void stop() {
        System.out.println("stop: " + Thread.currentThread().getName());
    }
}


