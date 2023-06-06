import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JButton check;
	 JCheckBox checkBox;

	public SudokuFrame() {
		super("Sudoku Solver");
		setLayout(new BorderLayout(4,4));

		addComponents();
		addActionListeners();
		// Could do this:
		setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	 private void addActionListeners() {
		 inputArea.getDocument().addDocumentListener(new DocumentListener() {
			 @Override
			 public void insertUpdate(DocumentEvent e) {
				 if (checkBox.isSelected()) solve();
			 }

			 @Override
			 public void removeUpdate(DocumentEvent e) {
				 if (checkBox.isSelected()) solve();
			 }

			 @Override
			 public void changedUpdate(DocumentEvent e) {
				 if (checkBox.isSelected()) solve();
			 }
		 });
		 check.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 solve();
			 }
		 });
	 }

	 private void solve() {
		 Sudoku sudoku;
		 try{
			 sudoku = new Sudoku(inputArea.getText());
			 System.out.println(sudoku); // print the raw problem
			 int count = sudoku.solve();
			 System.out.println("solutions:" + count);
			 System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
			 System.out.println(sudoku.getSolutionText());

			 if(count > 0){
				 outputArea.setText(sudoku.getSolutionText() + "solutions:" + count + "\n" +
						 "elapsed:" + sudoku.getElapsed() + "ms");
			 }
		 }catch (Exception ex){
			 outputArea.setText("Parsing Problem");
		 }


	 }

	 private void addComponents() {
		 inputArea = new JTextArea(15,20);
		 outputArea = new JTextArea(15,20);
		 inputArea.setBorder(new TitledBorder("Puzzle"));
		 outputArea.setBorder(new TitledBorder("Solution"));
		 inputArea.setEditable(true);
		 outputArea.setEditable(false);

		 check = new JButton("Check");
		 checkBox = new JCheckBox("Auto check");
		 checkBox.setSelected(true);

		 JPanel checksPanel = new JPanel();
		 checksPanel.setLayout( new BoxLayout(checksPanel, BoxLayout.X_AXIS));
		 checksPanel.add(check);
		 checksPanel.add(checkBox);

		 add(inputArea,BorderLayout.WEST);
		 add(outputArea,BorderLayout.EAST);
		 add(checksPanel,BorderLayout.SOUTH);
	 }


	 public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
