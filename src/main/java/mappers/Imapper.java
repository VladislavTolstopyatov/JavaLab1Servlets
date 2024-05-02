package mappers;

import exceptions.DataBaseException;

public interface Imapper<F, T> {
    T map(F object) throws DataBaseException;
}
