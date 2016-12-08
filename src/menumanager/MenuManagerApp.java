/*
 * MenuManagerApp.java
 */

package menumanager;

import java.util.EventObject;
import menumanager.src.LoadData;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class MenuManagerApp extends SingleFrameApplication implements Application.ExitListener {
	private MenuManagerView view_;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
		this.addExitListener(this); 
		this.view_ = new MenuManagerView(this);
        show(this.view_);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MenuManagerApp
     */
    public static MenuManagerApp getApplication() {
        return Application.getInstance(MenuManagerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MenuManagerApp.class, args);
    }

	public boolean canExit(EventObject event) {
		return this.view_.promptForExit();
	}
	public void willExit(EventObject event) {}
}
