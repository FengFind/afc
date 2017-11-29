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
package fr.utbm.set.jasim.environment.model.perception.tree.structures.octree;

import fr.utbm.set.geom.bounds.CombinableBounds;
import fr.utbm.set.jasim.environment.model.perception.tree.DynamicPerceptionTree;
import fr.utbm.set.jasim.environment.model.perception.tree.manipulators.TreeManipulator;
import fr.utbm.set.jasim.environment.model.world.MobileEntity;

/**
 * This is the generic implementation of a oct-tree
 * containing bounds.
 * 
 * @param <CB> is the type of the bounds common to <var>&lt;DB&gt;</var> and <var>&lt;NB&gt;</var>.
 * @param <DB> is the type of the bounds of the data.
 * @param <D> is the type of the user data inside this tree.
 * @param <NB> is the type of the bounds of the nodes.
 * @param <N> is the type of the nodes in the tree.
 * @param <T> is the type of the tree.
 * @param <M> is the type of the manipulator on the tree.
 * @author $Author: sgalland$
 * @author $Author: ngaud$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class DynamicIcosepOctTree<
					CB extends CombinableBounds<? super CB,?,?,?>,
					DB extends CB,
					D extends MobileEntity<DB>,
					NB extends CB,
					N extends DynamicIcosepOctTreeNode<CB,DB,D,NB,N,T,M>,
					T extends DynamicIcosepOctTree<CB,DB,D,NB,N,T,M>,
					M extends TreeManipulator<D,N,T>>
extends AbstractIcosepOctTree<CB,DB,D,NB,N,T>
implements DynamicPerceptionTree<CB,DB,D,NB,N,T,M> {

	private static final long serialVersionUID = 7189080557818816632L;
	
	private final M manipulator;
	
	/** Empty tree.
	 * 
	 * @param manipulator is the manipulator to use.
	 */
	public DynamicIcosepOctTree(M manipulator) {
		this.manipulator = manipulator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public M getManipulator() {
		return this.manipulator;
	}

}