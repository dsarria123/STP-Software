package edu.ncsu.csc216.stp.view.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.stp.model.manager.TestPlanManager;
import edu.ncsu.csc216.stp.model.test_plans.AbstractTestPlan;
import edu.ncsu.csc216.stp.model.test_plans.FailingTestList;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.tests.TestResult;


/**
 * User Interface for the SystemTestPlan project that provides the user the ability
 * to interact with test plans, tests, and their results.
 * 
 * @author Dr. Sarah Heckman
 */
public class SystemTestPlanGUI extends JFrame implements ActionListener {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "SystemTestPlan";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the Load menu item. */
	private static final String LOAD_TITLE = "Load Test Plan(s)";
	/** Text for the Save menu item. */
	private static final String SAVE_TITLE = "Save Test Plan(s)";
	/** Text for the Clear menu item. */
	private static final String CLEAR_TITLE = "Clear Test Plan(s)";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for loading a system test plan file. */
	private JMenuItem itemLoad;
	/** Menu item for saving system test plans to a file. */
	private JMenuItem itemSave;
	/** Menu item for clearing system state. */
	private JMenuItem itemClear;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	/** JPanel for the TestCases */
	private TestPlansPanel pnlTestPlans;
	/** Border for System Test Plans and Test Cases */
	private TitledBorder borderTestPlans;
	/** JPanel for a Test */
	private TestCasePanel pnlTestCase;
	
	/** Current TestPlanManager. */
	private TestPlanManager manager;
	
