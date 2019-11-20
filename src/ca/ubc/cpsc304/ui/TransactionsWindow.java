package ca.ubc.cpsc304.ui;

import ca.ubc.cpsc304.delegates.TransactionsWindowDelegate;
import ca.ubc.cpsc304.model.ReservationModel;
import ca.ubc.cpsc304.number_generator.RandomNumberGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class is responsible for displaying and handling the transactions GUI.
 */
public class TransactionsWindow extends JFrame {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the login window
    private JTextField vtnameField;
    private JTextField locationField;
    private JTextField fromField;
    private JTextField toField;

    // delegate
    private TransactionsWindowDelegate delegate;


    DefaultTableModel vmodel = new DefaultTableModel();
    DefaultTableModel searchmodel = new DefaultTableModel();

    JButton seeVButton = new JButton(" ");
    JPanel myPanel = new JPanel();

    public TransactionsWindow() {
        super("SuperRent");
    }

    public void showFrame(TransactionsWindowDelegate delegate) {
        this.delegate = delegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        JLabel vtnameLabel = new JLabel("Enter vehicle type: ");
        JLabel locationLabel = new JLabel("Enter branch: ");
        JLabel fromLabel = new JLabel("From (yyyy-mm-dd hh): ");
        JLabel toLabel = new JLabel("Until (yyyy-mm-dd hh): ");

        vtnameField = new JTextField(TEXT_FIELD_WIDTH);
        locationField = new JTextField(TEXT_FIELD_WIDTH);
        fromField = new JTextField(TEXT_FIELD_WIDTH);
        toField = new JTextField(TEXT_FIELD_WIDTH);

        JButton searchButton = new JButton("Search");

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu reports = new JMenu("Reports");
        JMenu vehicles = new JMenu("Vehicles");
        mb.add(vehicles);
        mb.add(reports);
        JMenuItem rentals = new JMenuItem("Rentals");
        JMenuItem returns = new JMenuItem("Returns");
        JMenuItem makeReservation = new JMenuItem("Reserve");
        JMenuItem makeRental = new JMenuItem("Rent");
        JMenuItem makeReturn = new JMenuItem("Return");
        reports.add(rentals);
        reports.add(returns);
        vehicles.add(makeReservation);
        vehicles.add(makeRental);
        vehicles.add(makeReturn);

        // place menu bar
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 9;
        c.gridx = 0;
        c.gridy = 0;
        gb.setConstraints(mb, c);
        contentPane.add(mb);

        // place the vehicle type label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        gb.setConstraints(vtnameLabel, c);
        contentPane.add(vtnameLabel);

        // place the text field for the vehicle type
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        gb.setConstraints(vtnameField, c);
        contentPane.add(vtnameField);

        // place location label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 0);
        gb.setConstraints(locationLabel, c);
        contentPane.add(locationLabel);

        // place the location field
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        gb.setConstraints(locationField, c);
        contentPane.add(locationField);

        // place from label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 0);
        gb.setConstraints(fromLabel, c);
        contentPane.add(fromLabel);

        // place the from field
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        gb.setConstraints(fromField, c);
        contentPane.add(fromField);

        // place until label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 6;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 0);
        gb.setConstraints(toLabel, c);
        contentPane.add(toLabel);

        // place the until field
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 7;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        gb.setConstraints(toField, c);
        contentPane.add(toField);

        // place the search button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 8;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 0);
        gb.setConstraints(searchButton, c);
        contentPane.add(searchButton);

        // Set up vehicle table
        JTable vehicleTable = new JTable(vmodel);
        JScrollPane sp = new JScrollPane(vehicleTable);
        vehicleTable.setFillsViewportHeight(true);
        vehicleTable.setModel(vmodel);

        // add table to the ui
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 9;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        gb.setConstraints(sp, c);
        contentPane.add(sp);

        // set see vehicles button
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        gb.setConstraints(seeVButton, c);
        contentPane.add(seeVButton);

        // register buttons with action event handler
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchmodel = delegate.showVehicles(vtnameField.getText(),locationField.getText(),fromField.getText(),
                        toField.getText());
                int num = searchmodel.getRowCount();

                JButton btn = seeVButton;
                btn.setText(num + " Vehicles found");
                vehicleTable.setModel(vmodel);
            }
        });
        seeVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vehicleTable.setModel(searchmodel);
            }
        });
        makeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input = reservationForm();
                if (input != null) {
                    delegate.insertReservation(new ReservationModel(RandomNumberGenerator.generateRandomReservationNumber(),
                            input[0], input[1], input[2], input[3]));
                }
            }
        });
        makeRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        makeReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        rentals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input = reportInput();
                if (input != null) {
                    searchmodel = delegate.showDailyRentalsReport(input[0], input[1]);
                    vehicleTable.setModel(searchmodel);
                }
            }
        });
        returns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] input = reportInput();
                if (input != null) {
                    searchmodel = delegate.showDailyReturnsReport(input[0], input[1]);
                    vehicleTable.setModel(searchmodel);
                }
            }
        });

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
        vtnameField.requestFocus();
    }

    private String[] reservationForm() {
        JTextField vtField = new JTextField(10);
        JTextField dlField = new JTextField(10);
        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        myPanel.add(new JLabel("Vehicle Type:"));
        myPanel.add(vtField);
        myPanel.add(new JLabel("Driver License:"));
        myPanel.add(dlField);
        myPanel.add(new JLabel("From:"));
        myPanel.add(fromField);
        myPanel.add(new JLabel("To:"));
        myPanel.add(toField);

        String[] res = null;
        String error = "";
        boolean prev = false;
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Make Reservation", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String vtname = vtField.getText().trim();
            String dlicense = dlField.getText().trim();
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            if (vtname.isEmpty()) {
                error = " the type of vehicle you want";
                prev = true;
            }
            if (dlicense.isEmpty()){
                if (prev) {
                    error = error + ", your driver's license";
                } else {
                    error = error + " your driver's license";
                    prev = true;
                }
            }
            if (from.isEmpty()){
                if (prev) {
                    error = error + ", the starting time";
                } else {
                    error = error + " the starting time";
                    prev = true;
                }
            }if (to.isEmpty()){
                if (prev) {
                    error = error + " and the ending time";
                } else {
                    error = error + " the ending time";
                }
            }
            if (prev){
                error = "Please specify" + error;
                inputError(error);
            } else {
                res = new String[]{vtname, dlicense, from, to};
            }
        }
        return res;
    }

    public void inputError(String errorMsg) {
        JOptionPane.showMessageDialog(null,errorMsg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String[] reportInput(){
        JTextField dateField = new JTextField(10);
        JTextField branchField = new JTextField(10);

        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        myPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        myPanel.add(dateField);
        myPanel.add(Box.createVerticalStrut(1)); // a spacer
        myPanel.add(new JLabel("Branch (Optional):"));
        myPanel.add(branchField);

        String[] res = null;
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Report", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String branch = branchField.getText();
            res = new String[]{date, branch};
        }
        return res;
    }
}
