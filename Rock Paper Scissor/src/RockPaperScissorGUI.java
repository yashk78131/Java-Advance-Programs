/*
The GUI class will represent the fronted of our app
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// inheritting from the JFrame class will allow our class to function like a GUI
public class RockPaperScissorGUI extends JFrame {

    // player buttons
    JButton rockButton, paperButton, scissorButton;

    // will display the choice of the computer
    JLabel computerChoice;

    // we will be creating our constructor for our GUI class so that we can instantiate it in our App class which will contain the main()
    public RockPaperScissorGUI() {
         // invoke Jframe constructor and add title to the GUI
        super("Rock Paper Scissor");

        //set the size of the GUI
        setSize(450,574);

        //set layout to null to disable layout management, so we can use absolute positioning for the elements
        setLayout(null);

        // terminate the java virtual machine when closing the GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //load GUI in the center of the screen every time we run the application
        setLocationRelativeTo(null);

        // add GUI Components
        addGuiComponents();
    }

    private void addGuiComponents() {
        // create Computer Score Label
        JLabel computerScoreLabel = new JLabel("Computer: 0");

        // set x,y coordinates and width/height values
        computerScoreLabel.setBounds(0, 43, 450, 30);

        //set the font to have a font family of dialog, font-weight of bold, and a font-size of 26
        computerScoreLabel.setFont(new Font("Dialog",Font.BOLD,26));

        // place the text in the Center
        computerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add to GUI
        add(computerScoreLabel);

        // Create computer Choice
        computerChoice = new JLabel("?");
        computerChoice.setBounds(175,118,98,81);
        computerChoice.setFont(new Font("Dialog",Font.PLAIN,18));
        computerChoice.setHorizontalAlignment(SwingConstants.CENTER);

        // create a black border around
        computerChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(computerChoice);

        // create player score label
        JLabel playerScoreLabel = new JLabel("Player: 0");
        playerScoreLabel.setBounds(0,317,450,30);
        playerScoreLabel.setFont(new Font("Dialog",Font.BOLD,26));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerScoreLabel);


        // player buttons

        // rock Button
        rockButton = new JButton("Rock");
        rockButton.setBounds(40,387,105,81);
        rockButton.setFont(new Font("Dialog",Font.PLAIN,18));
        add(rockButton);

        // paper Button
        paperButton = new JButton("Paper");
        paperButton.setBounds(165,387,105,81);
        paperButton.setFont(new Font("Dialog",Font.PLAIN,18));
        add(paperButton);

        // Scissor Button
        scissorButton = new JButton("Scissor");
        scissorButton.setBounds(290 ,387,105,81);
        scissorButton.setFont(new Font("Dialog",Font.PLAIN,18));
        add(scissorButton);

        showDialog("Test Message ");


    }

    // displays message dialog which will show thr winner and a try again button to play again
    private void showDialog(String message) {

        JDialog resultDialog = new JDialog(this,"Result",true);
        resultDialog.setSize(227,124);
        resultDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        resultDialog.setResizable(false);

        // message label
        JLabel resultLabel = new JLabel(message);
        resultLabel.setFont(new Font("Dialog",Font.BOLD,18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultDialog.add(resultLabel);

        // try again button
        JButton tryAgainButton = new JButton("Try Again?");
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // reset computer choice
                computerChoice.setText("?");

                // close the dialog box
                resultDialog.dispose();

            }
        });

        resultDialog.add(tryAgainButton);

        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);

    }

}


