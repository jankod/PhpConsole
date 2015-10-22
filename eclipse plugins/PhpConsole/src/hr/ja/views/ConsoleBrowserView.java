package hr.ja.views;

import hr.ja.Activator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

public class ConsoleBrowserView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "hr.ja.views.ConsoleBrowserView";

	private TableViewer viewer;
	private Action actionEnterUrl;
	private Action actionRefreshPage;
	private Action actionClear;

	private Browser browser;

	/**
	 * The constructor.
	 */
	public ConsoleBrowserView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl("http://google.com");

		browser.setJavascriptEnabled(true);
		new CustomBrowserFunction(browser, "eclipse");

		makeActions();
		// hookContextMenu();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ConsoleBrowserView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionEnterUrl);
		manager.add(new Separator());
		manager.add(actionRefreshPage);
		manager.add(actionClear);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionEnterUrl);
		manager.add(actionRefreshPage);
		manager.add(actionClear);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionEnterUrl);
		manager.add(actionRefreshPage);
		manager.add(actionClear);
	}

	private void makeActions() {
		actionEnterUrl = new Action() {
			public void run() {
				IPreferenceStore store = Activator.getDefault().getPreferenceStore();
				String url = store.getString("url");
				
				InputDialog dlg = new InputDialog(Display.getCurrent()
						.getActiveShell(), "URL", "Enter url of PhpConsole PHP script.", url, null);
				if (dlg.open() == Window.OK) {
					// User clicked OK; update the label with the input
					String newUrl = dlg.getValue();
					browser.setUrl(newUrl);
					store.setValue("url", newUrl);
				}
			}
		};
		actionEnterUrl.setText("URL");

		actionEnterUrl.setToolTipText("Enter URL of PHPConsole page");
		actionEnterUrl.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("PhpConsole", "icons/url.gif"));

		actionRefreshPage = new Action() {
			public void run() {
				browser.refresh();
				// browser.setUrl("http://localhost/DigitalizacijaPBF/debug/");//
				// broshowMessage("Action 2 executed");
			}
		};
		actionRefreshPage.setText("Refresh");
		actionRefreshPage.setToolTipText("Refresh page in browser");
		actionRefreshPage.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor("PhpConsole", "icons/refresh.gif"));

		actionClear = new Action() {
			public void run() {
				browser.evaluate("deleteMsg()");
			}
		};
		actionClear.setText("Clear");
		actionClear.setToolTipText("Clear messages");
		actionClear.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));
		
	}

	public void setFocus() {
		
		browser.setFocus();
	}
	
}