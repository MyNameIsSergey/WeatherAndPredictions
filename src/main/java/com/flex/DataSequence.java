package com.flex;

import java.sql.SQLException;

public interface DataSequence<T> {
    T NextElement() throws SQLException;
}

