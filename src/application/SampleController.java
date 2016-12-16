package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model model = new Model();
	
	public void setModel(Model model){
		this.model=model;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtLunghezza;

    @FXML
    private Button btnCarica;

    @FXML
    private TextField txtParolaIniziale;

    @FXML
    private TextField txtParolaFinale;

    @FXML
    private Button btnCerca;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCarica(ActionEvent event) {
    	txtResult.clear();
    	try{
    		int lunghezza = Integer.parseInt(txtLunghezza.getText());
    		if(lunghezza==0){
    		  txtResult.appendText("Inserire un numero diverso da 0 ! \n");
    		  return;
    	    }
    	    int numero = model.getNumeroParole(lunghezza);
    	    UndirectedGraph<String, DefaultEdge> grafo  = model.buildGraph(lunghezza);
    	    int numeroArchi = model.getNumeroArchi(grafo);
    	    String s = model.getParolaMax(grafo);
    	    	    
    	    txtResult.appendText("Caricate " +numero+ " parole di lunghezza "+lunghezza+" \n");
    	    txtResult.appendText("Creato grafo con "+numero+" vertici e "+numeroArchi+" archi \n");
    	    txtResult.appendText("La parola " +s+ " è la parola che possiede piu connessioni con altre parole  " );
    	
    	
    	}catch(Exception e ){
    		txtResult.appendText("Inserire un numero valido! \n");
    		return;
    	}
    }

    @FXML
    void doCerca(ActionEvent event) {
    	int lunghezza = Integer.parseInt(txtLunghezza.getText());
    	String iniziale = txtParolaIniziale.getText();
    	String finale = txtParolaFinale.getText();
    	
    	if(iniziale.equals(null) ||  finale.equals(null)){
    		txtResult.appendText("Inserire due parole valide! \n");
    		return;
    	}
    		int l1 = iniziale.length();
    		int l2 = finale.length();
    	    if(l1 !=l2 ){
    	     txtResult.appendText("Le due parole devono essere della stessa lunghezza \n ");
    	     return;
    	     }
    	     if(lunghezza != l1 || lunghezza !=l2){                        //bastava l1, tanto sono =
    	    	 txtResult.appendText("La lunghezza non è corretta! \n ");
    	    	 return;
    	    		 
    	    	 }
    		 if(!model.getPresente(iniziale)){
    			 txtResult.appendText("La parola iniziale non è presente nel db dizionario ! \n");
    			 return;
    		 }
    		 if(!model.getPresente(finale)){
    			 txtResult.appendText("La parola finale non è presente nel db dizionario ! \n");
    			 return;
    		 }
    		 UndirectedGraph<String, DefaultEdge> grafo  = model.buildGraph(lunghezza);
    		 List<String> path = model.esisteCammino(grafo, iniziale, finale);
    		    if(path.size() == 0){
    			 txtResult.appendText("Il cammino non esiste! \n ");
    			 return; 
    		    }
    		      else{
    		    	  txtResult.appendText("La lista di parole che le collega è : "+path.toString());
    		      }	
    }
    	 
    @FXML
    void initialize() {
        assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCarica != null : "fx:id=\"btnCarica\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtParolaIniziale != null : "fx:id=\"txtParolaIniziale\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtParolaFinale != null : "fx:id=\"txtParolaFinale\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

    }
}
