package com.jamescookie.facebook;

import com.jamescookie.graphics.ImageManipulator;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class ImageWriter extends ImageManipulator {
    private Logger log = Logger.getLogger(this.getClass());
    private static final int LARGEST_DIMENSION = 30;

    public ImageWriter(URL url, OutputStream os) throws IOException {
        super(url, os);
    }

    public ImageWriter(File file, OutputStream os) throws IOException {
        super(file, os);
    }

    public ImageWriter(InputStream is, OutputStream os) throws IOException {
        super(is, os);
    }

    public void overlayImages(List<PanoramioInfo> list, File tempDir) throws IOException {
        for (PanoramioInfo info : list) {
            BufferedImage thumbImage = getThumbnail(info, tempDir);
            if (thumbImage == null) {
                continue;
            }
            writeThumbnailOverImage(thumbImage, info.getPoint());
        }
        writeImage(out, image);
    }

    private void writeThumbnailOverImage(BufferedImage bufferedImage, Point p) {
        Graphics2D g = image.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        int x = getX(p, bufferedImage);
        int y = getY(p, bufferedImage);
        g.setColor(Color.WHITE);
        g.drawRect(x-1, y-1, bufferedImage.getWidth()+1, bufferedImage.getHeight()+1);
        if ((x + bufferedImage.getWidth() > image.getWidth()) ||
            (y + bufferedImage.getHeight() > image.getHeight())) {
            int rectWidth = image.getWidth() - x;
            int rectHeight = image.getHeight() - y;
            if (rectWidth > bufferedImage.getWidth()) rectWidth = bufferedImage.getWidth();
            if (rectHeight > bufferedImage.getHeight()) rectHeight = bufferedImage.getHeight();
            g.drawImage(bufferedImage, x, y, rectWidth, rectHeight, null);
        } else {
            g.drawImage(bufferedImage, x, y, null);
        }
        g.dispose();
    }

    private int getY(Point p, BufferedImage bufferedImage) {
        int y = (int) p.getY() - (bufferedImage.getHeight() / 2);
        if (y < 1) y = 1;
        return y;
    }

    private int getX(Point p, BufferedImage bufferedImage) {
        int x = (int) p.getX() - (bufferedImage.getWidth() / 2);
        if (x < 1) x = 1;
        return x;
    }

    private BufferedImage getThumbnail(PanoramioInfo info, File tempDir) throws IOException {
        BufferedImage thumbImage;
        String filename = info.getUrl().getFile();
        filename = filename.substring(filename.lastIndexOf("/"), filename.length());
        File thumb = new File(tempDir, filename);
        if (thumb.exists()) {
            thumbImage = ImageIO.read(thumb);
        } else {
            thumbImage = getRemoteImageAndSaveLocally(info, thumb);
        }
        return thumbImage;
    }

    private BufferedImage getRemoteImageAndSaveLocally(PanoramioInfo info, File thumb) {
        BufferedImage thumbImage;
        try {
            thumbImage = ImageManipulator.createThumbnail(LARGEST_DIMENSION, ImageIO.read(info.getUrl()));
            ImageManipulator.writeImage(new FileOutputStream(thumb), thumbImage);
        } catch (IOException e) {
            thumbImage = null;
            log.error("Error with thumbnail: "+ info.getUrl(), e);
            try {
                if (thumb.exists()) {
                    thumb.delete();
                }
            } catch (Exception e1) {
                log.error("Couldn't even delete it!", e1);
            }
        }
        return thumbImage;
    }

}
