package net.sevecek.videoboss.gui;

import net.sevecek.videoboss.entity.Customer;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component("addOrEditCustomerDialog")
@Scope("prototype")
public class AddOrEditCustomerDialog extends JDialog {

    private Customer oldCustomer;
    private Customer newCustomer;
    private JButton btnCancel;
    private JButton btnOK;
    private JLabel labAddress;
    private JLabel labCenterLeft;
    private JLabel labCenterRight;
    private JLabel labFirstName;
    private JLabel labID;
    private JLabel labLastName;
    private JLabel labTitle;
    private JTextField txtAddress;
    private JTextField txtFirstName;
    private JTextField txtID;
    private JTextField txtLastName;

    //-------------------------------------------------------------------------

    public AddOrEditCustomerDialog(JFrame parent, Customer customer) {
        super(parent, true);
        oldCustomer = customer;
        newCustomer = null;
        initComponents();
        loadDataFromBean();
    }

    
    public Customer execute() {
        setVisible(true);
        return newCustomer;
    }

    private void loadDataFromBean() {
        if (oldCustomer != null) {
            txtID.setText(oldCustomer.getId().toString());
            txtFirstName.setText(oldCustomer.getFirstName());
            txtLastName.setText(oldCustomer.getLastName());
            txtAddress.setText(oldCustomer.getAddress());
        }
    }

    private void saveDataToBean() {
        newCustomer = new Customer();
        newCustomer.setFirstName(txtFirstName.getText());
        newCustomer.setLastName(txtLastName.getText());
        newCustomer.setAddress(txtAddress.getText());
        if (oldCustomer != null) {
            newCustomer.setId(oldCustomer.getId());
            newCustomer.setDeleted(oldCustomer.isDeleted());
            newCustomer.setVersion(oldCustomer.getVersion());
        }
    }

    private void initComponents() {
        labTitle = new JLabel();
        labID = new JLabel();
        txtID = new JTextField();
        labFirstName = new JLabel();
        txtFirstName = new JTextField();
        labLastName = new JLabel();
        txtLastName = new JTextField();
        labAddress = new JLabel();
        txtAddress = new JTextField();
        labCenterRight = new JLabel();
        btnOK = new JButton();
        btnCancel = new JButton();
        labCenterLeft = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit the customer");

        labTitle.setFont(labTitle.getFont().deriveFont(labTitle.getFont().getStyle() | java.awt.Font.BOLD, labTitle.getFont().getSize()+3));
        labTitle.setText("Edit the customer");

        labID.setText("Number");

        txtID.setEditable(false);

        labFirstName.setText("Name");

        labLastName.setText("Surname");

        labAddress.setText("Address");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(labID, GroupLayout.Alignment.TRAILING)
                                    .addComponent(labFirstName, GroupLayout.Alignment.TRAILING)
                                    .addComponent(labAddress, GroupLayout.Alignment.TRAILING)))
                            .addComponent(labLastName))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtFirstName, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtLastName, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtID, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labTitle)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labCenterLeft, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labCenterRight, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.HORIZONTAL, btnCancel, btnOK);

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labID)
                    .addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labFirstName)
                    .addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labLastName))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labAddress)
                    .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOK)
                        .addComponent(btnCancel))
                    .addComponent(labCenterRight, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labCenterLeft, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        Container parent = getParent();
        if (parent != null) {
            int x = parent.getX() + parent.getWidth() / 2 - getWidth() / 2;
            int y = parent.getY() + parent.getHeight() / 2 - getHeight() / 2;
            setLocation(x, y);
        }
        txtFirstName.requestFocusInWindow();
    }

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {
        saveDataToBean();
        setVisible(false);
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        setVisible(false);
    }
    
}
