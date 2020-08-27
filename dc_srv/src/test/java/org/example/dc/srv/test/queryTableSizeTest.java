package org.example.dc.srv.test;

import org.example.dc.srv.service.HiveQuery;

public class queryTableSizeTest {
    public static void main(String[] args) {
        HiveQuery hiveQuery = new HiveQuery();
        hiveQuery.queryTableSize();
    }
}
