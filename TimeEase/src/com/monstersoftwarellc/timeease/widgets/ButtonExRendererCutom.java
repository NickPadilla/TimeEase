/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

import com.pfcomponents.common.utils.GraphicUtil;
import com.pfcomponents.common.widgets.ButtonExRendererOffice;
import com.pfcomponents.common.widgets.EnButtonState;

/**
 * @author navid
 *
 */
public class ButtonExRendererCutom extends ButtonExRendererOffice {

	// border colors
	private RGB borderColor = new RGB(0, 0, 0);
	private RGB borderColorPressed = new RGB(251, 148, 65);
	private RGB borderColorHover = new RGB(251, 192, 92);
	
	// text colors
	private RGB textColor = new RGB(255, 255, 255);
	private RGB textColorHover = new RGB(0,0,0);

	// inner gradient colors
	private Gradient color = Gradient.fourColorGradient(new RGB(92, 92, 92), new RGB(92, 92, 92), new RGB(0, 0, 0), new RGB(92, 92, 92));
	private Gradient colorPressed = Gradient.fourColorGradient(new RGB(255, 184, 93), new RGB(255, 184, 93), new RGB(251, 148, 65), new RGB(255, 184, 93));
	private Gradient colorHover= Gradient.fourColorGradient(new RGB(255, 245, 199), new RGB(255, 230, 158), new RGB(255, 216, 106), new RGB(255, 216, 106));

	
	public void drawBorder(GC gc, Path p, EnButtonState state, double alpha){
		Color colorBorder = null;
		
		if (state == EnButtonState.Pressed){
			colorBorder = new Color(gc.getDevice(), borderColorPressed);
		}else{ 
			Color temp  = new Color(gc.getDevice(),borderColor);
			colorBorder = interpolateColor(gc.getDevice(), temp, borderColorHover, alpha);
			temp.dispose();
		}

		gc.setForeground(colorBorder);
		gc.drawPath(p);

		colorBorder.dispose();
	}

	// TODO Provide Split area color variables. Also fix dispose some value are overwritten here.
	public void drawSplitArea(GC gc, Rectangle rectSplit, double alpha, EnButtonState state){
		Color colorLine1 = new Color(gc.getDevice(), 255, 255, 255);
		Color colorLine2 = new Color(gc.getDevice(), 111, 111, 111);

		colorLine1 = GraphicUtil.interpolateColor(gc.getDevice(), colorLine1, 0, 0, 0, alpha);
		colorLine2 = GraphicUtil.interpolateColor(gc.getDevice(), colorLine2, 255, 255, 255, alpha);

		drawSplitArea(gc, rectSplit, colorLine1, colorLine2);

		colorLine1.dispose();
		colorLine2.dispose();
	}

	public void drawText(GC gc, Rectangle bounds, String text, EnButtonState state, double alpha, int splitWidth){
		Color temp = new Color(gc.getDevice(), textColor);
		Color colorText = interpolateColor(gc.getDevice(),temp,textColorHover,alpha);

		gc.setForeground(colorText);
		Point extent = gc.textExtent(text);

		gc.drawText(text, bounds.width / 2 - extent.x / 2 - splitWidth / 2, bounds.height / 2 - extent.y / 2, true);

		colorText.dispose();
		temp.dispose();
	}

	public void drawBackground(GC gc, Rectangle bounds, EnButtonState state, int arcSize, double alpha){
		Gradient toDraw = null;
		
		if (state == EnButtonState.Pressed){
			colorPressed.initColorPoints(gc.getDevice());
			toDraw = colorPressed;
		}else {
			color.initColorPoints(gc.getDevice());
			toDraw = color.interpolateGradient(gc.getDevice(), colorHover, alpha);
			color.dispose();
		}

		fillPart(gc, toDraw, bounds, arcSize);
		
		toDraw.dispose();
	}
	
