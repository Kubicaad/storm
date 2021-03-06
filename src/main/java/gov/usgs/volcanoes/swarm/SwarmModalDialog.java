package gov.usgs.volcanoes.swarm;

import com.jgoodies.forms.builder.ButtonBarBuilder;

import gov.usgs.volcanoes.core.util.UiUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * Swarm modal dialog.
 * 
 * @author Dan Cervelli
 */
public class SwarmModalDialog extends JDialog {
  private static final long serialVersionUID = -1;

  protected JButton okButton;
  protected JButton cancelButton;
  protected JButton helpButton;

  protected JPanel buttonPanel;

  protected JPanel mainPanel;

  protected JFrame parent;

  private boolean okClicked;

  protected static SwarmConfig swarmConfig;
  protected static final JFrame applicationFrame = Swarm.getApplicationFrame();
  protected String helpFile;

  protected SwarmModalDialog(JFrame parent, String title, String helpFile) {
    super(parent, title, true);
    this.helpFile = helpFile;
    swarmConfig = SwarmConfig.getInstance();
    setResizable(false);
    this.parent = parent;
    createUi();
  }

  protected SwarmModalDialog(JFrame parent, String title) {
    this(parent, title, null);
  }

  /**
   * Set size and location of dialog window.
   */
  public void setSizeAndLocation() {
    Dimension d = mainPanel.getPreferredSize();
    setSize(d.width + 10, d.height + 30);
    Dimension parentSize = parent.getSize();
    Point parentLoc = parent.getLocation();
    this.setLocation(parentLoc.x + (parentSize.width / 2 - d.width / 2),
        parentLoc.y + (parentSize.height / 2 - d.height / 2));
  }

  protected void createUi() {
    mainPanel = new JPanel(new BorderLayout());
    okButton = new JButton("OK");
    okButton.setMnemonic('O');
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (allowOk()) {
          dispose();
          okClicked = true;
          wasOk();
        }
      }
    });

    cancelButton = new JButton("Cancel");
    cancelButton.setMnemonic('C');
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (allowCancel()) {
          dispose();
          wasCancelled();
        }
      }
    });
    UiUtils.mapKeyStrokeToButton(mainPanel, "ESCAPE", "cancel1", cancelButton);
    this.addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        okButton.requestFocus();
        JRootPane root = SwingUtilities.getRootPane(okButton);
        if (root != null) {
          root.setDefaultButton(okButton);
        }
      }

      public void windowClosing(WindowEvent e) {
        if (!okClicked) {
          wasCancelled();
        }
      }
    });

    ButtonBarBuilder builder = new ButtonBarBuilder();
    builder.addGlue();

    if (helpFile != null) {
      helpButton = new JButton("Help");
      helpButton.setMnemonic('H');

      helpButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          HelpDialog.displayHelp(parent, helpFile);
        }
      });
      builder.addButton(okButton, cancelButton, helpButton);
    } else {
      builder.addButton(okButton, cancelButton);
    }
    buttonPanel = builder.getPanel();
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    this.setContentPane(mainPanel);
  }

  public void setVisible(boolean visible) {
    this.setSizeAndLocation();
    super.setVisible(visible);
  }

  protected boolean allowOk() {
    return true;
  }

  protected boolean allowCancel() {
    return true;
  }

  protected void wasOk() {}

  protected void wasCancelled() {}
}
