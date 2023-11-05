package MainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PCcalcController implements ActionListener{
    ProgrammerCalc parent;
    public String switchNum;
    Color defCol;
    String curCommand="", curValue="",resValue="",curValue2="";
    int curPlMin;
    boolean switchSign=true;
    StringBuilder sb=new StringBuilder();
    public PCcalcController(ProgrammerCalc parent){
        this.parent=parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        defCol=parent.buttonEqual.getBackground();
        // READING NUMS********************************************
        if(isNumSymbol(command)) {//считывание числа
            switchSign=true;
            sb.append(command);
           // System.out.println("digit "+sb);
            parent.displayField.setText(sb.toString());
        }
        // CLEAR ALL *************************************************
        if(command.equals("Cls")){
            sb.delete(0,sb.length());sb.append("");
            curValue=""; resValue="";
            parent.displayField.setText("0");
        }
        // +- COMMAND *************************************************
        if (e.getActionCommand().equals("+/-")) {
            if (switchSign){
                curPlMin = Integer.parseInt(getDec(parent.displayField.getText()));
                switchSign=false;
            }
            curPlMin=-curPlMin;
               if (switchNum.equals("bin")) {
                   sb.delete(0, sb.length());
                   sb.append(Integer.toBinaryString(curPlMin));
               } else if (switchNum.equals("hex")) {
                   sb.delete(0, sb.length());
                   sb.append(Integer.toHexString(curPlMin));
               } else if (switchNum.equals("oct")) {
                   sb.delete(0, sb.length());
                   sb.append(Integer.toOctalString(curPlMin));
               } else {
                   sb.delete(0, sb.length());
                   sb.append(curPlMin);
               }
               parent.displayField.setText(sb.toString());

        }
        // NOT COMMAND *************************************************
        if (e.getActionCommand().equals("not")) {
            if (switchSign){
                curPlMin = Integer.parseInt(getDec(parent.displayField.getText()));
                switchSign=false;
            }
            curPlMin=~curPlMin;
            if (switchNum.equals("bin")){
                sb.delete(0,sb.length());
                sb.append(Integer.toBinaryString( curPlMin ));
            }
            else if(switchNum.equals("hex")){
                sb.delete(0,sb.length());
                sb.append(Integer.toHexString( curPlMin ));
            }
            else if(switchNum.equals("oct")){
                sb.delete(0,sb.length());
                sb.append(Integer.toOctalString( curPlMin ));
            }
            else{
                sb.delete(0,sb.length());
                sb.append( curPlMin );
            }
            parent.displayField.setText(sb.toString());
        }
        // SWITCHING NUMS*****************************************

                      // TO BIN=====================================
        if (e.getActionCommand().equals("bin")){
            System.out.println("resV "+resValue+"   "+switchNum);
            restoreBtn();
            disableBtn(2);

            parent.displayField.setText(getBinary(parent.displayField.getText()));

            pressingSwitchNumBtn(e.getActionCommand(),parent.buttonBin);
        }
                                // TO DEC =========================================
        if (e.getActionCommand().equals("dec")){
            restoreBtn();
            disableBtn(10);

            parent.displayField.setText(getDec(parent.displayField.getText()));
            pressingSwitchNumBtn(e.getActionCommand(), parent.buttonDec);
        }
                             // TO OCT ===============================================
        if (e.getActionCommand().equals("oct")){
            restoreBtn();
            disableBtn(8);

            parent.displayField.setText(getOct(parent.displayField.getText()));
            pressingSwitchNumBtn(e.getActionCommand(),parent.buttonOct);
        }
        // TO HEX ==================================================
        if(e.getActionCommand().equals("hex")){
            restoreBtn();
            disableBtn(16);
            System.out.println("OK before txtfield");
            parent.displayField.setText(getHex(parent.displayField.getText()).toUpperCase());
            pressingSwitchNumBtn(e.getActionCommand(),parent.buttonHex);
        }

        // CALCULATION*****************************************************
        if(isMathSign(command)){

            if(sb.toString().equals("")&&curValue.equals("")&&resValue.equals("")){
                // ничего не введено

                return;
            }
            else if (!curValue.equals("")&&!curCommand.equals("")){// две переменных и мат. действие


                if(!switchNum.equals("dec")){

                    curValue2=getDec(sb.toString());
                }else{
                    curValue2= sb.toString();
                }

                  calcExec(command);


            }
            else { //sb 1-я переменная и мат. действие
                System.out.println("sb1+curCommand");
                curCommand=command; //запоминаем мат. действие
                if (!resValue.equals("")&&sb.toString().equals("")){ //использование рез-та после знака =
                    curValue=resValue;
                    System.out.println("resValue"+curValue);
                    resValue="";
                }else {
                    System.out.println("curValue" + curValue);
                    curValue = getDec(sb.toString());// запоминаем значение первой переменной
                    sb.delete(0, sb.length());
                }
            }
        }
        //====================== "=" ====================
        if(command.equals("=")) {

            System.out.println("curValue " + curValue + " sb " + sb.toString() + " command " + command+"curValue2 "+curValue2);
            if (sb.toString().equals("") && curValue.equals("")) {// ничего не введено
                //System.out.println("=" + command);
                return;
            } else if (!sb.toString().equals("") && curCommand.equals("")) {//введено только первое число
                resValue = getDec(sb.toString());
                sb.delete(0, sb.length());
            } else if (!curCommand.equals("") && sb.toString().equals("")&&curValue2.equals("")) {
                // команда = нажата сразу после арифметической команды
                return;
            } else {

                //System.out.println("curValue " + curValue + " sb " + sb.toString() + " command " + command);

                {if(!sb.toString().equals("")) {// приведение второго значения к десятичному виду
                    curValue2=getDec(sb.toString());
                }
                    //System.out.println("curValue " + curValue + " sb " + curValue2 + " command " + command);
                    calcExec(command);
                }
            }
        }
    }
    private void restoreBtn(){// восстановление цвета по умолчанию на всех кнопках
        for (int c=0; c<parent.buttonsNum.length;c++) {
            parent.buttonsNum[c].setEnabled(true);
            parent.buttonBin.setBackground(defCol);
            parent.buttonDec.setBackground(defCol);
            parent.buttonOct.setBackground(defCol);
            parent.buttonHex.setBackground(defCol);
        }

    };
    public void disableBtn(int c){// управление активацией числовых кнопок
                                  // в зависимости от системы счисления
        for (;c<parent.buttonsNum.length;c++)
            parent.buttonsNum[c].setEnabled(false);

    }
    private boolean isNumSymbol(String strToCheck){ // проверка символов чисел
        if(strToCheck.length()==1&&((strToCheck.charAt(0)>='0'&&strToCheck.charAt(0)<='9')
                ||(strToCheck.charAt(0)>='A'&&strToCheck.charAt(0)<='F')))
        return true;
        else return false;
    }
    private String MathCalc(String arg1,String arg2, String mathSign) { //математические операции
        String res = "no sign";

        switch (mathSign) {
            case ("+"): {
                res = String.valueOf(Integer.parseInt(arg1) + Integer.parseInt(arg2));
                break;
            }
            case ("-"): {
                res = String.valueOf(Integer.parseInt(arg1) - Integer.parseInt(arg2));
                break;
            }
            case ("*"): {
                res = String.valueOf(Integer.parseInt(arg1) * Integer.parseInt(arg2));
                break;
            }
            case ("/"): {
                res = String.valueOf(Integer.parseInt(arg1) / Integer.parseInt(arg2));
                break;
            }
            case ("and"): {
                res = String.valueOf(Integer.parseInt(arg1) & Integer.parseInt(arg2));
                break;
            }
            case ("or"): {
                res = String.valueOf(Integer.parseInt(arg1) | Integer.parseInt(arg2));
                break;
            }
            case ("xor"): {
                res = String.valueOf(Integer.parseInt(arg1) ^ Integer.parseInt(arg2));
                break;
            }
            case ("shr"): {
                res = String.valueOf(Integer.parseInt(arg1) >> Integer.parseInt(arg2));
                break;
            }
            case ("shl"): {
                res = String.valueOf(Integer.parseInt(arg1) << Integer.parseInt(arg2));
                break;
            }
        }
        return res;
    }
    private boolean isMathSign(String stringToCheck){ // проверка математических символов
        if (stringToCheck.equals("+")|| stringToCheck.equals("-")
                ||stringToCheck.equals("/")||stringToCheck.equals("*")
                ||stringToCheck.equals("and")||stringToCheck.equals("or")
                ||stringToCheck.equals("xor")||stringToCheck.equals("shr")
                ||stringToCheck.equals("shl")
        )
            return true;
        else return false;
    }
    private String getBinary(String strNum){ //получение двоичного числа
                                             // в виде строки
        if (!strNum.equals("")){
            int tempInt;
            if(switchNum.equals("dec")){
                tempInt=Integer.parseInt(strNum,10);
            } else if(switchNum.equals("hex")){
                tempInt=Integer.parseInt(strNum,16);
            } else if (switchNum.equals("oct"))  {
                tempInt=Integer.parseInt(strNum,8);
            } else {
                tempInt=Integer.parseInt(strNum,2);
            }
            return Integer.toBinaryString(tempInt);
        }else return "";
    }
    public String getDec(String strNum){ //получение десятичного числа
                                         // в виде строки
        if (!strNum.equals("")){
            int tempInt;
            if(switchNum.equals("bin")){
                tempInt=Integer.parseInt(strNum,2);
            } else if(switchNum.equals("hex")){
                tempInt=Integer.parseInt(strNum,16);
            } else if (switchNum.equals("oct")) {
                tempInt=Integer.parseInt(strNum,8);
            } else {
                tempInt=Integer.parseInt(strNum,10);
            }
            return String.valueOf(tempInt);
        }
       else return "";
    }
    private String getOct(String strNum){ //получение восьмеричного числа
                                          // в виде строки
        if (!strNum.equals("")){
            int tempInt;
            if(switchNum.equals("bin")){
                tempInt=Integer.parseInt(strNum,2);
            } else if(switchNum.equals("hex")){
                tempInt=Integer.parseInt(strNum,16);
            } else  if (switchNum.equals("oct")) {
                tempInt=Integer.parseInt(strNum,8);
            } else {
                tempInt=Integer.parseInt(strNum,10);
            }
            return Integer.toOctalString(tempInt);
        }
        else return "";
    }
    private String getHex(String strNum){ //получение шестнадцатиричного числа
                                          // в виде строки
        if (!strNum.equals("")){
            int tempInt;
            if(switchNum.equals("bin")){
                tempInt=Integer.parseInt(strNum,2);
            } else if(switchNum.equals("dec")){
                tempInt=Integer.parseInt(strNum,10);
            } else if(switchNum.equals("oct")) {
                tempInt=Integer.parseInt(strNum,8);
            }else {
                tempInt=Integer.parseInt(strNum,16);
            }
            return Integer.toHexString(tempInt);
        }
        else return "";
    }
    private void calcExec(String command){ //вычисление результата
        resValue=MathCalc(curValue,curValue2,curCommand);// результат в десятичной форме
        if(switchNum.equals("bin")){
            parent.displayField.setText(Integer.toBinaryString(Integer.parseInt(resValue)));
        } else if(switchNum.equals("hex")){
            parent.displayField.setText(Integer.toHexString(Integer.parseInt(resValue)).toUpperCase());
        }else if (switchNum.equals("oct")) {
            parent.displayField.setText(Integer.toOctalString(Integer.parseInt(resValue)));
        } else{
            parent.displayField.setText(resValue);
        }
        curValue="";curValue2="";
        sb.delete(0,sb.length());
        if(command.equals("=")){
            curCommand="";
        }else {
            curCommand=command;
        }
    }
    private void pressingSwitchNumBtn(String sw, JButton btn){ //переключение между
                                                               //системами счисления

        if(!sb.toString().equals("")&&curCommand.equals("")){
            System.out.println("num1 resV "+resValue+"   "+switchNum);
            resValue=getDec(sb.toString());
            sb.delete(0,sb.length());
        } else if (!sb.toString().equals("")&&!curCommand.equals("")) {
            System.out.println("num2 resV "+resValue+"   "+switchNum);
            curValue2=getDec(sb.toString());
            System.out.println("num2 resV "+resValue+"   "+switchNum+"curValue2"+curValue2);
            sb.delete(0,sb.length());
        }
        switchNum=sw;
        btn.setBackground(Color.LIGHT_GRAY);
    }

}
