module br.edu.fesa.presentation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens br.edu.fesa.presentation to javafx.fxml;
    exports br.edu.fesa.presentation;
}