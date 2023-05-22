package br.edu.fesa.infra.dao;

import java.util.List;

public interface Dao<T> {
    List<T> obterTodos();

    T buscar(T t);

    T salvar(T t);

    void atualizar(T t);

    void deletar(T t);
}
