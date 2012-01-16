package org.eclipse.emf.mwe.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class DirectoryCleaner extends AbstractWorkflowComponent2 {

	private static final String COMPONENT_NAME = "Directory Cleaner";

	private static final Log LOG = LogFactory.getLog(DirectoryCleaner.class);

	private String directory;

	private final Collection<String> excludes = new HashSet<String>();

	private final Collection<String> defaultExcludes = Arrays.asList(new String[] { "CVS", ".cvsignore", ".svn" });

	private boolean useDefaultExcludes = true;

	/**
	 * Sets the directory.
	 *
	 * @param directory
	 *            name of directory
	 */
	public void setDirectory(final String directory) {
		this.directory = directory;
	}

	/**
	 * @see org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent#getLogMessage()
	 */
	@Override
	public String getLogMessage() {
		return "cleaning directory '" + directory + "'";
	}

	@Override
	protected void invokeInternal(final WorkflowContext model, final ProgressMonitor monitor, final Issues issues) {
		if (directory != null) {
			final StringTokenizer st = new StringTokenizer(directory, ",");
			while (st.hasMoreElements()) {
				final String dir = st.nextToken().trim();
				final File f = new File(dir);
				if (f.exists() && f.isDirectory()) {
					LOG.info("Cleaning " + f.getAbsolutePath());
					try {
						cleanFolder(f.getAbsolutePath());
					}
					catch (final FileNotFoundException e) {
						issues.addError(e.getMessage());
					}
				}
			}
		}
	}

	@Override
	protected void checkConfigurationInternal(final Issues issues) {
		if (directory == null) {
			issues.addWarning("No directories specified!");
		}
	}

	/**
	 * Deletes all files and subdirectories under dir. Returns true if all
	 * deletions were successful. If a deletion fails, the method stops
	 * attempting to delete and returns false.
	 */
	public void cleanFolder(final String srcGenPath) throws FileNotFoundException {
		final File f = new File(srcGenPath);
		if (!f.exists()) {
			throw new FileNotFoundException(srcGenPath + " " + f.getAbsolutePath());
		}
		LOG.debug("Cleaning folder " + f.getPath());
		cleanFolder(f, new FileFilter() {
			public boolean accept(final File path) {
				return !isExcluded(path);
			}
		}, false, false);
	}

	public boolean isExcluded(final File path) {
		if (useDefaultExcludes && defaultExcludes.contains(path.getName())) {
			return true;
		}
		return excludes.contains(path.getName());
	}

	public boolean cleanFolder(final File parentFolder, final FileFilter filter, final boolean continueOnError,
			final boolean deleteParentFolder) throws FileNotFoundException {
		if (!parentFolder.exists()) {
			throw new FileNotFoundException(parentFolder.getAbsolutePath());
		}
		FileFilter myFilter = filter;
		if (myFilter == null) {
			myFilter = new FileFilter() {
				public boolean accept(final File pathname) {
					return true;
				}
			};
		}
		LOG.debug("Cleaning folder " + parentFolder.toString());
		final File[] contents = parentFolder.listFiles(myFilter);
		for (final File content : contents) {
			final File file = content;
			if (file.isDirectory()) {
				if (!cleanFolder(file, myFilter, continueOnError, false) && !continueOnError) {
					return false;
				}
			}
			else {
				if (!file.delete()) {
					LOG.error("Couldn't delete " + file.getAbsolutePath());
					if (!continueOnError) {
						return false;
					}
				}
			}
		}
		if (deleteParentFolder) {
			if (!parentFolder.delete()) {
				LOG.error("Couldn't delete " + parentFolder.getAbsolutePath());
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns if the default excludes are used.
	 *
	 * @return <code>true</code>, if the default excludes are used, otherwise
	 *         <code>false</code>.
	 */
	public boolean isUseDefaultExcludes() {
		return useDefaultExcludes;
	}

	/**
	 * Sets if the default excludes are used.
	 *
	 * @param useDefaultExcludes
	 *            If <code>true</code>, the default excludes are used, if
	 *            <code>false</code>, the default excludes are ignored.
	 */
	public void setUseDefaultExcludes(final boolean useDefaultExcludes) {
		this.useDefaultExcludes = useDefaultExcludes;
	}

	/**
	 * Adds an exclude.
	 *
	 * @param exclude
	 *            the exclude
	 */
	public void addExclude(final String exclude) {
		excludes.add(exclude);
	}

	/**
	 * @see org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent#getComponentName()
	 */
	@Override
	public String getComponentName() {
		return COMPONENT_NAME;
	}

}
/*******************************************************************************
 * $Log: DirectoryCleaner.java,v $
 * Revision 1.12  2009/02/21 23:32:55  pschonbac
 * Merged changes from oAW 4.3.1
 *
 * Revision 1.18  2009/02/20 00:49:12  pschoenb
 * Restored original behavior of DirectoryCleaner. There has been some loss of functionality because of the latest changes.
 *
 * Revision 1.17  2009/02/02 22:45:34  pschoenb
 * Error message improved
 *
 * Revision 1.16  2009/01/30 14:55:53  pschoenb
 * bug 221820: DirectoryCleaner should have an exclude property and default excludes
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=221820
 *
 * Revision 1.15  2009/01/15 14:12:34  kthoms
 * Removed deprecated code
 *
 * Revision 1.14  2008/12/23 21:53:35  pschoenb
 * Merged from branch B_v43
 *
 * Revision 1.13.4.7  2008/11/06 17:41:24  pschoenb
 * Default exclude fixed.
 * Revision 1.13.4.6 2008/11/06 17:40:15
 * pschoenb Excluding mechanism implemented as requested by Markus Völter.
 * Revision 1.13.4.5 2008/10/29 22:00:52 kthoms added 'setDirectories' for
 * backwards compatibility reasons.
 *
 * Revision 1.13.4.4 2008/10/27 22:58:53 pschoenb bug 251828: JavaDoc for
 * Workflow Components https://bugs.eclipse.org/bugs/show_bug.cgi?id=251828
 * Revision 1.13.4.3 2008/10/21 16:53:49 pschoenb bug 250536: ProgressMonitor is
 * not used https://bugs.eclipse.org/bugs/show_bug.cgi?id=250536
 *
 * Code simplified and cleaned up. Revision 1.13.4.2 2008/10/16 18:06:46
 * pschoenb bug 250536: ProgressMonitor is not used
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=250536
 *
 * Basic implementation Revision 1.13.4.1 2008/10/14 22:24:52 pschoenb bug
 * 250536: ProgressMonitor is not used
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=250536
 *
 * Basic implementation Revision 1.13 2007/08/16 12:07:31 sefftinge added URL to
 * my name
 *
 * Revision 1.12 2006/11/22 13:29:03 voelter *** empty log message ***
 *
 * Revision 1.11 2006/09/11 09:43:24 voelter version changed to 4.1.1
 *
 * Revision 1.10 2006/04/26 09:53:37 sefftinge Java 5 migration (not finished
 * yet)
 *
 * Revision 1.9 2006/04/20 17:40:37 sefftinge clean up Revision 1.8 2006/04/20
 * 17:33:17 sefftinge clean up
 *
 * Revision 1.7 2006/03/11 18:35:14 voelter *** empty log message ***
 *
 * Revision 1.6 2006/03/08 11:11:40 sefftinge workflowfactory rewritten
 *
 * Revision 1.5 2006/02/03 20:36:59 voelter *** empty log message ***
 *
 * Revision 1.4 2006/01/31 15:42:17 sefftinge license added
 *
 * Revision 1.3 2006/01/16 19:02:25 sefftinge issues mech. reactivated
 *
 * Revision 1.2 2006/01/16 14:44:17 sefftinge - logging with
 * apache.commons.logging no single logger instance anymore - runtime Issues
 * mechanism removed - issues for checkConfiguration simply collects Strings,
 * now Revision 1.1 2006/01/11 16:37:18 voelter alles neu macht der mai
 *
 * Revision 1.1 2006/01/11 14:05:18 arnohaase *** empty log message ***
 *
 * Revision 1.1 2006/01/11 13:52:10 arnohaase *** empty log message ***
 *
 * Revision 1.1 2005/12/12 20:36:39 arnohaase *** empty log message ***
 *
 * Revision 1.3 2005/10/14 12:10:08 sefftinge *** empty log message ***
 *
 * Revision 1.2 2005/09/23 09:34:00 sefftinge *** empty log message ***
 *
 * Revision 1.1 2005/09/21 17:25:52 sefftinge *** empty log message ***
 *
 *
 ******************************************************************************/