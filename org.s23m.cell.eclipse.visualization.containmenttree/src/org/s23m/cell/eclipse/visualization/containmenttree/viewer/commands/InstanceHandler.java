package org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands;

import java.util.HashMap;
import java.util.Map;

import org.s23m.cell.SemanticStateOfInMemoryModel;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.api.models2.RepositoryStructure;
import org.s23m.cell.serialization.Gmodel;
import org.s23m.cell.serialization.serializer.InstanceMap;
import org.s23m.cell.serialization.serializer.SerializationType;
import org.s23m.cell.serialization.serializer.Serializer;
import org.s23m.cell.serialization.serializer.SerializerHolder;

public class InstanceHandler {

	private final Serializer serializer;
	private final InstanceMap instanceMap;

	private static class InstanceHolder {
		public static final InstanceHandler INSTANCE = new InstanceHandler();
	}

	public static InstanceHandler getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private InstanceHandler() {
		serializer = SerializerHolder.getGmodelInstanceSerializer(SerializationType.XML);
		instanceMap = InstanceMap.getInstance();
	}

	private Map<String, Gmodel> fetchRequiredSerializedArtifacts(final String uuid) {
		final Map<String,Gmodel> artifacts = new HashMap<String, Gmodel>();
		fetchArtifact(uuid, artifacts, true);
		return artifacts;
	}

	public void doInitialFullDeserialization() throws IllegalArgumentException, IllegalAccessException {
		if ( SemanticStateOfInMemoryModel.gmodelEditorIsLive()) {
			//instanceMap.reset();
			final Map<String,Gmodel> artifacts = fetchRequiredSerializedArtifacts(Root.root.identity().uniqueRepresentationReference().toString());
			//InstanceBuilder.decommssionOutdatedOuterShellInstances();
			serializer.doInitialFullDeserialization(artifacts);
		} else {
			instanceMap.reset();
			throw new IllegalAccessException("Basic repository structure cannot be found");
		}
	}

	public void doCoordinateInstanceDeserialization() throws IllegalArgumentException, IllegalAccessException {
		if ( SemanticStateOfInMemoryModel.gmodelEditorIsLive()) {
			final Map<String,Gmodel> artifacts = fetchRequiredSerializedArtifacts(RepositoryStructure.graphVisualizations.identity().uniqueRepresentationReference().toString());
			serializer.deserializeInstances(artifacts);
		} else {
			instanceMap.reset();
			throw new IllegalAccessException("Basic repository structure cannot be found");
		}
	}

	//Fetch an instance with the UUID and deserialize it
	public void deserializeInstance(final String uuid) throws IllegalAccessException {
		if ( SemanticStateOfInMemoryModel.gmodelEditorIsLive()) {
			final Map<String, Gmodel> artifacts = new HashMap<String, Gmodel>();
			fetchArtifact(uuid,artifacts, false);
			instanceMap.removeFromBuiltInstances(uuid);
			serializer.deserializeInstances(artifacts);
		} else {
			throw new IllegalAccessException("Basic repository structure cannot be found");
		}
	}

	public void fetchArtifact(final String uuid, final Map<String,Gmodel> artifacts, final boolean isRecursive) {
//		System.err.println("Fetching "+uuid);
//		try {
//		final String serializedInstance = ((Connector)Mediator.getComponent(ProtocolType.REPOSITORY)).getArtefact(uuid);
//		final Gmodel rootModel = serializer.unmarshallModel(serializedInstance);
//		artifacts.put(uuid, rootModel);
//		if (isRecursive) {
//			final Gmodel.Instance rootSet = rootModel.getInstance().get(0);
//			for (final InstanceType connectedInstance : rootSet.getInstance()) {
//				final String id = connectedInstance.getSemanticIdentity().getUniqueRepresentationReference();
//				if (!artifacts.containsKey(id)) {
//					fetchArtifact(id, artifacts, isRecursive);
//				}
//			}
//		}
//		} catch (final IllegalStateException ex) {
//			Logger.getLogger("global").log(	java.util.logging.Level.SEVERE, null, ex);
//		}
		throw new UnsupportedOperationException("Obsolte operation");
	}

}