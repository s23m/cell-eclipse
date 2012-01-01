package org.s23m.cell.eclipse.visualization.containmenttree.viewer;

public class ContainmentTreeViewerStatus {

	private boolean isKernelInitialized = false;

	public void setKernelInitialized(final boolean isKernelInitialized) {
		this.isKernelInitialized = isKernelInitialized;
	}

	public boolean isKernelInitialized() {
		return isKernelInitialized;
	}

	private ContainmentTreeViewerStatus() {}

	private static class StatusHolder {
	     public static final ContainmentTreeViewerStatus INSTANCE = new ContainmentTreeViewerStatus();
	   }

	public static ContainmentTreeViewerStatus getInstance() {
	     return StatusHolder.INSTANCE;
	}

}
