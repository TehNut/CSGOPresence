package info.tehnut.csgo.presence;

import java.awt.*;
import java.net.URL;

public class TrayHandler {

    public static boolean tryFindSurf;

    public static void init() {
        if (!SystemTray.isSupported())
            return;

        Image icon = Toolkit.getDefaultToolkit().createImage(TrayHandler.class.getResource("/tray_icon.png"));
        TrayIcon trayIcon = new TrayIcon(icon, "CSGOPresence");
        SystemTray tray = SystemTray.getSystemTray();
        PopupMenu menu = new PopupMenu();

        menu.add(new MenuItem("CSGOPresence by TehNut"));
        menu.addSeparator();

        CheckboxMenuItem attemptSurfRecognition = new CheckboxMenuItem("Try to match surf maps", tryFindSurf);
        attemptSurfRecognition.addItemListener(listener -> tryFindSurf = attemptSurfRecognition.getState());
        menu.add(attemptSurfRecognition);

        MenuItem githubButton = new MenuItem("GitHub");
        githubButton.addActionListener(listener -> {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/TehNut/CSGOPresence").toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        menu.add(githubButton);

        MenuItem quitButton = new MenuItem("Quit");
        quitButton.addActionListener(listener -> System.exit(0));
        menu.add(quitButton);

        trayIcon.setPopupMenu(menu);
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