	protected void fillPart(GC g, Gradient gradient, Rectangle clientRect, int arcSize){
		SortedMap<Integer, Color> points = gradient.getColorPoints();
	    // there must always be at least 2 points in a gradient.
	    if(points.size() < 2){
	    	throw new IllegalArgumentException("The backGround cannot be drawn. The gradient must contain at least 2 points.");
	    }
		
	    // draw gradient based upon points provided.
	    // 2 points are processed per iteration
	    Entry<Integer, Color> startPoint = null;
	    Entry<Integer, Color> endPoint = null;
	    int radiusUp = 1;
	    int radiusDown = 1;
	    Iterator<Entry<Integer, Color>> pointIter = points.entrySet().iterator();
	    
	    while(pointIter.hasNext()){

	    	if(startPoint == null){
	    		// always at least 2 so this is ok.
	    		startPoint = pointIter.next();
	    		endPoint = pointIter.next();
	    		// drawing top so make sure top has arc
	    		radiusUp = arcSize;
	    	}else{
	    		endPoint = pointIter.next();
	    		// if the points are the same we don't draw transition from them but instead go to the next point
	    		// unless for some reason this is the last point. WTF?
	    		if(endPoint.getKey() - startPoint.getKey() == 0 && pointIter.hasNext()){
	    			startPoint = endPoint;
	    			endPoint = pointIter.next();
	    		}
	    		
	    		// is last segment
	    		if(!pointIter.hasNext()){
	    			radiusDown = arcSize;
	    		}
	    	}
	    	
 	    	// first calculate the starting point we must take the 1 pixel border into account
	    	// additionally we must convert the percentages 
	    	int start = convertPercentage(startPoint.getKey(), clientRect.height);
	    	int end =  convertPercentage(endPoint.getKey(), clientRect.height);
	    	int height = end - start;
	    	
	    	Rectangle rect = new Rectangle(1, start, clientRect.width - 2, height);
	    	Path p = GraphicUtil.getRoundPath(g, g.getDevice(), rect.x, rect.y, rect.width, rect.height, radiusUp, radiusUp, radiusDown, radiusDown);
	
		    g.setClipping(p);
		    p.dispose();
	
		    g.setForeground(startPoint.getValue());
		    g.setBackground(endPoint.getValue());
	
		    g.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);
	
		    g.setClipping((Path)null);
		    
		    // setup for next loop
		    startPoint = endPoint;
	    	radiusUp = 1;
		    radiusDown = 1;
	    }
	}
	
	/**
	 * Linear conversion on 0 - 100 scale to height.
	 * @return
	 */
	private int convertPercentage(int percentage,int height){
		return (int)((percentage * (height - 2)) / 100d) + 1;
	}
	
	
	/**
	 * Delegator method that allows the RGB class to be used.
	 */
	private Color interpolateColor(Device device, Color startColor,RGB endColor,double alpha) {
		return GraphicUtil.interpolateColor(device, startColor, endColor.red, endColor.green, endColor.blue, alpha);
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public ButtonExRendererCutom setBorderColor(RGB borderColor) {
		this.borderColor = borderColor;
		return this;		
	}

	/**
	 * @param borderColorPressed the borderColorPressed to set
	 */
	public ButtonExRendererCutom setBorderColorPressed(RGB borderColorPressed) {
		this.borderColorPressed = borderColorPressed;
		return this;		
	}

	/**
	 * @param borderColorHover the borderColorHover to set
	 */
	public ButtonExRendererCutom setBorderColorHover(RGB borderColorHover) {
		this.borderColorHover = borderColorHover;
		return this;		
	}

	/**
	 * @param textColor the textColor to set
	 */
	public ButtonExRendererCutom setTextColor(RGB textColor) {
		this.textColor = textColor;
		return this;		
	}

	/**
	 * @param textColorHover the textColorHover to set
	 */
	public ButtonExRendererCutom setTextColorHover(RGB textColorHover) {
		this.textColorHover = textColorHover;
		return this;		
	}

	/**
	 * @param color the color to set
	 */
	public ButtonExRendererCutom setColor(Gradient color) {
		this.color = color;
		return this;		
	}

	/**
	 * @param colorPressed the colorPressed to set
	 */
	public ButtonExRendererCutom setColorPressed(Gradient colorPressed) {
		this.colorPressed = colorPressed;
		return this;		
	}

	/**
	 * @param colorHover the colorHover to set
	 */
	public ButtonExRendererCutom setColorHover(Gradient colorHover) {
		this.colorHover = colorHover;
		return this;
	}
	
}
