/* 
 * $Id$
 * 
 * Copyright (c) 2006-09, Multiagent Team - Systems and Transportation Laboratory (SeT)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Systems and Transportation Laboratory ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 *
 * http://www.multiagent.fr/
 */
package fr.utbm.set.jasim.environment.model.perception.algorithm;

import fr.utbm.set.geom.bounds.bounds3d.CombinableBounds3D;
import fr.utbm.set.jasim.environment.interfaces.body.AgentBody3D;
import fr.utbm.set.jasim.environment.interfaces.body.perceptions.InterestFilter;
import fr.utbm.set.jasim.environment.interfaces.body.perceptions.PhysicalPerceptionAlterator;
import fr.utbm.set.jasim.environment.model.perception.frustum.Frustum3D;
import fr.utbm.set.jasim.environment.model.perception.tree.DynamicPerceptionTree;
import fr.utbm.set.jasim.environment.model.perception.tree.StaticPerceptionTree;
import fr.utbm.set.jasim.environment.model.perception.tree.cullers.BottomUpFrustumCullingIterator3D;
import fr.utbm.set.jasim.environment.model.perception.tree.cullers.FrustumCuller;
import fr.utbm.set.jasim.environment.model.perception.tree.manipulators.TreeManipulator3D;
import fr.utbm.set.jasim.environment.model.perceptions.PerceptionGeneratorType;
import fr.utbm.set.jasim.environment.model.world.Entity3D;
import fr.utbm.set.jasim.environment.model.world.MobileEntity3D;

/**
 * This perception algorithm is going top-down for static 
 * tree and bottom-up for dynamic tree.<br>
 * 
 * @param <SB> is the bounds of the static entities.
 * @param <MB> is the bounds of the mobile entities.
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SequentialHybridPerceptionAlgorithm<SB extends CombinableBounds3D, MB extends CombinableBounds3D>
extends AbstractSequentialPerceptionAlgorithm<SB,MB> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PerceptionGeneratorType getPerceptionGeneratorType() {
		return PerceptionGeneratorType.LOCAL_SEQUENTIAL_BOTTOMUP;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FrustumCuller<CombinableBounds3D, MB, MobileEntity3D<MB>, ?, ?> createDynamicCuller(
			DynamicPerceptionTree<CombinableBounds3D, MB, MobileEntity3D<MB>, ?, ?, ?, ? extends TreeManipulator3D<MB, ?, ?>> tree,
			AgentBody3D<?,?,MB> entity,
			Frustum3D frustum, PhysicalPerceptionAlterator physicFilter,
			InterestFilter interestFilter) {
		BottomUpFrustumCullingIterator3D<MB,MobileEntity3D<MB>,?,?> iterator =
			new BottomUpFrustumCullingIterator3D(
				tree,
				entity,
				frustum);
		return new FrustumCuller(iterator, frustum, physicFilter, interestFilter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FrustumCuller<CombinableBounds3D, SB, Entity3D<SB>, ?, ?> createStaticCuller(
			StaticPerceptionTree<CombinableBounds3D, SB, Entity3D<SB>, ?, ?> tree,
			AgentBody3D<?,?,MB> entity,
			Frustum3D frustum, PhysicalPerceptionAlterator physicFilter,
			InterestFilter interestFilter) {
		return tree.getFrustumCuller(frustum, physicFilter, interestFilter);
	}

}
