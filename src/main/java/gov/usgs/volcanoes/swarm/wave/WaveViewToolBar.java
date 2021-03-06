package gov.usgs.volcanoes.swarm.wave;

import gov.usgs.volcanoes.swarm.Icons;
import gov.usgs.volcanoes.swarm.SwarmUtil;
import gov.usgs.volcanoes.swarm.wave.WaveViewSettings.ViewType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class WaveViewToolBar {
  private JButton waveSet;
  private JToggleButton waveToggle;
  private JToggleButton spectraToggle;
  private JToggleButton spectrogramToggle;
  private ButtonGroup waveTypes;
  private WaveViewToolBarListener listener;

  public WaveViewToolBar(WaveViewSettings s, JToolBar dest, WaveViewToolBarListener listener) {
    this.listener = listener;
    createUi(dest);
  }

  /**
   * Create wave view tool bar section of user interface.
   * @param dest  tool bar; destination.
   */
  public void createUi(JToolBar dest) {
    waveSet = SwarmUtil.createToolBarButton(Icons.gear, "Wave view settings (?)",
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            listener.displaySettingsDialog();
          }
        });
    listener.mapKeyStroke("shift SLASH", "settings", waveSet);
    dest.add(waveSet);

    waveToggle =
        SwarmUtil.createToolBarToggleButton(Icons.wave, "Wave view (W or ,)", new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            listener.setType(ViewType.WAVE);
          }
        });
    listener.mapKeyStroke("COMMA", "wave1", waveToggle);
    listener.mapKeyStroke("W", "wave2", waveToggle);
    dest.add(waveToggle);

    spectraToggle = SwarmUtil.createToolBarToggleButton(Icons.spectra, "Spectra view (S or .)",
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            listener.setType(ViewType.SPECTRA);
          }
        });
    listener.mapKeyStroke("PERIOD", "spectra1", spectraToggle);
    listener.mapKeyStroke("S", "spectra2", spectraToggle);
    dest.add(spectraToggle);

    spectrogramToggle = SwarmUtil.createToolBarToggleButton(Icons.spectrogram,
        "Spectrogram view (G or /)", new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            listener.setType(ViewType.SPECTROGRAM);
          }
        });
    listener.mapKeyStroke("SLASH", "spectrogram1", spectrogramToggle);
    listener.mapKeyStroke("G", "spectrogram2", spectrogramToggle);
    dest.add(spectrogramToggle);

    waveTypes = new ButtonGroup();
    waveTypes.add(waveToggle);
    waveTypes.add(spectraToggle);
    waveTypes.add(spectrogramToggle);
  }

  /**
   * Set enabled flag for the various toggles.
   * @param enable true if enabled; false otherwise.
   */
  public void setEnabled(boolean enable) {
    waveSet.setEnabled(enable);
    waveToggle.setEnabled(enable);
    spectraToggle.setEnabled(enable);
    spectrogramToggle.setEnabled(enable);

  }
}
