package com.jamescookie.facebook;

import com.jamescookie.graphics.ImageManipulator;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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

    public void overlayImages(List<PanoramioInfo> list) throws IOException {
        for (PanoramioInfo info : list) {
            BufferedImage bufferedImage = createThumbnail(30, ImageIO.read(info.getUrl()));
            Graphics2D g = image.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            Point p = info.getPoint();
            int x = (int) p.getX() - (bufferedImage.getWidth() / 2);
            int y = (int) p.getY() - (bufferedImage.getHeight() / 2);
            g.drawImage(bufferedImage, x, y, null);
            g.dispose();
        }
        writeImage(out, image);
    }

}
