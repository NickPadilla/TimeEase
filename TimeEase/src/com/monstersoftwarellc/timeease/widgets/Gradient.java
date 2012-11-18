/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;

import com.pfcomponents.common.utils.GraphicUtil;

/**
 * @author navid
 * Holds rgb values to make up a linear gradient.
 * Gradient is drawn top to bottom. 
 */
public class Gradient {
	
	private TreeMap<Integer, RGB> points = new TreeMap<Integer, RGB>();
	private TreeMap<Integer, Color> colorPoints = new TreeMap<Integer, Color>();
	
	public static Gradient twoColorGRadient(RGB firstColor, RGB secondColor){
		return new Gradient().addPoint(0, firstColor).addPoint(100, secondColor);
	}
	
	public static Gradient threeColorGradient(RGB firstColor,RGB secondColor,RGB thirdColor){
		return new Gradient().addPoint(0,firstColor).addPoint(50, secondColor).addPoint(100, thirdColor);
	}
	
	public static Gradient fourColorGradient(RGB firstColor,RGB secondColor,RGB thirdColor,RGB fourthColor){
		return new Gradient().addPoint(0,firstColor).addPoint(50, secondColor).addPoint(51, thirdColor).addPoint(100, fourthColor);
	}

	/**
	 * Adds a gradient stop point. If this point already exists it will be overwritten.
	 * If colorPoints have already been initialized a call to this method will cause them to be disposed.
	 * @param stopPoint	 This is used to define the starting point of the gradient. It is specified as a percentage.
	 * @param color of the gradient
	 * @return this for fluent interface.
	 */
	public Gradient addPoint(Integer stopPoint, RGB color){
		if(colorPoints.size() > 0){
			dispose();
		}
		points.put(stopPoint, color);
		return this;
	}
	
	/**
	 * Adds a point with an already initialized Color object.
	 * dispose() must be called after calling this method.
	 * @param stopPoint This is used to define the starting point of the gradient. It is specified as a percentage.
	 * @param color of the gradient
	 * @return this for fluent interface.
	 * @throws IllegalArgumentException if the point already exists in the map. NOTE this is different from the RGB variant that just overwrites previous colors.
	 */
	public Gradient addPoint(Integer stopPoint, Color color){
		colorPoints.put(stopPoint, color);
		points.put(stopPoint, color.getRGB());
		return this;
	}
	
	/**
	 * Initializes an internal map with Color objects for each point instead of RGB values. This method will have no affect if color points have already been initialized.
	 * You can then get these points with a call to getColorPoints. 
	 * dispose() must be called after calling this method.
	 * @param device
	 */
	public void initColorPoints(Device device){
		if(colorPoints.size() == 0){
			for(Entry<Integer, RGB> entry : points.entrySet()){
				colorPoints.put(entry.getKey(), new Color(device,entry.getValue()));
			}
		}
	}
	
	/**
	 * Gets the points for 
	 * @return the colorPoints for this gradient or an empty map if they have not been initialized.
	 */
	public SortedMap<Integer, Color> getColorPoints(){
		return colorPoints;
	}
	
	/**
	 * Disposes all color points stored within this gradient.
	 */
	public void dispose(){
		for(Color color : colorPoints.values()){
			color.dispose();
		}
		colorPoints.clear();
	}
	
	/**
	 * Interpolates a gradient that is in between this gradient and the target gradient based upon the alpha value. Both gradients must have identical points.
	 * NOTE: this gradient and the gradient returned will have their color points initialized and dispose MUST be called. Only the target will remain unchanged.
	 * @param device 
	 * @param target the gradient to fade to.
	 * @param alpha The alpha value should be between 0 and 1. If 1 then the target gradient would be the final gradient. IF alpha was 0 then this gradient would be the final value.
	 * @return the interpolated Gradient
	 * @throws IllegalArgumentException if the this and the target Gradient do not have the same points.
	 */
	public Gradient interpolateGradient(Device device, final Gradient target,final double alpha){
		if(this.points.size() != target.getPoints().size()){
			throw new IllegalArgumentException("Gradients do not have the same number of points.");
		}
		Gradient ret = new Gradient();
		SortedMap<Integer, RGB> targetPoints = target.getPoints();
		initColorPoints(device);
		for(Entry<Integer,Color> entry : colorPoints.entrySet()){		
			
			RGB targetPointRGB = targetPoints.get(entry.getKey());
			
			if(targetPointRGB == null){
				throw new IllegalArgumentException("Gradients do not have matching points.");
			}
			
			ret.addPoint(entry.getKey(), interpolateColor(device, entry.getValue(), targetPointRGB, alpha));
		}
		
		return ret;
	}
	
	/**
	 * Delegator method that allows the RGB class to be used.
	 */
	private Color interpolateColor(Device device, Color startColor,RGB endColor,double alpha) {
		return GraphicUtil.interpolateColor(device, startColor, endColor.red, endColor.green, endColor.blue, alpha);
	}
	
	/**
	 * @return the points
	 */
	public SortedMap<Integer, RGB> getPoints() {
		return points;
	}
	
}
