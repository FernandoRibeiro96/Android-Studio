package com.fernandoribeiro.crudcadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.fernandoribeiro.crudcadastro.BDHelper.ProdutosBd;
import com.fernandoribeiro.crudcadastro.model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCadastrar;

    ListView lista;
    ProdutosBd bdHelper;
    Produtos produto;
    ArrayList<Produtos> listView_produtos;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listView_Produtos_Id);
        registerForContextMenu(lista);

        btnCadastrar = (Button) findViewById(R.id.btn_Cadastrar_Id);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
                startActivity(intent);

            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(i);

                Intent in= new Intent(MainActivity.this, FormularioProdutos.class);
                in.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(in);


            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                produto = (Produtos) adapterView.getItemAtPosition(i);
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar este Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                bdHelper = new ProdutosBd(MainActivity.this);
                bdHelper.deletarProduto(produto);
                bdHelper.close();

                carregarProduto();
                return true;
            }
        });
    }

    protected void onResume(){
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto(){
        bdHelper = new ProdutosBd(MainActivity.this);
        listView_produtos = bdHelper.getLista();
        bdHelper.close();

        //verifica se a lista é diferente de null
        if (listView_produtos != null){
            adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_list_item_1, listView_produtos);
            lista.setAdapter(adapter);
        }
        //finish();
    }
}