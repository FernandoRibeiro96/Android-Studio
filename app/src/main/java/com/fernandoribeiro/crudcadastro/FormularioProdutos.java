package com.fernandoribeiro.crudcadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fernandoribeiro.crudcadastro.BDHelper.ProdutosBd;
import com.fernandoribeiro.crudcadastro.model.Produtos;

public class FormularioProdutos extends AppCompatActivity {

    EditText nomeProduto;
    EditText descricaoProduto;
    EditText quantidadeProduto;
    Button btnModificar;

    Produtos editarProdutos, produto;
    ProdutosBd bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produtos);

        produto = new Produtos();
        bdHelper = new ProdutosBd(FormularioProdutos.this);

        //pegando informação da activity anterior
        Intent intent = getIntent();
        editarProdutos = (Produtos) intent.getSerializableExtra("produto-escolhido");

        nomeProduto = (EditText) findViewById(R.id.editText_NomeProduto_Id);
        descricaoProduto = (EditText) findViewById(R.id.editText_DescricaoProduto_Id);
        quantidadeProduto = (EditText) findViewById(R.id.editText_Quantidade_Id);
        btnModificar = (Button) findViewById(R.id.btn_Modificar_ID);

        if (editarProdutos != null){
            btnModificar.setText("Modificar");

            nomeProduto.setText(editarProdutos.getNomeProduto());
            descricaoProduto.setText(editarProdutos.getDescricaoProduto());
            quantidadeProduto.setText(editarProdutos.getQuantidadeProduto()+"");

            produto.setId(editarProdutos.getId());

        }else{
            btnModificar.setText("Cadastrar");
        }

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produto.setNomeProduto(nomeProduto.getText().toString());
                produto.setDescricaoProduto(descricaoProduto.getText().toString());
                produto.setQuantidadeProduto(Integer.parseInt(quantidadeProduto.getText().toString()));

                if (btnModificar.getText().toString().equals("Cadastrar")){
                    bdHelper.salvarProdutos(produto);
                    bdHelper.close();
                }else {
                    bdHelper.alterarProdutos(produto);
                    bdHelper.close();
                }

            }
        });
    }
}