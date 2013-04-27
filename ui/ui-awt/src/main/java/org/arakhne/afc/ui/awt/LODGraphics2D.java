/* 
 * $Id$
 * 
 * Copyright (C) 2005-09 Stephane GALLAND.
 * Copyright (C) 2012-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */


package org.arakhne.afc.ui.awt;


import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import org.arakhne.afc.ui.Graphics2DLOD;
import org.arakhne.afc.ui.StringAnchor;
import org.arakhne.afc.ui.TextAlignment;

/** This graphic context permits to display
 *  something with a level of details.
 *
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface LODGraphics2D {

	/** Sets the URL of an image to draw.
	 * This URL may be used in place of the image as a reference.
	 * 
	 * @param url
	 */
	public void setImageURL(URL url);
	
	/** Replies the URL of an image to draw.
	 * This URL may be used in place of the image as a reference.
	 * 
	 * @return url
	 */
	public URL getImageURL();

	/** Replies the anchor for the strings to draw.
	 * 
	 * @return the anchor for the strings.
	 */
	public StringAnchor getStringAnchor();
	
	/** Replies the LOD.
	 * 
	 * @return the LOD.
	 */
	public Graphics2DLOD getLOD();

	/** Replies if this graphical context is for printing.
	 * 
	 * @return <code>true</code> if this graphical context was
	 * created for printing.
	 */
	public boolean isPrinting();

	/** Replies if this graphical context supports anti-aliasing.
	 * 
	 * @return <code>true</code> if this graphical context has its
	 * anti-aliasing flag set, otherwhise <code>false</code>.
	 */
	public boolean isAntiAliased();

	/** Set if this graphical context supports anti-aliasing.
	 * 
	 * @param anti_aliased_enable is <code>true</code> to allow antio-aliased,
	 * otherwise <code>false</code>
	 */
	public void setAntiAliased(boolean anti_aliased_enable);

	/**
	 * Return the size of the target component in the screen space.
	 * 
	 * @return a dimension or <code>null</code>
	 * @see #getViewportRect()
	 * @see #getViewportSize()
	 */
	public Dimension2D getTargetComponentSize();

	/**
	 * Return the rectangle that corresponds to the displayed
	 * area.
	 * 
	 * @return the rectangle expressed in the logic coordinate space.
	 * @see #getTargetComponentSize()
	 * @see #getViewportSize()
	 */
	public Rectangle2D getViewportRect();

	/**
	 * Return the size of the viewport in the logical space.
	 * 
	 * @return a dimension or <code>null</code>
	 * @see #getTargetComponentSize()
	 * @see #getViewportRect()
	 */
	public Dimension2D getViewportSize();

	/** Draws a pixel.
	 *  <p>
	 *  The given coordinates will be transformed.
	 *
	 * @param x is the location where to paint the pixel.
	 * @param y is the location where to paint the pixel.
	 */
	public void drawPoint(float x, float y);

    /** Replies if this figure could drawn or not.
     * <p>
     * This function assumes that a drawable figure
     * must have a bounding box with its width and
     * height greaters or equals to a specified size.
     * This size depends on the adviced level of details:
     * {@link Graphics2DLOD#LOW_LEVEL_OF_DETAIL},
     * {@link Graphics2DLOD#NORMAL_LEVEL_OF_DETAIL}
     * or {@link Graphics2DLOD#HIGH_LEVEL_OF_DETAIL}.
     *
     * @param shape is the shape to draw.
     * @return <code>true</code> if the figure is drawable, otherwhise
     * <code>false</code>.
     */ 
    public boolean hit(Shape shape);

    /** Replies if this figure could drawn or not.
     * <p>
     * This function assumes that a drawable figure
     * must have a bounding box with its width and
     * height greaters or equals to a specified size.
     * This size depends on the adviced level of details:
     * {@link Graphics2DLOD#LOW_LEVEL_OF_DETAIL},
     * {@link Graphics2DLOD#NORMAL_LEVEL_OF_DETAIL}
     * or {@link Graphics2DLOD#HIGH_LEVEL_OF_DETAIL}.
     *
     * @param rect is the rect to draw.
     * @return <code>true</code> if the figure is drawable, otherwhise
     * <code>false</code>.
     */ 
    public boolean hit(Rectangle2D rect);

    ////////////////////////////////////////////////////////////
	// Graphics API inherited from java.awt.Graphics

	/**
	 * Creates a new <code>Graphics</code> object that is 
	 * a copy of this <code>Graphics</code> object.
	 * @return     a new graphics context that is a copy of 
	 *                       this graphics context.
	 */
	public abstract Graphics create();

	/**
	 * Creates a new <code>Graphics</code> object based on this 
	 * <code>Graphics</code> object, but with a new translation and clip area.
	 * The new <code>Graphics</code> object has its origin 
	 * translated to the specified point (<i>x</i>,&nbsp;<i>y</i>). 
	 * Its clip area is determined by the intersection of the original
	 * clip area with the specified rectangle.  The arguments are all
	 * interpreted in the coordinate system of the original 
	 * <code>Graphics</code> object. The new graphics context is 
	 * identical to the original, except in two respects: 
	 * <p>
	 * <ul>
	 * <li>
	 * The new graphics context is translated by (<i>x</i>,&nbsp;<i>y</i>).  
	 * That is to say, the point (<code>0</code>,&nbsp;<code>0</code>) in the 
	 * new graphics context is the same as (<i>x</i>,&nbsp;<i>y</i>) in 
	 * the original graphics context. 
	 * <li>
	 * The new graphics context has an additional clipping rectangle, in 
	 * addition to whatever (translated) clipping rectangle it inherited 
	 * from the original graphics context. The origin of the new clipping 
	 * rectangle is at (<code>0</code>,&nbsp;<code>0</code>), and its size  
	 * is specified by the <code>width</code> and <code>height</code>
	 * arguments.
	 * </ul>
	 * <p>
	 * @param      x   the <i>x</i> coordinate.
	 * @param      y   the <i>y</i> coordinate.
	 * @param      width   the width of the clipping rectangle.
	 * @param      height   the height of the clipping rectangle.
	 * @return     a new graphics context.
	 */
	public Graphics create(float x, float y, float width, float height);

	/**
	 * Gets this graphics context's current color.
	 * @return    this graphics context's current color.
	 */
	public Color getColor();

	/**
	 * Sets this graphics context's current color to the specified 
	 * color. All subsequent graphics operations using this graphics 
	 * context use this specified color. 
	 * @param     c   the new rendering color.
	 */
	public void setColor(Color c);

	/**
	 * Sets the paint mode of this graphics context to overwrite the 
	 * destination with this graphics context's current color. 
	 * This sets the logical pixel operation function to the paint or
	 * overwrite mode.  All subsequent rendering operations will
	 * overwrite the destination with the current color. 
	 */
	public void setPaintMode();

	/**
	 * Sets the paint mode of this graphics context to alternate between 
	 * this graphics context's current color and the new specified color. 
	 * This specifies that logical pixel operations are performed in the 
	 * XOR mode, which alternates pixels between the current color and 
	 * a specified XOR color. 
	 * <p>
	 * When drawing operations are performed, pixels which are the 
	 * current color are changed to the specified color, and vice versa. 
	 * <p>
	 * Pixels that are of colors other than those two colors are changed 
	 * in an unpredictable but reversible manner; if the same figure is 
	 * drawn twice, then all pixels are restored to their original values. 
	 * @param     c1 the XOR alternation color
	 */
	public void setXORMode(Color c1);

	/**
	 * Gets the current font.
	 * @return    this graphics context's current font.
	 */
	public Font getFont();

	/**
	 * Sets this graphics context's font to the specified font. 
	 * All subsequent text operations using this graphics context 
	 * use this font. A null argument is silently ignored.
	 * @param  font   the font.
	 */
	public void setFont(Font font);

	/**
	 * Gets the font metrics of the current font.
	 * @return    the font metrics of this graphics 
	 *                    context's current font.
	 */
	public FontMetrics getFontMetrics();

	/**
	 * Gets the font metrics for the specified font.
	 * @return    the font metrics for the specified font.
	 * @param     f the specified font
	 */
	public FontMetrics getFontMetrics(Font f);


	/** 
	 * Intersects the current clip with the specified rectangle.
	 * The resulting clipping area is the intersection of the current
	 * clipping area and the specified rectangle.  If there is no 
	 * current clipping area, either because the clip has never been 
	 * set, or the clip has been cleared using <code>setClip(null)</code>, 
	 * the specified rectangle becomes the new clip.
	 * This method sets the user clip, which is independent of the
	 * clipping associated with device bounds and window visibility.  
	 * This method can only be used to make the current clip smaller.
	 * To set the current clip larger, use any of the setClip methods.
	 * Rendering operations have no effect outside of the clipping area.
	 * @param x the x coordinate of the rectangle to intersect the clip with
	 * @param y the y coordinate of the rectangle to intersect the clip with
	 * @param width the width of the rectangle to intersect the clip with
	 * @param height the height of the rectangle to intersect the clip with
	 */
	public void clipRect(float x, float y, float width, float height);

	/**
	 * Sets the current clip to the rectangle specified by the given
	 * coordinates.  This method sets the user clip, which is 
	 * independent of the clipping associated with device bounds
	 * and window visibility.  
	 * Rendering operations have no effect outside of the clipping area.
	 * @param       x the <i>x</i> coordinate of the new clip rectangle.
	 * @param       y the <i>y</i> coordinate of the new clip rectangle.
	 * @param       width the width of the new clip rectangle.
	 * @param       height the height of the new clip rectangle.
	 */
	public void setClip(float x, float y, float width, float height);

	/**
	 * Gets the current clipping area.
	 * This method returns the user clip, which is independent of the
	 * clipping associated with device bounds and window visibility.
	 * If no clip has previously been set, or if the clip has been 
	 * cleared using <code>setClip(null)</code>, this method returns 
	 * <code>null</code>.
	 * @return      a <code>Shape</code> object representing the 
	 *              current clipping area, or <code>null</code> if
	 *              no clip is set.
	 */
	public Shape getClip();

	/**
	 * Sets the current clipping area to an arbitrary clip shape.
	 * Not all objects that implement the <code>Shape</code> 
	 * interface can be used to set the clip.  The only 
	 * <code>Shape</code> objects that are guaranteed to be 
	 * supported are <code>Shape</code> objects that are
	 * obtained via the <code>getClip</code> method and via 
	 * <code>Rectangle</code> objects.  This method sets the
	 * user clip, which is independent of the clipping associated
	 * with device bounds and window visibility.
	 * @param clip the <code>Shape</code> to use to set the clip
	 */
	public void setClip(Shape clip);

	/**
	 * Copies an area of the component by a distance specified by 
	 * <code>dx</code> and <code>dy</code>. From the point specified
	 * by <code>x</code> and <code>y</code>, this method
	 * copies downwards and to the right.  To copy an area of the 
	 * component to the left or upwards, specify a negative value for 
	 * <code>dx</code> or <code>dy</code>.
	 * If a portion of the source rectangle lies outside the bounds 
	 * of the component, or is obscured by another window or component, 
	 * <code>copyArea</code> will be unable to copy the associated
	 * pixels. The area that is omitted can be refreshed by calling 
	 * the component's <code>paint</code> method.
	 * @param       x the <i>x</i> coordinate of the source rectangle.
	 * @param       y the <i>y</i> coordinate of the source rectangle.
	 * @param       width the width of the source rectangle.
	 * @param       height the height of the source rectangle.
	 * @param       dx the horizontal distance to copy the pixels.
	 * @param       dy the vertical distance to copy the pixels.
	 */
	public void copyArea(float x, float y, float width, float height, float dx, float dy);

	/** 
	 * Draws a line, using the current color, between the points 
	 * <code>(x1,&nbsp;y1)</code> and <code>(x2,&nbsp;y2)</code> 
	 * in this graphics context's coordinate system. 
	 * @param   x1  the first point's <i>x</i> coordinate.
	 * @param   y1  the first point's <i>y</i> coordinate.
	 * @param   x2  the second point's <i>x</i> coordinate.
	 * @param   y2  the second point's <i>y</i> coordinate.
	 */
	public void drawLine(float x1, float y1, float x2, float y2);

	/** 
	 * Fills the specified rectangle. 
	 * The left and right edges of the rectangle are at 
	 * <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>. 
	 * The top and bottom edges are at 
	 * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>. 
	 * The resulting rectangle covers an area 
	 * <code>width</code> pixels wide by 
	 * <code>height</code> pixels tall.
	 * The rectangle is filled using the graphics context's current color. 
	 * @param         x   the <i>x</i> coordinate 
	 *                         of the rectangle to be filled.
	 * @param         y   the <i>y</i> coordinate 
	 *                         of the rectangle to be filled.
	 * @param         width   the width of the rectangle to be filled.
	 * @param         height   the height of the rectangle to be filled.
	 */
	public void fillRect(float x, float y, float width, float height);

	/** 
	 * Draws the outline of the specified rectangle. 
	 * The left and right edges of the rectangle are at 
	 * <code>x</code> and <code>x&nbsp;+&nbsp;width</code>. 
	 * The top and bottom edges are at 
	 * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>. 
	 * The rectangle is drawn using the graphics context's current color.
	 * @param         x   the <i>x</i> coordinate 
	 *                         of the rectangle to be drawn.
	 * @param         y   the <i>y</i> coordinate 
	 *                         of the rectangle to be drawn.
	 * @param         width   the width of the rectangle to be drawn.
	 * @param         height   the height of the rectangle to be drawn.
	 */
	public void drawRect(float x, float y, float width, float height);

	/** 
	 * Clears the specified rectangle by filling it with the background
	 * color of the current drawing surface. This operation does not 
	 * use the current paint mode. 
	 * <p>
	 * Beginning with Java&nbsp;1.1, the background color 
	 * of offscreen images may be system dependent. Applications should 
	 * use <code>setColor</code> followed by <code>fillRect</code> to 
	 * ensure that an offscreen image is cleared to a specific color. 
	 * @param       x the <i>x</i> coordinate of the rectangle to clear.
	 * @param       y the <i>y</i> coordinate of the rectangle to clear.
	 * @param       width the width of the rectangle to clear.
	 * @param       height the height of the rectangle to clear.
	 */
	public void clearRect(float x, float y, float width, float height);

	/** 
	 * Draws an outlined round-cornered rectangle using this graphics 
	 * context's current color. The left and right edges of the rectangle 
	 * are at <code>x</code> and <code>x&nbsp;+&nbsp;width</code>, 
	 * respectively. The top and bottom edges of the rectangle are at 
	 * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>. 
	 * @param      x the <i>x</i> coordinate of the rectangle to be drawn.
	 * @param      y the <i>y</i> coordinate of the rectangle to be drawn.
	 * @param      width the width of the rectangle to be drawn.
	 * @param      height the height of the rectangle to be drawn.
	 * @param      arcWidth the horizontal diameter of the arc 
	 *                    at the four corners.
	 * @param      arcHeight the vertical diameter of the arc 
	 *                    at the four corners.
	 */
	public void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

	/** 
	 * Fills the specified rounded corner rectangle with the current color.
	 * The left and right edges of the rectangle 
	 * are at <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>, 
	 * respectively. The top and bottom edges of the rectangle are at 
	 * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>. 
	 * @param       x the <i>x</i> coordinate of the rectangle to be filled.
	 * @param       y the <i>y</i> coordinate of the rectangle to be filled.
	 * @param       width the width of the rectangle to be filled.
	 * @param       height the height of the rectangle to be filled.
	 * @param       arcWidth the horizontal diameter 
	 *                     of the arc at the four corners.
	 * @param       arcHeight the vertical diameter 
	 *                     of the arc at the four corners.
	 */
	public void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

	/**
	 * Draws a 3-D highlighted outline of the specified rectangle.
	 * The edges of the rectangle are highlighted so that they
	 * appear to be beveled and lit from the upper left corner.
	 * <p>
	 * The colors used for the highlighting effect are determined 
	 * based on the current color.
	 * The resulting rectangle covers an area that is 
	 * <code>width&nbsp;+&nbsp;1</code> pixels wide
	 * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
	 * @param       x the <i>x</i> coordinate of the rectangle to be drawn.
	 * @param       y the <i>y</i> coordinate of the rectangle to be drawn.
	 * @param       width the width of the rectangle to be drawn.
	 * @param       height the height of the rectangle to be drawn.
	 * @param       raised a boolean that determines whether the rectangle
	 *                      appears to be raised above the surface 
	 *                      or sunk into the surface.
	 */
	public void draw3DRect(float x, float y, float width, float height, boolean raised);    

	/**
	 * Paints a 3-D highlighted rectangle filled with the current color.
	 * The edges of the rectangle will be highlighted so that it appears
	 * as if the edges were beveled and lit from the upper left corner.
	 * The colors used for the highlighting effect will be determined from
	 * the current color.
	 * @param       x the <i>x</i> coordinate of the rectangle to be filled.
	 * @param       y the <i>y</i> coordinate of the rectangle to be filled.
	 * @param       width the width of the rectangle to be filled.
	 * @param       height the height of the rectangle to be filled.
	 * @param       raised a boolean value that determines whether the 
	 *                      rectangle appears to be raised above the surface 
	 *                      or etched into the surface.
	 */
	public void fill3DRect(float x, float y, float width, float height, boolean raised);    

	/** 
	 * Draws the outline of an oval.
	 * The result is a circle or ellipse that fits within the 
	 * rectangle specified by the <code>x</code>, <code>y</code>, 
	 * <code>width</code>, and <code>height</code> arguments. 
	 * <p> 
	 * The oval covers an area that is 
	 * <code>width&nbsp;+&nbsp;1</code> pixels wide 
	 * and <code>height&nbsp;+&nbsp;1</code> pixels tall. 
	 * @param       x the <i>x</i> coordinate of the upper left 
	 *                     corner of the oval to be drawn.
	 * @param       y the <i>y</i> coordinate of the upper left 
	 *                     corner of the oval to be drawn.
	 * @param       width the width of the oval to be drawn.
	 * @param       height the height of the oval to be drawn.
	 */
	public void drawOval(float x, float y, float width, float height);

	/** 
	 * Fills an oval bounded by the specified rectangle with the
	 * current color.
	 * @param       x the <i>x</i> coordinate of the upper left corner 
	 *                     of the oval to be filled.
	 * @param       y the <i>y</i> coordinate of the upper left corner 
	 *                     of the oval to be filled.
	 * @param       width the width of the oval to be filled.
	 * @param       height the height of the oval to be filled.
	 */
	public void fillOval(float x, float y, float width, float height);

	/**
	 * Draws the outline of a circular or elliptical arc 
	 * covering the specified rectangle.
	 * <p>
	 * The resulting arc begins at <code>startAngle</code> and extends  
	 * for <code>arcAngle</code> degrees, using the current color.
	 * Angles are interpreted such that 0&nbsp;degrees 
	 * is at the 3&nbsp;o'clock position. 
	 * A positive value indicates a counter-clockwise rotation
	 * while a negative value indicates a clockwise rotation.
	 * <p>
	 * The center of the arc is the center of the rectangle whose origin 
	 * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the 
	 * <code>width</code> and <code>height</code> arguments. 
	 * <p>
	 * The resulting arc covers an area 
	 * <code>width&nbsp;+&nbsp;1</code> pixels wide
	 * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
	 * <p>
	 * The angles are specified relative to the non-square extents of
	 * the bounding rectangle such that 45 degrees always falls on the
	 * line from the center of the ellipse to the upper right corner of
	 * the bounding rectangle. As a result, if the bounding rectangle is
	 * noticeably longer in one axis than the other, the angles to the
	 * start and end of the arc segment will be skewed farther along the
	 * longer axis of the bounds.
	 * @param        x the <i>x</i> coordinate of the 
	 *                    upper-left corner of the arc to be drawn.
	 * @param        y the <i>y</i>  coordinate of the 
	 *                    upper-left corner of the arc to be drawn.
	 * @param        width the width of the arc to be drawn.
	 * @param        height the height of the arc to be drawn.
	 * @param        startAngle the beginning angle.
	 * @param        arcAngle the angular extent of the arc, 
	 *                    relative to the start angle.
	 */
	public void drawArc(float x, float y, float width, float height, float startAngle, float arcAngle);

	/** 
	 * Fills a circular or elliptical arc covering the specified rectangle.
	 * <p>
	 * The resulting arc begins at <code>startAngle</code> and extends  
	 * for <code>arcAngle</code> degrees.
	 * Angles are interpreted such that 0&nbsp;degrees 
	 * is at the 3&nbsp;o'clock position. 
	 * A positive value indicates a counter-clockwise rotation
	 * while a negative value indicates a clockwise rotation.
	 * <p>
	 * The center of the arc is the center of the rectangle whose origin 
	 * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the 
	 * <code>width</code> and <code>height</code> arguments. 
	 * <p>
	 * The resulting arc covers an area 
	 * <code>width&nbsp;+&nbsp;1</code> pixels wide
	 * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
	 * <p>
	 * The angles are specified relative to the non-square extents of
	 * the bounding rectangle such that 45 degrees always falls on the
	 * line from the center of the ellipse to the upper right corner of
	 * the bounding rectangle. As a result, if the bounding rectangle is
	 * noticeably longer in one axis than the other, the angles to the
	 * start and end of the arc segment will be skewed farther along the
	 * longer axis of the bounds.
	 * @param        x the <i>x</i> coordinate of the 
	 *                    upper-left corner of the arc to be filled.
	 * @param        y the <i>y</i>  coordinate of the 
	 *                    upper-left corner of the arc to be filled.
	 * @param        width the width of the arc to be filled.
	 * @param        height the height of the arc to be filled.
	 * @param        startAngle the beginning angle.
	 * @param        arcAngle the angular extent of the arc, 
	 *                    relative to the start angle.
	 */
	public void fillArc(float x, float y, float width, float height, float startAngle, float arcAngle);

	/** 
	 * Draws a sequence of connected lines defined by 
	 * arrays of <i>x</i> and <i>y</i> coordinates. 
	 * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
	 * The figure is not closed if the first point 
	 * differs from the last point.
	 * @param       xPoints an array of <i>x</i> points
	 * @param       yPoints an array of <i>y</i> points
	 * @param       nPoints the total number of points
	 */
	public void drawPolyline( float[] xPoints, float[] yPoints, int nPoints);

	/** 
	 * Draws a closed polygon defined by 
	 * arrays of <i>x</i> and <i>y</i> coordinates. 
	 * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
	 * <p>
	 * This method draws the polygon defined by <code>nPoint</code> line 
	 * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code> 
	 * line segments are line segments from 
	 * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code> 
	 * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for 
	 * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.  
	 * The figure is automatically closed by drawing a line connecting
	 * the final point to the first point, if those points are different.
	 * @param        xPoints   a an array of <code>x</code> coordinates.
	 * @param        yPoints   a an array of <code>y</code> coordinates.
	 * @param        nPoints   a the total number of points.
	 */
	public void drawPolygon( float[] xPoints, float[] yPoints, int nPoints);
	
	/** 
	 * Fills a closed polygon defined by 
	 * arrays of <i>x</i> and <i>y</i> coordinates. 
	 * <p>
	 * This method draws the polygon defined by <code>nPoint</code> line 
	 * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code> 
	 * line segments are line segments from 
	 * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code> 
	 * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for 
	 * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.  
	 * The figure is automatically closed by drawing a line connecting
	 * the final point to the first point, if those points are different.
	 * <p>
	 * The area inside the polygon is defined using an 
	 * even-odd fill rule, also known as the alternating rule.
	 * @param        xPoints   a an array of <code>x</code> coordinates.
	 * @param        yPoints   a an array of <code>y</code> coordinates.
	 * @param        nPoints   a the total number of points.
	 */
	public void fillPolygon( float[] xPoints, float[] yPoints, int nPoints);

	/** 
	 * Draws the text given by the specified character array, using this 
	 * graphics context's current font and color. The baseline of the 
	 * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this 
	 * graphics context's coordinate system. 
	 * @param data the array of characters to be drawn
	 * @param offset the start offset in the data
	 * @param length the number of characters to be drawn
	 * @param x the <i>x</i> coordinate of the baseline of the text
	 * @param y the <i>y</i> coordinate of the baseline of the text
	 * @throws NullPointerException if <code>data</code> is <code>null</code>.
	 * @throws IndexOutOfBoundsException if <code>offset</code> or
	 * <code>length</code>is less than zero, or 
	 * <code>offset+length</code> is greater than the length of the
	 * <code>data</code> array.
	 */
	public void drawChars(char data[], int offset, int length, float x, float y);

	/** 
	 * Draws the text given by the specified byte array, using this 
	 * graphics context's current font and color. The baseline of the 
	 * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this 
	 * graphics context's coordinate system.
	 * <p>
	 * Use of this method is not recommended as each byte is interpreted
	 * as a Unicode code point in the range 0 to 255, and so can only be
	 * used to draw Latin characters in that range.
	 * @param data the data to be drawn
	 * @param offset the start offset in the data
	 * @param length the number of bytes that are drawn
	 * @param x the <i>x</i> coordinate of the baseline of the text
	 * @param y the <i>y</i> coordinate of the baseline of the text
	 * @throws NullPointerException if <code>data</code> is <code>null</code>.
	 * @throws IndexOutOfBoundsException if <code>offset</code> or
	 * <code>length</code>is less than zero, or <code>offset+length</code> 
	 * is greater than the length of the <code>data</code> array.
	 */
	public void drawBytes(byte data[], int offset, int length, float x, float y);

	/** 
	 * Draws as much of the specified image as is currently available.
	 * The image is drawn with its top-left corner at 
	 * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate 
	 * space. Transparent pixels in the image do not affect whatever 
	 * pixels are already there. 
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * complete image has not yet been loaded, and it has not been dithered 
	 * and converted for the current output device.
	 * <p>
	 * If the image has completely loaded and its pixels are
	 * no longer being changed, then
	 * <code>drawImage</code> returns <code>true</code>. 
	 * Otherwise, <code>drawImage</code> returns <code>false</code>
	 * and as more of
	 * the image becomes available 
	 * or it is time to draw another frame of animation,
	 * the process that loads the image notifies 
	 * the specified image observer.
	 * @param    img the specified image to be drawn. This method does
	 *               nothing if <code>img</code> is null.
	 * @param    x   the <i>x</i> coordinate.
	 * @param    y   the <i>y</i> coordinate.
	 * @param    observer    object to be notified as more of 
	 *                          the image is converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float x, float y, ImageObserver observer);

	/**
	 * Draws as much of the specified image as has already been scaled
	 * to fit inside the specified rectangle.
	 * <p>
	 * The image is drawn inside the specified rectangle of this 
	 * graphics context's coordinate space, and is scaled if 
	 * necessary. Transparent pixels do not affect whatever pixels
	 * are already there. 
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * entire image has not yet been scaled, dithered, and converted
	 * for the current output device.
	 * If the current output representation is not yet complete, then
	 * <code>drawImage</code> returns <code>false</code>. As more of
	 * the image becomes available, the process that loads the image notifies
	 * the image observer by calling its <code>imageUpdate</code> method.
	 * <p>
	 * A scaled version of an image will not necessarily be
	 * available immediately just because an unscaled version of the
	 * image has been constructed for this output device.  Each size of
	 * the image may be cached separately and generated from the original
	 * data in a separate image production sequence.
	 * @param    img    the specified image to be drawn. This method does
	 *                  nothing if <code>img</code> is null.
	 * @param    x      the <i>x</i> coordinate.
	 * @param    y      the <i>y</i> coordinate.
	 * @param    width  the width of the rectangle.
	 * @param    height the height of the rectangle.
	 * @param    observer    object to be notified as more of 
	 *                          the image is converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float x, float y, float width, float height, ImageObserver observer);

	/** 
	 * Draws as much of the specified image as is currently available.
	 * The image is drawn with its top-left corner at 
	 * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate 
	 * space.  Transparent pixels are drawn in the specified
	 * background color.
	 * <p> 
	 * This operation is equivalent to filling a rectangle of the
	 * width and height of the specified image with the given color and then
	 * drawing the image on top of it, but possibly more efficient.
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * complete image has not yet been loaded, and it has not been dithered 
	 * and converted for the current output device.
	 * <p>
	 * If the image has completely loaded and its pixels are
	 * no longer being changed, then
	 * <code>drawImage</code> returns <code>true</code>. 
	 * Otherwise, <code>drawImage</code> returns <code>false</code>
	 * and as more of
	 * the image becomes available 
	 * or it is time to draw another frame of animation,
	 * the process that loads the image notifies 
	 * the specified image observer.
	 * @param    img the specified image to be drawn. This method does
	 *               nothing if <code>img</code> is null.
	 * @param    x      the <i>x</i> coordinate.
	 * @param    y      the <i>y</i> coordinate.
	 * @param    bgcolor the background color to paint under the
	 *                         non-opaque portions of the image.
	 * @param    observer    object to be notified as more of 
	 *                          the image is converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float x, float y, Color bgcolor, ImageObserver observer);

	/**
	 * Draws as much of the specified image as has already been scaled
	 * to fit inside the specified rectangle.
	 * <p>
	 * The image is drawn inside the specified rectangle of this 
	 * graphics context's coordinate space, and is scaled if 
	 * necessary. Transparent pixels are drawn in the specified
	 * background color. 
	 * This operation is equivalent to filling a rectangle of the
	 * width and height of the specified image with the given color and then
	 * drawing the image on top of it, but possibly more efficient.
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * entire image has not yet been scaled, dithered, and converted
	 * for the current output device.
	 * If the current output representation is not yet complete then
	 * <code>drawImage</code> returns <code>false</code>. As more of
	 * the image becomes available, the process that loads the image notifies 
	 * the specified image observer.
	 * <p>
	 * A scaled version of an image will not necessarily be
	 * available immediately just because an unscaled version of the
	 * image has been constructed for this output device.  Each size of
	 * the image may be cached separately and generated from the original
	 * data in a separate image production sequence.
	 * @param    img       the specified image to be drawn. This method does
	 *                     nothing if <code>img</code> is null.
	 * @param    x         the <i>x</i> coordinate.
	 * @param    y         the <i>y</i> coordinate.
	 * @param    width     the width of the rectangle.
	 * @param    height    the height of the rectangle.
	 * @param    bgcolor   the background color to paint under the
	 *                         non-opaque portions of the image.
	 * @param    observer    object to be notified as more of 
	 *                          the image is converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float x, float y, float width, float height, Color bgcolor, ImageObserver observer);

	/**
	 * Draws as much of the specified area of the specified image as is
	 * currently available, scaling it on the fly to fit inside the
	 * specified area of the destination drawable surface. Transparent pixels 
	 * do not affect whatever pixels are already there.
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * image area to be drawn has not yet been scaled, dithered, and converted
	 * for the current output device.
	 * If the current output representation is not yet complete then
	 * <code>drawImage</code> returns <code>false</code>. As more of
	 * the image becomes available, the process that loads the image notifies 
	 * the specified image observer.
	 * <p>
	 * This method always uses the unscaled version of the image
	 * to render the scaled rectangle and performs the required
	 * scaling on the fly. It does not use a cached, scaled version
	 * of the image for this operation. Scaling of the image from source
	 * to destination is performed such that the first coordinate
	 * of the source rectangle is mapped to the first coordinate of
	 * the destination rectangle, and the second source coordinate is
	 * mapped to the second destination coordinate. The subimage is
	 * scaled and flipped as needed to preserve those mappings.
	 * @param       img the specified image to be drawn. This method does
	 *                  nothing if <code>img</code> is null.
	 * @param       dx1 the <i>x</i> coordinate of the first corner of the
	 *                    destination rectangle.
	 * @param       dy1 the <i>y</i> coordinate of the first corner of the
	 *                    destination rectangle.
	 * @param       dx2 the <i>x</i> coordinate of the second corner of the
	 *                    destination rectangle.
	 * @param       dy2 the <i>y</i> coordinate of the second corner of the
	 *                    destination rectangle.
	 * @param       sx1 the <i>x</i> coordinate of the first corner of the
	 *                    source rectangle.
	 * @param       sy1 the <i>y</i> coordinate of the first corner of the
	 *                    source rectangle.
	 * @param       sx2 the <i>x</i> coordinate of the second corner of the
	 *                    source rectangle.
	 * @param       sy2 the <i>y</i> coordinate of the second corner of the
	 *                    source rectangle.
	 * @param       observer object to be notified as more of the image is
	 *                    scaled and converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float dx1, float dy1, float dx2, float dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer);

	/**
	 * Draws as much of the specified area of the specified image as is
	 * currently available, scaling it on the fly to fit inside the
	 * specified area of the destination drawable surface. 
	 * <p>
	 * Transparent pixels are drawn in the specified background color. 
	 * This operation is equivalent to filling a rectangle of the
	 * width and height of the specified image with the given color and then
	 * drawing the image on top of it, but possibly more efficient.
	 * <p>
	 * This method returns immediately in all cases, even if the
	 * image area to be drawn has not yet been scaled, dithered, and converted
	 * for the current output device.
	 * If the current output representation is not yet complete then
	 * <code>drawImage</code> returns <code>false</code>. As more of
	 * the image becomes available, the process that loads the image notifies 
	 * the specified image observer.
	 * <p>
	 * This method always uses the unscaled version of the image
	 * to render the scaled rectangle and performs the required
	 * scaling on the fly. It does not use a cached, scaled version
	 * of the image for this operation. Scaling of the image from source
	 * to destination is performed such that the first coordinate
	 * of the source rectangle is mapped to the first coordinate of
	 * the destination rectangle, and the second source coordinate is
	 * mapped to the second destination coordinate. The subimage is
	 * scaled and flipped as needed to preserve those mappings.
	 * @param       img the specified image to be drawn. This method does
	 *                  nothing if <code>img</code> is null.
	 * @param       dx1 the <i>x</i> coordinate of the first corner of the
	 *                    destination rectangle.
	 * @param       dy1 the <i>y</i> coordinate of the first corner of the
	 *                    destination rectangle.
	 * @param       dx2 the <i>x</i> coordinate of the second corner of the
	 *                    destination rectangle.
	 * @param       dy2 the <i>y</i> coordinate of the second corner of the
	 *                    destination rectangle.
	 * @param       sx1 the <i>x</i> coordinate of the first corner of the
	 *                    source rectangle.
	 * @param       sy1 the <i>y</i> coordinate of the first corner of the
	 *                    source rectangle.
	 * @param       sx2 the <i>x</i> coordinate of the second corner of the
	 *                    source rectangle.
	 * @param       sy2 the <i>y</i> coordinate of the second corner of the
	 *                    source rectangle.
	 * @param       bgcolor the background color to paint under the
	 *                    non-opaque portions of the image.
	 * @param       observer object to be notified as more of the image is
	 *                    scaled and converted.
	 * @return   <code>false</code> if the image pixels are still changing;
	 *           <code>true</code> otherwise.
	 */
	public boolean drawImage(Image img, float dx1, float dy1, float dx2, float dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer);

	/**
	 * Disposes of this graphics context and releases 
	 * any system resources that it is using. 
	 * A <code>Graphics</code> object cannot be used after 
	 * <code>dispose</code>has been called.
	 * <p>
	 * When a Java program runs, a large number of <code>Graphics</code>
	 * objects can be created within a short time frame.
	 * Although the finalization process of the garbage collector 
	 * also disposes of the same system resources, it is preferable 
	 * to manually free the associated resources by calling this
	 * method rather than to rely on a finalization process which 
	 * may not run to completion for a long period of time.
	 * <p>
	 * Graphics objects which are provided as arguments to the 
	 * <code>paint</code> and <code>update</code> methods 
	 * of components are automatically released by the system when 
	 * those methods return. For efficiency, programmers should
	 * call <code>dispose</code> when finished using
	 * a <code>Graphics</code> object only if it was created 
	 * directly from a component or another <code>Graphics</code> object.
	 */
	public void dispose();

	/**
	 * Returns true if the specified rectangular area might intersect 
	 * the current clipping area.
	 * The coordinates of the specified rectangular area are in the
	 * user coordinate space and are relative to the coordinate
	 * system origin of this graphics context.
	 * This method may use an algorithm that calculates a result quickly
	 * but which sometimes might return true even if the specified
	 * rectangular area does not intersect the clipping area.
	 * The specific algorithm employed may thus trade off accuracy for
	 * speed, but it will never return false unless it can guarantee
	 * that the specified rectangular area does not intersect the
	 * current clipping area.
	 * The clipping area used by this method can represent the
	 * intersection of the user clip as specified through the clip
	 * methods of this graphics context as well as the clipping
	 * associated with the device or image bounds and window visibility.
	 *
	 * @param x the x coordinate of the rectangle to test against the clip
	 * @param y the y coordinate of the rectangle to test against the clip
	 * @param width the width of the rectangle to test against the clip
	 * @param height the height of the rectangle to test against the clip
	 * @return <code>true</code> if the specified rectangle intersects
	 *         the bounds of the current clip; <code>false</code>
	 *         otherwise.
	 */
	public boolean hitClip(float x, float y, float width, float height);

	////////////////////////////////////////////////////////////
	// Graphics API inherited from java.awt.Graphics2D

	/**
	 * Strokes the outline of a <code>Shape</code> using the settings of the 
	 * current <code>Graphics2D</code> context.  The rendering attributes
	 * applied include the <code>Clip</code>, <code>Transform</code>,
	 * <code>Paint</code>, <code>Composite</code> and 
	 * <code>Stroke</code> attributes.
	 * @param s the <code>Shape</code> to be rendered
	 */
	public void draw(Shape s);

	/**
	 * Strokes the outline of a path using the settings of the 
	 * current <code>Graphics2D</code> context.  The rendering attributes
	 * applied include the <code>Clip</code>, <code>Transform</code>,
	 * <code>Paint</code>, <code>Composite</code> and 
	 * <code>Stroke</code> attributes.
	 * @param s the path to be rendered
	 */
	public void draw(PathIterator s);

	/**
	 * Renders an image, applying a transform from image space into user space
	 * before drawing.
	 * The transformation from user space into device space is done with
	 * the current <code>Transform</code> in the <code>Graphics2D</code>.
	 * The specified transformation is applied to the image before the
	 * transform attribute in the <code>Graphics2D</code> context is applied.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>, and <code>Composite</code> attributes. 
	 * Note that no rendering is done if the specified transform is 
	 * noninvertible.
	 * @param img the specified image to be rendered. 
	 *            This method does nothing if <code>img</code> is null.
	 * @param xform the transformation from image space into user space
	 * @param obs the {@link ImageObserver}
	 * to be notified as more of the <code>Image</code>
	 * is converted
	 * @return <code>true</code> if the <code>Image</code> is 
	 * fully loaded and completely rendered, or if it's null;
	 * <code>false</code> if the <code>Image</code> is still being loaded.
	 */
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs);

	/**
	 * Renders a <code>BufferedImage</code> that is
	 * filtered with a 
	 * {@link BufferedImageOp}.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>
	 * and <code>Composite</code> attributes.  This is equivalent to:
	 * <pre>
	 * img1 = op.filter(img, null);
	 * drawImage(img1, new AffineTransform(1f,0f,0f,1f,x,y), null);
	 * </pre>
	 * @param op the filter to be applied to the image before rendering
	 * @param img the specified <code>BufferedImage</code> to be rendered. 
	 *            This method does nothing if <code>img</code> is null.
	 * @param x the x coordinate of the location in user space where
	 * the upper left corner of the image is rendered
	 * @param y the y coordinate of the location in user space where
	 * the upper left corner of the image is rendered
	 */
	public void drawImage(BufferedImage img, BufferedImageOp op, float x, float y);

	/**
	 * Renders a {@link RenderedImage},
	 * applying a transform from image 
	 * space into user space before drawing.
	 * The transformation from user space into device space is done with
	 * the current <code>Transform</code> in the <code>Graphics2D</code>.
	 * The specified transformation is applied to the image before the
	 * transform attribute in the <code>Graphics2D</code> context is applied.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>, and <code>Composite</code> attributes. Note
	 * that no rendering is done if the specified transform is
	 * noninvertible.
	 * @param img the image to be rendered. This method does
	 *            nothing if <code>img</code> is null.
	 * @param xform the transformation from image space into user space
	 */
	public void drawRenderedImage(RenderedImage img, AffineTransform xform);

	/**
	 * Renders a 
	 * {@link RenderableImage},
	 * applying a transform from image space into user space before drawing.
	 * The transformation from user space into device space is done with
	 * the current <code>Transform</code> in the <code>Graphics2D</code>.
	 * The specified transformation is applied to the image before the
	 * transform attribute in the <code>Graphics2D</code> context is applied.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>, and <code>Composite</code> attributes. Note
	 * that no rendering is done if the specified transform is
	 * noninvertible.
	 *<p> 
	 * Rendering hints set on the <code>Graphics2D</code> object might
	 * be used in rendering the <code>RenderableImage</code>.  
	 * If explicit control is required over specific hints recognized by a 
	 * specific <code>RenderableImage</code>, or if knowledge of which hints 
	 * are used is required, then a <code>RenderedImage</code> should be   
	 * obtained directly from the <code>RenderableImage</code>
	 * and rendered using 
	 *{@link #drawRenderedImage(RenderedImage, AffineTransform) drawRenderedImage}.
	 * @param img the image to be rendered. This method does
	 *            nothing if <code>img</code> is null.
	 * @param xform the transformation from image space into user space
	 */
	public void drawRenderableImage(RenderableImage img, AffineTransform xform);

	/**
	 * Renders the text specified by the specified <code>String</code>, 
	 * using the current text attribute state in the <code>Graphics2D</code> context. 
	 * The baseline of the first character is at position 
	 * (<i>x</i>,&nbsp;<i>y</i>) in the User Space.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>, <code>Paint</code>, <code>Font</code> and
	 * <code>Composite</code> attributes. For characters in script systems 
	 * such as Hebrew and Arabic, the glyphs can be rendered from right to
	 * left, in which case the coordinate supplied is the location of the
	 * leftmost character on the baseline.
	 * @param str the <code>String</code> to be rendered
	 * @param x the x coordinate of the location where the
	 * <code>String</code> should be rendered
	 * @param y the y coordinate of the location where the
	 * <code>String</code> should be rendered
	 * @throws NullPointerException if <code>str</code> is
	 *         <code>null</code>
	 */
	public void drawString(String str, float x, float y);

	/**
	 * Renders the text of the specified iterator applying its attributes
	 * in accordance with the specification of the {@link TextAttribute} class.
	 * <p>
	 * The baseline of the first character is at position
	 * (<i>x</i>,&nbsp;<i>y</i>) in User Space.
	 * For characters in script systems such as Hebrew and Arabic,
	 * the glyphs can be rendered from right to left, in which case the 
	 * coordinate supplied is the location of the leftmost character
	 * on the baseline.
	 * @param iterator the iterator whose text is to be rendered
	 * @param x the x coordinate where the iterator's text is to be
	 * rendered
	 * @param y the y coordinate where the iterator's text is to be
	 * rendered
	 * @throws NullPointerException if <code>iterator</code> is
	 *         <code>null</code>
	 */
	public void drawString(AttributedCharacterIterator iterator, float x, float y);

	/**
	 * Renders the text of the specified 
	 * {@link GlyphVector} using
	 * the <code>Graphics2D</code> context's rendering attributes.
	 * The rendering attributes applied include the <code>Clip</code>,
	 * <code>Transform</code>, <code>Paint</code>, and
	 * <code>Composite</code> attributes.  The <code>GlyphVector</code>
	 * specifies individual glyphs from a {@link Font}.
	 * The <code>GlyphVector</code> can also contain the glyph positions.  
	 * This is the fastest way to render a set of characters to the
	 * screen.
	 * @param g the <code>GlyphVector</code> to be rendered
	 * @param x the x position in User Space where the glyphs should
	 * be rendered
	 * @param y the y position in User Space where the glyphs should
	 * be rendered
	 * @throws NullPointerException if <code>g</code> is <code>null</code>.
	 */
	public void drawGlyphVector(GlyphVector g, float x, float y);

	/**
	 * Fills the interior of a <code>Shape</code> using the settings of the   
	 * <code>Graphics2D</code> context. The rendering attributes applied 
	 * include the <code>Clip</code>, <code>Transform</code>,
	 * <code>Paint</code>, and <code>Composite</code>.
	 * @param s the <code>Shape</code> to be filled
	 */
	public void fill(Shape s);

	/**
	 * Fills the interior of a path using the settings of the   
	 * <code>Graphics2D</code> context. The rendering attributes applied 
	 * include the <code>Clip</code>, <code>Transform</code>,
	 * <code>Paint</code>, and <code>Composite</code>.
	 * @param s the <code>path</code> to be filled
	 */
	public void fill(PathIterator s);

	/**
	 * Checks whether or not the specified <code>Shape</code> intersects 
	 * the specified {@link Rectangle}, which is in device
	 * space. If <code>onStroke</code> is false, this method checks
	 * whether or not the interior of the specified <code>Shape</code>
	 * intersects the specified <code>Rectangle</code>.  If 
	 * <code>onStroke</code> is <code>true</code>, this method checks
	 * whether or not the <code>Stroke</code> of the specified 
	 * <code>Shape</code> outline intersects the specified
	 * <code>Rectangle</code>.
	 * The rendering attributes taken into account include the
	 * <code>Clip</code>, <code>Transform</code>, and <code>Stroke</code> 
	 * attributes.
	 * @param rect the area in device space to check for a hit
	 * @param s the <code>Shape</code> to check for a hit
	 * @param onStroke flag used to choose between testing the 
	 * stroked or the filled shape.  If the flag is <code>true</code>, the 
	 * <code>Stroke</code> oultine is tested.  If the flag is 
	 * <code>false</code>, the filled <code>Shape</code> is tested.
	 * @return <code>true</code> if there is a hit; <code>false</code> 
	 * otherwise.
	 */
	public boolean hit(Rectangle2D rect, Shape s, boolean onStroke);

	/**
	 * Returns the device configuration associated with this 
	 * <code>Graphics2D</code>.
	 * @return the device configuration of this <code>Graphics2D</code>.
	 */
	public GraphicsConfiguration getDeviceConfiguration();

	/**
	 * Sets the <code>Composite</code> for the <code>Graphics2D</code> context.
	 * The <code>Composite</code> is used in all drawing methods such as 
	 * <code>drawImage</code>, <code>drawString</code>, <code>draw</code>,
	 * and <code>fill</code>.  It specifies how new pixels are to be combined 
	 * with the existing pixels on the graphics device during the rendering 
	 * process.
	 * <p>If this <code>Graphics2D</code> context is drawing to a
	 * <code>Component</code> on the display screen and the
	 * <code>Composite</code> is a custom object rather than an
	 * instance of the <code>AlphaComposite</code> class, and if
	 * there is a security manager, its <code>checkPermission</code>
	 * method is called with an <code>AWTPermission("readDisplayPixels")</code>
	 * permission.
	 * 
	 * @param comp the <code>Composite</code> object to be used for rendering
	 * @throws SecurityException
	 *         if a custom <code>Composite</code> object is being
	 *         used to render to the screen and a security manager
	 *         is set and its <code>checkPermission</code> method
	 *         does not allow the operation.
	 */
	public void setComposite(Composite comp);

	/**
	 * Sets the <code>Paint</code> attribute for the 
	 * <code>Graphics2D</code> context.  Calling this method
	 * with a <code>null</code> <code>Paint</code> object does 
	 * not have any effect on the current <code>Paint</code> attribute
	 * of this <code>Graphics2D</code>.  
	 * @param paint the <code>Paint</code> object to be used to generate 
	 * color during the rendering process, or <code>null</code>
	 */
	public void setPaint( Paint paint );

	/**
	 * Sets the <code>Stroke</code> for the <code>Graphics2D</code> context.
	 * @param s the <code>Stroke</code> object to be used to stroke a 
	 * <code>Shape</code> during the rendering process
	 */
	public void setStroke(Stroke s);

	/**
	 * Sets the value of a single preference for the rendering algorithms.
	 * Hint categories include controls for rendering quality and overall 
	 * time/quality trade-off in the rendering process.  Refer to the
	 * <code>RenderingHints</code> class for definitions of some common
	 * keys and values.
	 * @param hintKey the key of the hint to be set.
	 * @param hintValue the value indicating preferences for the specified
	 * hint category.
	 */
	public void setRenderingHint(Key hintKey, Object hintValue);

	/**
	 * Returns the value of a single preference for the rendering algorithms.
	 * Hint categories include controls for rendering quality and overall 
	 * time/quality trade-off in the rendering process.  Refer to the
	 * <code>RenderingHints</code> class for definitions of some common
	 * keys and values.
	 * @param hintKey the key corresponding to the hint to get. 
	 * @return an object representing the value for the specified hint key.
	 * Some of the keys and their associated values are defined in the
	 * <code>RenderingHints</code> class.
	 */
	public Object getRenderingHint(Key hintKey);

	/**
	 * Replaces the values of all preferences for the rendering
	 * algorithms with the specified <code>hints</code>.
	 * The existing values for all rendering hints are discarded and
	 * the new set of known hints and values are initialized from the
	 * specified {@link Map} object.
	 * Hint categories include controls for rendering quality and
	 * overall time/quality trade-off in the rendering process.
	 * Refer to the <code>RenderingHints</code> class for definitions of
	 * some common keys and values.
	 * @param hints the rendering hints to be set
	 */
	public void setRenderingHints(Map<?,?> hints);

	/**
	 * Sets the values of an arbitrary number of preferences for the
	 * rendering algorithms.
	 * Only values for the rendering hints that are present in the
	 * specified <code>Map</code> object are modified.
	 * All other preferences not present in the specified 
	 * object are left unmodified.
	 * Hint categories include controls for rendering quality and
	 * overall time/quality trade-off in the rendering process.
	 * Refer to the <code>RenderingHints</code> class for definitions of
	 * some common keys and values.
	 * @param hints the rendering hints to be set
	 */
	public void addRenderingHints(Map<?,?> hints);

	/**
	 * Gets the preferences for the rendering algorithms.  Hint categories
	 * include controls for rendering quality and overall time/quality
	 * trade-off in the rendering process.
	 * Returns all of the hint key/value pairs that were ever specified in 
	 * one operation.  Refer to the
	 * <code>RenderingHints</code> class for definitions of some common
	 * keys and values.
	 * @return a reference to an instance of <code>RenderingHints</code>
	 * that contains the current preferences.
	 */
	public RenderingHints getRenderingHints();

	/**
	 * Concatenates the current
	 * <code>Graphics2D</code> <code>Transform</code> 
	 * with a translation transform. 
	 * Subsequent rendering is translated by the specified
	 * distance relative to the previous position.
	 * This is equivalent to calling transform(T), where T is an
	 * <code>AffineTransform</code> represented by the following matrix:
	 * <pre>
	 *		[   1    0    tx  ]
	 *		[   0    1    ty  ]
	 *		[   0    0    1   ]
	 * </pre>
	 * @param tx the distance to translate along the x-axis
	 * @param ty the distance to translate along the y-axis
	 */
	public void translate(float tx, float ty);

	/**
	 * Concatenates the current <code>Graphics2D</code>
	 * <code>Transform</code> with a rotation transform. 
	 * Subsequent rendering is rotated by the specified radians relative
	 * to the previous origin.
	 * This is equivalent to calling <code>transform(R)</code>, where R is an
	 * <code>AffineTransform</code> represented by the following matrix:
	 * <pre>
	 *		[   cos(theta)    -sin(theta)    0   ]
	 *		[   sin(theta)     cos(theta)    0   ]
	 *		[       0              0         1   ]
	 * </pre>
	 * Rotating with a positive angle theta rotates points on the positive
	 * x axis toward the positive y axis.
	 * @param theta the angle of rotation in radians
	 */
	public void rotate(float theta);

	/**
	 * Concatenates the current <code>Graphics2D</code> 
	 * <code>Transform</code> with a translated rotation 
	 * transform.  Subsequent rendering is transformed by a transform
	 * which is constructed by translating to the specified location, 
	 * rotating by the specified radians, and translating back by the same
	 * amount as the original translation.  This is equivalent to the
	 * following sequence of calls:
	 * <pre>
	 *		translate(x, y);
	 *		rotate(theta);
	 *		translate(-x, -y);
	 * </pre>
	 * Rotating with a positive angle theta rotates points on the positive
	 * x axis toward the positive y axis.
	 * @param theta the angle of rotation in radians
	 * @param x the x coordinate of the origin of the rotation
	 * @param y the y coordinate of the origin of the rotation
	 */
	public void rotate(float theta, float x, float y);

	/**
	 * Concatenates the current <code>Graphics2D</code>
	 * <code>Transform</code> with a scaling transformation 
	 * Subsequent rendering is resized according to the specified scaling
	 * factors relative to the previous scaling.
	 * This is equivalent to calling <code>transform(S)</code>, where S is an
	 * <code>AffineTransform</code> represented by the following matrix:
	 * <pre>
	 *		[   sx   0    0   ]
	 *		[   0    sy   0   ]
	 *		[   0    0    1   ]
	 * </pre>
	 * @param sx the amount by which X coordinates in subsequent
	 * rendering operations are multiplied relative to previous
	 * rendering operations.
	 * @param sy the amount by which Y coordinates in subsequent 
	 * rendering operations are multiplied relative to previous 
	 * rendering operations.
	 */
	public void scale(float sx, float sy);

	/**
	 * Concatenates the current <code>Graphics2D</code>
	 * <code>Transform</code> with a shearing transform. 
	 * Subsequent renderings are sheared by the specified
	 * multiplier relative to the previous position.
	 * This is equivalent to calling <code>transform(SH)</code>, where SH
	 * is an <code>AffineTransform</code> represented by the following
	 * matrix:
	 * <pre>
	 *		[   1   shx   0   ]
	 *		[  shy   1    0   ]
	 *		[   0    0    1   ]
	 * </pre>
	 * @param shx the multiplier by which coordinates are shifted in 
	 * the positive X axis direction as a function of their Y coordinate
	 * @param shy the multiplier by which coordinates are shifted in
	 * the positive Y axis direction as a function of their X coordinate
	 */
	public void shear(float shx, float shy);

	/**
	 * Composes an <code>AffineTransform</code> object with the 
	 * <code>Transform</code> in this <code>Graphics2D</code> according 
	 * to the rule last-specified-first-applied.  If the current
	 * <code>Transform</code> is Cx, the result of composition
	 * with Tx is a new <code>Transform</code> Cx'.  Cx' becomes the
	 * current <code>Transform</code> for this <code>Graphics2D</code>.
	 * Transforming a point p by the updated <code>Transform</code> Cx' is
	 * equivalent to first transforming p by Tx and then transforming
	 * the result by the original <code>Transform</code> Cx.  In other
	 * words, Cx'(p) = Cx(Tx(p)).  A copy of the Tx is made, if necessary,
	 * so further modifications to Tx do not affect rendering.
	 * @param Tx the <code>AffineTransform</code> object to be composed with 
	 * the current <code>Transform</code>
	 */
	public void transform(AffineTransform Tx);

	/**
	 * Overwrites the Transform in the <code>Graphics2D</code> context.
	 * WARNING: This method should <b>never</b> be used to apply a new
	 * coordinate transform on top of an existing transform because the 
	 * <code>Graphics2D</code> might already have a transform that is
	 * needed for other purposes, such as rendering Swing 
	 * components or applying a scaling transformation to adjust for the
	 * resolution of a printer.  
	 * <p>To add a coordinate transform, use the 
	 * <code>transform</code>, <code>rotate</code>, <code>scale</code>,
	 * or <code>shear</code> methods.  The <code>setTransform</code> 
	 * method is intended only for restoring the original 
	 * <code>Graphics2D</code> transform after rendering, as shown in this
	 * example:
	 * <pre><blockquote>
	 * // Get the current transform
	 * AffineTransform saveAT = g2.getTransform();
	 * // Perform transformation
	 * g2d.transform(...);
	 * // Render
	 * g2d.draw(...);
	 * // Restore original transform
	 * g2d.setTransform(saveAT);
	 * </blockquote></pre>
	 *
	 * @param Tx the <code>AffineTransform</code> that was retrieved
	 *           from the <code>getTransform</code> method
	 */
	public void setTransform(AffineTransform Tx);

	/**
	 * Returns a copy of the current <code>Transform</code> in the 
	 * <code>Graphics2D</code> context.
	 * @return the current <code>AffineTransform</code> in the 
	 *             <code>Graphics2D</code> context.
	 */
	public AffineTransform getTransform();

	/**
	 * Returns the current <code>Paint</code> of the 
	 * <code>Graphics2D</code> context.
	 * @return the current <code>Graphics2D</code> <code>Paint</code>,
	 * which defines a color or pattern.
	 */
	public Paint getPaint();

	/**
	 * Returns the current <code>Composite</code> in the
	 * <code>Graphics2D</code> context.
	 * @return the current <code>Graphics2D</code> <code>Composite</code>,
	 *              which defines a compositing style.
	 */
	public Composite getComposite();

	/**
	 * Sets the background color for the <code>Graphics2D</code> context. 
	 * The background color is used for clearing a region.
	 * When a <code>Graphics2D</code> is constructed for a
	 * <code>Component</code>, the background color is
	 * inherited from the <code>Component</code>. Setting the background color 
	 * in the <code>Graphics2D</code> context only affects the subsequent      
	 * <code>clearRect</code> calls and not the background color of the  
	 * <code>Component</code>.  To change the background
	 * of the <code>Component</code>, use appropriate methods of 
	 * the <code>Component</code>.
	 * @param color the background color that isused in
	 * subsequent calls to <code>clearRect</code>
	 */
	public void setBackground(Color color);

	/**
	 * Returns the background color used for clearing a region.
	 * @return the current <code>Graphics2D</code> <code>Color</code>,
	 * which defines the background color.
	 */
	public Color getBackground();

	/**
	 * Returns the current <code>Stroke</code> in the
	 * <code>Graphics2D</code> context.
	 * @return the current <code>Graphics2D</code> <code>Stroke</code>,
	 *                 which defines the line style.
	 */
	public Stroke getStroke();

	/**
	 * Intersects the current <code>Clip</code> with the interior of the
	 * specified <code>Shape</code> and sets the <code>Clip</code> to the
	 * resulting intersection.  The specified <code>Shape</code> is
	 * transformed with the current <code>Graphics2D</code>
	 * <code>Transform</code> before being intersected with the current 
	 * <code>Clip</code>.  This method is used to make the current
	 * <code>Clip</code> smaller.
	 * To make the <code>Clip</code> larger, use <code>setClip</code>.
	 * The <i>user clip</i> modified by this method is independent of the
	 * clipping associated with device bounds and visibility.  If no clip has 
	 * previously been set, or if the clip has been cleared using 
	 * {@link Graphics#setClip(Shape) setClip} with a <code>null</code>
	 * argument, the specified <code>Shape</code> becomes the new 
	 * user clip.
	 * @param s the <code>Shape</code> to be intersected with the current
	 *          <code>Clip</code>.  If <code>s</code> is <code>null</code>,
	 *          this method clears the current <code>Clip</code>.
	 */
	public void clip(Shape s);

	/**
	 * Get the rendering context of the <code>Font</code> within this 
	 * <code>Graphics2D</code> context.
	 * The {@link FontRenderContext}
	 * encapsulates application hints such as anti-aliasing and 
	 * fractional metrics, as well as target device specific information
	 * such as dots-per-inch.  This information should be provided by the
	 * application when using objects that perform typographical
	 * formatting, such as <code>Font</code> and
	 * <code>TextLayout</code>.  This information should also be provided
	 * by applications that perform their own layout and need accurate
	 * measurements of various characteristics of glyphs such as advance
	 * and line height when various rendering hints have been applied to
	 * the text rendering.
	 *
	 * @return a reference to an instance of FontRenderContext.
	 */

	public FontRenderContext getFontRenderContext();
	
	/** Draw a string inside a cartridge.
	 * <p>
	 * A cartridge is a text surround by a box. The LODGraphics2D object
	 * ensures that the cartridges do not overlaps and tries to move
	 * them if it is the case.
	 * 
	 * @param text is the text inside the cartridge. If {@code \n} characters
	 * is inside, the text will be drawn on several lines.
	 * @param x is the position of the upper left corner of the cartridge.
	 * @param y is the position of the upper left corner of the cartridge.
	 * @param backgroundColor is the color of the background, or <code>null</code> for transparent background.
	 * @param textColor is the color of the text, or <code>null</code> for the current LODGraphics2D color.
	 * @param borderColor is the color of the cartridge border, or <code>null</code> for transparent borders.
	 * @param alignment is the alignment of the text in the cartridge
	 */
	public void drawCartridge(String text, float x, float y, Color textColor, Color backgroundColor, Color borderColor, TextAlignment alignment);

	/** Draw a string inside a cartridge.
	 * <p>
	 * A cartridge is a text surround by a box. The LODGraphics2D object
	 * ensures that the cartridges do not overlaps and tries to move
	 * them if it is the case.
	 * 
	 * @param text is the text inside the cartridge. If {@code \n} characters
	 * is inside, the text will be drawn on several lines.
	 * @param x is the position of the upper left corner of the cartridge.
	 * @param y is the position of the upper left corner of the cartridge.
	 */
	public void drawCartridge(String text, float x, float y);

	/** Draw a string inside a cartridge.
	 * <p>
	 * A cartridge is a text surround by a box. The LODGraphics2D object
	 * ensures that the cartridges do not overlaps and tries to move
	 * them if it is the case.
	 * 
	 * @param text is the text inside the cartridge. If {@code \n} characters
	 * is inside, the text will be drawn on several lines.
	 * @param x is the position of the upper left corner of the cartridge.
	 * @param y is the position of the upper left corner of the cartridge.
	 * @param backgroundColor is the color of the background, or <code>null</code> for transparent background.
	 * @param borderColor is the color of the cartridge border, or <code>null</code> for transparent borders.
	 */
	public void drawCartridge(String text, float x, float y, Color backgroundColor, Color borderColor);
	
}