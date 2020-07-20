module Project.team.m {

    requires javafx.controls;
    requires  javafx.fxml;
    requires com.google.gson;
    requires  javafx.base;
    requires  javafx.archetype.simple;
    requires javafx.baseEmpty;


    opens client.view.gui to javafx.fxml,javafx.controls,javafx.graphics,javafx.base;
    opens client to javafx.graphics;
    opens model.accounts to javafx.base;
    opens model.off to javafx.base;
    opens model.request to javafx.base;
    opens model.productRelated to javafx.base;
    opens model.log to javafx.base;
    opens server.menus to java.base;
    opens serverClient to javafx.graphics;
    opens model.bank to javafx.base;

}