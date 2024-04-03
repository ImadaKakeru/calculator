package calcrater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NumBtnHandle implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) { 
    	//e.getSource()でボタンクリックされた時の内容を読みとれる。それをselectedBtnに代入する。
    	JButton selectedBtn = (JButton) e.getSource();
    	Calc.equal = 0;
    	if(Calc.aa == 1){
    		System.out.println(Calc.aa);
    		Calc.current = "";
    	}
    	Calc.aa = 0;
    	//数字の入力がきたらそれを表示する。
    	for (JButton btn : Calc.numBtn) {
    		if (selectedBtn == btn) {
    			Calc.appendToOutput(btn.getText());
    			Calc.updateOutput();
    		}
    	}
    }
}
