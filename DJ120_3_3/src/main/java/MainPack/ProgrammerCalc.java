package MainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ProgrammerCalc {
    JFrame frame;
    JTextField displayField;
    JButton [] buttonsNum;
    JButton buttonReset;
    JButton buttonEqual;
    JButton buttonRev;
    JButton buttonPlus, buttonMinus,buttonDiv, buttonMult;
    JButton buttonHex,buttonDec,buttonBin,buttonOct;
    JButton buttonXor,buttonAnd,buttonOr,buttonNot;
    JButton buttonShr,buttonShl;





    public ProgrammerCalc(){
        frame= new JFrame();
    }
    PCcalcController buttonCntr=new PCcalcController(this);
    public void init() {
        frame.setTitle("Programmer Calculator");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        displayField = new JFormattedTextField(0);
        displayField.setFont(new Font("Times New Romans",Font.PLAIN,20));
        displayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                e.consume();

            }
        });
        JPanel centerPanel =new JPanel();
        GridLayout centerGrid=new GridLayout(1,2,0,0);
        centerPanel.setLayout(centerGrid);
        centerPanel.add(getButtonNumGroup());
        centerPanel.add(getButtonsActGroup());
        frame.add(displayField,BorderLayout.NORTH);
        buttonEqual=new JButton("=");
        buttonEqual.setFont(new Font("",Font.BOLD,15));
        buttonEqual.setMargin(new Insets(10,0,10,0));
        buttonEqual.addActionListener(buttonCntr);
        frame.add(buttonEqual,BorderLayout.SOUTH);


        frame.add(centerPanel,BorderLayout.CENTER);
        buttonCntr.disableBtn(10);
        buttonDec.setBackground(Color.LIGHT_GRAY);
        buttonCntr.switchNum="dec";
        displayField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
    private Container getButtonNumGroup(){
        JPanel panel=new JPanel();
        GridLayout bNum=new GridLayout(4,4,0,0);
        panel.setLayout(bNum);
        buttonsNum=new JButton[16];

        for (int c=0; c<buttonsNum.length;c++) {

            if(c<10){
            buttonsNum[c]= new JButton(String.valueOf(c));
            buttonsNum[c].addActionListener(buttonCntr);
                buttonsNum[c].setSize(70,40);
            }else
            {
                buttonsNum[c]=new JButton(String.valueOf((char)('A'+c-10)));
                buttonsNum[c].addActionListener(buttonCntr);
                buttonsNum[c].setSize(60,40);
            }

        }
        for (int c=0;c<buttonsNum.length;c++){
            if(c<4){
                panel.add(buttonsNum[12+c]);
            }else if(c<8){
                panel.add(buttonsNum[8+c-4]);
            }else if (c<12){
                panel.add(buttonsNum[4+c-8]);
            }else{
                panel.add(buttonsNum[c-12]);
            }
        }
        return panel;
    }
    private Container getButtonsActGroup(){
        JPanel panel=new JPanel();
        GridLayout bAct=new GridLayout(4,4,0,0);
        panel.setLayout(bAct);
        buttonShr=new JButton("shr");
        panel.add(buttonShr);
        buttonShr.addActionListener(buttonCntr);
        buttonXor=new JButton("xor");
        panel.add(buttonXor);
        buttonXor.addActionListener(buttonCntr);
        buttonDiv=new JButton("/");
        panel.add(buttonDiv);
        buttonDiv.addActionListener(buttonCntr);
        buttonHex=new JButton("hex");
        panel.add(buttonHex);
        buttonHex.addActionListener(buttonCntr);
        buttonShl=new JButton("shl");
        panel.add(buttonShl);
        buttonShl.addActionListener(buttonCntr);
        buttonAnd=new JButton("and");
        panel.add(buttonAnd);
        buttonAnd.addActionListener(buttonCntr);
        buttonMult=new JButton("*");
        panel.add(buttonMult);
        buttonMult.addActionListener(buttonCntr);
        buttonDec=new JButton("dec");
        panel.add(buttonDec);
        buttonDec.addActionListener(buttonCntr);
        buttonReset=new JButton("Cls");
        panel.add(buttonReset);
        buttonReset.addActionListener(buttonCntr);
        buttonOr=new JButton("or");
        panel.add(buttonOr);
        buttonOr.addActionListener(buttonCntr);
        buttonMinus=new JButton("-");
        panel.add(buttonMinus);
        buttonMinus.addActionListener(buttonCntr);
        buttonOct=new JButton("oct");
        panel.add(buttonOct);
        buttonOct.addActionListener(buttonCntr);
        buttonRev=new JButton("+/-");
        panel.add(buttonRev);
        buttonRev.addActionListener(buttonCntr);
        buttonNot=new JButton("not");
        panel.add(buttonNot);
        buttonNot.addActionListener(buttonCntr);
        buttonPlus=new JButton("+");
        panel.add(buttonPlus);
        buttonPlus.addActionListener(buttonCntr);
        buttonBin=new JButton("bin");
        panel.add(buttonBin);
        buttonBin.addActionListener(buttonCntr);
        return panel;
    }


}
