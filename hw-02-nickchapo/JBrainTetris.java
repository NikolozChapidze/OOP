import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris {
    private final Brain brain;
    private JCheckBox brainMode;
    private boolean newPiece;
    private JCheckBox animate;
    private JSlider adversary;
    private JLabel status;
    @Override
    public void addNewPiece() {
        newPiece = true;
        super.addNewPiece();
    }

    @Override
    public JComponent createControlPanel() {
        JPanel panel = (JPanel) super.createControlPanel();
        panel.add(new JLabel("Brain:"));

        brainMode = new JCheckBox("Brain active");
        panel.add(brainMode);
        animate = new JCheckBox("Animate Falling");
        animate.setSelected(true);
        panel.add(animate);

        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);
        status = new JLabel("ok");
        little.add(status);
        panel.add(little);

        return panel;
    }

    @Override
    public Piece pickNextPiece() {
        if(super.random.nextInt(99) + 1 < adversary.getValue()){
            Piece result = null;
            status.setText("*ok*");
            double score = 0;
            for (int i = 0; i < super.pieces.length; i++) {
                Piece posPiece = super.pieces[i];
                Brain.Move posMove = brain.bestMove(super.board, posPiece,super.board.getHeight(), null);
                if(posMove != null && posMove.score > score){
                    score = posMove.score;
                    result = posPiece;
                }
            }
            return (result == null) ? super.pickNextPiece() : result;
        }else {
            status.setText("ok");
            return super.pickNextPiece();
        }
    }

    @Override
    public void tick(int verb) {
        if(verb == DOWN && brainMode.isSelected()){
            Brain.Move nextMove = null;
            if (newPiece){
                newPiece = false;
                board.undo();
                nextMove = brain.bestMove(super.board, super.currentPiece, super.board.getHeight(), nextMove);
            }
            if (nextMove != null){
                if (!nextMove.piece.equals(currentPiece)){
                    super.tick(ROTATE);
                }
                if(nextMove.x > super.currentX){
                    super.tick(RIGHT);
                }else if(nextMove.x < super.currentX){
                    super.tick(LEFT);
                }else if(!animate.isSelected() && nextMove.y < currentY){
                    super.tick(DROP);
                }
            }
        }
        super.tick(verb);
    }

    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(30);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}