package com.movies.utils;

import com.movies.exceptions.DataNotFoundException;
import com.movies.models.ImageMovie;
import com.movies.repositories.ImageMovieDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner {
    @Autowired
    private LocalVarsHelper localVarsHelper;

    @Autowired
    private ImageMovieDAO imageMovieDAO;

    @Override
    public void run(String... args) throws Exception {
        log.info("Uploading images to H2...");
        updateRecordsWithMovieImage("oppenheimer.jpg", 7722L);
        updateRecordsWithMovieImage("barbie.jpg", 7723L);
        updateRecordsWithMovieImage("m3gan.jpg", 7724L);
        updateRecordsWithMovieImage("creed iii.jpg", 7725L);
        updateRecordsWithMovieImage("scream_vi.jpg", 7726L);
        updateRecordsWithMovieImage("wonka.jpg", 7727L);
        updateRecordsWithMovieImage("cocaine_bear.jpg", 7728L);
        updateRecordsWithMovieImage("indianaj5.jpeg", 7729L);
        updateRecordsWithMovieImage("the_batman.jpg", 7730L);
        updateRecordsWithMovieImage("jurassic_world_dominion.jpg", 7731L);
        updateRecordsWithMovieImage("morbius.jpg", 7732L);
        updateRecordsWithMovieImage("pray.jpg", 7733L);
        updateRecordsWithMovieImage("sonic2.jpg", 7734L);
        updateRecordsWithMovieImage("the_woman_king.jpg", 7735L);
        updateRecordsWithMovieImage("halloween_ends.jpg", 7736L);
        updateRecordsWithMovieImage("dont_worry_darling.jpg", 7737L);
        log.info("All images was uploaded successfully!!");
    }

    private void updateRecordsWithMovieImage(String imageName, Long recordId) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(localVarsHelper.getStaticDirectory() + imageName));
        ImageMovie imageMovie = imageMovieDAO.findById(recordId)
                .orElseThrow(() -> new DataNotFoundException("Image movie id: " + recordId + " not found"));
        imageMovie.setImage(data);
        imageMovieDAO.save(imageMovie);
    }
}
