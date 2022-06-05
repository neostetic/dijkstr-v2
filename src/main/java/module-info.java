module cz.spsmb.dijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.spsmb.dijkstra to javafx.fxml;
    exports cz.spsmb.dijkstra;
}