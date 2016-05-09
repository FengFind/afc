/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
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
package org.arakhne.afc.math.geometry.d2.afp;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.MultiShape2D;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.ai.PathIterator2ai;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractMultiShape2afpTest<T extends MultiShape2afp<?, T, C, ?, ?, ?, B>,
		C extends Shape2afp<?, ?, ?, ?, ?, B>,
		B extends Rectangle2afp<?, ?, ?, ?, ?, B>> extends AbstractShape2afpTest<T, B> {

	protected C firstObject;
	
	protected C secondObject;

	@Override
	protected final T createShape() {
		T shape = (T) createMultiShape();
		firstObject = (C) createRectangle(5, 8, 2, 1);
		secondObject = (C) createCircle(-5, 18, 2);
		shape.add(firstObject);
		shape.add(secondObject);
		return shape;
	}

	@Override
	public void testClone() {
		MultiShape2afp clone = this.shape.clone();
		PathIterator2afp pi = (PathIterator2afp) clone.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 9);
		assertElement(pi, PathElementType.LINE_TO, 5, 9);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertElement(pi, PathElementType.MOVE_TO, -3, 18);
		assertElement(pi, PathElementType.CURVE_TO, -3, 19.10457, -3.89543, 20, -5, 20);
		assertElement(pi, PathElementType.CURVE_TO, -6.10457, 20, -7, 19.10457, -7, 18);
		assertElement(pi, PathElementType.CURVE_TO, -7, 16.89543, -6.10457, 16, -5, 16);
		assertElement(pi, PathElementType.CURVE_TO, -3.89543, 16, -3, 16.89543, -3, 18);
		assertElement(pi, PathElementType.CLOSE, -3, 18);
		assertNoElement(pi);
	}

	@Override
	public void equalsObject() {
		assertFalse(this.shape.equals(null));
		assertFalse(this.shape.equals(new Object()));
		assertFalse(this.shape.equals(createMultiShape()));
		assertFalse(this.shape.equals(createSegment(5, 8, 5, 10)));
		assertTrue(this.shape.equals(this.shape));
		assertTrue(this.shape.equals(this.shape.clone()));
	}

	@Override
	public void equalsObject_withPathIterator() {
		assertFalse(this.shape.equals(createMultiShape().getPathIterator()));
		assertFalse(this.shape.equals(createSegment(5, 8, 5, 10).getPathIterator()));
		assertTrue(this.shape.equals(this.shape.getPathIterator()));
		assertTrue(this.shape.equals(this.shape.clone().getPathIterator()));
	}

	@Override
	public void equalsToPathIterator() {
		assertFalse(this.shape.equalsToPathIterator((PathIterator2ai) null));
		assertFalse(this.shape.equalsToPathIterator(createMultiShape().getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createSegment(5, 8, 5, 10).getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(this.shape.getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(this.shape.clone().getPathIterator()));
	}

	@Override
	public void equalsToShape() {
		assertFalse(this.shape.equalsToShape(null));
		assertFalse(this.shape.equalsToShape((T) createMultiShape()));
		assertTrue(this.shape.equalsToShape((T) this.shape));
		assertTrue(this.shape.equalsToShape((T) this.shape.clone()));
	}

	@Override
	public void isEmpty() {
		assertFalse(this.shape.isEmpty());
		this.shape.clear();
		assertTrue(this.shape.isEmpty());
	}

	@Override
	public void clear() {
		this.shape.clear();
		PathIterator2afp pi = this.shape.getPathIterator();
		assertNoElement(pi);
	}

	@Override
	public void containsDoubleDouble() {
		// Outside bounding box
		assertFalse(this.shape.contains(-10, 2));
		assertFalse(this.shape.contains(-10, 14));
		assertFalse(this.shape.contains(-10, 25));
		assertFalse(this.shape.contains(-1, 25));
		assertFalse(this.shape.contains(1, 2));
		assertFalse(this.shape.contains(12, 2));
		assertFalse(this.shape.contains(12, 14));
		assertFalse(this.shape.contains(12, 25));
		// Inside bounding box - outside subshape
		assertFalse(this.shape.contains(-6, 8));
		assertFalse(this.shape.contains(4, 17));
		// Inside circle
		assertTrue(this.shape.contains(-4, 19));
		// Inside rectangle
		assertTrue(this.shape.contains(6, 8.25));
	}

	@Override
	public void containsPoint2D() {
		// Outside bounding box
		assertFalse(this.shape.contains(createPoint(-10, 2)));
		assertFalse(this.shape.contains(createPoint(-10, 14)));
		assertFalse(this.shape.contains(createPoint(-10, 25)));
		assertFalse(this.shape.contains(createPoint(-1, 25)));
		assertFalse(this.shape.contains(createPoint(1, 2)));
		assertFalse(this.shape.contains(createPoint(12, 2)));
		assertFalse(this.shape.contains(createPoint(12, 14)));
		assertFalse(this.shape.contains(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertFalse(this.shape.contains(createPoint(-6, 8)));
		assertFalse(this.shape.contains(createPoint(4, 17)));
		// Inside circle
		assertTrue(this.shape.contains(createPoint(-4, 19)));
		// Inside rectangle
		assertTrue(this.shape.contains(createPoint(6, 8.25)));
	}

	@Override
	public void containsRectangle2afp() {
		// Outside
		assertFalse(this.shape.contains(createRectangle(-20, 14, .5, .5)));
		assertFalse(this.shape.contains(createRectangle(-2,-10, .5, .5)));
		// Intersecting
		assertFalse(this.shape.contains(createRectangle(-6,16, .5, .5)));
		assertFalse(this.shape.contains(createRectangle(4.75, 8, .5, .5)));
		// Inside
		assertTrue(this.shape.contains(createRectangle(-4, 18, .5, .5)));
		assertTrue(this.shape.contains(createRectangle(5.5, 8.5, .5, .5)));
	}

	@Override
	public void getClosestPointTo() {
		Point2D result;
		// Outside bounding box
		result = this.shape.getClosestPointTo(createPoint(-10, 2));
		assertEpsilonEquals(-5.59655, result.getX());
		assertEpsilonEquals(16.09104, result.getY());
		result = this.shape.getClosestPointTo(createPoint(-10, 14));
		assertEpsilonEquals(-6.56174, result.getX());
		assertEpsilonEquals(16.75061, result.getY());
		result = this.shape.getClosestPointTo(createPoint(-10, 25));
		assertEpsilonEquals(-6.16248, result.getX());
		assertEpsilonEquals(19.62747, result.getY());
		result = this.shape.getClosestPointTo(createPoint(-1, 25));
		assertEpsilonEquals(-4.00772, result.getX());
		assertEpsilonEquals(19.73649, result.getY());
		result = this.shape.getClosestPointTo(createPoint(1, 2));
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(8, result.getY());
		result = this.shape.getClosestPointTo(createPoint(12, 2));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(8, result.getY());
		result = this.shape.getClosestPointTo(createPoint(12, 14));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(9, result.getY());
		result = this.shape.getClosestPointTo(createPoint(12, 25));
		assertEpsilonEquals(-3.15064, result.getX());
		assertEpsilonEquals(18.7615, result.getY());
		// Inside bounding box - outside subshape
		result = this.shape.getClosestPointTo(createPoint(-6, 8));
		assertEpsilonEquals(-5.19901, result.getX());
		assertEpsilonEquals(16.00993, result.getY());
		result = this.shape.getClosestPointTo(createPoint(4, 17));
		assertEpsilonEquals(-3.01223, result.getX());
		assertEpsilonEquals(17.77914, result.getY());
		// Inside circle
		result = this.shape.getClosestPointTo(createPoint(-4, 19));
		assertEpsilonEquals(-4, result.getX());
		assertEpsilonEquals(19, result.getY());
		// Inside rectangle
		result = this.shape.getClosestPointTo(createPoint(6, 8.25));
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(8.25, result.getY());
	}

	@Override
	public void getFarthestPointTo() {
		Point2D result;
		// Outside bounding box
		result = this.shape.getFarthestPointTo(createPoint(-10, 2));
		assertEpsilonEquals(-4.40345, result.getX());
		assertEpsilonEquals(19.90896, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(-10, 14));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(8, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(-10, 25));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(8, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(-1, 25));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(8, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(1, 2));
		assertEpsilonEquals(-5.70225, result.getX());
		assertEpsilonEquals(19.87266, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(12, 2));
		assertEpsilonEquals(-6.4564, result.getX());
		assertEpsilonEquals(19.37073, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(12, 14));
		assertEpsilonEquals(-6.94683, result.getX());
		assertEpsilonEquals(18.45808, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(12, 25));
		assertEpsilonEquals(-6.84936, result.getX());
		assertEpsilonEquals(17.2385, result.getY());
		// Inside bounding box - outside subshape
		result = this.shape.getFarthestPointTo(createPoint(-6, 8));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(9, result.getY());
		result = this.shape.getFarthestPointTo(createPoint(4, 17));
		assertEpsilonEquals(-6.98777, result.getX());
		assertEpsilonEquals(18.22086, result.getY());
		// Inside circle
		result = this.shape.getFarthestPointTo(createPoint(-4, 19));
		assertEpsilonEquals(7, result.getX());
		assertEpsilonEquals(8, result.getY());
		// Inside rectangle
		result = this.shape.getFarthestPointTo(createPoint(6, 8.25));
		assertEpsilonEquals(-6.49669, result.getX());
		assertEpsilonEquals(19.32662, result.getY());
	}

	@Override
	public void getDistance() {
		// Outside bounding box
		assertEpsilonEquals(14.76305, this.shape.getDistance(createPoint(-10, 2)));
		assertEpsilonEquals(4.40312, this.shape.getDistance(createPoint(-10, 14)));
		assertEpsilonEquals(6.60233, this.shape.getDistance(createPoint(-10, 25)));
		assertEpsilonEquals(6.06226, this.shape.getDistance(createPoint(-1, 25)));
		assertEpsilonEquals(7.21110, this.shape.getDistance(createPoint(1, 2)));
		assertEpsilonEquals(7.81025, this.shape.getDistance(createPoint(12, 2)));
		assertEpsilonEquals(7.07107, this.shape.getDistance(createPoint(12, 14)));
		assertEpsilonEquals(16.38478, this.shape.getDistance(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertEpsilonEquals(8.04988, this.shape.getDistance(createPoint(-6, 8)));
		assertEpsilonEquals(7.05538, this.shape.getDistance(createPoint(4, 17)));
		// Inside circle
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(-4, 19)));
		// Inside rectangle
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(6, 8.25)));
	}

	@Override
	public void getDistanceSquared() {
		// Outside bounding box
		assertEpsilonEquals(217.94778, this.shape.getDistanceSquared(createPoint(-10, 2)));
		assertEpsilonEquals(19.38749, this.shape.getDistanceSquared(createPoint(-10, 14)));
		assertEpsilonEquals(43.5907, this.shape.getDistanceSquared(createPoint(-10, 25)));
		assertEpsilonEquals(36.75092, this.shape.getDistanceSquared(createPoint(-1, 25)));
		assertEpsilonEquals(52, this.shape.getDistanceSquared(createPoint(1, 2)));
		assertEpsilonEquals(61, this.shape.getDistanceSquared(createPoint(12, 2)));
		assertEpsilonEquals(50, this.shape.getDistanceSquared(createPoint(12, 14)));
		assertEpsilonEquals(268.46089, this.shape.getDistanceSquared(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertEpsilonEquals(64.8005, this.shape.getDistanceSquared(createPoint(-6, 8)));
		assertEpsilonEquals(49.77843, this.shape.getDistanceSquared(createPoint(4, 17)));
		// Inside circle
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(-4, 19)));
		// Inside rectangle
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(6, 8.25)));
	}

	@Override
	public void getDistanceL1() {
		// Outside bounding box
		assertEpsilonEquals(18.49449, this.shape.getDistanceL1(createPoint(-10, 2)));
		assertEpsilonEquals(6.18887, this.shape.getDistanceL1(createPoint(-10, 14)));
		assertEpsilonEquals(9.21006, this.shape.getDistanceL1(createPoint(-10, 25)));
		assertEpsilonEquals(8.27123, this.shape.getDistanceL1(createPoint(-1, 25)));
		assertEpsilonEquals(10, this.shape.getDistanceL1(createPoint(1, 2)));
		assertEpsilonEquals(11, this.shape.getDistanceL1(createPoint(12, 2)));
		assertEpsilonEquals(10, this.shape.getDistanceL1(createPoint(12, 14)));
		assertEpsilonEquals(21, this.shape.getDistanceL1(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertEpsilonEquals(8.81092, this.shape.getDistanceL1(createPoint(-6, 8)));
		assertEpsilonEquals(7.79137, this.shape.getDistanceL1(createPoint(4, 17)));
		// Inside circle
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(-4, 19)));
		// Inside rectangle
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(6, 8.25)));
	}

	@Override
	public void getDistanceLinf() {
		// Outside bounding box
		assertEpsilonEquals(14.09104, this.shape.getDistanceLinf(createPoint(-10, 2)));
		assertEpsilonEquals(3.43826, this.shape.getDistanceLinf(createPoint(-10, 14)));
		assertEpsilonEquals(5.37253, this.shape.getDistanceLinf(createPoint(-10, 25)));
		assertEpsilonEquals(5.26351, this.shape.getDistanceLinf(createPoint(-1, 25)));
		assertEpsilonEquals(6, this.shape.getDistanceLinf(createPoint(1, 2)));
		assertEpsilonEquals(6, this.shape.getDistanceLinf(createPoint(12, 2)));
		assertEpsilonEquals(5, this.shape.getDistanceLinf(createPoint(12, 14)));
		assertEpsilonEquals(15.15064, this.shape.getDistanceLinf(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertEpsilonEquals(8.00993, this.shape.getDistanceLinf(createPoint(-6, 8)));
		assertEpsilonEquals(7.01223, this.shape.getDistanceLinf(createPoint(4, 17)));
		// Inside circle
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(-4, 19)));
		// Inside rectangle
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(6, 8.25)));
	}

	@Override
	public void setIT() {
		this.shape.set((T) createMultiShape());
		PathIterator2afp pi = this.shape.getPathIterator();
		assertNoElement(pi);
		MultiShape2afp newShape = createMultiShape();
		newShape.add(createRectangle(-6, 48, 5, 7));
		this.shape.set((T) newShape);
		pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, -6, 48);
		assertElement(pi, PathElementType.LINE_TO, -1, 48);
		assertElement(pi, PathElementType.LINE_TO, -1, 55);
		assertElement(pi, PathElementType.LINE_TO, -6, 55);
		assertElement(pi, PathElementType.CLOSE, -6, 48);
		assertNoElement(pi);
	}

	@Override
	public void translateDoubleDouble() {
		this.shape.translate(10, -2);
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void translateVector2D() {
		this.shape.translate(createVector(10, -2));
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void toBoundingBox() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());
	}

	@Override
	public void toBoundingBoxB() {
		B box = createRectangle(0, 0, 0, 0);
		this.shape.toBoundingBox(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());
	}

	@Override
	public void getPathIterator() {
		PathIterator2afp pi = (PathIterator2afp) this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 9);
		assertElement(pi, PathElementType.LINE_TO, 5, 9);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertElement(pi, PathElementType.MOVE_TO, -3, 18);
		assertElement(pi, PathElementType.CURVE_TO, -3, 19.10457, -3.89543, 20, -5, 20);
		assertElement(pi, PathElementType.CURVE_TO, -6.10457, 20, -7, 19.10457, -7, 18);
		assertElement(pi, PathElementType.CURVE_TO, -7, 16.89543, -6.10457, 16, -5, 16);
		assertElement(pi, PathElementType.CURVE_TO, -3.89543, 16, -3, 16.89543, -3, 18);
		assertElement(pi, PathElementType.CLOSE, -3, 18);
		assertNoElement(pi);
	}

	@Override
	public void getPathIteratorTransform2D() {
		PathIterator2afp pi = this.shape.getPathIterator(null);
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 9);
		assertElement(pi, PathElementType.LINE_TO, 5, 9);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertElement(pi, PathElementType.MOVE_TO, -3, 18);
		assertElement(pi, PathElementType.CURVE_TO, -3, 19.10457, -3.89543, 20, -5, 20);
		assertElement(pi, PathElementType.CURVE_TO, -6.10457, 20, -7, 19.10457, -7, 18);
		assertElement(pi, PathElementType.CURVE_TO, -7, 16.89543, -6.10457, 16, -5, 16);
		assertElement(pi, PathElementType.CURVE_TO, -3.89543, 16, -3, 16.89543, -3, 18);
		assertElement(pi, PathElementType.CLOSE, -3, 18);
		assertNoElement(pi);

		pi = this.shape.getPathIterator(new Transform2D());
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 8);
		assertElement(pi, PathElementType.LINE_TO, 7, 9);
		assertElement(pi, PathElementType.LINE_TO, 5, 9);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertElement(pi, PathElementType.MOVE_TO, -3, 18);
		assertElement(pi, PathElementType.CURVE_TO, -3, 19.10457, -3.89543, 20, -5, 20);
		assertElement(pi, PathElementType.CURVE_TO, -6.10457, 20, -7, 19.10457, -7, 18);
		assertElement(pi, PathElementType.CURVE_TO, -7, 16.89543, -6.10457, 16, -5, 16);
		assertElement(pi, PathElementType.CURVE_TO, -3.89543, 16, -3, 16.89543, -3, 18);
		assertElement(pi, PathElementType.CLOSE, -3, 18);
		assertNoElement(pi);

		Transform2D transform = new Transform2D();
		transform.setTranslation(10, -2);
		pi = this.shape.getPathIterator(transform);
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void createTransformedShape() {
		Transform2D transform = new Transform2D();
		transform.setTranslation(10, -2);
		Shape2afp newShape = this.shape.createTransformedShape(transform);
		PathIterator2afp pi = (PathIterator2afp) newShape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void intersectsRectangle2afp() {
		// Outside
		assertFalse(this.shape.intersects(createRectangle(-20, 14, .5, .5)));
		assertFalse(this.shape.intersects(createRectangle(-2, -10, .5, .5)));
		// Intersecting
		assertTrue(this.shape.intersects(createRectangle(-6, 16, .5, .5)));
		assertTrue(this.shape.intersects(createRectangle(4.75, 8, .5, .5)));
		// Inside
		assertTrue(this.shape.intersects(createRectangle(-4, 18, .5, .5)));
		assertTrue(this.shape.intersects(createRectangle(5.5, 8.5, .5, .5)));
	}

	@Override
	public void intersectsCircle2afp() {
		// Outside
		assertFalse(this.shape.intersects(createCircle(-20, 14, .5)));
		assertFalse(this.shape.intersects(createCircle(-2,- 10, .5)));
		// Intersecting
		assertTrue(this.shape.intersects(createCircle(-6, 16, .5)));
		assertTrue(this.shape.intersects(createCircle(4.75, 8, .5)));
		// Inside
		assertTrue(this.shape.intersects(createCircle(-4, 18, .5)));
		assertTrue(this.shape.intersects(createCircle(5.5, 8.5, .5)));
	}

	@Override
	public void intersectsTriangle2afp() {
		// Outside
		assertFalse(this.shape.intersects(createTriangle(-20, 14, -19.5, 14, -20, 14.5)));
		assertFalse(this.shape.intersects(createTriangle(-2, -10, -1.5, -10, -2, -9.5)));
		// Intersecting
		assertTrue(this.shape.intersects(createTriangle(-6, 16, -5.5, 16, -6, 16.5)));
		assertTrue(this.shape.intersects(createTriangle(4.75, 8, 5.25, 8, 4.75, 8.5)));
		// Inside
		assertTrue(this.shape.intersects(createTriangle(-4, 18, -3.5, 18, -4, 18.5)));
		assertTrue(this.shape.intersects(createTriangle(5.5, 8.5, 6, 8.5, 5.5, 9)));
	}

	@Override
	public void intersectsEllipse2afp() {
		// Outside
		assertFalse(this.shape.intersects(createEllipse(-20, 14, .5, .5)));
		assertFalse(this.shape.intersects(createEllipse(-2, -10, .5, .5)));
		// Intersecting
		assertTrue(this.shape.intersects(createEllipse(-6, 16, .5, .5)));
		assertTrue(this.shape.intersects(createEllipse(4.75, 8, .5, .5)));
		// Inside
		assertTrue(this.shape.intersects(createEllipse(-4, 18, .5, .5)));
		assertTrue(this.shape.intersects(createEllipse(5.5, 8.5, .5, .5)));
	}

	@Override
	public void intersectsSegment2afp() {
		// Outside
		assertFalse(this.shape.intersects(createSegment(-20, 14, -19.5, 14)));
		assertFalse(this.shape.intersects(createSegment(-2, -10, -1.5, -10)));
		// Intersecting
		assertTrue(this.shape.intersects(createSegment(-6, 16, -5.5, 16.5)));
		assertTrue(this.shape.intersects(createSegment(4.75, 8, 5.25, 8)));
		// Inside
		assertTrue(this.shape.intersects(createSegment(-4, 18, -3.5, 18)));
		assertTrue(this.shape.intersects(createSegment(5.5, 8.5, 6, 8.5)));
	}

	@Override
	public void intersectsPath2afp() {
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));
		
		path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));

		path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));
	}

	@Override
	public void intersectsPathIterator2afp() {
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		
		path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
	}

	@Override
	public void intersectsOrientedRectangle2afp() {
		// Outside
		assertFalse(this.shape.intersects(createOrientedRectangle(-20, 14, 1, 0, .5, .5)));
		assertFalse(this.shape.intersects(createOrientedRectangle(-2, -10, 1, 0, .5, .5)));
		// Intersecting
		assertTrue(this.shape.intersects(createOrientedRectangle(-6, 16, 1, 0, .5, .5)));
		assertTrue(this.shape.intersects(createOrientedRectangle(4.75, 8, 1, 0, .5, .5)));
		// Inside
		assertTrue(this.shape.intersects(createOrientedRectangle(-4, 18, 1, 0, .5, .5)));
		assertTrue(this.shape.intersects(createOrientedRectangle(5.5, 8.5, 1, 0, .5, .5)));
	}

	@Override
	public void intersectsParallelogram2afp() {
		// Outside
		assertFalse(this.shape.intersects(createParallelogram(-20, 14, 1, 0, .5, 0, 1, .5)));
		assertFalse(this.shape.intersects(createParallelogram(-2, -10, 1, 0, .5, 0, 1, .5)));
		// Intersecting
		assertTrue(this.shape.intersects(createParallelogram(-6, 16, 1, 0, .5, 0, 1, .5)));
		assertTrue(this.shape.intersects(createParallelogram(4.75, 8, 1, 0, .5, 0, 1, .5)));
		// Inside
		assertTrue(this.shape.intersects(createParallelogram(-4, 18, 1, 0, .5, 0, 1, .5)));
		assertTrue(this.shape.intersects(createParallelogram(5.5, 8.5, 1, 0, .5, 0, 1, .5)));
	}

	@Override
	public void intersectsRoundRectangle2afp() {
		// Outside
		assertFalse(this.shape.intersects(createRoundRectangle(-20, 14, .5, .5, .1, .1)));
		assertFalse(this.shape.intersects(createRoundRectangle(-2, -10, .5, .5, .1, .1)));
		// Intersecting
		assertTrue(this.shape.intersects(createRoundRectangle(-6, 16, .5, .5, .1, .1)));
		assertTrue(this.shape.intersects(createRoundRectangle(4.75, 8, .5, .5, .1, .1)));
		// Inside
		assertTrue(this.shape.intersects(createRoundRectangle(-4, 18, .5, .5, .1, .1)));
		assertTrue(this.shape.intersects(createRoundRectangle(5.5, 8.5, .5, .5, .1, .1)));
	}

	@Override
	public void intersectsShape2D() {
		assertTrue(this.shape.intersects((Shape2D) createCircle(4.75, 8, .5)));
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.intersects((Shape2D) path));
		path.closePath();
		assertTrue(this.shape.intersects((Shape2D) path));
	}

	@Override
	public void operator_addVector2D() {
		this.shape.operator_add(createVector(10, -2));
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void operator_plusVector2D() {
		T shape = this.shape.operator_plus(createVector(10, -2));
		PathIterator2afp pi = shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void operator_removeVector2D() {
		this.shape.operator_remove(createVector(10, -2));
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, -5, 10);
		assertElement(pi, PathElementType.LINE_TO, -3, 10);
		assertElement(pi, PathElementType.LINE_TO, -3, 11);
		assertElement(pi, PathElementType.LINE_TO, -5, 11);
		assertElement(pi, PathElementType.CLOSE, -5, 10);
		assertElement(pi, PathElementType.MOVE_TO, -13, 20);
		assertElement(pi, PathElementType.CURVE_TO, -13, 21.10457, -13.89543, 22, -15, 22);
		assertElement(pi, PathElementType.CURVE_TO, -16.10457, 22, -17, 21.10457, -17, 20);
		assertElement(pi, PathElementType.CURVE_TO, -17, 18.89543, -16.10457, 18, -15, 18);
		assertElement(pi, PathElementType.CURVE_TO, -13.89543, 18, -13, 18.89543, -13, 20);
		assertElement(pi, PathElementType.CLOSE, -13, 20);
		assertNoElement(pi);
	}

	@Override
	public void operator_minusVector2D() {
		T shape = this.shape.operator_minus(createVector(10, -2));
		PathIterator2afp pi = shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, -5, 10);
		assertElement(pi, PathElementType.LINE_TO, -3, 10);
		assertElement(pi, PathElementType.LINE_TO, -3, 11);
		assertElement(pi, PathElementType.LINE_TO, -5, 11);
		assertElement(pi, PathElementType.CLOSE, -5, 10);
		assertElement(pi, PathElementType.MOVE_TO, -13, 20);
		assertElement(pi, PathElementType.CURVE_TO, -13, 21.10457, -13.89543, 22, -15, 22);
		assertElement(pi, PathElementType.CURVE_TO, -16.10457, 22, -17, 21.10457, -17, 20);
		assertElement(pi, PathElementType.CURVE_TO, -17, 18.89543, -16.10457, 18, -15, 18);
		assertElement(pi, PathElementType.CURVE_TO, -13.89543, 18, -13, 18.89543, -13, 20);
		assertElement(pi, PathElementType.CLOSE, -13, 20);
		assertNoElement(pi);
	}

	@Override
	public void operator_multiplyTransform2D() {
		Transform2D transform = new Transform2D();
		transform.setTranslation(10, -2);
		Shape2afp newShape = this.shape.operator_multiply(transform);
		PathIterator2afp pi = (PathIterator2afp) newShape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 15, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 6);
		assertElement(pi, PathElementType.LINE_TO, 17, 7);
		assertElement(pi, PathElementType.LINE_TO, 15, 7);
		assertElement(pi, PathElementType.CLOSE, 15, 6);
		assertElement(pi, PathElementType.MOVE_TO, 7, 16);
		assertElement(pi, PathElementType.CURVE_TO, 7, 17.10457, 6.10457, 18, 5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 3.89543, 18, 3, 17.10457, 3, 16);
		assertElement(pi, PathElementType.CURVE_TO, 3, 14.89543, 3.89543, 14, 5, 14);
		assertElement(pi, PathElementType.CURVE_TO, 6.10457, 14, 7, 14.89543, 7, 16);
		assertElement(pi, PathElementType.CLOSE, 7, 16);
		assertNoElement(pi);
	}

	@Override
	public void operator_andPoint2D() {
		assertFalse(this.shape.operator_and(createPoint(-10, 2)));
		assertFalse(this.shape.operator_and(createPoint(-10, 14)));
		assertFalse(this.shape.operator_and(createPoint(-10, 25)));
		assertFalse(this.shape.operator_and(createPoint(-1, 25)));
		assertFalse(this.shape.operator_and(createPoint(1, 2)));
		assertFalse(this.shape.operator_and(createPoint(12, 2)));
		assertFalse(this.shape.operator_and(createPoint(12, 14)));
		assertFalse(this.shape.operator_and(createPoint(12, 25)));
		assertFalse(this.shape.operator_and(createPoint(-6, 8)));
		assertFalse(this.shape.operator_and(createPoint(4, 17)));
		assertTrue(this.shape.operator_and(createPoint(-4, 19)));
		assertTrue(this.shape.operator_and(createPoint(6, 8.25)));
	}

	@Override
	public void operator_andShape2D() {
		assertTrue(this.shape.operator_and(createCircle(4.75, 8, .5)));
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);
		assertFalse(this.shape.operator_and(path));
		path.closePath();
		assertTrue(this.shape.operator_and(path));
	}

	@Override
	public void operator_upToPoint2D() {
		assertEpsilonEquals(14.76305, this.shape.operator_upTo(createPoint(-10, 2)));
		assertEpsilonEquals(4.40312, this.shape.operator_upTo(createPoint(-10, 14)));
		assertEpsilonEquals(6.60233, this.shape.operator_upTo(createPoint(-10, 25)));
		assertEpsilonEquals(6.06226, this.shape.operator_upTo(createPoint(-1, 25)));
		assertEpsilonEquals(7.21110, this.shape.operator_upTo(createPoint(1, 2)));
		assertEpsilonEquals(7.81025, this.shape.operator_upTo(createPoint(12, 2)));
		assertEpsilonEquals(7.07107, this.shape.operator_upTo(createPoint(12, 14)));
		assertEpsilonEquals(16.38478, this.shape.operator_upTo(createPoint(12, 25)));
		assertEpsilonEquals(8.04988, this.shape.operator_upTo(createPoint(-6, 8)));
		assertEpsilonEquals(7.05538, this.shape.operator_upTo(createPoint(4, 17)));
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(-4, 19)));
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(6, 8.25)));
	}

	@Test
	public void getFirstShapeContainingPoint2D() {
		// Outside bounding box
		assertNull(this.shape.getFirstShapeContaining(createPoint(-10, 2)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(-10, 14)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(-10, 25)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(-1, 25)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(1, 2)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(12, 2)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(12, 14)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(12, 25)));
		// Inside bounding box - outside subshape
		assertNull(this.shape.getFirstShapeContaining(createPoint(-6, 8)));
		assertNull(this.shape.getFirstShapeContaining(createPoint(4, 17)));
		// Inside circle
		assertSame(secondObject, this.shape.getFirstShapeContaining(createPoint(-4, 19)));
		// Inside rectangle
		assertSame(firstObject, this.shape.getFirstShapeContaining(createPoint(6, 8.25)));
	}
	
	@Test
	public void getShapesContainingPoint2D() {
		// Outside bounding box
		assertTrue(this.shape.getShapesContaining(createPoint(-10, 2)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(-10, 14)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(-10, 25)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(-1, 25)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(1, 2)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(12, 2)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(12, 14)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(12, 25)).isEmpty());
		// Inside bounding box - outside subshape
		assertTrue(this.shape.getShapesContaining(createPoint(-6, 8)).isEmpty());
		assertTrue(this.shape.getShapesContaining(createPoint(4, 17)).isEmpty());
		// Inside circle
		assertEquals(Arrays.asList(secondObject), this.shape.getShapesContaining(createPoint(-4, 19)));
		// Inside rectangle
		assertEquals(Arrays.asList(firstObject), this.shape.getShapesContaining(createPoint(6, 8.25)));
	}

	@Test
	public void getFirstShapeIntersectingShape2D() {
		MultiShape2D shape2d = this.shape;
		
		assertSame(firstObject, shape2d.getFirstShapeIntersecting(createCircle(4.75, 8, .5)));
		
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);

		assertNull(shape2d.getFirstShapeIntersecting(path));
		path.closePath();
		assertSame(firstObject, shape2d.getFirstShapeIntersecting(path));
	}
	
	@Test
	public void getShapesIntersectingShape2D() {
		MultiShape2D shape2d = this.shape;
		
		assertEquals(Arrays.asList(firstObject), shape2d.getShapesIntersecting(createCircle(4.75, 8, .5)));
		
		Path2afp path = createPath();
		path.moveTo(-6, 2);
		path.lineTo(10, 6);
		path.lineTo(8, 14);
		path.lineTo(-4, 12);
		path.lineTo(-12, 22);
		path.lineTo(6, 20);

		assertTrue(shape2d.getShapesIntersecting(path).isEmpty());
		path.closePath();
		assertEquals(Arrays.asList(firstObject, secondObject), shape2d.getShapesIntersecting(path));
	}

	@Test
	public void getBackendDataList() {
		assertNotNull(this.shape.getBackendDataList());
		assertEquals(2, this.shape.getBackendDataList().size());
		assertSame(firstObject, this.shape.getBackendDataList().get(0));
		assertSame(secondObject, this.shape.getBackendDataList().get(1));
	}

	@Test
	public void onGeometryChange_changeFirstObject() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		firstObject.translate(12, -7);
		
		// C:  -7; 16; -3; 20
		// R:   5;  8;  7;  9
		
		// R': 17;  1; 19;  2 
		
		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(1, box.getMinY());
		assertEpsilonEquals(19, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());
	}
	
	@Test
	public void onGeometryChange_changeSecondObject() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		secondObject.translate(12, -7);
		
		// C:  -7; 16; -3; 20
		// R:   5;  8;  7;  9
		
		// C':  5;  9;  9; 13 

		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(9, box.getMaxX());
		assertEpsilonEquals(13, box.getMaxY());
	}

	@Test
	public void onBackendDataListChange_addition() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		this.shape.add((C) createCircle(10, 14, 1));
		
		// C:  -7; 16; -3; 20
		// R:   5;  8;  7;  9
		
		// C':  9;  13;  11; 15 

		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(11, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());
	}

	@Test
	public void onBackendDataListChange_removalFirstObject() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		this.shape.remove(firstObject);
		
		// C:  -7; 16; -3; 20
		// R:   5;  8;  7;  9
		
		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(16, box.getMinY());
		assertEpsilonEquals(-3, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());
	}

	@Test
	public void onBackendDataListChange_removalSecondObject() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		this.shape.remove(secondObject);
		
		// C:  -7; 16; -3; 20
		// R:   5;  8;  7;  9

		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(9, box.getMaxY());
	}

	@Test
	public void noGeometryChangeAfterRemoval() {
		B box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(-7, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(20, box.getMaxY());

		this.shape.remove(secondObject);
		secondObject.translate(1453,  -451);
		
		box = this.shape.toBoundingBox();
		assertNotNull(box);
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(7, box.getMaxX());
		assertEpsilonEquals(9, box.getMaxY());
	}

}