package calcrater;

import javax.swing.*;

//import static java.math.BigDecimal.ROUND_HALF_UP;

import java.awt.*;
//import java.awt.event.*;
import java.math.*;
import java.text.*;
public class Calc extends JFrame {
	
	private static final long serialVersionUID = 1L;
	static JButton btnExchange, btnAdd, btnSubtract, btnDivide, btnMultiply, btnClear, btnEquals, btnDot, btnper, btnroot;
	static JButton numBtn[];
	static JTextField output;
	static String previous,current,operator,precurrent,preope,prepre,preOperator,curcur;
	static String ERROR = "ERROR";
	static int equal =0;
	static int cc = 0;
	static int aa = 0;
	public static Object opeOperator;
	
	static void selectOperator(String newOperator) {
		if(current.isEmpty() && !previous.isEmpty()){
			cc = 1;
			operator = newOperator;
			return;
		}
		//数字の入力がない時に数字以外のボタンが押された時はoperatorに演算子を代入する。
		if (current.isEmpty()) {
			cc = 0;
			operator = newOperator;
			return;
		}
		
		if(!prepre.isEmpty() && !previous.isEmpty() && !current.isEmpty()){
			doublecalc();
			updateOutput();
			operator = newOperator;
			previous = current;
			return;
		}
        //１つ前の数字入力が空でなかったら、計算を行う。（ただし、ここでの計算はあくまでnewOperatorではなくprevious[operator]currentの計算でnewOperatorは使われない。）
		if (!previous.isEmpty()) {
			cc = 0;
			//現在保持しているOperatorが掛け算もしくは割り算の時はそのまま計算する。
			if(operator.equals("*") || operator.equals("÷")){
				calculate();
				updateOutput();
				operator = newOperator;
				previous = current;
			}
			//現在保持しているOperatorが足し算もしくは引き算の時
			else{
				//新しく入ってくる演算子（NewOperator）が足し算か引き算ならそのまま計算する。
				if(newOperator.equals("+") || newOperator.equals("-")){
					calculate();
					updateOutput();
					operator = newOperator;
					previous = current;
				}
				//新しく入ってくる演算子が掛け算、割り算の場合、Preoperatorに今保持しているOperatorを入れて、Operatorに新しい演算子を入れる。
				else{
					preOperator = operator;
					operator = newOperator;
					prepre = previous;
					previous = current;
				}
			}
		}
		
        //計算を終えたらpreviousに、operatorを入ってきたnewOperatorに入れる。
		operator = newOperator;
		previous = current;
	}
	//電卓でボタンを押した時、それを１つずつ表示する。
	static void appendToOutput(String num){
		//この分岐により、現在の表示内容にドットが１つでも含まれていたら跳ね返すようにしている。
		if (num.equals(".") && current.contains(".")){
			return;
		}
		if(num.equals(".") && current.length()== 0){
			current +=0;
			current += num;
			return;
		}
		else if(num.equals(".") && current.equals("0"))
		{
			current += num;
			return;
		}
		if(current.equals("0")){
			current = num;
			return;
		}
		current += num;
	}

