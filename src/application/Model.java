package application;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import bean.Parola;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao ();
	UndirectedGraph<String, DefaultEdge> grafo ;
	
	public int getNumeroParole(int lunghezza){
		int conta;
		conta = dao.contaParole(lunghezza);
		return conta;
	}
	
	public List<Parola> getParoleLun(int lunghezza){
		List<Parola> paroleLung = new LinkedList<Parola>();
		paroleLung= dao.getParoleDiLunghezza(lunghezza);
		return paroleLung;
		
	}
	
	public boolean paroleSimili(String n1, String n2){
		int lun1 = n1.length();
		int lun2 = n2.length();
		if(lun1==lun2){
			int diff =0;
			for(int i=0; i<n1.length() & i<n2.length(); i++){
				if(n1.charAt(i)!= n2.charAt(i)){
					diff++;
				}	
			}
			if(diff==1){
				return true;
			}
		}
		return false;
	}
	
	public UndirectedGraph<String, DefaultEdge> getGrafo(){
		return grafo;
	}
	
	public int getNumeroArchi(UndirectedGraph<String, DefaultEdge>grafo){    //numero archi del grafo
		Set<DefaultEdge> archi = grafo.edgeSet();
		int numeroArchi = archi.size();
		return numeroArchi;
	}
	

	public UndirectedGraph<String, DefaultEdge> buildGraph(int lunghezza){                                  //costruisce il grafo
		grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);	
		for(Parola p : getParoleLun(lunghezza)){                           //i nodi sono le parole ke hanno quella lunghezza
			grafo.addVertex(p.getNome());	
		}
		for(String v1 : grafo.vertexSet()){
			for(String v2 : grafo.vertexSet()){
				if( v1 != v2){
					if(paroleSimili(v1, v2)){
						grafo.addEdge(v1, v2);
					}
				}
			}
		}
		return grafo;
	}
	
	
	//devo pero anche contare il numero di collegamenti max, so max
	
	public String getParolaMax(UndirectedGraph<String, DefaultEdge>grafo){   //visita del grafo x il max numero di collegamenti della parola
		String best =null;
		int max=0;
		for(String v : grafo.vertexSet()){
			BreadthFirstIterator<String, DefaultEdge> visita = new BreadthFirstIterator<String, DefaultEdge>(grafo, v);
			int contatore=0;
			while(visita.hasNext()){
				String s = visita.next();
				contatore++;
			}
			if(contatore > max){
				best = v;
				max=contatore;
			}
		}
		return best;
	}
	
	public int getNumeroMaxCollegamenti(UndirectedGraph<String, DefaultEdge>grafo){
		int max=0;
		String nome = getParolaMax(grafo);
		return max;
	}
	
	public boolean getPresente(String nome){
		return dao.isPresente(nome);
	}
	
	public boolean esisteArco(UndirectedGraph<String, DefaultEdge>grafo, String iniziale, String finale){
		if(grafo.containsEdge(iniziale, finale)){
			return true;
		}
		else return false;		
	}
	
	
	public List<String> getCammino(UndirectedGraph<String, DefaultEdge>grafo, String iniziale, String finale){
		if(iniziale == null || finale ==null){
			return null;
		}
		DijkstraShortestPath<String, DefaultEdge> camminoD = new DijkstraShortestPath<String, DefaultEdge>(grafo, iniziale, finale);
		GraphPath<String, DefaultEdge> path = camminoD.getPath();
		if(path== null){            //se non esiste il cammino
			return null;
		}
		return Graphs.getPathVertexList(path);   //se esiste il cammino
		}	
}
