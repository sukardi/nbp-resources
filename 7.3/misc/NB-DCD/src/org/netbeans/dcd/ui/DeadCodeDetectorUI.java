/*
 * Copyright 2008 by Emeric Vernat
 *
 *     This file is part of Dead Code Detector.
 *
 * Dead Code Detector is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dead Code Detector is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dead Code Detector.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.netbeans.dcd.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.netbeans.dcd.DeadCodeDetector;
import org.netbeans.dcd.Parameters;

/**
 * Classe principale de DCD en mode graphique avec la m�thode main.
 * @author evernat
 */
public class DeadCodeDetectorUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Color GRADIENT_COLOR = new Color(166, 202, 240);
	private static final int APPEAR_STEP = 25;
	private static final int APPEAR_DURATION = 750;
	final ParametersPanel parametersPanel;
	javax.swing.Timer appearTimer;

	/**
	 * Constructeur du panel.
	 * Ce constructeur peut �tre utilis� � la place de main
	 * pour instancier ce panel et le placer dans une JFrame � afficher.
	 * @param parameters Parameters
	 */
	public DeadCodeDetectorUI(Parameters parameters) {
		super();
		setLayout(new BorderLayout());
		parametersPanel = new ParametersPanel(parameters);
		add(parametersPanel, BorderLayout.CENTER);
	}

	static void showFrame(final File propertiesFile) {
		try {
			if (System.getProperty("java.version").compareTo("1.6") < 0) {
				throw new IllegalStateException(
						"You must use a JRE version >= 1.6. Download it from http://java.com");
			}
			try {
				Class.forName("org.objectweb.asm.ClassReader");
			} catch (final ClassNotFoundException e) {
				throw new IllegalStateException(
						"ASM classes not found. Add the asm jar file in the classpath and run again.",
						e);
			}

			// look and feel
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			Parameters parameters;
			try {
				if (System.getProperty(Parameters.DIRECTORIES_KEY) != null) {
					parameters = Parameters.createFromSystemProperties();
				} else if (propertiesFile != null) {
					parameters = Parameters.createFromPropertiesFile(propertiesFile);
				} else {
					parameters = Parameters.createFromUserPreferences();
				}
			} catch (final Exception e) {
				DcdUiHelper.printStackTrace(e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				parameters = null;
			}
			// instanciation apr�s look and feel
			final DeadCodeDetectorUI ui = new DeadCodeDetectorUI(parameters);
			final JFrame frame = new JFrame(DeadCodeDetector.APPLICATION_NAME);
			frame.add(ui);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.addWindowListener(new WindowAdapter() {
				/** {@inheritDoc} */
				@Override
				public void windowClosing(WindowEvent e) {
					ui.parametersPanel.getController().actionCancel();
					if (propertiesFile == null) {
						try {
							ui.parametersPanel.createParameters().writeToUserPreferences();
						} catch (final Exception ex) {
							DcdUiHelper.printStackTrace(ex);
						}
					}
				}
			});
			final Dimension dimension = frame.getToolkit().getScreenSize();
			frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2
					- frame.getHeight() / 2);
			ui.disappear();
			frame.setVisible(true);
			ui.appear();
		} catch (final Exception e) {
			DcdUiHelper.printStackTrace(e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void appear() {
		final JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		final long startTime = System.currentTimeMillis();
		appearTimer = new javax.swing.Timer(APPEAR_STEP, new ActionListener() {
			/** {@inheritDoc} */
			@Override
			public void actionPerformed(ActionEvent e) {
				// calculate elapsed time
				final long elapsed = System.currentTimeMillis() - startTime;
				final float alpha = Math.min(1f, 1.0f * elapsed / APPEAR_DURATION);
				setWindowOpacity(frame, alpha);
				if (elapsed >= APPEAR_DURATION) { // should we stop timer?
					appearTimer.stop();
				}
			}
		});

		appearTimer.start();
	}

	private void disappear() {
		final JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		setWindowOpacity(frame, 0f);
	}

	static void setWindowOpacity(JFrame frame, float alpha) {
		// cette m�thode fonctionne � partir de Java 1.6.0_10 (update 10)
		try {
			//invoke AWTUtilities.setWindowOpacity(win, alpha);
			final Class<?> awtutil = Class.forName("com.sun.awt.AWTUtilities");
			final Method setWindowOpaque = awtutil.getMethod("setWindowOpacity", Window.class,
					float.class);
			setWindowOpaque.invoke(null, frame, alpha);
		} catch (final Throwable ex) { // NOPMD
			// pour MAC OS X
			frame.getRootPane().putClientProperty("Window.alpha", Float.valueOf(alpha));
			// sinon pas v1.6.0_u10 ou + : tant pis
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(Graphics graphics) {
		// Surcharg�e pour dessiner le fond avec gradient
//		final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
//		final String lafName = lookAndFeel != null ? lookAndFeel.getName() : null;
//		if ("Substance".equals(lafName)) {
//			super.paintComponent(graphics); // le gradient fonctionne mal en substance ?
//		}

		final Color startColor = getBackground();
		final Color endColor = GRADIENT_COLOR;
		final int w = getWidth();
		final int h = getHeight();

		// l'image du gradient pourrait �tre mise en cache, mais ce n'est pas grave
		final Paint paint = new GradientPaint(0, h / 2f, startColor, 1, h, endColor, false);
		final Graphics2D graphix = (Graphics2D) graphics.create();
		graphix.setPaint(paint);
		graphix.fillRect(0, 0, w, h);
		graphix.dispose();
	}

	/**
	 * M�thode ex�cut�e pour lancer l'IHM.
	 * @param args String[]
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // NOPMD
					/** {@inheritDoc} */
					@Override
					public void run() {
						final File propertiesFile;
						if (args.length > 0) {
							propertiesFile = new File(args[0]);
						} else {
							propertiesFile = null;
						}
						showFrame(propertiesFile);
					}
				});
	}
}
