package com.example.JavafxSpringboot.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            Stage stage = stageReadyEvent.getStage();
            ClassPathResource fxml = new ClassPathResource("fxml/login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);

            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.setTitle(this.applicationTitle);
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