	static void processoutputNumber() {
		if(current.contains("E")){	
			BigDecimal a = new BigDecimal(current);
			a = a.setScale(9,RoundingMode.HALF_UP);
			BigDecimal zero = new BigDecimal("0");
			zero = zero.setScale(9,RoundingMode.HALF_UP);
			BigDecimal dt = new BigDecimal(Math.pow(10,-7));
			BigDecimal one = new BigDecimal(1);
			if(a.compareTo(dt) > 0 && a.compareTo(one) < 0){
				DecimalFormat b = new DecimalFormat("0.########");
				current = b.format(a);	
			}
			else if(a.compareTo(zero) < 0){
				DecimalFormat b = new DecimalFormat("0.########");
				current = b.format(a);	
			}
			else if(a.compareTo(zero) == 0) {
				current = "0";
			}
			else{
				DecimalFormat b = new DecimalFormat("#.########E0");
				current = b.format(a);	
			}
		}
		
		if(!current.contains(".")){
			String integerPart = current.split("\\.")[0];
			current = integerPart;
			if(current.length() > 9){
				BigDecimal a = new BigDecimal(current);
				DecimalFormat b = new DecimalFormat("#.########E0");
				current = b.format(a);
				return;
			}
			else{
				return;
			}
		}
		
		if (current.length() > 0) {
			//currentにはいる数字を小数部分と整数部分にわけ、小数部分をdecimal、整数部分をintegerに入れている。decimalが０だったら小数部分はいらないのでintegerのみを残す。
			String integerPart = current.split("\\.")[0];
			String decimalPart = current.split("\\.")[1];
			if (Double.parseDouble(decimalPart) == 0){
				current = integerPart;
			}
			else{
				int i;
				if(decimalPart.contains("E")){
					String decimaldecimal = decimalPart.split("E")[0];
					String e = decimalPart.split("E")[1];
					int len = decimaldecimal.length();
					for( i = len-1 ; i >= 0 ; i--){
						if(decimaldecimal.charAt(i) != '0'){
							i++;
							break;
						}
					}
					decimaldecimal = decimaldecimal.substring(0,i);
					decimalPart = decimaldecimal + "E" +e;
					current = integerPart +"."+ decimalPart;
				}
				else{
					int len = decimalPart.length();
					for( i = len-1 ; i >= 0 ; i--){
						if(decimalPart.charAt(i) != '0'){
							i++;
							break;
						}
					}
					decimalPart = decimalPart.substring(0,i);
					current = integerPart +"."+ decimalPart;
				}
			}

			if(integerPart.length() > 9){
				BigDecimal a = new BigDecimal(integerPart);
				DecimalFormat b = new DecimalFormat("0.########E0");
				current = b.format(a);
				return;
			}
			if(current.contains("E")){
				return;
			}
			if(current.length() > 9){
				BigDecimal a = new BigDecimal(current);
				int lenint = integerPart.length();
				int lendec = decimalPart.length();
				if(lendec - lenint > 0){
					a = a.setScale(lendec-lenint,RoundingMode.HALF_UP);
					current = a.toString();
				}
				BigDecimal zero = new BigDecimal("0");
				zero = zero.setScale(9,RoundingMode.HALF_UP);
				if(a.compareTo(zero) == 0){
					current = "0";
				}
			}
		}
	}

