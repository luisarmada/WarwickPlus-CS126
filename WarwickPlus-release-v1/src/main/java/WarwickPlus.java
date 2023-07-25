import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.InsetsUIResource;

import screen.*;
import stores.*;
import utils.*;

public class WarwickPlus {
    private static Credits credits = new Credits();
    private static Keywords keywords = new Keywords();
    private static Movies movies = new Movies();
    private static Ratings ratings = new Ratings();

    public static int getHSize() {
        return Constants.hSize;
    }

    public static int getVSize(){
        return Constants.vSize;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Warwick+");
        frame.setVisible(false);

        frame.setBackground(Constants.background);
        frame.getContentPane().setBackground(Constants.background);
        ImageIcon icon = new ImageIcon("src/main/resources/img/WarwickPlusIcon.png");
        frame.setIconImage(icon.getImage());

        DisplayImage logo;
        try {
            logo = new DisplayImage("src/main/resources/img/WarwickPlusLogo.png");
            logo.setBounds(0, (int) (Constants.vSize * 0.125), Constants.hSize, (int) (Constants.vSize * 0.25));
            frame.getContentPane().add(logo);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
       

        JProgressBar loadingBar = new JProgressBar(0, 100000);
        loadingBar.setBounds(40, (int) (Constants.vSize*0.5), (Constants.hSize-(40*2)), 40);
        loadingBar.setValue(JProgressBar.CENTER);
        loadingBar.setStringPainted(true);

        JLabel loadingText = new JLabel("Loading...");
        loadingText.setBounds(0, (int) (Constants.vSize*0.75), Constants.hSize, 40);
        loadingText.setHorizontalAlignment(JLabel.CENTER);
        loadingText.setForeground(Constants.fontColor);

        frame.getContentPane().add(loadingBar);
        frame.getContentPane().add(loadingText);

        frame.setSize(Constants.hSize, Constants.vSize);

        //frame.add(content);

        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoadData loading = new LoadData(loadingBar, loadingText, credits, keywords, movies, ratings);
        Thread loadingThread = new Thread(loading);

        loadingThread.start();
        try{
            loadingThread.join();
        } catch (Exception e) {

        }
        System.out.println("\nFinished Loading...");

        setHomescreen(frame.getContentPane());
        
    }

    private static void setHomescreen(Container frame) {
        System.out.println("Home screen --> Default");
        frame.setVisible(false);
        frame.removeAll();

        JPanel content = new JPanel();
        JTextField searchBox = new JTextField("Search...");

        content.setBounds((int) (Constants.hSize * 0.15), (int) (Constants.vSize * 0.07), (int) (Constants.hSize * 0.85), (int) (Constants.vSize * 0.93));
        content.setBackground(Constants.highlight);
        content.setForeground(Constants.fontColor);
        content.setLayout(null);

        //Build logo with required click listener
        DisplayImage logo;
        try {
            logo = new DisplayImage("src/main/resources/img/WarwickPlusLogo.png");
            logo.setBounds(0, (int) (Constants.vSize * 0.01), (int) (Constants.hSize * 0.15), (int) (Constants.vSize * 0.05));
            logo.addMouseListener(new MouseInputListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    content.removeAll();
                    searchBox.setText("Search...");
                    setHomescreen(frame);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }

            });

            frame.add(logo);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //Build search box with required listeners
        searchBox.setBounds((int) (Constants.hSize * 0.15), (int) (Constants.vSize * 0.01), (int) (Constants.hSize*0.85), (int) (Constants.vSize * 0.05));
        searchBox.setBackground(Constants.highlight);
        searchBox.setForeground(Constants.fontColor);
        searchBox.setMargin(new InsetsUIResource(3, 3, 3, 3));
        searchBox.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {searchBox.setText("");}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {}
            
        });
        searchBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SearchScreen.createPanel(content, e.getActionCommand().toString(), movies, credits, keywords);
                    }
                });
            }
        });

        String[] menuString = {"Cast", "Crew", "Ratings", "Cast Distances"};
        int menuVStart = (int) (Constants.vSize*0.07);
        int menuVEnd = (int) (Constants.vSize*0.7);
        int menuVSize = (menuVEnd - menuVStart)/menuString.length;

        for (int i = 0; i < menuString.length; i++) {
            JPanel menuItemPanel = new JPanel();
            menuItemPanel.setBounds(0, menuVStart + (menuVSize * i), (int) (Constants.hSize * 0.15), menuVSize);
            menuItemPanel.setLayout(new GridBagLayout());
            menuItemPanel.setBackground(Constants.background);
            menuItemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

            switch(menuString[i]) {
                case "Ratings": 
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    RatingsScreen.createPanel(content, movies, ratings, credits);                                    
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}
                        
                    });
                    break;
                case "Films": 
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    ConstructionScreen.createPanel(content, movies, ratings, credits);                                    
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}
                        
                    });
                    break;
                case "Genres": 
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ConstructionScreen.createPanel(content, movies, ratings, credits);                                    
                            }
                        });
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}

                    @Override
                    public void mouseDragged(MouseEvent e) {}

                    @Override
                    public void mouseMoved(MouseEvent e) {}
                    
                });
                break;
                case "Cast":
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    CastScreen.createPanel(content, movies, ratings, credits);
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}

                    });
                    break;
                case "Crew":
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    CrewScreen.createPanel(content, movies, ratings, credits);
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}

                    });
                    break;
                case "Cast Distances":
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    CastDistances.createPanel(content, movies, ratings, credits);
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}

                    });
                    break;
                default:
                    menuItemPanel.addMouseListener(new MouseInputListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    UnderConstructionScreen.createPanel(content);
                                }
                            });
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {}

                        @Override
                        public void mouseReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered(MouseEvent e) {}

                        @Override
                        public void mouseExited(MouseEvent e) {}

                        @Override
                        public void mouseDragged(MouseEvent e) {}

                        @Override
                        public void mouseMoved(MouseEvent e) {}

                    });
            }

            JLabel menuItemLabel = new JLabel(menuString[i]);
            menuItemLabel.setHorizontalAlignment(JLabel.CENTER);
            menuItemLabel.setVerticalAlignment(JLabel.CENTER);
            menuItemLabel.setForeground(Constants.fontColor);
            
            menuItemPanel.add(menuItemLabel);

            frame.add(menuItemPanel);
        }

        //Build stat block
        String statString = "";
        if (credits.getUniqueCastIDs() == null || credits.getUniqueCrewIDs() == null || keywords.getUnique() == null) {
            statString = " Films: " + movies.size() + " movies\n Film Credits: " + credits.size() + " movies\n     Unique Cast: null\n     Unique Crew: null\n Ratings: " + ratings.size() + "\n Keywords: " + keywords.size() + " movies\n     (null unique keywords)";
        } else {
            statString = " Films: " + movies.size() + " movies\n Film Credits: " + credits.size() + " movies\n     Unique Cast: " + credits.getUniqueCastIDs().length + "\n     Unique Crew: " + credits.getUniqueCrewIDs().length + "\n Ratings: " + ratings.size() + "\n Keywords: " + keywords.size() + " movies\n     (" + keywords.getUnique().length + " unique keywords)";
        }
        JTextArea stats = new JTextArea(statString);
        stats.setForeground(Constants.fontColor);
        stats.setBackground(Constants.background);
        stats.setEditable(false);
        stats.setMargin(new InsetsUIResource(3, 3, 3, 3));
        stats.setBounds(0, (int) (Constants.vSize * 0.75), (int) (Constants.hSize * 0.15), (int) (Constants.vSize * 0.25));

        MainScreen.createPanel(content, movies, ratings, credits);

        //Build frame
        frame.add(searchBox);
        frame.add(stats);
        frame.add(content);

        frame.setVisible(true);
    }
}