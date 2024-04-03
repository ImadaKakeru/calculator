package calcrater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class OtherBtnHandle implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e ) {
		JButton selectedBtn = (JButton) e.getSource();
		//updateOutput();
		if (selectedBtn == Calc.btnExchange) {
			Calc.equal = 0;
			Calc.aa = 0;
			Calc.exchange();
		} 
		else if (selectedBtn == Calc.btnClear) {
			Calc.equal = 0;
			Calc.aa = 0;
			Calc.clear();
		} 
		else if (selectedBtn == Calc.btnEquals){
			//equal = 1;
			Calc.aa = 1;
			if(Calc.prepre.isEmpty())
			{
				Calc.calculate();	
				Calc.updateOutput();
				return;
			}
			else {
				Calc.doublecalc(); 
				Calc.updateOutput();
				return;
			}
		}
		else if (selectedBtn == Calc.btnper){
			Calc.aa = 0;
			if(Calc.current ==""){
				if(Calc.previous == ""){
					Calc.equal = 0;
					Calc.output.setText("0");
					Calc.operator = null;
					Calc.opeOperator = null;
					return;
				}
				else{
					Calc.equal = 0;
					Calc.current = Calc.previous;
					Calc.previous = "";
					Calc.per();
				}
				
			}
			else{
				Calc.equal = 0;
				Calc.per();
			}
		}
		else if(selectedBtn == Calc.btnroot){
			Calc.aa = 0;
			if(Calc.current ==""){
				if(Calc.previous == ""){
					Calc.equal = 0;
					Calc.output.setText("0");
					Calc.operator = null;
					Calc.opeOperator = null;
					return;
				}
				else{
					Calc.equal = 0;
					Calc.current = Calc.previous;
					Calc.previous = "";
					Calc.root();
				}	
			}
			else{
				Calc.equal = 0;
				Calc.root();
			}
		}
		Calc.updateOutput();
	}
}