	//実際の計算を行う
	static void calculate() {
		// [=]が連続できた時の処理
		if(equal == 1){
			BigDecimal result = new BigDecimal(0);
			BigDecimal num1 = new BigDecimal(current);
	        BigDecimal num2 = new BigDecimal(precurrent);
	        BigDecimal zero = new BigDecimal(0);
			switch (preope) {
            case "*":
                result = num1.multiply(num2);
                break;
                
            case "+":
            	result = num1.add(num2);
                break;
                
            case "-":
                result = num1.subtract(num2);
                break;
                
            case "÷":
                if(num2.equals(zero)){
            		current = ERROR;
            		operator = null;
            		previous = "";
            		equal = 0;
            		return;
            	}
                result = num1.divide(num2,9,RoundingMode.HALF_UP);
                break;
                
            default:
                break;
			}
			BigDecimal dt = new BigDecimal(Math.pow(10,-8));
			BigDecimal dd = new BigDecimal(Math.pow(10, 125));
			dd = dd.setScale(9,RoundingMode.HALF_UP);
			BigDecimal de = dd.negate();
	        if(result.compareTo(zero) >= 0 && result.compareTo(dt) < 0){
	    		current = String.valueOf(0);
	    		operator = null;
	    		previous = "";
	    		processoutputNumber();
	    		equal = 0;
	    		return;
	    	}
	        else if(result.compareTo(de) < 0){
	        	current = "ERROR";
	        	operator = null;
	        	previous = "";
	        }
	        else if(result.compareTo(dd) > 0){
	        	current = "ERROR";
	        	operator = null;
	        	previous = "";
	        	equal = 0;
	        	return;
	        }
	        else {
	        	current = result.toString();
		        processoutputNumber();
		        return;
	        }
		}
		else{
			//計算ができない状態の場合はそのままリターンする。
	        if (previous.length() < 1 || current.length() < 1) {
	        	equal = 0;
	        	return;	
	        }
	        BigDecimal result = new BigDecimal(0);
	        BigDecimal zero = new BigDecimal(0);
	        //previousとcurrentをdouble型に変換し、計算を行う。
	        BigDecimal num1 = new BigDecimal(previous);
	        BigDecimal num2 = new BigDecimal(current);
	        switch (operator) {
	            case "*":
	            	result = num1.multiply(num2);
	                break;
	                
	            case "+":
	            	result = num1.add(num2);
	                break;
	                
	            case "-":
	                result = num1.subtract(num2);
	                break;
	                
	            case "÷":
	            	if(num2.equals(zero)){
	            		current = ERROR;
	            		operator = null;
	            		previous = "";
	            		return;
	            	}
	            	result = num1.divide(num2,9,RoundingMode.HALF_UP);
	            	break;	
	            default:
	                break;
	        }
	        //計算結果をcurrentに入れ、オペレーターと前の値previousを初期化する。
	        BigDecimal dt = new BigDecimal(Math.pow(10,-8));
	        dt = dt.setScale(9,RoundingMode.HALF_UP);
	        BigDecimal dd = new BigDecimal(Math.pow(10, 125));
	        dd = dd.setScale(9,RoundingMode.HALF_UP);
	        BigDecimal de = dd.negate();
	        if(result.compareTo(zero) >= 0 && result.compareTo(dt) < 0){
	    		current = String.valueOf(0);
	    		operator = null;
	    		previous = "";
	    		processoutputNumber();
	    		return;
	    	}
	        else if(result.compareTo(dd) > 0){
	        	current = "ERROR";
	        	operator = null;
	        	previous = "";
	        	return;
	        }
	        else if(result.compareTo(de) < 0){
	        	current = "ERROR";
	        	operator = null;
	        	previous = "";
	        }
	        else{
	        	precurrent = current;
	        	preope = operator;
	            current = result.toString();
	            operator = null;
	            previous = "";
	            equal = 1;
	            processoutputNumber();
	        }	
		}
	}
	
	static void doublecalc(){
		if (previous.length() < 1 || current.length() < 1 || prepre.length() <1) {
        	equal = 0;
        	return;	
        }
        BigDecimal result = new BigDecimal(0);
        BigDecimal zero = new BigDecimal(0);
        //previousとcurrentをdouble型に変換し、計算を行う。
        BigDecimal num1 = new BigDecimal(prepre);
        BigDecimal num2 = new BigDecimal(previous);
        BigDecimal num3 = new BigDecimal(current);
        switch (operator) {
            case "*":
            	result = num2.multiply(num3);
                break;
            case "÷":
            	if(num3.equals(zero)){
            		current = ERROR;
            		operator = null;
            		previous = "";
            		prepre = "";
            		preOperator = "";
            		return;
            	}
            	result = num2.divide(num3,8,RoundingMode.HALF_UP);
            	break;	
            default:
                break;
        }
        switch (preOperator) {
        case "+":
        	result = num1.add(result);
            break;
            
        case "-":
            result = num1.subtract(result);
            break;	
        default:
            break;
        }
        //計算結果をcurrentに入れ、オペレーターと前の値previousを初期化する。
        BigDecimal dt = new BigDecimal(Math.pow(10,-8));
        dt = dt.setScale(9,RoundingMode.HALF_UP);
        BigDecimal dd = new BigDecimal(Math.pow(10, 125));
        dd = dd.setScale(9,RoundingMode.HALF_UP);
        BigDecimal de = dd.negate();
        if(result.compareTo(zero) >= 0 && result.compareTo(dt) < 0){
    		current = String.valueOf(0);
    		operator = null;
    		previous = "";
    		prepre = "";
    		preOperator = null;
    		processoutputNumber();
    		return;
    	}
        else if(result.compareTo(dd) > 0){
        	current = "ERROR";
        	operator = null;
        	previous = "";
        	prepre = "";
    		preOperator = null;
        	return;
        }
        else if(result.compareTo(de) < 0){
        	current = "ERROR";
        	operator = null;
        	previous = "";
        }
        else{
        	precurrent = current;
        	preope = operator;
            current = result.toString();
            preOperator = null;
            operator = null;
            previous = "";
            prepre = "";
            equal = 1;
            processoutputNumber();
        }
	}
	static void exchange() {
		if (current.length() < 1) {
			current = "0";
        	equal = 0;
        	return;	
        }
		BigDecimal s = new BigDecimal(current);
		BigDecimal zero = new BigDecimal(0);
    	zero = zero.setScale(9,RoundingMode.HALF_UP);
    	if(s.compareTo(zero) == 0){
    		return;
    	}
    	else if(s.compareTo(zero) < 0){
    		current = String.valueOf(s.negate());
    	}
    	else{
    		current  = String.valueOf(s.negate());
    	}
    	processoutputNumber();
	}
        
