/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/**
 * @author nicholas
 *
 */
public class ImageUtility {

    enum Origin {
        TOP, RIGHT, LEFT, BOTTOM;
    }
	
	/**
	 * Creates SWT Image to use for display in GUI. Pass in the raw image bytes.
	 * @param imageBytes
	 * @param display
	 * @return
	 */
	public static Image getImageFromBytes(byte[] imageBytes, Display display){
		Image byteImage = null;
		if(imageBytes != null){
			BufferedInputStream inputStreamReader = new BufferedInputStream(new ByteArrayInputStream(imageBytes));
			ImageData imageData = new ImageData(inputStreamReader);
			byteImage = new Image(display, imageData);
		}
		return byteImage;
	}

	/**
	 * Gets the image bytes from the given URL
	 * @param url
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static byte[] getImageBytesFromURL(URL url) throws FileNotFoundException, MalformedURLException, IOException {
		byte[] photoBytes = new byte[1024];
		URL fileURL = FileLocator.toFileURL(url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream( 1000 );
		FileInputStream stream = new FileInputStream(new File(fileURL.getFile()));
		for (int readNum; (readNum = stream.read(photoBytes)) != -1;) {
			baos.write(photoBytes, 0, readNum); 
		}
		baos.flush();
		photoBytes = baos.toByteArray();
		baos.close();
		return photoBytes;
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static byte[] getImageBytesFromFile(String fileString) throws MalformedURLException, IOException {
		File file = new File(fileString);
		URL url = FileLocator.toFileURL(file.toURI().toURL());
		return getImageBytesFromURL(url);
	}
	

	/**
	 * Automatically crops an image, by continuously removing full rows or columns from all sides, as long as all the
	 * pixels are the dominant color
	 */
	public static BufferedImage autoCrop(BufferedImage bi) {
		// find the crop points from all sides
		Color bgColor = getDominantColor(bi);
		int cropFromTop = findNumberOfRowsToCrop(bi, Origin.TOP,bgColor);
		int cropFromBottom = findNumberOfRowsToCrop(bi, Origin.BOTTOM,bgColor);
		int cropFromLeft = findNumberOfColumnsToCrop(bi, Origin.LEFT,bgColor);
		int cropFromRight = findNumberOfColumnsToCrop(bi, Origin.RIGHT,bgColor);

		int width = bi.getWidth() - cropFromLeft - cropFromRight;
		int height = bi.getHeight() - cropFromBottom - cropFromTop;
		// System.out.println(width);
		return bi.getSubimage(cropFromLeft, cropFromTop, width, height);
	}

	private static Color getDominantColor(BufferedImage bi){
		// Get color of every pixel
		int imgWidth = bi.getWidth();
		int imgHeight = bi.getHeight();
		int[][] pixelRGB = new int[imgWidth][imgHeight];
		HashMap<Integer, Integer> colorCount = new HashMap<Integer, Integer>();
		Integer count = new Integer(0);

		for(int w = 0; w < imgWidth; w++){
			for(int h = 0; h < imgHeight; h++){
				pixelRGB[w][h] = bi.getRGB(w, h);
				count = colorCount.get(pixelRGB[w][h]);
				colorCount.put(pixelRGB[w][h], count == null? 1 : count + 1);
			}
		}
		
		// Determine background (dominant) color
		int background = 0;
		int max = 0;
		int value = 0;
		for(Map.Entry<Integer, Integer> entry: colorCount.entrySet()){
			value = entry.getValue();
			if(value > max){
				max = value;
				background = entry.getKey();
			}
		}
		return new Color(background);
	}
	
	/**
	 * Computes how many rows of pixels can be cropped from the specified {@link Origin}; only {@link Origin#TOP} and
	 * {@link Origin#BOTTOM} are valid
	 */
	private static int findNumberOfRowsToCrop(BufferedImage bi, Origin origin,Color bgColor) {
		if (origin != Origin.TOP && origin != Origin.BOTTOM) {
			throw new IllegalArgumentException("Direction can only be '" + Origin.TOP + "' (downwards) or '"
					+ Origin.BOTTOM + "' (upwards)");
		}
		int rows = 0;
		float threshold = 0.10f;
		while (rows >= 0 && rows < bi.getHeight()) {
			int pixesOverThreshold = 0;
			for (int i = 0; i < bi.getWidth(); i++) {
				Color color = new Color(bi.getRGB(i, origin == Origin.TOP ? rows : bi.getHeight() - rows - 1));
				int diffRGBA = Math.abs(color.getRed() - bgColor.getRed());
				diffRGBA += Math.abs(color.getGreen() - bgColor.getGreen());
				diffRGBA += Math.abs(color.getBlue() - bgColor.getBlue());
				diffRGBA += Math.abs(color.getAlpha() - bgColor.getAlpha());
				if(diffRGBA >= threshold * 4.0 * 255.0){
					pixesOverThreshold++;
					// if more than 10 pixels are outside of threshold then don't trim.
					if(pixesOverThreshold > 10){
						return rows;
					}
				}
			}
			rows++;
		}
		return rows;
	}

	/**
	 * Computes how many cols of pixels can be cropped from the specified {@link Origin}; only {@link Origin#LEFT} and
	 * {@link Origin#RIGHT} are valid
	 */
	private static int findNumberOfColumnsToCrop(BufferedImage bi, Origin origin,Color bgColor) {
		if (origin != Origin.RIGHT && origin != Origin.LEFT) {
			throw new IllegalArgumentException("Direction can onl be '" + Origin.LEFT + "' (rightwards) or '"
					+ Origin.RIGHT + "' (leftwards)");
		}
		int cols = 0;
		float threshold = 0.10f;
		while (cols >= 0 && cols < bi.getWidth()) {
			int pixesOverThreshold = 0;
			for (int i = 0; i < bi.getHeight(); i++) {
				Color color = new Color(bi.getRGB(origin == Origin.LEFT ? cols : bi.getWidth() - cols - 1, i));
				int diffRGBA = Math.abs(color.getRed() - bgColor.getRed());
				diffRGBA += Math.abs(color.getGreen() - bgColor.getGreen());
				diffRGBA += Math.abs(color.getBlue() - bgColor.getBlue());
				diffRGBA += Math.abs(color.getAlpha() - bgColor.getAlpha());
				if(diffRGBA >= threshold * 4.0 * 255.0){
					pixesOverThreshold++;
					// if more than 10 pixels are outside of threshold then don't trim.
					if(pixesOverThreshold > 10){
						return cols;
					}
				}
			}
			cols++;
		}
		return cols;
	}

	public static BufferedImage scaleImage(BufferedImage buim, int width) {
		int height = (int) (width / ((double) buim.getWidth() / (double) buim.getHeight()));
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		scaledImage.createGraphics().drawImage(buim.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return scaledImage;
	}

	public static BufferedImage scaleImageByHeight(BufferedImage buim, int height) {
		int width = (int) (height / ((double) buim.getHeight() / (double) buim.getWidth()));
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		scaledImage.createGraphics().drawImage(buim.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return scaledImage;
	}

}
