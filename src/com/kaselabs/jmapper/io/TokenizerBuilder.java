package com.kaselabs.jmapper.io;

import com.kaselabs.jmapper.tokenizer.Automata;
import com.kaselabs.jmapper.tokenizer.CompoundAutomata;
import com.kaselabs.jmapper.tokenizer.SimpleAutomata;
import com.kaselabs.jmapper.token.TokenType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 8/1/2017.
 *
 * Will need to be redesigned for clarity purposes in near future.
 */
public class TokenizerBuilder {

	public static final String AUTOMATA_NODE = "automata";
	public static final String TOKEN_TYPE_NODE = "tokenType";
	public static final String NUMBER_OF_STATES_NODE = "numberOfStates";
	public static final String FINAL_STATE_NODE = "finalState";
	public static final String DEFAULT_MAP_NODE = "defaultMap";
	public static final String START_STATE_NODE = "startState";
	public static final String END_STATE_NODE = "endState";
	public static final String TRANSITION_NODE = "transition";
	public static final String TRIGGER_NODE = "trigger";

	public static final String COMPOUND_AUTOMATA_NODE = "compoundAutomata";
	public static final String SUBAUTOMATA_NODE = "subAutomata";


	///////////////////////////
	//// public functions /////
	///////////////////////////

	public Automata createRecognizer(Document document) {
		return createAutomata(document.getDocumentElement());
	}


	//////////////////////////
	///// Parse Automata /////
	//////////////////////////

	private Automata createAutomata(Node root) {
		if (root.getNodeName().equals(AUTOMATA_NODE))
			return createSimpleAutomata(root);
		else if (root.getNodeName().equals(COMPOUND_AUTOMATA_NODE))
			return createCompoundAutomata(root);
		else
			throw new IllegalArgumentException("Illegal Tokenizer Data");
	}

	private SimpleAutomata createSimpleAutomata(Node saElement) {
		NodeList nodes = saElement.getChildNodes();

		TokenType type = TokenType.valueOf(parseStringFromNode(nodes.item(0)));
		int numberOfStates = parseIntFromNode(nodes.item(1));
		SimpleAutomata sa = new SimpleAutomata(type, numberOfStates);

		int currentNode = 2;
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(FINAL_STATE_NODE)) {
			sa.addFinalState(parseIntFromNode(nodes.item(currentNode)));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(DEFAULT_MAP_NODE)) {
			createDefaultMap(sa, nodes.item(currentNode));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(TRANSITION_NODE)) {
			createTransition(sa, nodes.item(currentNode));
			currentNode++;
		}

		return sa;
	}

	/**
	 * Adds a default map onto the Automata based on the data stored
	 * in the node. It is assumed that the node passed as an argument is
	 * a DEFAULT_MAP_NODE.
	 * @param a Automata to add the default map to
	 * @param node storing the default map data
	 */
	private void createDefaultMap(Automata a, Node node) {
		NodeList nodes = node.getChildNodes();
		int start = parseIntFromNode(nodes.item(0));
		int end = parseIntFromNode(nodes.item(1));
		a.addDefaultStateFor(start, end);
	}

	/**
	 * Adds a transition onto the Automata based on the data stored
	 * in the node. It is assumed that the node passed as an argument is
	 * a TRANSITION_NODE.
	 * @param a Automata to add the transition to
	 * @param node storing the transition data
	 */
	private void createTransition(Automata a, Node node) {
		NodeList nodes = node.getChildNodes();
		int start = parseIntFromNode(nodes.item(0));
		int end = parseIntFromNode(nodes.item(1));
		List<Character> triggers = new ArrayList<>();
		for (int i = 2; i < nodes.getLength(); i++)
			triggers.add(parseCharacterFromNode(nodes.item(i)));
		a.addTransition(start, end, triggers);
	}

	////////////////////////////////////
	///// Parse a CompoundAutomata /////
	////////////////////////////////////

	private CompoundAutomata createCompoundAutomata(Node caElement) {
		NodeList nodes = caElement.getChildNodes();

		TokenType type = TokenType.valueOf(parseStringFromNode(nodes.item(0)));
		int numberOfStates = parseIntFromNode(nodes.item(1));
		CompoundAutomata ca = new CompoundAutomata(type, numberOfStates);

		int currentNode = 2;
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(FINAL_STATE_NODE)) {
			ca.addFinalState(parseIntFromNode(nodes.item(currentNode)));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(DEFAULT_MAP_NODE)) {
			createDefaultMap(ca, nodes.item(currentNode));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(TRANSITION_NODE)) {
			createTransition(ca, nodes.item(currentNode));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(SUBAUTOMATA_NODE)) {
			createSubAutomata(ca, nodes.item(currentNode));
			currentNode++;
		}

		return null;
	}

	private void createSubAutomata(CompoundAutomata ca, Node subAutomataElement) {
		NodeList nodes = subAutomataElement.getChildNodes();
		int startNode = parseIntFromNode(nodes.item(0));
		Automata subAutomata = createAutomata(nodes.item(1));
		ca.addAutomata(startNode, subAutomata);
	}

	/////////////////////////////
	///// Parse simple data /////
	/////////////////////////////

	/**
	 * Creates an integer from the node passed as an argument. Assumed that
	 * the data stored within the node is an int.
 	 * @param node containing Integer information
	 * @return int represented in the node
	 */
	private int parseIntFromNode(Node node) {
		return Integer.parseInt(node.getTextContent());
	}

	/**
	 * Creates a String from the node passed as an argument. Assumed that
	 * the data stored within the node is a String.
	 * @param node containing String information
	 * @return string represented in the node
	 */
	private String parseStringFromNode(Node node) {
		return node.getTextContent();
	}

	/**
	 * Creates a char from the node passed as an argument. Assumed that
	 * the data stored within the node is a char.
	 * @param node containing char information
	 * @return char represented in the node
	 */
	private char parseCharacterFromNode(Node node) {
		return node.getTextContent().charAt(0);
	}
}