	//deleteで０文字の時に削除をしようとすると不正アクセスになるため、これもCキーと併用で作成しておく。
	static void clear(){
		current = "0";
		previous = "";
		operator = null;
		prepre = "";
		preOperator = null;
		curcur = "";
		precurrent = "";
		cc = 0;
	}
	
    static void per(){
    	if (current.length() < 1) {
        	equal = 0;
        	return;	
        }
    	BigDecimal result = new BigDecimal(current);
    	BigDecimal one = new BigDecimal(1);
    	BigDecimal zero = new BigDecimal(0);
    	zero = zero.setScale(9,RoundingMode.HALF_UP);
    	BigDecimal h = new BigDecimal(100);
    	BigDecimal dt = new BigDecimal(Math.pow(10,8));
    	dt = one.divide(dt,9,RoundingMode.HALF_UP); 
    	result = result.divide(h,9,RoundingMode.HALF_UP);
    	if(result.compareTo(dt) < 0 && result.compareTo(zero) > 0){
    		current = "0";
    		return;
    	}
    	operator = null;
		previous = "";
    	current = result.toString();
    	processoutputNumber();
    }
    
    static void root(){
    	if (current.length() < 1) {
        	equal = 0;
        	return;	
        }
    	BigDecimal cc = new BigDecimal(Double.parseDouble(current));
    	BigDecimal zero = new BigDecimal(0);
    	if(cc.compareTo(zero) < 0){
    		current = ERROR;
    		operator = null;
    		previous = "";
    		return;
    	}
    	BigDecimal a = new BigDecimal(Math.sqrt(Double.parseDouble(current)));
    	zero = zero.setScale(9,RoundingMode.HALF_UP);
    	if(a.compareTo(zero) < 0){
    		current = ERROR;
    		operator = null;
    		previous = "";
    		return;
    	}
    	a = a.setScale(8,RoundingMode.HALF_UP);
    	current = (String.valueOf(a));
    	BigDecimal dt = new BigDecimal(Math.pow(10,-8));
    	dt = dt.setScale(9,RoundingMode.HALF_UP);
        if(a.compareTo(dt) > 0 && a.compareTo(dt) < 0){
    		current = String.valueOf(0);
    		operator = null;
    		previous = "";
    		processoutputNumber();
    		return;
    	}
        operator = null;
		previous = "";
    	processoutputNumber();
    }
    
	static void updateOutput() {
	    //currentをString形式でoutput、すなわち電卓の出力部分に出す。
        output.setText(current);
        if(current.equals(ERROR)){
        	clear();
        }
    }
		
