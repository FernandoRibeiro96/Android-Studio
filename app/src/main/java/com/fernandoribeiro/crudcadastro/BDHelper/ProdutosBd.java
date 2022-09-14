package com.fernandoribeiro.crudcadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fernandoribeiro.crudcadastro.model.Produtos;

import java.util.ArrayList;

public class ProdutosBd extends SQLiteOpenHelper {

    //Definindo nome e versão do bd e criando um construtor
    private static final String DATABASE = "BD_Produtos";
    private static final int VERSION = 1;

    public ProdutosBd(Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Definir tabela a ser criada... definir atributos
        String produto = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " nomeproduto TEXT NOT NULL, descricaoproduto TEXT NOT NULL, quantidadeproduto INTEGER);";

        //definir execucao
        sqLiteDatabase.execSQL(produto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Verifica e Exclui se existir alguma tabela existente com o nome produtos
        String produto = "DROP TABLE IF EXISTS produtos";
        sqLiteDatabase.execSQL(produto);
    }

    //Salvar
    public void salvarProdutos(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeProduto", produto.getNomeProduto());
        values.put("descricaoProduto", produto.getDescricaoProduto());
        values.put("quantidadeProduto", produto.getQuantidadeProduto());

        //insere na tabela os valores de values
        getWritableDatabase().insert("produtos", null, values);
    }

    //alterar produto
    public void alterarProdutos(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("nomeProduto", produto.getNomeProduto());
        values.put("descricaoProduto", produto.getDescricaoProduto());
        values.put("quantidadeProduto", produto.getQuantidadeProduto());

        //atualiza na tabela os valores de values
        String [] args = {produto.getId().toString()};
        getWritableDatabase().update("produtos", values, "id=?", args);
    }

    //deletar produto
    public void deletarProduto(Produtos produto){
        //deletar na tabela os valores de values
        String [] args = {produto.getId().toString()};
        getWritableDatabase().delete("produtos","id=?", args);
    }

    //Listar produtos
    //pega a classe produtos
    public ArrayList<Produtos> getLista(){
        //select
        String [] columns = {"id", "nomeproduto", "descricaoproduto", "quantidadeproduto"};
        //Responsavel por está salvando a lista
        Cursor cursor = getWritableDatabase().query("produtos", columns,
                null, null, null, null, null,null);
        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        //ir sempre para o proximo, para não ficar parando
        while (cursor.moveToNext()){
            Produtos produto = new Produtos();
            produto.setId(cursor.getLong(0));
            produto.setNomeProduto(cursor.getString(1));
            produto.setDescricaoProduto(cursor.getString(2));
            produto.setQuantidadeProduto(cursor.getInt(3));

            produtos.add(produto);
        }
        return produtos;
    }
}
