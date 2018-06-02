package be.sylvainvansteelandt.projects.jwdownloader;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import org.apache.commons.io.FileUtils;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import jmtp.DeviceAlreadyOpenedException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import jmtp.PortableDeviceStorageObject;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] allMonths = new String[] { "januari", "februari", "maart", "april", "mei", "juni", "juli",
			"augustus", "september", "oktober", "november", "december" };
	private String[] monthsOntwaakt = new String[] { "februari", "april", "juni", "augustus", "oktober", "december" };
	private String[] monthsPubliek = new String[] { "januari", "maart", "mei", "juli", "september", "november" };
	private String[] monthsOntwaakt2018 = new String[] {"maart", "juli", "november"};
	private String[] monthsPubliek2018 = new String[] {"januari", "mei", "september"};
	
	private JPanel contentPane;
	private PortableDeviceManager manager = new PortableDeviceManager();
	private PortableDevice player = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);

		setTitle("JWDownloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		UIManager.put("Label.disabledForeground", Color.BLACK);

		JPanel pnlStart = new JPanel();
		pnlStart.setBorder(new TitledBorder(null, "Start", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlStart.setBounds(10, 11, 709, 123);
		contentPane.add(pnlStart);
		pnlStart.setLayout(null);

		JLabel lblStepOne = new JLabel("Stap 1: Test connectie MP3-speler");
		lblStepOne.setBounds(10, 24, 164, 14);
		lblStepOne.setBackground(UIManager.getColor("Button.background"));
		lblStepOne.setHorizontalAlignment(SwingConstants.LEFT);
		lblStepOne.setEnabled(false);
		lblStepOne.setBorder(null);
		lblStepOne.setSize(lblStepOne.getPreferredSize());
		pnlStart.add(lblStepOne);

		JLabel lblStepOneOutput = new JLabel("");
		lblStepOneOutput.setHorizontalAlignment(SwingConstants.LEFT);
		lblStepOneOutput.setEnabled(false);
		lblStepOneOutput.setBackground(SystemColor.menu);
		lblStepOneOutput.setBounds(10, 93, 259, 14);
		pnlStart.add(lblStepOneOutput);

		JButton btnTestConnectie = new JButton("Test Connectie");
		btnTestConnectie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean test = ConnectionTester.testMP3PlayerConnectivity();
				if (test) {
					lblStepOneOutput.setText("Uw MP3-speler is correct aangesloten!");
					lblStepOneOutput.setBorder(null);
					lblStepOneOutput.setSize(lblStepOneOutput.getPreferredSize());
				} else {
					lblStepOneOutput.setText("Uw MP3-speler is niet correct aangesloten.");
					lblStepOneOutput.setSize(lblStepOneOutput.getPreferredSize());
				}
			}
		});
		btnTestConnectie.setBounds(10, 50, 123, 23);
		btnTestConnectie.setFocusPainted(false);
		btnTestConnectie.setBorder(null);
		pnlStart.add(btnTestConnectie);

		JLabel lblStepTwo = new JLabel("Stap 2: Test connectie internet");
		lblStepTwo.setHorizontalAlignment(SwingConstants.LEFT);
		lblStepTwo.setEnabled(false);
		lblStepTwo.setBackground(SystemColor.menu);
		lblStepTwo.setBounds(279, 24, 164, 14);
		lblStepTwo.setBorder(null);
		lblStepTwo.setSize(lblStepTwo.getPreferredSize());
		pnlStart.add(lblStepTwo);

		JLabel lblStepTwoOutput = new JLabel("");
		lblStepTwoOutput.setHorizontalAlignment(SwingConstants.LEFT);
		lblStepTwoOutput.setEnabled(false);
		lblStepTwoOutput.setBackground(SystemColor.menu);
		lblStepTwoOutput.setBounds(279, 93, 420, 14);
		pnlStart.add(lblStepTwoOutput);

		JButton btnTestInternetConnectie = new JButton("Test Connectie");
		btnTestInternetConnectie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean test = ConnectionTester.testInternetConnectivity();

					if (test) {
						lblStepTwoOutput.setText("U bent verbonden met het internet!");
					} else {
						lblStepTwoOutput
								.setText("U bent niet verbonden met het internet. Controleer uw netwerkinstellingen.");
					}
				} catch (IOException | InterruptedException e1) {
					lblStepTwoOutput.setText("Er ging iets mis bij het verbinden. Probeer het opnieuw.");
				}
			}
		});
		btnTestInternetConnectie.setFocusPainted(false);
		btnTestInternetConnectie.setBounds(279, 50, 123, 23);
		btnTestInternetConnectie.setBorder(null);
		pnlStart.add(btnTestInternetConnectie);

		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout(null);
		pnlOptions.setBorder(new TitledBorder(null, "Publicatie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOptions.setBounds(10, 145, 709, 123);
		contentPane.add(pnlOptions);

		JLabel lblTaal = new JLabel("Taal:");
		JLabel lblPublicatie = new JLabel("Publicatie:");
		JLabel lblJaargang = new JLabel("Jaargang:");
		JLabel lblMaand = new JLabel("Maand:");
		JLabel lblOutput = new JLabel("Details:");
		JComboBox cboTaal = new JComboBox();
		JComboBox cboPublicatie = new JComboBox();
		JComboBox cboJaar = new JComboBox();
		JComboBox cboMaand = new JComboBox();

		cboJaar.setModel(new DefaultComboBoxModel(new String[] { "2015", "2016", "2017", "2018", "2019", "2020" }));
		String year = LocalDateTime.now().getYear() + "";
		cboJaar.setSelectedItem(year);

		lblTaal.setSize(new Dimension(164, 14));
		lblTaal.setHorizontalAlignment(SwingConstants.LEFT);
		lblTaal.setEnabled(false);
		lblTaal.setBorder(null);
		lblTaal.setBackground(SystemColor.menu);
		lblTaal.setBounds(10, 24, 77, 14);
		pnlOptions.add(lblTaal);

		lblPublicatie.setSize(new Dimension(164, 14));
		lblPublicatie.setHorizontalAlignment(SwingConstants.LEFT);
		lblPublicatie.setEnabled(false);
		lblPublicatie.setBorder(null);
		lblPublicatie.setBackground(SystemColor.menu);
		lblPublicatie.setBounds(10, 56, 77, 14);
		pnlOptions.add(lblPublicatie);

		cboTaal.setModel(new DefaultComboBoxModel(new String[] { "Engels", "Frans", "Nederlands" }));
		cboTaal.setSelectedIndex(1);
		lblTaal.setLabelFor(cboTaal);
		cboTaal.setBounds(108, 21, 123, 20);
		pnlOptions.add(cboTaal);

		cboPublicatie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cboMaand.setModel(new DefaultComboBoxModel(
						checkMonths(cboPublicatie.getSelectedItem().toString(), cboJaar.getSelectedItem().toString())));
			}
		});
		cboPublicatie
				.setModel(new DefaultComboBoxModel(new String[] { "Ontwaakt!", "Wachttoren", "Wachttoren Studie" }));
		cboPublicatie.setSelectedIndex(0);
		cboPublicatie.setBounds(108, 53, 123, 20);
		pnlOptions.add(cboPublicatie);

		lblJaargang.setSize(new Dimension(164, 14));
		lblJaargang.setHorizontalAlignment(SwingConstants.LEFT);
		lblJaargang.setEnabled(false);
		lblJaargang.setBorder(null);
		lblJaargang.setBackground(SystemColor.menu);
		lblJaargang.setBounds(279, 24, 77, 14);
		pnlOptions.add(lblJaargang);

		lblMaand.setSize(new Dimension(164, 14));
		lblMaand.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaand.setEnabled(false);
		lblMaand.setBorder(null);
		lblMaand.setBackground(SystemColor.menu);
		lblMaand.setBounds(279, 56, 77, 14);
		pnlOptions.add(lblMaand);

		cboJaar.setBounds(382, 21, 123, 20);
		cboJaar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cboMaand.setModel(new DefaultComboBoxModel(
						checkMonths(cboPublicatie.getSelectedItem().toString(), cboJaar.getSelectedItem().toString())));
			}
		});
		pnlOptions.add(cboJaar);
		cboMaand.setBounds(382, 53, 123, 20);
		String month = LocalDateTime.now().getMonth() + "";
		cboMaand.setModel(new DefaultComboBoxModel(
				checkMonths(cboPublicatie.getSelectedItem().toString(), cboJaar.getSelectedItem().toString())));
		pnlOptions.add(cboMaand);
		
		JTextArea txtOutput = new JTextArea();
		txtOutput.setEditable(false);
		txtOutput.setBounds(10, 321, 709, 123);
		contentPane.add(txtOutput);
		
		JScrollPane scpOutput = new JScrollPane(txtOutput);
		DefaultCaret caret = (DefaultCaret) txtOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scpOutput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scpOutput.setBounds(10, 321, 709, 123);
		contentPane.add(scpOutput);

		JButton btnDownload = new JButton("Download!");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String publicatie = cboPublicatie.getSelectedItem().toString();
				String taal = cboTaal.getSelectedItem().toString();
				String maand = cboMaand.getSelectedItem().toString();
				String jaar = cboJaar.getSelectedItem().toString();
				
				
				txtOutput.setText(txtOutput.getText() + "U koos ervoor om volgende publicatie te downloaden: de "
						+ publicatie + " van " + maand + " " + jaar + ", in het " + taal + ".\n");
				txtOutput.setText(txtOutput.getText() + "De applicatie zoekt naar de URL van het ZIP-bestand...\n");
				String[] urls = JWUrlBuilder.getPossiblePublicationURLs(taal, publicatie, jaar, maand);
				Runnable task = () -> {
					String url = JWUrlDownloader.findExistingURL(urls);
					txtOutput.setText(txtOutput.getText() + "Het ZIP-bestand is gevonden! De url is: " + url + ".\n");
					try {
						String filename = JWUrlDownloader.downloadFile(url);
						txtOutput.setText(txtOutput.getText() + "Het ZIP-bestand werd gedownload naar " + filename + ".\n");
						Path source = Paths.get(filename);
						Files.move(source, source.resolveSibling(publicatie.replace("!","") + " " + maand + " " + jaar + ".zip"));
						String home = System.getProperty("user.home");
						String newPath = home + "/Downloads/" + publicatie.replace("!","") + " " + maand + " " + jaar + ".zip";
						txtOutput.setText(txtOutput.getText() + "Het ZIP-bestand werd hernoemd naar " + newPath + ".\n");
						Compressor.unzip(newPath,"");
						File dir = new File(newPath.replace(".zip", ""));
						txtOutput.setText(txtOutput.getText() + "Het ZIP-bestand werd uitgepakt naar " + dir + ".\n");
						File[] directoryListing = dir.listFiles();
						for (File file : directoryListing) {
							String[] parts = file.getName().split("\\.");
							int i = parts[0].length();
							String sCount = parts[0].substring(i - 2);
							MP3File mp3File = new MP3File(file);
							String title = Normalizer.normalize(mp3File.getID3v2Tag().getSongTitle(), Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9 ]", "");
							title = title.startsWith("y") ? title.substring(1) : title;
							title = title.trim();
							String newFileName = sCount + " - " + title;
							file.renameTo(new File(dir + "/" + newFileName + ".mp3"));
						}
						
						txtOutput.setText(txtOutput.getText() + "Alle bestanden werden hernoemd naar rangnummer + titel.\n");
						
						manager.refreshDeviceList();
						for(PortableDevice device : manager){
							try{
								device.open();
							} catch(DeviceAlreadyOpenedException e){
								
							}
							
							if(device.getModel().equalsIgnoreCase("WALKMAN NWZ-E585")){
								player = device;
							} 
						}
						
						PortableDeviceStorageObject storage = getStorage(player);
						
						PortableDeviceFolderObject folder = storage.createFolderObject(publicatie.replace("!","") + " " + maand + " " + jaar);
						
						File[] newDirectoryListing = dir.listFiles();
						
						for(File file : newDirectoryListing){
							folder.addAudioObject(file, "", "", new BigInteger("1000"));
						}
						
						txtOutput.setText(txtOutput.getText() + "Het overzetten van MP3-bestanden naar de MP3-speler is klaar.\n");
						
						PortableDeviceObject[] pdao = folder.getChildObjects();
						
						folder.createPlaylistObject(publicatie.replace("!","") + " " + maand + " " + jaar, pdao);
						player.close();
						txtOutput.setText(txtOutput.getText() + "Een playlist object werd aangemaakt.\n");
						txtOutput.setText(txtOutput.getText() + "Het plaatsen van de " + publicatie + " " + maand + " " + jaar + " op uw MP3-speler is voltooid!\n");
						
						//Clean up
						Files.deleteIfExists(Paths.get(newPath));
						txtOutput.setText(txtOutput.getText() + "Tijdelijk bestand opgeruimd: " + newPath + ".\n");
						if(Files.exists(Paths.get(dir.toString()))) {
							FileUtils.deleteDirectory(dir);
						}
						txtOutput.setText(txtOutput.getText() + "Tijdelijke map opgeruimd: " + dir.toString() + ".\n");
						
					} catch (IOException e) {
						System.out.println("error");
					} catch (TagException e) {
						System.out.println("tagerror");
					}
				};
				new Thread(task).start();
			}
		});
		btnDownload.setFocusPainted(false);
		btnDownload.setBorder(null);
		btnDownload.setBounds(554, 20, 123, 50);
		pnlOptions.add(btnDownload);

		lblOutput.setSize(new Dimension(164, 14));
		lblOutput.setHorizontalAlignment(SwingConstants.LEFT);
		lblOutput.setEnabled(false);
		lblOutput.setBorder(null);
		lblOutput.setBackground(SystemColor.menu);
		lblOutput.setBounds(10, 285, 77, 14);
		contentPane.add(lblOutput);
		
	}
	
	private PortableDeviceStorageObject getStorage(PortableDevice device) {

		if (device.getRootObjects() != null) {

			for (PortableDeviceObject object : device.getRootObjects()) {

				if (object instanceof PortableDeviceStorageObject) {
					PortableDeviceStorageObject storage = (PortableDeviceStorageObject) object;
					return storage;
				}
			}
		}
		return null;
	}

	private String[] checkMonths(String publication, String year) {
		if (publication.equalsIgnoreCase("Ontwaakt!")) {
			if (Integer.parseInt(year) >= 2018) {
				return this.monthsOntwaakt2018;
			} else if (Integer.parseInt(year) > 2015) {
				return this.monthsOntwaakt;
			} else {
				return this.allMonths;
			}
		} else if (publication.equalsIgnoreCase("Wachttoren")) {
			if (Integer.parseInt(year) >= 2018) {
				return this.monthsPubliek2018;
			} else if (Integer.parseInt(year) > 2015) {
				return this.monthsPubliek;
			} else {
				return this.allMonths;
			}
		} else {
			if (Integer.parseInt(year) > 2007) {
				return this.allMonths;
			} else {
				return null;
			}
		}
	}
}
