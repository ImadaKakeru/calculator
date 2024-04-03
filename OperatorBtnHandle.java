package calcrater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class OperatorBtnHandle implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedBtn = (JButton) e.getSource();
        Calc.equal = 0;
        Calc.aa = 0;
        if (selectedBtn == Calc.btnMultiply) {
            Calc.selectOperator(Calc.btnMultiply.getText());
        } 
        else if (selectedBtn == Calc.btnAdd) {
            Calc.selectOperator(Calc.btnAdd.getText());
        } 
        else if (selectedBtn == Calc.btnSubtract) {
            Calc.selectOperator(Calc.btnSubtract.getText());
        } 
        else if (selectedBtn == Calc.btnDivide) {
            Calc.selectOperator(Calc.btnDivide.getText());
        }
        Calc.updateOutput();
        if(Calc.cc == 1){
        	 Calc.output.setText(Calc.previous);
        	return;
        }
        else{
            Calc.current = "";
        }
    }
}
