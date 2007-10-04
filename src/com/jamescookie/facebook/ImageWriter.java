package com.jamescookie.facebook;

import com.jamescookie.graphics.ImageManipulator;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageWriter extends ImageManipulator {

    public ImageWriter(URL url, OutputStream os) throws IOException {
        super(url, os);
    }

    public ImageWriter(File file, OutputStream os) throws IOException {
        super(file, os);
    }

    public ImageWriter(InputStream is, OutputStream os) throws IOException {
        super(is, os);
    }

    public void overlayImages(List<URL> urls) throws IOException {
        List<BufferedImage> images = new ArrayList<BufferedImage>();
        for (URL url : urls) {
            images.add(createThumbnail(30, ImageIO.read(url)));
        }

        int i = 0;
        for (BufferedImage bufferedImage : images) {
            Graphics2D g = image.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            int x = ((image.getWidth() - bufferedImage.getWidth()) / 2) + i++;
            int y = ((image.getHeight() - bufferedImage.getHeight()) / 2) + i;
            g.drawImage(bufferedImage, x, y, null);
            g.dispose();
        }

        writeImage(out, image);
    }

}
