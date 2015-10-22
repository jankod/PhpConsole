package hr.ja.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;

public class EclipseUtil {

	public static void openFileOnLine(String path, int line)
			throws CoreException, FileNotFoundException {
		File fileToOpen = new File(path);
		if (fileToOpen.exists() && fileToOpen.isFile()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(
					fileToOpen.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IEditorPart e = IDE.openEditorOnFileStore(page, fileStore);
			// System.out.println("editor " + e.getClass());

			// IPath ipath = Path.fromOSString(fileToOpen.getCanonicalPath());
			
			
			IResource resource = ResourceUtil.getResource(e.getEditorInput());
			if (resource != null) {
				IMarker marker = resource.createMarker(IMarker.TEXT);
				marker.setAttribute(IMarker.LINE_NUMBER, line);
				IDE.gotoMarker(e, marker);
			} else {
				// FIXME: how do create marker without resource??
				throw new RuntimeException("Canot find resource????");
			}
		} else {
			throw new FileNotFoundException(path);
		}

	}
}
