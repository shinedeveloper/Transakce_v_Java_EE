package net.sevecek.videoboss.gui;

import java.awt.event.*;
import javax.annotation.*;
import javax.inject.*;
import javax.swing.*;
import javax.swing.border.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.service.*;

@Named("mainWindow")
@Lazy
public class CustomerWindow extends JFrame {

    @Inject
    private ServiceFacade serviceFacade;

    @Inject
    private ApplicationContext applicationContext;

    private JButton btnAddCustomer;
    private JButton btnDeleteCustomer;
    private JButton btnEditCustomer;
    private JScrollPane jScrollPane1;
    private JLabel labCenterLeft;
    private JLabel labCenterRight;
    private JLabel labCustomers;
    private JTable tabCustomers;
    private net.sevecek.tablemodel.BeanTableModel<Customer> customersTableModel;


    @PostConstruct
    private void initComponents() {
        customersTableModel = new net.sevecek.tablemodel.BeanTableModel<Customer>();
        jScrollPane1 = new JScrollPane();
        tabCustomers = new JTable();
        btnDeleteCustomer = new JButton();
        labCenterLeft = new JLabel();
        labCenterRight = new JLabel();
        labCustomers = new JLabel();
        btnAddCustomer = new JButton();
        btnEditCustomer = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabulka zákazníků");
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                onWindowOpened(e);
            }
        });

        customersTableModel.setBeanClassName("net.sevecek.videoboss.entity.Customer");
        customersTableModel.setPropertyNames(new String[] {"id", "firstName", "lastName", "address"});

        jScrollPane1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        tabCustomers.setAutoCreateRowSorter(true);
        tabCustomers.setModel(customersTableModel);
        tabCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabCustomers);

        btnDeleteCustomer.setText("Delete");
        btnDeleteCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        labCustomers.setFont(labCustomers.getFont().deriveFont(labCustomers.getFont().getStyle() | java.awt.Font.BOLD, labCustomers.getFont().getSize() + 5));
        labCustomers.setText("Customers");

        btnAddCustomer.setText("Přidat");
        btnAddCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        btnEditCustomer.setText("Edit");
        btnEditCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEditCustomerActionPerformed(evt);
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
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addComponent(labCustomers)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(labCenterLeft, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAddCustomer)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditCustomer)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDeleteCustomer)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labCenterRight, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                                .addContainerGap())))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, btnAddCustomer, btnDeleteCustomer, btnEditCustomer);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labCustomers)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnAddCustomer)
                                                .addComponent(btnEditCustomer)
                                                .addComponent(btnDeleteCustomer))
                                        .addComponent(labCenterRight, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labCenterLeft, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    }

    //--------------------------------------------------------------------------------

    private void onWindowOpened(WindowEvent evt) {
        this.customersTableModel.setRows(serviceFacade.findAllCustomers());
    }


    private void btnEditCustomerActionPerformed(ActionEvent evt) {
        Customer selectedCustomer = getSelectedCustomer();
        if (selectedCustomer == null) return;
        AddOrEditCustomerDialog dialog = new AddOrEditCustomerDialog(this, selectedCustomer);
        Customer updatedCustomer = dialog.execute();
        if (updatedCustomer != null) {
            updatedCustomer = serviceFacade.updateCustomer(updatedCustomer);
            customersTableModel.updateBean(updatedCustomer);
        }
    }


    private void btnAddCustomerActionPerformed(ActionEvent evt) {
        AddOrEditCustomerDialog dialog = new AddOrEditCustomerDialog(this, null);
        Customer newCustomer = dialog.execute();
        if (newCustomer != null) {
            newCustomer = serviceFacade.addCustomer(newCustomer);
            customersTableModel.addBean(newCustomer);
        }
    }

    private AddOrEditCustomerDialog getDialog(Customer existingCustomer) {
        AddOrEditCustomerDialog dialog = (AddOrEditCustomerDialog)
                applicationContext.getBean("addOrEditCustomerDialog", this, existingCustomer);
        return dialog;
    }


    private void btnDeleteCustomerActionPerformed(ActionEvent evt) {
        Customer selectedCustomer = getSelectedCustomer();
        if (selectedCustomer == null) return;
        selectedCustomer = serviceFacade.deleteCustomer(selectedCustomer);
        customersTableModel.removeBean(selectedCustomer);
    }


    public Customer getSelectedCustomer() {
        int selectedRow = getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        return (Customer) customersTableModel.getBeanAt(selectedRow);
    }


    private int getSelectedRow() {
        int selectedRow = tabCustomers.getSelectionModel().getLeadSelectionIndex();
        if (selectedRow >= 0) {
            selectedRow = tabCustomers.convertRowIndexToModel(selectedRow);
        }
        return selectedRow;
    }

}
