package cz.spsmb.dijkstra.file;

import java.util.ArrayList;

public class FileNode {
    public ArrayList<String> createNodes;
    public ArrayList<String[]> updateNodes;

    public FileNode(ArrayList<String> createNodes, ArrayList<String[]> updateNodes) {
        this.createNodes = createNodes;
        this.updateNodes = updateNodes;
    }

    public ArrayList<String> getCreateNodes() {
        return createNodes;
    }

    public void setCreateNodes(ArrayList<String> createNodes) {
        this.createNodes = createNodes;
    }

    public ArrayList<String[]> getUpdateNodes() {
        return updateNodes;
    }

    public void setUpdateNodes(ArrayList<String[]> updateNodes) {
        this.updateNodes = updateNodes;
    }
}
