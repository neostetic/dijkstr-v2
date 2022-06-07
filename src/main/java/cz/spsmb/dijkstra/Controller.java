package cz.spsmb.dijkstra;

import cz.spsmb.dijkstra.algorithm.Dijkstra;
import cz.spsmb.dijkstra.algorithm.Graph;
import cz.spsmb.dijkstra.algorithm.Node;
import cz.spsmb.dijkstra.file.FileNode;
import cz.spsmb.dijkstra.file.FileUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
        if (FileUtil.exists()) {
            try {
                FileNode updateArrays = FileUtil.readFromFile();
                for (int i = 0; i < updateArrays.createNodes.size(); i++) {
                    createNodeName(updateArrays.createNodes.get(i));
                }
                for (int i = 0; i < updateArrays.updateNodes.size(); i++) {
                    String node1 = updateArrays.updateNodes.get(i)[0];
                    String node2 = updateArrays.updateNodes.get(i)[1];
                    int distance = Integer.parseInt(updateArrays.updateNodes.get(i)[2]);
                    updateDestiny(nodeArray.get(getIndexInArray(node1)), nodeArray.get(getIndexInArray(node2)), distance);
                }
            } catch (Exception e) {
                popupSet("Something went wrong with Input Save (input.txt corrupted) - Restart the Program");
            }
        }
    }

    public void onCreateNodeButton(ActionEvent actionEvent) {
        String name = stringController(createName.getText());
        if (getIndexInArray(name) < 0) {
            createNodeName(name);
        } else {
            popupSet("Node already exist (" + name + ")");
        }
    }

    public void onUpdateNodeButton(ActionEvent actionEvent) {
        String name = stringController(updateName.getText());
        String destinyName = stringController(updateDestiny.getText());

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
                if (destinyDistance < 0) {
                    popupSet("Please, set valid Number Type (" + updateDistance.getText() + ")");
                } else {
                    updateDestiny(node, destinyNode, destinyDistance);
                    updateName.setText("");
                    updateDestiny.setText("");
                    updateDistance.setText("");
                }
            } catch (NumberFormatException e) {
                popupSet("Please, set valid Number Type (" + updateDistance.getText() + ")");
            }
        }

    }
    
    public void onDeleteButton(ActionEvent actionEvent) {
        nodeArray.clear();
        FileUtil.deleteFile();
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
            nodeList.getItems().add(node.toControllerString());
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
                calculateText.setText(
                        "Chosen Node: " + calculateDestiny.getText() + "\n" +
                        "Calculate paths: " + Dijkstra.calculateShortestPathFromSource(graph, nodeArray.get(getIndexInArray(nodeDestiny))).toStringWithDistance());
                calculateDestiny.setText("");
            }
        } catch (Exception e) {
            popupSet("Your Nodes are Missing");
        }
    }

    private void createNodeName(String name) {
        stringController(name);
        nodeArray.add(new Node(name));
        createName.setText("");
        FileUtil.writeToFile(name);
        updateListView();
    }

    private void updateDestiny(Node node, Node destinyNode, int destinyDistance) {
        node.addDestination(destinyNode, destinyDistance);
        destinyNode.addDestination(node, destinyDistance);
        FileUtil.writeToFile(node.getName(), destinyNode.getName(), destinyDistance);
        updateListView();
    }

    private String stringController(String string) {
        if (string.length() <= 0 || string.equals(" "))
            return "*empty*";
        else
            return string.replaceAll(";","").replaceAll(":","").replaceAll(",","");
    }
}