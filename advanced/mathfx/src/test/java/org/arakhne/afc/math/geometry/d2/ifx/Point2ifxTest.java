/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.math.geometry.d2.ifx;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.geometry.d2.AbstractPoint2DTest;
import org.arakhne.afc.math.geometry.d2.Shape2D;

@SuppressWarnings("all")
public class Point2ifxTest extends AbstractPoint2DTest<Point2ifx, Vector2ifx, Point2ifx> {

	@Override
	public boolean isIntCoordinates() {
		return true;
	}
	
	@Override
	public Point2ifx createTuple(double x, double y) {
		return new Point2ifx(x, y);
	}
	
	@Override
	public Vector2ifx createVector(double x, double y) {
		return new Vector2ifx(x, y);
	}

	@Override
	public Point2ifx createPoint(double x, double y) {
		return new Point2ifx(x, y);
	}

	@Override
	public void operator_andShape2D() {
		Shape2D shape = new Circle2ifx(5, 8, 5);
		assertFalse(createPoint(0,0).operator_and(shape));
		assertFalse(createPoint(11,10).operator_and(shape));
		assertFalse(createPoint(11,50).operator_and(shape));
		assertFalse(createPoint(9,12).operator_and(shape));
		assertTrue(createPoint(9,11).operator_and(shape));
		assertTrue(createPoint(8,12).operator_and(shape));
		assertTrue(createPoint(3,7).operator_and(shape));
		assertFalse(createPoint(10,11).operator_and(shape));
		assertTrue(createPoint(9,10).operator_and(shape));
	}
	
	@Override
	public void operator_upToShape2D() {
		Shape2D shape = new Circle2ifx(5, 8, 5);
		assertEpsilonEquals(0f, createPoint(5,8).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(10,10).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(4,8).operator_upTo(shape));
		assertEpsilonEquals(4.242640687f, createPoint(0,0).operator_upTo(shape));
		assertEpsilonEquals(1f, createPoint(5,14).operator_upTo(shape));
	}


}
