package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImageProcessing
{
    public static void main(String[] args)
    {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD(
                "https://images.unsplash.com/photo-1656477477824-1a4cfef063e7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80");
        // Or load your own image using a URL!
        //int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        //        viewImageData(imageData);
        assert imageData != null;

        // trimm the image
        twoDToImage(trimBorders(imageData, imageData[0].length / 3), "target/img-result/trimmed.jpg");
        // get negative colours of image
        twoDToImage(negativeColor(imageData), "target/img-result/negative.jpg");
        // stretch the image horizontally
        twoDToImage(stretchHorizontally(imageData), "target/img-result/stretched(horizontally).jpg");
        // shrink the image vertically
        twoDToImage(shrinkVertically(imageData), "target/img-result/shrinked(vertically).jpg");
        // invert vertically the image
        twoDToImage(invertImage(imageData), "target/img-result/inverted(vertically).jpg");

        // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
        // Painting with pixels
    }

    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount)
    {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2)
        {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++)
            {
                for (int j = 0; j < trimmedImg[i].length; j++)
                {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        }
        else
        {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }


    /**
     * Change RGB values of your image to negative
     *
     * @param imageTwoD Image represented as 2D array
     * @return 2D array of negative values of each pixel from the image
     */
    public static int[][] negativeColor(int[][] imageTwoD)
    {
        int[][] modifiedImageData = new int[imageTwoD.length][imageTwoD[0].length];

        for (int i = 0; i < imageTwoD.length; i++)
        {
            for (int j = 0; j < imageTwoD[i].length; j++)
            {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];

                modifiedImageData[i][j] = getColorIntValFromRGBA(rgba);
            }
        }
        return modifiedImageData;
    }

    public static int[][] stretchHorizontally(int[][] imageTwoD)
    {
        int[][] modifiedImageData = new int[imageTwoD.length][imageTwoD[0].length * 2];

        int it = 0;

        for (int i = 0; i < imageTwoD.length; i++)
        {
            for (int j = 0; j < imageTwoD[i].length; j++)
            {
                it = j*2;
                modifiedImageData[i][it] = imageTwoD[i][j];
                modifiedImageData[i][it+1] = imageTwoD[i][j];
            }
        }

        return modifiedImageData;
    }

    public static int[][] shrinkVertically(int[][] imageTwoD)
    {
        int[][] modifiedImageData = new int[imageTwoD.length/2][imageTwoD[0].length];

        for (int i = 0; i < imageTwoD[0].length; i++)
        {
            for (int j = 0; j < imageTwoD.length-1; j+=2)
            {
                modifiedImageData[j/2][i] = imageTwoD[j][i];
            }
        }
        return modifiedImageData;
    }

    public static int[][] invertImage(int[][] imageTwoD)
    {
        int[][] modifiedImageData = new int[imageTwoD.length][imageTwoD[0].length];

        for(int i = 0; i < imageTwoD.length; i++)
        {
            for(int j = 0; j < imageTwoD[i].length; j++)
            {
                modifiedImageData[i][j] = imageTwoD[(imageTwoD.length-1) -i][(imageTwoD[i].length-1)-j];
            }
        }
        return modifiedImageData;
    }

    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue)
    {
        // TODO: Fill in the code for this method
        return null;
    }

    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas)
    {
        // TODO: Fill in the code for this method
        return null;
    }

    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition,
                                         int color)
    {
        // TODO: Fill in the code for this method
        return null;
    }

    public static int[][] generateRectangles(int[][] canvas, int numRectangles)
    {
        // TODO: Fill in the code for this method
        return null;
    }

    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink)
    {
        try
        {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http"))
            {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null)
                {
                    System.out.println("Failed to get image from provided URL.");
                }
            }
            else
            {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++)
            {
                for (int j = 0; j < imgCols; j++)
                {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        }
        catch (Exception e)
        {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static void twoDToImage(int[][] imgData, String fileName)
    {
        try
        {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++)
            {
                for (int j = 0; j < imgCols; j++)
                {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        }
        catch (Exception e)
        {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }

    public static int[] getRGBAFromPixel(int pixelColorValue)
    {
        Color pixelColor = new Color(pixelColorValue);
        return new int[]{pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha()};
    }

    public static int getColorIntValFromRGBA(int[] colorData)
    {
        if (colorData.length == 4)
        {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        }
        else
        {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }

    public static void viewImageData(int[][] imageTwoD)
    {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3)
        {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels)
            {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        }
        else
        {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}