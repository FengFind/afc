/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2018 The original authors, and other authors.
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

package org.arakhne.afc.math.geometry.d3.dfx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import org.eclipse.xtext.xbase.lib.Pure;

import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.geometry.MathFXAttributeNames;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d3.afp.PathElement3afp;
import org.arakhne.afc.vmutil.ReflectionUtil;
import org.arakhne.afc.vmutil.asserts.AssertMessages;

/** An element of the path.
 *
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @author $Author: tpiotrow$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public abstract class PathElement3dfx implements PathElement3afp {

	private static final long serialVersionUID = 1724746568685625149L;

	/** Type of the element.
	 */
	protected final PathElementType type;

	/** Target point.
	 */
	protected Point3dfx to = new Point3dfx();

	/** Is Empty property.
	 */
	protected ReadOnlyBooleanWrapper isEmpty;

	/** Constructor.
	 * @param type is the type of the element.
	 * @param tox the x coordinate of the target point.
	 * @param toy the x coordinate of the target point.
	 * @param toz the x coordinate of the target point.
	 */
	PathElement3dfx(PathElementType type, DoubleProperty tox, DoubleProperty toy, DoubleProperty toz) {
	    assert type != null : AssertMessages.notNullParameter(0);
	    assert tox != null : AssertMessages.notNullParameter(1);
	    assert toy != null : AssertMessages.notNullParameter(2);
	    this.type = type;
	    this.to.x = tox;
	    this.to.y = toy;
	    this.to.z = toz;
	}

	/** Constructor by setting.
	 * @param type is the type of the element.
	 * @param toPoint the point to set as the target point.
	 */
	PathElement3dfx(PathElementType type, Point3dfx toPoint) {
		assert type != null : AssertMessages.notNullParameter(0);
		assert toPoint != null : AssertMessages.notNullParameter(1);
		this.type = type;
		this.to = toPoint;
	}

	@Pure
	@Override
	public String toString() {
		return ReflectionUtil.toString(this);
	}

	@Pure
	@Override
	public abstract boolean equals(Object obj);

	@Pure
	@Override
	public abstract int hashCode();

	/** Replies the property that indicates if this patth element is empty.
	 *
	 * @return the isEmpty property.
	 */
	public abstract BooleanProperty isEmptyProperty();

	@Override
	public boolean isEmpty() {
		return isEmptyProperty().get();
	}

	@Pure
	@Override
	public final double getToX() {
		return this.to.getX();
	}

	@Pure
	@Override
	public final double getToY() {
		return this.to.getY();
	}

	@Pure
	@Override
	public final double getToZ() {
		return this.to.getZ();
	}

	/** Replies the x coordinate of the starting point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO}.
	 */
	@Pure
	public abstract DoubleProperty fromXProperty();

	/** Replies the y coordinate of the starting point property.
	 *
	 * @return the y coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO}.
	 */
	@Pure
	public abstract DoubleProperty fromYProperty();

	/** Replies the z coordinate of the starting point property.
	 *
	 * @return the z coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO}.
	 */
	@Pure
	public abstract DoubleProperty fromZProperty();

	/** Replies the x coordinate of the first control point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlX1Property();

	/** Replies the y coordinate of the first control point property.
	 *
	 * @return the y coordinate, or {@link Double#NaN} if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlY1Property();

	/** Replies the z coordinate of the first control point property.
	 *
	 * @return the z coordinate, or {@link Double#NaN} if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlZ1Property();

	/** Replies the x coordinate of the second control point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, {@link PathElementType#QUAD_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlX2Property();

	/** Replies the y coordinate of the second  control point property.
	 *
	 * @return the y coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, {@link PathElementType#QUAD_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlY2Property();

	/** Replies the z coordinate of the second  control point property.
	 *
	 * @return the z coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, {@link PathElementType#QUAD_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlZ2Property();

	/** Replies the x coordinate of the target point property.
	 *
	 * @return the x coordinate.
	 */
	@Pure
	public DoubleProperty toXProperty() {
		return this.to.xProperty();
	}

	/** Replies the y coordinate of the target point property.
	 *
	 * @return the y coordinate.
	 */
	@Pure
	public DoubleProperty toYProperty() {
		return this.to.yProperty();
	}

	/** Replies the z coordinate of the target point property.
	 *
	 * @return the z coordinate.
	 */
	@Pure
	public DoubleProperty toZProperty() {
		return this.to.zProperty();
	}

	@Pure
	@Override
	public final PathElementType getType() {
		return this.type;
	}

	/** Copy the coords into the given array, except the source point.
	 *
	 * @param array the output array.
	 */
	@Pure
	public abstract void toArray(DoubleProperty[] array);

	/** Copy the coords into an array, except the source point.
	 *
	 * @return the array of the points, except the source point.
	 */
	@Pure
	public abstract DoubleProperty[] toArray();

	/** An element of the path that represents a <code>MOVE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class MovePathElement3dfx extends PathElement3dfx {
		private static final long serialVersionUID = 4465791748559255427L;

		/** Constructor.
		 * @param tox x coordinate of the target point.
		 * @param toy y coordinate of the target point.
		 * @param toz z coordinate of the target point.
		 */
		MovePathElement3dfx(DoubleProperty tox, DoubleProperty toy, DoubleProperty toz) {
		    super(PathElementType.MOVE_TO, tox, toy, toz);
		}

		/** Constructor by setting.
         * @param toPoint the point to set as the target point.
         */
		MovePathElement3dfx(Point3dfx toPoint) {
			super(PathElementType.MOVE_TO, toPoint);
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				final PathElement3afp elt = (PathElement3afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getToZ() == elt.getToZ();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getToZ());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, MathFXAttributeNames.IS_EMPTY, true);
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return false;
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.ix();
			array[1] = this.to.iy();
			array[2] = this.to.iz();
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.getX();
			array[1] = this.to.getY();
			array[2] = this.to.getZ();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.xProperty();
			array[1] = this.to.yProperty();
			array[2] = this.to.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.to.xProperty(), this.to.yProperty(), this.to.zProperty()};
		}

		@Pure
		@Override
		public double getFromX() {
			return 0.;
		}

		@Pure
		@Override
		public double getFromY() {
			return 0.;
		}

		@Pure
		@Override
		public double getFromZ() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty fromZProperty() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ2Property() {
			return null;
		}

	}

	/** An element of the path that represents a <code>LINE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class LinePathElement3dfx extends PathElement3dfx {

		private static final long serialVersionUID = -8828290765080530997L;

		private Point3dfx from = new Point3dfx();

		/** Constructor.
		 * @param fromx x coordinate of the origin point.
		 * @param fromy y coordinate of the origin point.
		 * @param fromz z coordinate of the origin point.
		 * @param tox x coordinate of the target point.
		 * @param toy y coordinate of the target point.
		 * @param toz z coordinate of the target point.
		 */
		LinePathElement3dfx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty fromz, DoubleProperty tox,
		        DoubleProperty toy, DoubleProperty toz) {
		    super(PathElementType.LINE_TO, tox, toy, toz);
		    assert fromx != null : AssertMessages.notNullParameter(0);
		    assert fromy != null : AssertMessages.notNullParameter(1);
		    assert fromz != null : AssertMessages.notNullParameter(2);
		    this.from.x = fromx;
		    this.from.y = fromy;
		    this.from.z = fromz;
		}

		/** Constructor bt setting.
		 * @param fromPoint the point to set as the origin point.
		 * @param toPoint the point to set as the target point.
		 */
		LinePathElement3dfx(Point3dfx fromPoint, Point3dfx toPoint) {
			super(PathElementType.LINE_TO, toPoint);
			assert fromPoint != null : AssertMessages.notNullParameter(0);
			this.from = fromPoint;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				final PathElement3afp elt = (PathElement3afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getToZ() == elt.getToZ()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY()
						&& getFromZ() == elt.getFromZ();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getToZ());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			bits = 31L * bits + Double.doubleToLongBits(getFromZ());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, MathFXAttributeNames.IS_EMPTY);
				this.isEmpty.bind(Bindings.createBooleanBinding(() ->
				    MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(fromZProperty().get(), toZProperty().get()),
						fromXProperty(), toXProperty(), fromYProperty(), toYProperty(), fromZProperty(), toZProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.ix();
			array[1] = this.to.iy();
			array[2] = this.to.iz();
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.getX();
			array[1] = this.to.getY();
			array[2] = this.to.getZ();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.xProperty();
			array[1] = this.to.yProperty();
			array[2] = this.to.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.to.xProperty(), this.to.yProperty(), this.to.zProperty()};
		}

		@Pure
		@Override
		public double getFromX() {
			return this.from.getX();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.from.getY();
		}

		@Pure
		@Override
		public double getFromZ() {
			return this.from.getZ();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.from.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.from.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromZProperty() {
			return this.from.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ2Property() {
			return null;
		}

	}

	/** An element of the path that represents a <code>QUAD_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	@SuppressWarnings("checkstyle:magicnumber")
	static class QuadPathElement3dfx extends PathElement3dfx {

		private static final long serialVersionUID = 4782822639304211439L;

		private Point3dfx from = new Point3dfx();

		private Point3dfx ctrl = new Point3dfx();

		/** Constructor.
		 * @param fromx x coordinate of the origin point.
		 * @param fromy y coordinate of the origin point.
		 * @param fromz z coordinate of the origin point.
		 * @param ctrlx x coordinate of the control point.
		 * @param ctrly y coordinate of the control point.
		 * @param ctrlz z coordinate of the control point.
		 * @param tox x coordinate of the target point.
		 * @param toy y coordinate of the target point.
		 * @param toz z coordinate of the target point.
		 */
		QuadPathElement3dfx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty fromz, DoubleProperty ctrlx,
		        DoubleProperty ctrly, DoubleProperty ctrlz, DoubleProperty tox, DoubleProperty toy, DoubleProperty toz) {
		    super(PathElementType.QUAD_TO, tox, toy, toz);
		    assert fromx != null : AssertMessages.notNullParameter(0);
		    assert fromy != null : AssertMessages.notNullParameter(1);
		    assert fromz != null : AssertMessages.notNullParameter(2);
		    assert ctrlx != null : AssertMessages.notNullParameter(3);
		    assert ctrly != null : AssertMessages.notNullParameter(4);
		    assert ctrlz != null : AssertMessages.notNullParameter(5);
		    this.from.x = fromx;
		    this.from.y = fromy;
		    this.from.z = fromz;
		    this.ctrl.x = ctrlx;
		    this.ctrl.y = ctrly;
		    this.ctrl.z = ctrlz;
		}

		/** Constructor by setting.
		 * @param fromPoint the point to set as the origin point.
		 * @param ctrlPoint the point to set as the control point.
		 * @param toPoint the point to set as the target point.
		 */
		@SuppressWarnings("checkstyle:magicnumber")
		QuadPathElement3dfx(Point3dfx fromPoint, Point3dfx ctrlPoint, Point3dfx toPoint) {
		    super(PathElementType.QUAD_TO, toPoint);
		    assert fromPoint != null : AssertMessages.notNullParameter(0);
			assert ctrlPoint != null : AssertMessages.notNullParameter(1);
			this.from = fromPoint;
			this.ctrl = ctrlPoint;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				final PathElement3afp elt = (PathElement3afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getToZ() == elt.getToZ()
						&& getCtrlX1() == elt.getCtrlX1()
						&& getCtrlY1() == elt.getCtrlY1()
						&& getCtrlZ1() == elt.getCtrlZ1()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY()
						&& getFromZ() == elt.getFromZ();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getToZ());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlZ1());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			bits = 31L * bits + Double.doubleToLongBits(getFromZ());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, MathFXAttributeNames.IS_EMPTY);
				this.isEmpty.bind(Bindings.createBooleanBinding(() ->
				    MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(fromZProperty().get(), toZProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlX1Property().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlY1Property().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlZ1Property().get(), toZProperty().get()),
						fromXProperty(), toXProperty(), fromYProperty(), toYProperty(), fromZProperty(), toZProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 6 : AssertMessages.tooSmallArrayParameter(0, 6);
			array[0] = this.ctrl.ix();
			array[1] = this.ctrl.iy();
			array[2] = this.ctrl.iz();
			array[3] = this.to.ix();
			array[4] = this.to.iy();
			array[5] = this.to.iz();
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 6 : AssertMessages.tooSmallArrayParameter(0, 6);
			array[0] = this.ctrl.getX();
			array[1] = this.ctrl.getY();
			array[2] = this.ctrl.getZ();
			array[4] = this.to.getX();
			array[5] = this.to.getY();
			array[6] = this.to.getZ();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 6 : AssertMessages.tooSmallArrayParameter(0, 6);
			array[0] = this.ctrl.xProperty();
			array[1] = this.ctrl.yProperty();
			array[2] = this.ctrl.zProperty();
			array[3] = this.to.xProperty();
			array[4] = this.to.yProperty();
			array[5] = this.to.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
            return new DoubleProperty[] {this.ctrl.xProperty(), this.ctrl.yProperty(), this.ctrl.zProperty(),
                    this.to.xProperty(), this.to.yProperty(), this.to.zProperty(), };
		}

		@Pure
		@Override
		public double getFromX() {
			return this.from.getX();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.from.getY();
		}

		@Pure
		@Override
		public double getFromZ() {
			return this.from.getZ();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return this.ctrl.getX();
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return this.ctrl.getY();
		}

		@Pure
		@Override
		public double getCtrlZ1() {
			return this.ctrl.getZ();
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.from.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.from.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromZProperty() {
			return this.from.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return this.ctrl.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return this.ctrl.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ1Property() {
			return this.ctrl.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ2Property() {
			return null;
		}

	}

	/** An element of the path that represents a <code>CURVE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	@SuppressWarnings("checkstyle:magicnumber")
	static class CurvePathElement3dfx extends PathElement3dfx {

		private static final long serialVersionUID = -2831895270995173092L;

		private Point3dfx from = new Point3dfx();

		private Point3dfx ctrl1 = new Point3dfx();

		private Point3dfx ctrl2 = new Point3dfx();

		/** Constructor.
		 * @param fromx x coordinate of the origin point.
		 * @param fromy y coordinate of the origin point.
		 * @param fromz z coordinate of the origin point.
		 * @param ctrlx1 x coordinate of the first control point.
		 * @param ctrly1 y coordinate of the first control point.
		 * @param ctrlz1 z coordinate of the first control point.
		 * @param ctrlx2 x coordinate of the second control point.
		 * @param ctrly2 y coordinate of the second control point.
		 * @param ctrlz2 z coordinate of the second control point.
		 * @param tox x coordinate of the target point.
		 * @param toy y coordinate of the target point.
		 * @param toz z coordinate of the target point.
		 */
		@SuppressWarnings({"checkstyle:parameternumber", "checkstyle:magicnumber"})
		CurvePathElement3dfx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty fromz, DoubleProperty ctrlx1,
		        DoubleProperty ctrly1, DoubleProperty ctrlz1, DoubleProperty ctrlx2, DoubleProperty ctrly2, DoubleProperty ctrlz2,
		        DoubleProperty tox, DoubleProperty toy, DoubleProperty toz) {
		    super(PathElementType.CURVE_TO, tox, toy, toz);
		    assert fromx != null : AssertMessages.notNullParameter(0);
		    assert fromy != null : AssertMessages.notNullParameter(1);
		    assert fromz != null : AssertMessages.notNullParameter(2);
		    assert ctrlx1 != null : AssertMessages.notNullParameter(3);
		    assert ctrly1 != null : AssertMessages.notNullParameter(4);
		    assert ctrlz1 != null : AssertMessages.notNullParameter(5);
		    assert ctrlx2 != null : AssertMessages.notNullParameter(6);
		    assert ctrly2 != null : AssertMessages.notNullParameter(7);
		    assert ctrlz2 != null : AssertMessages.notNullParameter(8);
		    this.from.x = fromx;
		    this.from.y = fromy;
		    this.from.z = fromz;
		    this.ctrl1.x = ctrlx1;
		    this.ctrl1.y = ctrly1;
		    this.ctrl1.z = ctrlz1;
		    this.ctrl2.x = ctrlx2;
		    this.ctrl2.y = ctrly2;
		    this.ctrl2.z = ctrlz2;
		}

		/** Constructor by setting.
		 * @param fromPoint the point to set as the origin point.
		 * @param ctrl1Point the point to set as the first control point.
		 * @param ctrl2Point the point to set as the second control point.
		 * @param toPoint the point to set as the target point.
		 */
		@SuppressWarnings({"checkstyle:parameternumber", "checkstyle:magicnumber"})
		CurvePathElement3dfx(Point3dfx fromPoint, Point3dfx ctrl1Point, Point3dfx ctrl2Point,
		        Point3dfx toPoint) {
			super(PathElementType.CURVE_TO, toPoint);
			assert fromPoint != null : AssertMessages.notNullParameter(0);
			assert ctrl1Point != null : AssertMessages.notNullParameter(1);
			assert ctrl2Point != null : AssertMessages.notNullParameter(2);
			this.from = fromPoint;
			this.ctrl1 = ctrl1Point;
			this.ctrl2 = ctrl2Point;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				final PathElement3afp elt = (PathElement3afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getToZ() == elt.getToZ()
						&& getCtrlX1() == elt.getCtrlX1()
						&& getCtrlY1() == elt.getCtrlY1()
						&& getCtrlZ1() == elt.getCtrlZ1()
						&& getCtrlX2() == elt.getCtrlX2()
						&& getCtrlY2() == elt.getCtrlY2()
						&& getCtrlZ2() == elt.getCtrlZ2()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY()
						&& getFromZ() == elt.getFromZ();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getToZ());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlZ1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX2());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY2());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlZ2());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			bits = 31L * bits + Double.doubleToLongBits(getFromZ());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		@SuppressWarnings("checkstyle:booleanexpressioncomplexity")
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, MathFXAttributeNames.IS_EMPTY);
				this.isEmpty.bind(Bindings.createBooleanBinding(() ->
				    MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(fromZProperty().get(), toZProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlX1Property().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlY1Property().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlZ1Property().get(), toZProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlX2Property().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlY2Property().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(ctrlZ2Property().get(), toZProperty().get()),
						fromXProperty(), toXProperty(), fromYProperty(), toYProperty(), fromZProperty(), toZProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 9 : AssertMessages.tooSmallArrayParameter(0, 9);
			array[0] = this.ctrl1.ix();
			array[1] = this.ctrl1.iy();
			array[2] = this.ctrl1.iz();
			array[3] = this.ctrl2.ix();
			array[4] = this.ctrl2.iy();
			array[5] = this.ctrl2.iz();
			array[6] = this.to.ix();
			array[7] = this.to.iy();
			array[8] = this.to.iz();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 9 : AssertMessages.tooSmallArrayParameter(0, 9);
			array[0] = this.ctrl1.xProperty();
			array[1] = this.ctrl1.yProperty();
			array[2] = this.ctrl1.zProperty();
			array[3] = this.ctrl2.xProperty();
			array[4] = this.ctrl2.yProperty();
			array[5] = this.ctrl2.zProperty();
			array[6] = this.to.xProperty();
			array[7] = this.to.yProperty();
			array[8] = this.to.zProperty();
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 9 : AssertMessages.tooSmallArrayParameter(0, 9);
			array[0] = this.ctrl1.getX();
			array[1] = this.ctrl1.getY();
			array[2] = this.ctrl1.getZ();
			array[3] = this.ctrl2.getX();
			array[4] = this.ctrl2.getY();
			array[5] = this.ctrl2.getZ();
			array[6] = this.to.getX();
			array[7] = this.to.getY();
			array[8] = this.to.getZ();
		}

		@Pure
		@Override
		@SuppressWarnings("checkstyle:arraytrailingcomma")
		public DoubleProperty[] toArray() {
            return new DoubleProperty[] {this.ctrl1.xProperty(), this.ctrl1.yProperty(), this.ctrl1.zProperty(),
                    this.ctrl2.xProperty(), this.ctrl2.yProperty(), this.ctrl2.zProperty(), this.to.xProperty(),
                    this.to.yProperty(), this.to.zProperty() };
		}

		@Pure
		@Override
		public double getFromX() {
			return this.from.getX();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.from.getY();
		}

		@Pure
		@Override
		public double getFromZ() {
			return this.from.getZ();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return this.ctrl1.xProperty().get();
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return this.ctrl1.yProperty().get();
		}

		@Pure
		@Override
		public double getCtrlZ1() {
			return this.ctrl1.zProperty().get();
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return this.ctrl2.xProperty().get();
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return this.ctrl2.yProperty().get();
		}

		@Pure
		@Override
		public double getCtrlZ2() {
			return this.ctrl2.zProperty().get();
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.from.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.from.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromZProperty() {
			return this.from.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return this.ctrl1.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return this.ctrl1.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ1Property() {
			return this.ctrl1.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return this.ctrl2.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return this.ctrl2.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ2Property() {
			return this.ctrl2.zProperty();
		}

	}

	/** An element of the path that represents a <code>CLOSE</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class ClosePathElement3dfx extends PathElement3dfx {

		private static final long serialVersionUID = 5324688417590599323L;

		private Point3dfx from = new Point3dfx();

		/** Constructor.
		 * @param fromx x coordinate of the origin point.
		 * @param fromy y coordinate of the origin point.
		 * @param fromz z coordinate of the origin point.
		 * @param tox x coordinate of the target point.
		 * @param toy y coordinate of the target point.
		 * @param toz z coordinate of the target point.
		 */
		ClosePathElement3dfx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty fromz, DoubleProperty tox,
		        DoubleProperty toy, DoubleProperty toz) {
		    super(PathElementType.CLOSE, tox, toy, toz);
		    assert fromx != null : AssertMessages.notNullParameter(0);
		    assert fromy != null : AssertMessages.notNullParameter(1);
		    assert fromz != null : AssertMessages.notNullParameter(2);
		    this.from.x = fromx;
		    this.from.y = fromy;
		    this.from.z = fromz;
		}

		/** Constructor by copy.
		 * @param fromPoint the point to set as the origin point.
		 * @param toPoint the point to set as the target point.
		 */
		ClosePathElement3dfx(Point3dfx fromPoint, Point3dfx toPoint) {
			super(PathElementType.CLOSE, toPoint);
			assert fromPoint != null : AssertMessages.notNullParameter(0);
			this.from = fromPoint;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				final PathElement3afp elt = (PathElement3afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getToZ() == elt.getToZ()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY()
						&& getFromZ() == elt.getFromZ();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getToZ());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			bits = 31L * bits + Double.doubleToLongBits(getFromZ());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, MathFXAttributeNames.IS_EMPTY);
				this.isEmpty.bind(Bindings.createBooleanBinding(() ->
				    MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
				            && MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
				            && MathUtil.isEpsilonEqual(fromZProperty().get(), toZProperty().get()),
						fromXProperty(), toXProperty(), fromYProperty(), toYProperty(), fromZProperty(), toZProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = (int) this.to.getX();
			array[1] = (int) this.to.getY();
			array[2] = (int) this.to.getZ();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.tooSmallArrayParameter(0, 3);
			array[0] = this.to.xProperty();
			array[1] = this.to.yProperty();
			array[2] = this.to.zProperty();
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert array != null : AssertMessages.notNullParameter();
			assert array.length >= 3 : AssertMessages.notNullParameter();
			array[0] = this.to.getX();
			array[1] = this.to.getY();
			array[2] = this.to.getZ();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.to.xProperty(), this.to.yProperty(), this.to.zProperty()};
		}

		@Pure
		@Override
		public double getFromX() {
			return this.from.getX();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.from.getY();
		}

		@Pure
		@Override
		public double getFromZ() {
			return this.from.getZ();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlZ2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.from.xProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.from.yProperty();
		}

		@Pure
		@Override
		public DoubleProperty fromZProperty() {
			return this.from.zProperty();
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlZ2Property() {
			return null;
		}

	}

}
