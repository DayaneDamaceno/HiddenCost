package br.edu.fesa.infra.dao;

import java.util.List;

public interface Dao<T> {
    List<T> obterTodos();

    T buscar(T t);

    void salvar(T t);

    void atualizar(T t, String[] params);

    void deletar(T t);
}