	/**
	 * Constructs a SystemTestPlanGUI object that will contain a JMenuBar and a
	 * JPanel that will hold different possible views of the data in
	 * the SystemTestPlan system.
	 */
	public SystemTestPlanGUI() {
		super();
		
		manager = new TestPlanManager();
		
		//Set up general GUI info
		setSize(1200, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();
		
		//Add panel to the container
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		
		pnlTestCase = new TestCasePanel();
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Test Case");
		pnlTestCase.setBorder(border);
		pnlTestCase.setToolTipText("Test Case");
		
		pnlTestPlans = new TestPlansPanel();
		lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		borderTestPlans = BorderFactory.createTitledBorder(lowerEtched, "Test Plans");
		pnlTestPlans.setBorder(borderTestPlans);
		pnlTestPlans.setToolTipText("Test Plans");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = .5;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		c.add(pnlTestPlans, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		c.add(pnlTestCase, constraints);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Makes the GUI Menu bar that contains options working SystemTestPlan objects.
	 */
	private void setUpMenuBar() {
		//Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemLoad = new JMenuItem(LOAD_TITLE);
		itemSave = new JMenuItem(SAVE_TITLE);
		itemClear = new JMenuItem(CLEAR_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		
		itemLoad.addActionListener(this);
		itemSave.addActionListener(this);
		itemClear.addActionListener(this);
		itemQuit.addActionListener(this);
		
		//Start with save button disabled
		itemSave.setEnabled(false);
		
		//Build Menu and add to GUI
		menu.add(itemLoad);
		menu.add(itemSave);
		menu.add(itemClear);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemClear) { 
			if (manager.isChanged()) {
				int select = JOptionPane.showConfirmDialog(null, "Test plans are unsaved. Would you like to save before clearning?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (select == 1) {
					manager.clearTestPlans();
				} 
			} else {
				manager.clearTestPlans();
			}
			
		} else if (e.getSource() == itemLoad) {
			try {
				manager.loadTestPlans(new File(getFileName(true)));
				pnlTestPlans.updateLists();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, "Unable to load file.");
			} catch (IllegalStateException ise) {
				//ignore the exception
			}
		} else if (e.getSource() == itemSave) {
			//Save current service group and incidents
			try {
				manager.saveTestPlans(new File(getFileName(false)));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemQuit) {
			if (manager != null && manager.isChanged()) {
				//Quit the program
				try {
					manager.saveTestPlans(new File(getFileName(false)));
					System.exit(0);  //Ignore SpotBugs warning here - this is the only place to quit the program!
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				} catch (IllegalStateException exp) {
					//Don't do anything - user canceled (or error)
				}
			} else {
				System.exit(0);
			}
		}
		
		pnlTestPlans.updateLists();
		
		itemSave.setEnabled(manager != null && manager.isChanged());
		repaint();
		validate();
		
	}
	
	/**
	 * Returns a file name generated through interactions with a JFileChooser
	 * object.
	 * @param load true if loading a file, false if saving
	 * @return the file name selected through JFileChooser
	 * @throws IllegalStateException if no file name provided
	 */
	private String getFileName(boolean load) {
		//Open JFileChooser to current working directory
		JFileChooser fc = new JFileChooser("./");  
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = fc.showOpenDialog(this);
		} else {
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File gameFile = fc.getSelectedFile();
		return gameFile.getAbsolutePath();
	}
	
	/**
	 * Starts the GUI for the SystemTestPlan application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new SystemTestPlanGUI();
	}
	
	/**
	 * JPanel for test plans.
	 */
	private class TestPlansPanel extends JPanel implements ActionListener {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Leading text for failing tests label */
		private static final String FAILING_TESTS_LABEL = "Number of Failing Tests: ";
		
		/** Label for selecting current test plan */
		private JLabel lblCurrentTestPlan;
		/** Combo box for test plan list */
		private JComboBox<String> comboTestPlans;
		/** Label for number of failing test */
		private JLabel lblFailingTests;
		
		/** Button to add a test plan */
		private JButton btnAddTestPlan;
		/** Button to edit the selected test plan */
		private JButton btnEditTestPlan;
		/** Button to remove the selected test plan */
		private JButton btnRemoveTestPlan;
		
		/** Button - move up */
		private JButton btnMoveUp;
		/** Button - move down */
		private JButton btnMoveDown;
		/** Button - move to front */
		private JButton btnMoveToFront;
		/** Button - move to back */
		private JButton btnMoveToBack;

		/** JTable for displaying the list of test cases */
		private JTable tableTestCases;
		/** TableModel for test cases */
		private TestCaseTableModel tableModel;
		
		/**
		 * Creates the test plan list and populates the tests for the current
		 * test plan.
		 */
		public TestPlansPanel() {
			super(new GridBagLayout());
			
			lblCurrentTestPlan = new JLabel("Current Test Plan");
			comboTestPlans = new JComboBox<String>();
			comboTestPlans.addActionListener(this);
			
			lblFailingTests = new JLabel(FAILING_TESTS_LABEL);
			
			btnAddTestPlan = new JButton("Add Test Plan");
			btnEditTestPlan = new JButton("Edit Test Plan");
			btnRemoveTestPlan = new JButton("Remove Test Plan");
			
			btnAddTestPlan.addActionListener(this);
			btnEditTestPlan.addActionListener(this);
			btnRemoveTestPlan.addActionListener(this);
			
			tableModel = new TestCaseTableModel();
			tableTestCases = new JTable(tableModel);
			tableTestCases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableTestCases.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableTestCases.setFillsViewportHeight(true);
			tableTestCases.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					int idx = tableTestCases.getSelectedRow();
					pnlTestCase.setTestCase(idx);
				}
				
			});
			
			btnMoveUp = new JButton("Move Up");
			btnMoveDown = new JButton("Move Down");
			btnMoveToFront = new JButton("Move to Front");
			btnMoveToBack = new JButton("Move to Back");
			
			btnMoveUp.addActionListener(this);
			btnMoveDown.addActionListener(this);
			btnMoveToFront.addActionListener(this);
			btnMoveToBack.addActionListener(this);
			
			JScrollPane listScrollPane = new JScrollPane(tableTestCases);
			
			JPanel pnlCurrentTestPlan = new JPanel();
			pnlCurrentTestPlan.setLayout(new GridLayout(1, 2));
			pnlCurrentTestPlan.add(lblCurrentTestPlan);
			pnlCurrentTestPlan.add(comboTestPlans);
			
			JPanel pnlTestPlanActions = new JPanel();
			pnlTestPlanActions.setLayout(new GridLayout(1, 3));
			pnlTestPlanActions.add(btnAddTestPlan);
			pnlTestPlanActions.add(btnEditTestPlan);
			pnlTestPlanActions.add(btnRemoveTestPlan);
			
			GridBagConstraints c = new GridBagConstraints();
			
			JPanel pnlTestCases = new JPanel();
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Test Cases");
			pnlTestCases.setBorder(border);
			pnlTestCases.setToolTipText("Test Cases");
			pnlTestCases.setLayout(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 20;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlTestCases.add(listScrollPane, c);
			
			// move up/down
			JPanel move0 = new JPanel();
			move0.setLayout(new GridLayout(1, 2));
			move0.add(btnMoveUp);
			move0.add(btnMoveDown);
			
			c.gridx = 0;
			c.gridy = 21;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlTestCases.add(move0, c);
			
			//move front/back
			JPanel move1 = new JPanel();
			move1.setLayout(new GridLayout(1, 2));
			move1.add(btnMoveToFront);
			move1.add(btnMoveToBack);
			
			c.gridx = 0;
			c.gridy = 22;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlTestCases.add(move1, c);
			
			
			// Put everything together
			c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlCurrentTestPlan, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(lblFailingTests, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlTestPlanActions, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 25;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlTestCases, c);
			
			updateLists();
		}

		/**
		 * Handles actions when the user interacts with buttons and items on 
		 * the JPanel.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		
			if (e.getSource() == comboTestPlans) {
				int idx = comboTestPlans.getSelectedIndex();
				
				if (idx == -1) {
					updateLists();
				} else {
					String testPlanName = comboTestPlans.getItemAt(idx);
					manager.setCurrentTestPlan(testPlanName);
					updateLists();
				}
			} else if (e.getSource() == btnAddTestPlan) {
				try {
					String testPlanName = (String) JOptionPane.showInputDialog(this, "Test Plan Name?", "Create New Test Plan", JOptionPane.QUESTION_MESSAGE);
					manager.addTestPlan(testPlanName);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnEditTestPlan) {
				try {
					if (manager.getCurrentTestPlan() instanceof FailingTestList) {
						JOptionPane.showMessageDialog(SystemTestPlanGUI.this, "The Failing Tests list may not be edited.");
					} else {
						String testPlanName = (String) JOptionPane.showInputDialog(this, "Update Test Plan Name?", "Edit Test Plan", JOptionPane.QUESTION_MESSAGE, null, null, manager.getCurrentTestPlan().getTestPlanName());
						manager.editTestPlan(testPlanName);
					}
					
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemoveTestPlan) {
				try {
					if (manager.getCurrentTestPlan() instanceof FailingTestList) {
						JOptionPane.showMessageDialog(SystemTestPlanGUI.this, "The Failing Test list may not be deleted.");
					} else {
						manager.removeTestPlan();
					}
					
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnMoveUp) {
				try {
					int idxTestCase = pnlTestPlans.tableTestCases.getSelectedRow();
					AbstractTestPlan testPlan = manager.getCurrentTestPlan();
					if (testPlan instanceof FailingTestList) {
						throw new IllegalArgumentException("The Failing Tests list may not be modified directly.");
					} else {
						testPlan.getTestCases().moveUp(idxTestCase);
					}
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnMoveDown) {
				try {
					int idxTestCase = pnlTestPlans.tableTestCases.getSelectedRow();
					AbstractTestPlan testPlan = manager.getCurrentTestPlan();
					if (testPlan instanceof FailingTestList) {
						throw new IllegalArgumentException("The Failing Tests list may not be modified directly.");
					} else {
						testPlan.getTestCases().moveDown(idxTestCase);
					}
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnMoveToFront) {
				try {
					int idxTestCase = pnlTestPlans.tableTestCases.getSelectedRow();
					AbstractTestPlan testPlan = manager.getCurrentTestPlan();
					if (testPlan instanceof FailingTestList) {
						throw new IllegalArgumentException("The Failing Tests list may not be modified directly.");
					} else {
						testPlan.getTestCases().moveToFront(idxTestCase);
					}
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnMoveToBack) {
				try {
					int idxTestCase = pnlTestPlans.tableTestCases.getSelectedRow();
					AbstractTestPlan testPlan = manager.getCurrentTestPlan();
					if (testPlan instanceof FailingTestList) {
						throw new IllegalArgumentException("The Failing Tests list may not be modified directly.");
					} else {
						testPlan.getTestCases().moveToBack(idxTestCase);
					}
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			}
			updateLists();
			SystemTestPlanGUI.this.repaint();
			SystemTestPlanGUI.this.validate();
		}
		
		public void updateLists() {
			if (manager == null) {
				btnAddTestPlan.setEnabled(false);
				btnEditTestPlan.setEnabled(false);
				btnRemoveTestPlan.setEnabled(false);

			} else {
				AbstractTestPlan currentTestPlan = manager.getCurrentTestPlan();
				
				String testPlanName = currentTestPlan.getTestPlanName();
				btnAddTestPlan.setEnabled(true);
				btnEditTestPlan.setEnabled(true);
				btnRemoveTestPlan.setEnabled(true);
				
				comboTestPlans.removeAllItems();
				String [] testPlanNames = manager.getTestPlanNames();
				for (int i = 0; i < testPlanNames.length; i++) {
					comboTestPlans.addItem(testPlanNames[i]);
				}
				
				comboTestPlans.setSelectedItem(testPlanName);
				
				if (comboTestPlans.getSelectedIndex() == 0) { //failing tests
					pnlTestCase.enableButtons(false);
					btnMoveUp.setEnabled(false);
					btnMoveDown.setEnabled(false);
					btnMoveToFront.setEnabled(false);
					btnMoveToBack.setEnabled(false);
				} else {
					pnlTestCase.setTestCase(tableTestCases.getSelectedRow());
					btnMoveUp.setEnabled(true);
					btnMoveDown.setEnabled(true);
					btnMoveToFront.setEnabled(true);
					btnMoveToBack.setEnabled(true);
				}
				
				lblFailingTests.setText(FAILING_TESTS_LABEL + currentTestPlan.getNumberOfFailingTests());
			}
			
			itemSave.setEnabled(manager != null && manager.isChanged());
			tableModel.updateData();
		}
		
		/**
		 * TestCaseTableModel is the object underlying the JTable object that displays
		 * the list of TestCases to the user.
		 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
		 */
		private class TestCaseTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Test ID", "Test Type", "Status"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the TestCaseTableModel by requesting the latest information
			 * from the Manager.
			 */
			public TestCaseTableModel() {
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col the column index
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row the row index
			 * @param col the column index
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row the row index
			 * @param col the column index
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with test cases information from the
			 * current test plan.
			 */
			private void updateData() {
				if (manager != null) {
					AbstractTestPlan currentTestPlan = manager.getCurrentTestPlan();
					if (currentTestPlan instanceof FailingTestList) {
						columnNames[2] = "Test Plan";
					} else {
						columnNames[2] = "Status";
					}
					data = currentTestPlan.getTestCasesAsArray();
				}
			}
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for interacting with a TestCase.
	 * @author Dr. Sarah Heckman
	 */
	private class TestCasePanel extends JPanel implements ActionListener {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label - test case id */
		private JLabel lblTestCaseId;
		/** TextField - test case it */
		private JTextField txtTestCaseId;
		
		/** Label - test type */
		private JLabel lblTestType;
		/** TextField - test type */
		private JTextField txtTestType;
		
		/** Label - description */
		private JLabel lblDescription;
		/** Text Area - description */
		private JTextArea txtDescription;
		
		/** Label - expected results */
		private JLabel lblExpectedResults;
		/** Text Area - expected results */
		private JTextArea txtExpectedResults;
		
		/** Label - actual results */
		private JLabel lblActualResults;
		/** Text Area - actual results */
		private JTextArea txtActualResults;
		
		/** Label - record actual results */
		private JLabel lblRecordActualResults;
		/** Text Area - record actual results */
		private JTextArea txtRecordActualResults;
		
		/** Label - passing/failing */
		private JLabel lblPassing;
		/** Combo box - passing/failing */
		private JComboBox<String> comboPassing;
		
		/** Button - add/edit */
		private JButton btnAdd;
		/** Button - remove */
		private JButton btnRemove;
		/** Button - record result */
		private JButton btnRecordResult;
		/** Button - clear */
		private JButton btnClear;
		
		
		
		public TestCasePanel() {
			super(new GridBagLayout());
			
			lblTestCaseId = new JLabel("Test Case ID");
			txtTestCaseId = new JTextField(25);
			
			lblTestType = new JLabel("Test Type");
			txtTestType = new JTextField(25);
					
			lblDescription = new JLabel("Description");
			txtDescription = new JTextArea(10, 50);
			
			lblExpectedResults = new JLabel("Expected Results");
			txtExpectedResults = new JTextArea(10, 50);
			
			lblActualResults = new JLabel("Actual Results Log");
			txtActualResults = new JTextArea(10, 50);
			txtActualResults.setEditable(false);
			
			btnAdd = new JButton("Add");
			btnRemove = new JButton("Remove");
			btnClear = new JButton("Clear Information");
			
			btnAdd.addActionListener(this);
			btnRemove.addActionListener(this);
			btnClear.addActionListener(this);
			
			lblRecordActualResults = new JLabel("Record Actual Results");
			txtRecordActualResults = new JTextArea(5, 25);
			
			
			lblPassing = new JLabel("Pass or Fail?");
			comboPassing = new JComboBox<String>();
			comboPassing.addItem(TestResult.PASS);
			comboPassing.addItem(TestResult.FAIL);
			
			btnRecordResult = new JButton("Record Result");
			btnRecordResult.addActionListener(this);
						
			enableButtons(false);
			
			JScrollPane scrollDescription = new JScrollPane(txtDescription);
			scrollDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JScrollPane scrollExpectedResults = new JScrollPane(txtExpectedResults);
			scrollExpectedResults.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollExpectedResults.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JScrollPane scrollActualResults = new JScrollPane(txtActualResults);
			scrollActualResults.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollActualResults.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JScrollPane scrollRecordActualResults = new JScrollPane(txtRecordActualResults);
			scrollRecordActualResults.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollRecordActualResults.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Row 1 - id
			JPanel row1 = new JPanel();
			row1.setLayout(new GridLayout(1, 2));
			row1.add(lblTestCaseId);
			row1.add(txtTestCaseId);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row1, c);
			
			//Row 2 - type
			JPanel row2 = new JPanel();
			row2.setLayout(new GridLayout(1, 2));
			row2.add(lblTestType);
			row2.add(txtTestType);
						
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row2, c);
			
			//Row 3 - labels
			JPanel row3 = new JPanel();
			row3.setLayout(new GridLayout(1, 2));
			row3.add(lblDescription);
			row3.add(lblExpectedResults);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row3, c);
			
			//Row 4 - text fields
			JPanel row4 = new JPanel();
			row4.setLayout(new GridLayout(1, 2));
			row4.add(scrollDescription);
			row4.add(scrollExpectedResults);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row4, c);
			
			//Row 5 - actual results
			JPanel actualResults = new JPanel();
			actualResults.setLayout(new GridLayout(2, 1));
			actualResults.add(lblActualResults);
			actualResults.add(scrollActualResults);
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(actualResults, c);
			
			//Row 6 - add/remove/clear
			JPanel row6 = new JPanel();
			row6.setLayout(new GridLayout(1, 3));
			row6.add(btnAdd);
			row6.add(btnRemove);
			row6.add(btnClear);
			
			c.gridx = 0;
			c.gridy = 8;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row6, c);
			
