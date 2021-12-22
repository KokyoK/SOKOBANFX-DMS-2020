package com.ae2dms.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class PrimaryStageTest extends ApplicationTest {
    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/StartPage-layout.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("SokobanFX");
        stage.show();
    }

    @Test
    public void testButtonText() {
        verifyThat("#contBtn", hasText("Continue"));
        verifyThat("#newBtn", hasText("New Game"));
        verifyThat("#entertain", hasText("Entertain Game！（2 Players）"));

        verifyThat("#bg", (ImageView imageView) -> {
            Image image = imageView.getImage();
            return image.getUrl().contains("start.jpeg");
        });

    }




}