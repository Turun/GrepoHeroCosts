import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUI implements ActionListener
{
    int heroCosts = 65;
    int costsFrom = 1;
    static final int lineNumber = 19;
    
    JTextField tfLineNumber;
    JTextField tfHeroCosts;
    JTextField tfCostsFrom;
    JTextArea ta;
    JCheckBox cb;
    
    Main m;
    
    public static void main(String[] args){GUI g = new GUI();}
    private GUI(){
        Main m = new Main(lineNumber+1);
        
        makeFrame();
        calc();
    }
    
    /**
     * creates the frame and does the layout things
     */
    private void makeFrame(){
        JFrame f = new JFrame("Grepolis Hero Cost Calculator");
        f.setVisible(false);
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.X_AXIS));
        f.getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        
        ta = new JTextArea();
        ta.setEditable(false);
        JScrollPane jsp = new JScrollPane(ta, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setPreferredSize(new Dimension(420,400));
        
        
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setMinimumSize  (new Dimension(150,150));
        main.setPreferredSize(new Dimension(150,150));
        main.setMaximumSize  (new Dimension(150,150));
        
        
        JPanel jpHeroCosts = new JPanel(new BorderLayout());
        tfHeroCosts = new JTextField(String.valueOf(heroCosts));
        tfHeroCosts.addActionListener(this);
        tfHeroCosts.setActionCommand("tfHeroCosts");
        
        jpHeroCosts.add(new JLabel("Herocosts:"), BorderLayout.NORTH);
        jpHeroCosts.add(tfHeroCosts, BorderLayout.CENTER);
        
        
        JPanel jpCostsFrom = new JPanel(new BorderLayout());
        tfCostsFrom = new JTextField(String.valueOf(costsFrom));
        tfCostsFrom.addActionListener(this);
        tfCostsFrom.setActionCommand("tfCostsFrom");
        
        jpCostsFrom.add(new JLabel("Sum of Costs from level:"), BorderLayout.NORTH);
        jpCostsFrom.add(tfCostsFrom, BorderLayout.CENTER);
        
        JPanel jcb = new JPanel(new FlowLayout());
        cb = new JCheckBox("Grepolis Table");
        cb.setSelected(false);
        jcb.add(cb);
        
        JPanel jcalc = new JPanel(new FlowLayout());
        JButton calc = new JButton("Calculate");
        calc.addActionListener(this);
        calc.setActionCommand("calc");
        jcalc.add(calc);
        
        
        main.add(jpHeroCosts);
        main.add(jpCostsFrom);
        main.add(jcb);
        main.add(Box.createRigidArea(new Dimension(5,5)));
        main.add(jcalc);
        main.add(Box.createVerticalGlue());
        
        
        JPanel mainContainer = new JPanel(new FlowLayout());
        mainContainer.add(main);
        
        
        
        f.add(jsp);
        f.add(Box.createRigidArea(new Dimension(5,5)));
        f.add(mainContainer);
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    /**
     * ensures the heroCosts and costsFrom variable are set right and then calculates the sppropiate sums and prints 
     * the text to the JTextArea, adjusting the format according to the grepolis table checkbox
     */
    private void calc(){
        getHeroCosts();
        getCostsFrom();
        
        if(m == null){
            m = new Main(lineNumber+1);
        }
        
        String[] lines = new String[lineNumber];
        for(int i = 0; i<lines.length; i++){            
            int[] val = m.data[i+1];
            int sum = 0;
            
            if(costsFrom == 0){
                sum+=heroCosts;
                if(costsFrom <= i){
                    sum += m.sum(costsFrom,i+1);
                }
            }else{
                if(costsFrom-1 <= i){
                    sum += m.sum(costsFrom-1,i+1);
                }
            }
            
            lines[i] = format(val[1],val[2],val[3],sum);
        }
        
        ta.setText("");
        if(cb.isSelected()){ta.append("[table]\n[*]From Level[|]With Points[|]Points needed from level "+String.valueOf(costsFrom)+"[|]To Level[/*]\n");
        }else{ta.append("From Level\tWith Points\tTo Level\tPoints needed from level "+String.valueOf(costsFrom)+"\n");}
        for(String s: lines){
            ta.append(s+"\n");
        }
        if(cb.isSelected()){ta.append("[/table]");}
    }
    
    /**
     * formats the three integers to the appropiate output format, depending on wheather the grepolis table checkbox is checked
     */
    private String format(int a, int b, int c, int d){
        String re = "";
        
        String A = String.valueOf(a);
        while(A.length() < 2){A = "_" + A;}
        
        String B = String.valueOf(b);
        while(B.length() < 3){B = "_" + B;}
        
        String C = String.valueOf(c);
        while(C.length() < 2){C = "_" + C;}
        
        String D = String.valueOf(d);
        if(d == 0){D = "_";}
        while(D.length() < 4){D = "_" + D;}
        
        if(!cb.isSelected()){
            re = A + "\t" + B + "\t" + C + "\t" + D;
        }else{
            re = "[*]"+A+"[|]"+B+"[|]"+D+"[|]"+C+"[/*]";
        }
        
        return re;
    }
    
    /**
     * sets the Hero costs which is needed for the calculation of the sums from level 0
     */
    private void getHeroCosts(){
        try{
            heroCosts = Integer.valueOf(tfHeroCosts.getText());
        }catch(NumberFormatException ex){
            tfHeroCosts.setText("Input a number please");
        }
    }
    
    /**
     * sets the value for the costs form variable that determines from where the sums should be calculated
     */
    private void getCostsFrom(){
        if(tfCostsFrom.getText().isEmpty()){
            costsFrom = 0;
            tfCostsFrom.setText("0");
            return;
        }
        try{
            costsFrom = Integer.valueOf(tfCostsFrom.getText());
            if(costsFrom > 19){
                tfCostsFrom.setText("Funny");
            }
        }catch(NumberFormatException ex){
            costsFrom = 0;
            tfCostsFrom.setText("0");
        }
    }
    
    /**
     * Listens to the performed actions and calls the appropiate method to set hero costs/ set costs from/ calculate the values
     */
    public void actionPerformed(ActionEvent e){
        String key = e.getActionCommand();
        switch(key){
            case("tfHeroCosts"):{
                getHeroCosts();
                break;
            }
            case("tfCostsFrom"):{
                getCostsFrom();
                break;
            }
            case("calc"):{
                calc();
                break;
            }
            case(""):{
                break;
            }
            default:{}
        }
    }
}
