package matlabjava11;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

public class MatlabJava11_lib {
	Future<MatlabEngine> eng;
	MatlabEngine ml;
	
	public MatlabJava11_lib() {
		this.eng = MatlabEngine.startMatlabAsync();
		try {
			ml = eng.get();
			//ml.putVariableAsync("data", data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//https://jp.mathworks.com/help/nnet/ref/alexnet.html
	//https://jp.mathworks.com/help/images/resize-an-image.html
	public void getAlex() {
		double result[] = null;
		String category[] = null;
		try {
			ml.eval("deepnet = alexnet;"); //Neural Network Toolbox(TM) Model for AlexNet Network
			ml.eval("deepnet.Layers"); //このネットワークは 25 個の層で構成されています。学習可能な重みを持つ 8 個の層があり、そのうち 5 個がたたみ込み層で、3 個が全結合層です。
			ml.eval("img1 = imread('img/dog01.jpg');"); 
			ml.eval("imshow(img1)");
			ml.eval("pause(5);");
			ml.eval("sz = deepnet.Layers(1).InputSize"); //ネットワークの最初の層の InputSize プロパティを使用して、ネットワークの入力サイズを求めます。
			ml.eval("img = imresize(img1,[227 227]);");
			//ml.eval("imshow(img)");
			ml.eval("pause(5);");
			ml.eval("pred1 = classify(deepnet,img)");
			ml.eval("categorynames = deepnet.Layers(end).ClassNames;");
			ml.eval("[pred,scores] = classify(deepnet,img);");
			ml.eval("highscores = scores > 0.01;");
			ml.eval("bar(scores(highscores))");
			ml.eval("xticklabels(categorynames(highscores))");
			ml.eval("pause(5);");
			ml.eval("saveas(gcf,'bar.png')");
			ml.eval("hcategory = categorynames(highscores)");
			ml.eval("hscore = scores(highscores)'");
			
			/* 課題hscoreをJava側に引き渡せなかった。
			//ml.eval("hscore = [hscore]");
			//ml.eval("for i = hscore \n"
			//		+ "disp(i) \n"
			//		+ "end");
			//ml.eval("hscore = table(scores(highscores)')");
			//ml.eval("hs = table2cell(hscore)");
			
			//result[0] = (double)ml.getVariable("hs1");
			Future<double[]> futureEval_val = ml.getVariableAsync("hscore");
			result = futureEval_val.get();
			//System.out.println("HighScores = "+Arrays.asList(result));
			 */
			
			Future<String[]> futureEval_st = ml.getVariableAsync("hcategory");
			category = futureEval_st.get();
			System.out.println("HighCategory = " + Arrays.asList(category));
			
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
