package com.kaselabs.jmapper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 8/1/2017.
 *
 */
public class RecognizerParser {

	public static final String FINITE_AUTOMATA_NODE = "finiteAutomata";
	public static final String TOKEN_TYPE_NODE = "tokenType";
	public static final String NUMBER_OF_STATES_NODE = "numberOfStates";
	public static final String FINAL_STATE_NODE = "finalState";
	public static final String DEFAULT_MAP_NODE = "defaultMap";
	public static final String START_STATE_NODE = "startState";
	public static final String END_STATE_NODE = "endState";
	public static final String TRANSITION_NODE = "transition";
	public static final String TRIGGER_NODE = "trigger";


	///////////////////////////
	//// public functions /////
	///////////////////////////

	public Recognizer createRecognizer(Document document) {
		Element root = document.getDocumentElement();
		if (root.getTagName().equals(FINITE_AUTOMATA_NODE))
			return createFiniteAutomata(root);
		else
			throw new IllegalArgumentException("Illegal Recognizer Data");
	}


	//////////////////////////////////
	///// Parse a FiniteAutomata /////
	//////////////////////////////////

	private FiniteAutomata createFiniteAutomata(Element faElement) {
		NodeList nodes = faElement.getChildNodes();

		TokenType type = TokenType.valueOf(parseStringFromNode(nodes.item(0)));
		int numberOfStates = parseIntFromNode(nodes.item(1));
		FiniteAutomata fa = new FiniteAutomata(type, numberOfStates);

		int currentNode = 2;
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(FINAL_STATE_NODE)) {
			fa.addFinalState(parseIntFromNode(nodes.item(currentNode)));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(DEFAULT_MAP_NODE)) {
			createDefaultMap(fa, nodes.item(currentNode));
			currentNode++;
		}
		while (currentNode < nodes.getLength() &&
				nodes.item(currentNode).getNodeName().equals(TRANSITION_NODE)) {
			createTransition(fa, nodes.item(currentNode));
			currentNode++;
		}

		return fa;
	}

	/**
	 * Adds a default map onto the FiniteAutomata based on the data stored
	 * in the node. It is assumed that the node passed as an argument is
	 * a DEFAULT_MAP_NODE.
	 * @param fa FiniteAutomata to add the default map to
	 * @param node storing the default map data
	 */
	private void createDefaultMap(FiniteAutomata fa, Node node) {
		NodeList nodes = node.getChildNodes();
		int start = parseIntFromNode(nodes.item(0));
		int end = parseIntFromNode(nodes.item(1));
		fa.addDefaultStateFor(start, end);
	}

	/**
	 * Adds a transition onto the FiniteAutomata based on the data stored
	 * in the node. It is assumed that the node passed as an argument is
	 * a TRANSITION_NODE.
	 * @param fa FiniteAutomata to add the transition to
	 * @param node storing the transition data
	 */
	private void createTransition(FiniteAutomata fa, Node node) {
		NodeList nodes = node.getChildNodes();
		int start = parseIntFromNode(nodes.item(0));
		int end = parseIntFromNode(nodes.item(1));
		List<Character> triggers = new ArrayList<>();
		for (int i = 2; i < nodes.getLength(); i++)
			triggers.add(parseCharacterFromNode(nodes.item(i)));
		fa.addTransition(start, end, triggers);
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
