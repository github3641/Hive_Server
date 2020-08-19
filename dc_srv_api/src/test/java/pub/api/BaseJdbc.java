package pub.api;

import java.sql.Connection;

public interface BaseJdbc {
    public Connection getConnect();
}
