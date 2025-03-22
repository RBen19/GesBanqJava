package org.beni.gescartebanque;

import ch.qos.logback.classic.Logger;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.controlsfx.tools.Utils;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.Base64;

public class HelloApplication extends Application {

    public  static void change(Stage stage,String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(file+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void start(Stage stage) throws Exception {
        change(stage,"login");
    }

    public static void main(String[] args) {
          Logger logger = (Logger) LoggerFactory.getLogger(HelloApplication.class);
          logger.info("de log4j a logback");

        String testSalt = UtilsFonction.generateSalt();
        System.out.println(testSalt);
        String hashedPassword = UtilsFonction.hashPassword("passer", testSalt);
        System.out.println(hashedPassword);

       // UtilsFonction.sendUsernameAndPasswordByMail("RBEN19","passer","bonmerciel@gmail.com");
       // RessourceDAO.CarteBancaireDao().confirmerRetrait(2L,"4v5SOf");
        JpaUtils.getEm();
        launch();
    }
}