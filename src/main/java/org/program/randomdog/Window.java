package org.program.randomdog;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.methods.CloseableHttpResponse;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.program.main.Program.*;

public class Window extends JFrame {
    private JLabel imageLabel;

    public Window() {
        this.setSize(800, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(imageLabel, BorderLayout.CENTER);
    }

    public void displayRandomDogImage() {
        try {
            URI getRequest = new URI("https://dog.ceo/api/breeds/image/random");
            get.setURI(getRequest);
            CloseableHttpResponse chr = client.execute(get);

            RandomDog randomDog = mapper.readValue(chr.getEntity().getContent(), new TypeReference<>() {});

            if (randomDog != null && "success".equals(randomDog.getStatus())) {
                BufferedImage image = ImageIO.read(new URL(randomDog.getMessage()));
                imageLabel.setIcon(new ImageIcon(image));
                this.toFront();
                this.setAlwaysOnTop(true);
                this.requestFocus();
                setLocationRelativeTo(null);
                this.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch dog image",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch dog image",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
