package cz.spsmb.dijkstra;

import cz.spsmb.dijkstra.algorithm.Dijkstra;
import cz.spsmb.dijkstra.algorithm.Graph;
import cz.spsmb.dijkstra.algorithm.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Controller {

    public Button createNodeButton;
    public TextField createName;
    public TextField updateName;
    public TextField updateDestiny;
    public TextField updateDistance;
    public TextField calculateDestiny;
    public TextArea calculateText;
    public Label popupText;
    public AnchorPane popup;
    public ListView<String> nodeList;
    public ArrayList<Node> nodeArray = new ArrayList<>();

    public void initialize() {
        popup.setVisible(false);
    }

    public void onCreateNodeButton(ActionEvent actionEvent) {
        String name = createName.getText();
        if (getIndexInArray(name) < 0) {
            nodeArray.add(new Node(name));
            createName.setText("");
            updateListView();
        } else {
            popupSet("Node already exist (" + name + ")");
        }
    }

    public void onUpdateNodeButton(ActionEvent actionEvent) {
        String name = updateName.getText();
        String destinyName = updateDestiny.getText();

        int nodeInt = getIndexInArray(name);
        int destinyNodeInt = getIndexInArray(destinyName);

        if (nodeInt < 0) {
            popupSet("Node doesn't exist (" + name + ")");
        } else if (destinyNodeInt < 0) {
            popupSet("Destiny Node doesn't exist (" + destinyName + ")");
        } else {
            Node node = nodeArray.get(nodeInt);
            Node destinyNode = nodeArray.get(destinyNodeInt);
            try {
                int destinyDistance = Integer.parseInt(updateDistance.getText());
                node.addDestination(destinyNode, destinyDistance);
                if (destinyDistance < 0) {
                    popupSet("Please, set valid Number Type (" + updateDistance.getText() + ")");
                } else {
                    updateName.setText("");
                    updateDestiny.setText("");
                    updateDistance.setText("");
                    updateListView();
                }
            } catch (NumberFormatException e) {
                popupSet("Please, set valid Number Type (" + updateDistance.getText() + ")");
            }
        }

    }
    
    public void onDeleteButton(ActionEvent actionEvent) {
        nodeArray.clear();
        updateListView();
    }

    private int getIndexInArray(String string) {
        for (int i = 0; i < nodeArray.size(); i++) {
            if (nodeArray.get(i).getName().equals(string)) return i;
        }
        return -1;
    }

    private void updateListView() {
        nodeList.getItems().clear();
        for (Node node : nodeArray) {
            nodeList.getItems().add(node.pleaseString());
        }
    }

    public void onPopupclick(MouseEvent mouseEvent) {
        popup.setVisible(false);
    }

    private void popupSet(String text) {
        popupText.setText(text);
        popup.setVisible(true);
    }

    public void onCalculateButton(ActionEvent actionEvent) {
        try {
            String nodeDestiny = calculateDestiny.getText();
            Graph graph = new Graph();
            for (Node node : nodeArray) {
                graph.addNode(node);
            }
            if (getIndexInArray(nodeDestiny) < 0) {
                popupSet("Your Destiny doesn't exists (" + nodeDestiny + ")");
            } else {
                calculateText.setText(Dijkstra.calculateShortestPathFromSource(graph, nodeArray.get(getIndexInArray(nodeDestiny))).toString());
                calculateDestiny.setText("");
            }
        } catch (Exception e) {
            popupSet("Your Nodes are Missing");
        }
    }
}