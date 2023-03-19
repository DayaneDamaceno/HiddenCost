package br.edu.fesa.dao;

import java.util.List;

public interface Dao<T> {
    List<T> obterTodos();

    T buscar(String[] params);

    void salvar(T t);

    void atualizar(T t, String[] params);

    void deletar(T t);
}