			//Row 7 - record actual results
			JPanel row7 = new JPanel();
			row7.setLayout(new GridLayout(1, 2));
			row7.add(lblRecordActualResults);
			row7.add(scrollRecordActualResults);
			
			c.gridx = 0;
			c.gridy = 9;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row7, c);
			
			//Row 8 - pass/fail + button
			JPanel row8 = new JPanel();
			row8.setLayout(new GridLayout(1, 3));
			row8.add(lblPassing);
			row8.add(comboPassing);
			row8.add(btnRecordResult);
			
			c.gridx = 0;
			c.gridy = 11;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row8, c);
			
			
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int idx = pnlTestPlans.tableTestCases.getSelectedRow();
			
			if (e.getSource() == btnAdd) {
				try {
					if (idx == -1) {
						TestCase t = new TestCase(txtTestCaseId.getText(), txtTestType.getText(), txtDescription.getText(), txtExpectedResults.getText());
						manager.addTestCase(t);
					} 
					setTestCase(-1);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRemove) {
				try {
					manager.getCurrentTestPlan().removeTestCase(idx);
					setTestCase(-1);
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRecordResult) {
				try {
					TestCase t = manager.getCurrentTestPlan().getTestCase(idx);
					
					String passing = (String) comboPassing.getSelectedItem();
					if (TestResult.PASS.equals(passing)) {
						t.addTestResult(true, txtRecordActualResults.getText());
					} else if (TestResult.FAIL.equals(passing)) {
						t.addTestResult(false, txtRecordActualResults.getText());
					} else {
						JOptionPane.showMessageDialog(SystemTestPlanGUI.this, "Invalid information.");
					}
					
					setTestCase(manager.getCurrentTestPlan() instanceof FailingTestList ? -1 : idx);
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnClear) {
				try {
					setTestCase(-1);
				} catch (IllegalArgumentException | IndexOutOfBoundsException iae) {
					JOptionPane.showMessageDialog(SystemTestPlanGUI.this, iae.getMessage());
				}
			
			} 
			itemSave.setEnabled(manager != null && manager.isChanged());
			pnlTestPlans.updateLists();
		}
		
		/**
		 * Sets the information for the selected test case and enables the buttons.
		 * @param idx index of selected test case
		 */
		public void setTestCase(int idx) {
			try {
				TestCase t = manager.getCurrentTestPlan().getTestCase(idx);
				
				txtTestCaseId.setText(t.getTestCaseId());
				txtTestType.setText(t.getTestType());
				txtDescription.setText(t.getTestDescription());
				txtExpectedResults.setText(t.getExpectedResults());
				txtActualResults.setText(t.getActualResultsLog());
				
				editableTextFields(false);
				
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(!(manager.getCurrentTestPlan() instanceof FailingTestList));
				btnRecordResult.setEnabled(true);
				btnClear.setEnabled(true);
				
			} catch (IndexOutOfBoundsException e) {
				txtTestCaseId.setText("");
				txtTestType.setText("");
				txtDescription.setText("");
				txtExpectedResults.setText("");
				txtActualResults.setText("");
				txtRecordActualResults.setText("");
				
				editableTextFields(true);
				
				pnlTestPlans.tableTestCases.getSelectionModel().clearSelection();
				
				btnAdd.setEnabled(true);
				btnRemove.setEnabled(false);
				btnRecordResult.setEnabled(false);
				btnClear.setEnabled(false);
			}
		}
		
		/**
		 * Enable or disable all buttons 
		 * @param enable true if enable, false if disable
		 */
		public void enableButtons(boolean enable) {
			btnAdd.setEnabled(enable); 
			btnRemove.setEnabled(enable);
			btnRecordResult.setEnabled(enable);
			btnClear.setEnabled(enable);

		}
		
		/**
		 * Enable or disable text fields
		 * @param editable true if editable, false if not editable
		 */
		public void editableTextFields(boolean editable) {
			txtTestCaseId.setEditable(editable);
			txtTestType.setEditable(editable);
			txtDescription.setEditable(editable);
			txtExpectedResults.setEditable(editable);
		}
		
		
	}

}