	//ボタンと出力の紐付けやレイアウトの設定を行なっている。
	public Calc(){ 
		super("Box caluclator");
		//mainPanelを作成、これが電卓の土台
		JPanel mainPanel = new JPanel();
		
		//Panelの列を１つずつ定義
		JPanel row1 = new JPanel();
		JPanel row2 = new JPanel();
		JPanel row3 = new JPanel();
		JPanel row4 = new JPanel();
		JPanel row5 = new JPanel();
		
		//outputや、計算の演算などを指示するボタンを作成。JTextFieldでテキストボックス、JButtonでボタンを設定できる。
		output = new JTextField();
        btnSubtract = new JButton("-");
        btnAdd = new JButton("+");
        btnDivide = new JButton("÷");
        btnMultiply = new JButton("*");
        btnDot = new JButton(".");
        btnEquals = new JButton("=");
        btnClear = new JButton("C");
        btnExchange = new JButton("+/-");
        btnper = new JButton("%");
        btnroot = new JButton("√");
        
        //Action Listenerのインスタンス化
        NumBtnHandle numBtnHandler = new NumBtnHandle();
        OtherBtnHandle otherBtnHandler = new OtherBtnHandle();
        OperatorBtnHandle opBtnHandler = new OperatorBtnHandle();
        
        //数字のパネルを１１個用意
        numBtn = new JButton[11];
        //一番最後の数字パネルは小数を表す「.」
        numBtn[10] = btnDot;
        
        for (int i = 0; i < numBtn.length - 1; i++) {
        	//ボタンは本来ストリング型なので、数字に変換したものをボタンに再度割り当てるようにしている。
            numBtn[i] = new JButton(String.valueOf(i));
            //ここではボタンの表示を設定している。.BOLDは太字で設定しているという意味。22はフォントのサイズ
            numBtn[i].setFont(new Font("Monospaced", Font.BOLD,22));
            //Action Listenerとボタンの紐付けを行なっている。
            numBtn[i].addActionListener(numBtnHandler);
        }
        
        //先程と同じ容量で演算子やドットボタンなどのフォントとそのサイズを指定。
        btnDot.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnEquals.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnAdd.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnSubtract.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDivide.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnMultiply.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnClear.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnExchange.setFont(new Font("halfspaced", Font.PLAIN, 15));
        btnper.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnroot.setFont(new Font("Monospaced", Font.BOLD, 22));
        
        //演算子のボタンにAction Listenerを紐づける。
        btnMultiply.addActionListener(opBtnHandler);
        btnAdd.addActionListener(opBtnHandler);
        btnSubtract.addActionListener(opBtnHandler);
        btnDivide.addActionListener(opBtnHandler);
        
        //数字、演算子以外のボタンにAction Listenerを紐づける。
        btnDot.addActionListener(numBtnHandler);
        btnExchange.addActionListener(otherBtnHandler);
        btnClear.addActionListener(otherBtnHandler);
        btnEquals.addActionListener(otherBtnHandler);
        btnper.addActionListener(otherBtnHandler);
        btnroot.addActionListener(otherBtnHandler);
        
        //最後にoutputの表示を設定
        output.setMaximumSize(new Dimension(185, 40));
        output.setFont(new Font("Monospaced", Font.BOLD, 27));
        output.setDisabledTextColor(new Color(0, 0, 0));
        output.setMargin(new Insets(0, 5, 0, 0));
        output.setText("0");
        output.setColumns(20);
        
        //ここまででボタン等の設定はできたが、まだレイアウトが終わっていないので、ここからはレイアウトを設定する。
        
        //先程設定した列rowを順番にレイアウトに設定
        row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
        row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
        row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
        row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
        row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));
		
        //各列にボタンを追加していく。
        //Box.createHorizontalGlue()は１列目のボタン二つを右端に寄せるために使っている。
        row1.add(Box.createHorizontalGlue());
        row1.add(btnClear);
        row1.add(btnExchange);
        row1.add(btnper);
        row1.add(btnroot);
        row2.add(numBtn[7]);
        row2.add(numBtn[8]);
        row2.add(numBtn[9]);
        row2.add(btnMultiply);
        row3.add(numBtn[4]);
        row3.add(numBtn[5]);
        row3.add(numBtn[6]);
        row3.add(btnAdd);
        row4.add(numBtn[1]);
        row4.add(numBtn[2]);
        row4.add(numBtn[3]);
        row4.add(btnSubtract);
        row5.add(btnDot);
        row5.add(numBtn[0]);
        row5.add(btnEquals);
        row5.add(btnDivide);
        
        //電卓を起動した時に、初めの出力が空欄になるよう設定している。これをしないと、NULLが最初に表示されてしまう。
        current = "";
        previous = "";
        prepre = "";
        curcur = "";
        
        //mainPanelに先程設定したレイアウトを乗せる。Box.createRigidArea(new Dimension(0, 5))は出力とボタンの部分にスペースをy軸に５分確保している。
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(output);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);
        
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300, 250);
	}
		
	public static void main(String[] args) {
		new Calc();     
	}
}
