package DarkestCode;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Properties;
import javazoom.jl.player.Player;



public class FrameTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Player player;
	private Thread musicThread;  // Variable pour stocker le thread
	private void stopMusic() {
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();  // **Interrompt le thread de musique si il est en cours d'exécution**
            player.close();  // Ferme le lecteur pour libérer les ressources
        }
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTest frame = new FrameTest();
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
	public FrameTest() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameTest.class.getResource("/DarkestCode/Ressources/torch.png")));
		JLabel label = new JLabel(new ImageIcon("/DarkestCode/Ressources/torch.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FrameTest.class.getResource("/DarkestCode/Ressources/dark.jpg")));
		lblNewLabel.setBounds(0, 0, 434, 261);
		contentPane.add(lblNewLabel);
		
		JLabel title = new JLabel("");
		title.setIcon(new ImageIcon(FrameTest.class.getResource("/DarkestCode/Ressources/Darkest_Dungeon_Logo.png")));
		title.setBounds(0, 0, 200, 76);
		contentPane.add(title);
		contentPane.setComponentZOrder(title, 0);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"The Hamlet", "The ruins", "Cove battle"}));
		comboBox.setBounds(10, 191, 81, 22);
		contentPane.add(comboBox);
		
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopMusic();
				
				String SelectedItem = (String) comboBox.getSelectedItem();
				String filePath = "/DarkestCode/Ressources/" + SelectedItem + ".mp3";
				
				try {
		            // Récupère le fichier via getResourceAsStream
		            InputStream audioSrc = FrameTest.class.getResourceAsStream(filePath);
		            
		            if (audioSrc != null) {
		                // Crée une nouvelle instance du Player avec l'InputStream
		                player = new Player(audioSrc);
		                
		                // Crée un thread pour jouer la musique sans bloquer l'interface utilisateur
		                musicThread = new Thread(() -> {
		                    try {
		                        player.play();  // Joue la musique
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                    }
		                });
		                musicThread.start();  // Lance le thread
		            } else {
		                // Si le fichier n'a pas été trouvé, affiche une erreur
		                System.out.println("Le fichier n'a pas été trouvé : " + filePath);
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
		});
		btnPlay.setBounds(10, 224, 81, 23);
		contentPane.add(btnPlay);
		contentPane.setComponentZOrder(btnPlay, 0);
		
	}
}
