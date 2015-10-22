package hr.ja.views;

import hr.ja.Activator;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

public class CustomBrowserFunction extends BrowserFunction {

	public CustomBrowserFunction(Browser browser, String name) {
		super(browser, name);
	}

	public Object function(Object[] arguments) {
		if (arguments.length < 2) {
			throw new IllegalArgumentException(
					"First argument is file, second line number!");
		}

		String errorMsg = null;

		String path = (String) arguments[0];
		int line = 0;

		//String m = arguments[0].getClass() + " " + arguments[1].getClass();
		//MessageDialog.openInformation(getBrowser().getShell(), "Parametri", m);
		if (arguments[1] instanceof Double) {
			Double d = (Double) arguments[1];
			line = d.intValue();
		}

		try {
			// System.out.println("Call " + path + ":" + line);
			EclipseUtil.openFileOnLine(path, line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorMsg = "Exception occur: " + e.getMessage();
			Activator.logError(errorMsg);
			Object returnValue = new Object[] { errorMsg };
			return returnValue;
		} catch (CoreException e) {
			e.printStackTrace();
			errorMsg = "Exception occur: " + e.getMessage();
			Object returnValue = new Object[] { errorMsg };
			Activator.logError(errorMsg);
			return returnValue;
		} catch (Throwable e) {
			e.printStackTrace();
			errorMsg = "Exception occur: " + e.getMessage();
			Object returnValue = new Object[] { errorMsg };
			Activator.logError(errorMsg);
			return returnValue;
		}

		Object returnValue = new Object[] { "" };
		return returnValue;
	}

}
