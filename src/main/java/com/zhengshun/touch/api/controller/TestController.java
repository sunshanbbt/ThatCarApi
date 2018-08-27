package com.zhengshun.touch.api.controller;


import javax.imageio.ImageIO;
        import java.awt.*;
        import java.awt.image.BufferedImage;
        import java.io.File;
        import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwj
 * date:2018/2/23
 * comment:
 */
public class TestController {

    /**
     * 切割图片
     *
     * @throws Exception
     */
    private static void splitImage() throws Exception {
        List<String> list = TestController.getAllFile("/Users/sunshan/Desktop/01/", false);
        for ( int j = 0; j< list.size(); j++ ) {
            System.out.println(list.get(j));
            String originalImg = list.get(j);
            File file = new File(originalImg);
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);

            int rows = 2;
            int cols = 3;
            int chunks = rows * cols;

            int chunkWidth = image.getWidth() / cols;
            int chunkHeight = image.getHeight() / rows;

            int count = 0;
            BufferedImage[] imgs = new BufferedImage[chunks];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight,
                            chunkWidth * y, chunkHeight * x,
                            chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }
            for (int i = 0; i < imgs.length; i++) {
                ImageIO.write(imgs[i], "jpg", new File("/Users/sunshan/Desktop/01/" + i + ".jpg"));
            }
        }

    }

    /**
     * 合并图片
     *
     * @throws Exception
     */
    private static void mergeImage() throws Exception {
        int rows = 3;
        int cols = 3;
        int chunks = rows * cols;

        int chunkWidth, chunkHeight;
        int type;

        File[] imgFiles = new File[chunks];
        for (int i = 0; i < chunks; i++) {
            imgFiles[i] = new File("/Users/sunshan/Desktop/01/" + i + ".jpg");
        }

        BufferedImage[] buffImages = new BufferedImage[chunks];
        for (int i = 0; i < chunks; i++) {
            buffImages[i] = ImageIO.read(imgFiles[i]);
        }
        type = buffImages[0].getType();
        chunkWidth = buffImages[0].getWidth();
        chunkHeight = buffImages[0].getHeight();

        BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, type);

        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                num++;
            }
        }

        ImageIO.write(finalImg, "jpeg", new File("C:\\Users\\liwj\\Desktop\\tidb\\image\\finalImg.jpg"));
    }

    public static void main(String[] args) {

        try {
            splitImage();
//            mergeImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static java.util.List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {

            if (file.getName().indexOf("jpg")>0||file.getName().indexOf("jpeg")>0) {
//                System.out.println( file.getName());
                if (file.isDirectory()) {
                    if (isAddDirectory) {
                        list.add(file.getAbsolutePath());
                    }
                    list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
                } else {
                    list.add(file.getAbsolutePath());
                }
            }
        }
        return list;
    }
}