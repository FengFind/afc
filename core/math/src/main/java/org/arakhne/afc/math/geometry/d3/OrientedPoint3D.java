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

package org.arakhne.afc.math.geometry.d3;

import org.eclipse.xtext.xbase.lib.Pure;

import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2D;
import org.arakhne.afc.vmutil.asserts.AssertMessages;

/** A point 2D with two orientation vectors relative to the polyline: the direction and the normal to the point.
 *
 * <p>The orientation vectors have no physical existence, i.e. they exist only to represent the direction of the
 * point and its normal when the point is part of a polyline. The normal vector is always perpendicular to the
 * direction vector. The point stores its length on the polyline to avoid rounding errors at discretization.
 *
 * @param <ST> is the type of the general implementation.
 * @param <IT> is the type of the implementation of this shape.
 * @param <I> is the type of the path elements.
 * @param <P> is the type of the points.
 * @param <V> is the type of the vectors.
 * @param <B> is the type of the bounding boxes.
 * @author $Author: tpiotrow$
 * @author $Author: sgalland$
 * @author $Author: olamotte$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface OrientedPoint3D<
        ST extends Shape3D<?, ?, I, P, V, B>,
        IT extends OrientedPoint3D<?, ?, I, P, V, B>,
        I extends PathIterator3D<?>,
        P extends Point3D<? super P, ? super V>,
        V extends Vector3D<? super V, ? super P>,
        B extends Shape3D<?, ?, I, P, V, B>>
        extends Shape3D<ST, IT, I, P, V, B> {

    /** Replies the X coordinate of the point.
     *
     * @return the x coordinate of the point.
     */
    @Pure double getX();

    /** Replies the X coordinate of the point.
     *
     * @return the x coordinate of the point.
     */
    @Pure int ix();

    /** Sets a new value in the X of the point.
     *
     * @param x the new value double x.
     */
    void setX(int x);

    /** Sets a new value in the X of the point.
     *
     * @param x the new value double x.
     */
    void setX(double x);

    /** Replies the Y coordinate of the point.
     *
     * @return the y coordinate of the point.
     */
    @Pure double getY();

    /** Replies the Y coordinate of the point.
     *
     * @return the y coordinate of the point.
     */
    @Pure int iy();

    /**  Sets a new value in the Y of the point.
     *
     * @param y the new value double y.
     */
    void setY(int y);

    /**  Sets a new value in the Y of the point.
     *
     * @param y the new value double y.
     */
    void setY(double y);

    /** Replies the Z coordinate of the point.
     *
     * @return the z coordinate of the point.
     */
    @Pure double getZ();

    /** Replies the Z coordinate of the point.
     *
     * @return the z coordinate of the point.
     */
    @Pure int iz();

    /**  Sets a new value in the Z of the point.
     *
     * @param z the new value double z.
     */
    void setZ(int z);

    /**  Sets a new value in the Z of the point.
     *
     * @param z the new value double z.
     */
    void setZ(double z);

    /** Replies the X coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the x coordinate of the direction vector.
     */
    @Pure double getDirectionX();

    /** Replies the X coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the x coordinate of the direction vector.
     */
    @Pure int idx();

    /** Sets a new value in the X direction of the point.
     *
     * @param dirX the new value double x.
     */
    void setDirectionX(int dirX);

    /** Sets a new value in the X direction of the point.
     *
     * @param dirX the new value double x.
     */
    void setDirectionX(double dirX);

    /** Replies the Y coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the y coordinate of the direction vector.
     */
    @Pure double getDirectionY();

    /** Replies the Y coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the y coordinate of the direction vector.
     */
    @Pure int idy();

    /**  Sets a new value in the Y direction of the point.
     *
     * @param dirY the new value double y.
     */
    void setDirectionY(int dirY);

    /**  Sets a new value in the Y direction of the point.
     *
     * @param dirY the new value double y.
     */
    void setDirectionY(double dirY);

    /** Replies the Z coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the z coordinate of the direction vector.
     */
    @Pure double getDirectionZ();

    /** Replies the Z coordinate of the direction vector.
     * If this point is not part of a polyline, the direction vector is null.
     *
     * @return the z coordinate of the direction vector.
     */
    @Pure int idz();

    /**  Sets a new value in the Z direction of the point.
     *
     * @param dirZ the new value double z.
     */
    void setDirectionZ(int dirZ);

    /**  Sets a new value in the Z direction of the point.
     *
     * @param dirZ the new value double z.
     */
    void setDirectionZ(double dirZ);

    /** Replies the X coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the x coordinate of the normal vector.
     */
    @Pure double getNormalX();

    /** Replies the X coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the x coordinate of the normal vector.
     */
    @Pure int inx();

    /** Replies the Y coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the y coordinate of the normal vector.
     */
    @Pure double getNormalY();

    /** Replies the Y coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the y coordinate of the normal vector.
     */
    @Pure int iny();

    /** Replies the Z coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the z coordinate of the normal vector.
     */
    @Pure double getNormalZ();

    /** Replies the Z coordinate of the normal vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the z coordinate of the normal vector.
     */
    @Pure int inz();

    /** Replies the X coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the x coordinate of the sway vector.
     */
    @Pure double getSwayX();

    /** Replies the X coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the x coordinate of the sway vector.
     */
    @Pure int isx();

    /** Replies the Y coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the y coordinate of the sway vector.
     */
    @Pure double getSwayY();

    /** Replies the Y coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the y coordinate of the sway vector.
     */
    @Pure int isy();

    /** Replies the Z coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the z coordinate of the sway vector.
     */
    @Pure double getSwayZ();

    /** Replies the Z coordinate of the sway vector.
     *  If this point is not part of a polyline, the normal vector is null.
     *
     * @return the z coordinate of the sway vector.
     */
    @Pure int isz();

    /** Replies the geometrical length of this point on
     * the polyline.
     *
     * @return the length of the point on the polyline
     */
    @Pure double getLength();

    /** Replies the geometrical length of this point on
     * the polyline.
     *
     * @return the length of the point on the polyline
     */
    @Pure int ilen();

    /** Sets a new value for the length of the point.
     *
     * @param length the length of the point on the polyline.
     */
    void setLength(int length);

    /** Sets a new value for the length of the point.
     *
     * @param length the length of the point on the polyline.
     */
    void setLength(double length);

    @Override
    default boolean contains(Point3D<?, ?> pt) {
        return getX() == pt.getX() && getY() == pt.getY() && getZ() == pt.getZ();
    }

    @Override
    @SuppressWarnings("checkstyle:booleanexpressioncomplexity")
    default boolean equalsToShape(IT shape) {
        if (shape == null) {
            return false;
        }
        if (shape == this) {
            return true;
        }
        // We don't need to check normal vector because it depends of direction and normal
        return getX() == shape.getX() && getY() == shape.getY() && getZ() == shape.getZ()
                && getDirectionX() == shape.getDirectionX()
                && getDirectionY() == shape.getDirectionY()
                && getDirectionZ() == shape.getDirectionZ()
                && getNormalX() == shape.getNormalX()
                && getNormalY() == shape.getNormalY()
                && getNormalZ() == shape.getNormalZ();
    }

    /** Replies this point.
     * @return this point
     */
    default P getPoint() {
        return getGeomFactory().newPoint(getX(), getY(), getZ());
    }

    @Override
    default double getDistanceL1(Point3D<?, ?> pt) {
        assert pt != null : AssertMessages.notNullParameter();
        return Point3D.getDistanceL1PointPoint(getX(), getY(), getZ(), pt.getX(), pt.getY(), pt.getZ());
    }

    @Override
    default double getDistanceLinf(Point3D<?, ?> pt) {
        assert pt != null : AssertMessages.notNullParameter();
        return Point3D.getDistanceLinfPointPoint(getX(), getY(), getZ(), pt.getX(), pt.getY(), pt.getZ());
    }

    @Override
    default double getDistanceSquared(Point3D<?, ?> pt) {
        assert pt != null : AssertMessages.notNullParameter();
        return Point3D.getDistanceSquaredPointPoint(getX(), getY(), getZ(), pt.getX(), pt.getY(), pt.getZ());
    }

    @Override
    default double getDistance(Point3D<?, ?> pt) {
        assert pt != null : AssertMessages.notNullParameter();
        return Point3D.getDistancePointPoint(getX(), getY(), getZ(), pt.getX(), pt.getY(), pt.getZ());
    }

    @Override
    default P getClosestPointTo(Point3D<?, ?> point) {
        return getPoint();
    }

    @Override
    default P getFarthestPointTo(Point3D<?, ?> point) {
        return getPoint();
    }

    @Override
    default boolean isEmpty() {
        return false;
    }

    /** Change the point.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     */
    default void set(int x, int y, int z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /** Change the point.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param length the length of the point on the polyline.
     */
    default void set(int x, int y, int z, int length) {
        setX(x);
        setY(y);
        setZ(z);
        setLength(length);
    }

    /** Change the point and its orientation vector.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param dirX x coordinate of the vector.
     * @param dirY y coordinate of the vector.
     * @param dirZ z coordinate of the vector.
     */
    default void set(int x, int y, int z, int dirX, int dirY, int dirZ) {
        setX(x);
        setY(y);
        setZ(z);
        setDirectionX(dirX);
        setDirectionY(dirY);
        setDirectionZ(dirZ);
    }

    /** Change the point and its orientation vector.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param length the length of the point on the polyline.
     * @param dirX x coordinate of the vector.
     * @param dirY y coordinate of the vector.
     * @param dirZ z coordinate of the vector.
     */
    default void set(int x, int y, int z, int length, int dirX, int dirY, int dirZ) {
        setX(x);
        setY(y);
        setZ(z);
        setLength(length);
        setDirectionX(dirX);
        setDirectionY(dirY);
        setDirectionZ(dirZ);
    }

    /** Change the point.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     */
    default void set(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /** Change the point.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param length the length of the point on the polyline.
     */
    default void set(double x, double y, double z, double length) {
        setX(x);
        setY(y);
        setZ(z);
        setLength(length);
    }

    /** Change the point and its orientation vector.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param dirX x coordinate of the vector.
     * @param dirY y coordinate of the vector.
     * @param dirZ z coordinate of the vector.
     */
    default void set(double x, double y, double z, double dirX, double dirY, double dirZ) {
        setX(x);
        setY(y);
        setZ(z);
        setDirectionX(dirX);
        setDirectionY(dirY);
        setDirectionZ(dirZ);
    }

    /** Change the point and its orientation vector.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @param z z coordinate of the point.
     * @param length the length of the point on the polyline.
     * @param dirX x coordinate of the vector.
     * @param dirY y coordinate of the vector.
     * @param dirZ z coordinate of the vector.
     */
    default void set(double x, double y, double z, double length, double dirX, double dirY, double dirZ) {
        setX(x);
        setY(y);
        setLength(length);
        setDirectionX(dirX);
        setDirectionY(dirY);
    }

    // TODO : make set(x, y, z dirX, dirY, dirZ, normX, normY, normZ) ? w/o length ? {int,double} ?

    @Override
    default void set(IT shape) {
        assert shape != null : AssertMessages.notNullParameter();
        set(shape.getX(), shape.getY(), shape.getZ(), shape.getLength(), shape.getDirectionX(), shape.getDirectionY(),
                shape.getDirectionZ());
    }

    @Override
    default void clear() {
        set(0, 0, 0, 0, 0, 0);
    }

    // TODO : make turn method mirroring Point3D future implementation

    /** Turn this point about the given rotation angle around the origin point.
     *
     * <p>The rotation is done according to the trigonometric coordinate.
     * A positive rotation angle corresponds to a left or right rotation
     * according to the current {@link CoordinateSystem2D}.
     *
     * @param angle is the rotation angle in radians.
     * @see #turn(double, OrientedPoint3D, OrientedPoint3D)
     */
    default void turn(double angle) {
        //
    }

    /** Turn the given point about the given rotation angle around the origin point, and set this
     * point with the result.
     *
     * <p>The rotation is done according to the trigonometric coordinate.
     * A positive rotation angle corresponds to a left or right rotation
     * according to the current {@link CoordinateSystem2D}.
     *
     * @param angle is the rotation angle in radians.
     * @param pointToTurn the point to turn.
     * @see #turn(double, OrientedPoint3D, OrientedPoint3D)
     * @see #turn(double)
     */
    default void turn(double angle, OrientedPoint3D<?, ?, ?, ?, ?, ?> pointToTurn) {
        //
    }

    /** Turn the given point about the given rotation angle around the origin point, and set this
     * point with the result.
     *
     * <p>The rotation is done according to the trigonometric coordinate.
     * A positive rotation angle corresponds to a left or right rotation
     * according to the current {@link CoordinateSystem2D}.
     *
     * @param angle is the rotation angle in radians.
     * @param pointToTurn the point to turn.
     * @param origin the origin point.
     * @see #turn(double, OrientedPoint3D)
     * @see #turn(double)
     */
    default void turn(double angle, OrientedPoint3D<?, ?, ?, ?, ?, ?> pointToTurn, OrientedPoint3D<?, ?, ?, ?, ?, ?> origin) {
        //
    }

}
