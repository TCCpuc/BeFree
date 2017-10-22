package tcc.befree.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Busca;
import tcc.befree.models.DDD;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.AdvancedSearchDialog;
import tcc.befree.telas.listaDeBuscas.SearchFragment;
import tcc.befree.telas.listaDeServicos.ServiceFragment;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    //private int idUsuario = 0;
    private int ultimaPosicao = 0;
    private Button search_advanced_button;
    private AdvancedSearchDialog dialog;
    private ServiceFragment serviceFragment = new ServiceFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private ViewPager viewPager;
    private int abaAtual = 0;
    private int currentPage;
    private String categoriaBuscaAvancada = "Todos";
    private int idcategoriaBuscaAvancada = 0;
    ArrayList<Integer> subCategoriasDaCategoria = new ArrayList<>();
    private String dddBuscaAvancada = "Todos";
    private int idDDDBuscaAvancada = 0;
    private String subcategoriaBuscaAvancada = "Todos";
    private int idSubCategoriaBuscaAvancada = 0;
    private String buscaSimples = "";
    private ApiModels api = new ApiModels();
    private Usuarios usuario;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search_advanced_button = (Button) findViewById(R.id.search_advanced_button);
        search_advanced_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AdvancedSearchDialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                Button saveButton = (Button)dialog.findViewById(R.id.advanced_search_dialog_button_ok);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        categoriaBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_categoria_spinner)).getSelectedItem().toString();
                        subcategoriaBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_sub_categoria_spinner)).getSelectedItem().toString();
                        dddBuscaAvancada = ((Spinner)dialog.findViewById(R.id.advanced_search_dialog_ddd_spinner)).getSelectedItem().toString();

                        ArrayList<Categoria> categorias = api.getCategorias();
                        for (Categoria c: categorias) {
                            if (c.descricao.equals(categoriaBuscaAvancada)) {
                                idcategoriaBuscaAvancada = c.idCategoria;
                                break;
                            }
                        }

                        subCategoriasDaCategoria = api.getSubCategoriasDaCategoria(idcategoriaBuscaAvancada);

                        ArrayList<DDD> ddds = api.getDDDs();
                        for (DDD ddd: ddds) {
                            if (dddBuscaAvancada.contains(ddd.descricao)) {
                                idDDDBuscaAvancada = ddd.id;
                                break;
                            }
                        }

                        ArrayList<SubCategoria> subCategorias = api.getSubCategorias();
                        for (SubCategoria s: subCategorias) {
                            if (s.descricao.equals(subcategoriaBuscaAvancada)) {
                                idSubCategoriaBuscaAvancada = s.idSubCategoria;
                                break;
                            }
                        }
                        dialog.dismiss();
                        busca();
                    }
                });
            }
        });

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_default);
        ab.setDisplayHomeAsUpEnabled(true);

        Intent it = this.getIntent();
        Bundle loginActivityIntent = it.getExtras();
        String x [] = loginActivityIntent.getString("arrayUsuario").split("%");
        id = Integer.parseInt(x[0]);
        usuario = new ApiModels().getUsuarioById(id);

//        usuario.idUsuario = Integer.parseInt(x[0]);
//        usuario.nomeUsuario = x[1];
//        usuario.cpf = x[2];
//        usuario.idCidade = Integer.parseInt(x[3]);
//        usuario.idEstado = Integer.parseInt(x[4]);
//        usuario.bairro = x[5];
//        usuario.logradouro = x[6];
//        usuario.numero = Integer.parseInt(x[7]);
//        usuario.cep = Integer.parseInt(x[8]);
//        usuario.email = x[9];
//        usuario.ddd = Integer.parseInt(x[10]);
//        usuario.imagemPerfil = x[11];
//        usuario.senha = x[12];
        //usuario.dataNascimento = Date.parse(x[10]);
        //usuario.dataCadastro = x[11].toDate;





        /*Bundle bundle = getIntent().getBundleExtra("idUsuario");
        try {
            idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }
        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                Bundle bundle = new Bundle();
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                if (tabLayout.getSelectedTabPosition() == 0) {
                    intent = new Intent(MainActivity.this, CreateServicoActivity.class);
                }
                else{
                    intent = new Intent(MainActivity.this, CreateBuscaActivity.class);
                }
                bundle.putInt("idUsuario",usuario.idUsuario);
                intent.putExtra("idUsuario", bundle);
                startActivity(intent);
            }
        });


        setTitle("Befree");
        //Instancia menu drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        //int id = getIntent().getExtras().getInt("bundle");
        refreshLista();

    }

    private void setupDrawerContent(NavigationView navigationView) {

        //navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
            /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/

        TextView mTextViewNomeUsuario = (TextView)header.findViewById(R.id.nome_usuario_menu);
        TextView mTextViewEmailUsuario = (TextView)header.findViewById(R.id.email_usuario_menu);
        CircleImageView mImagePerfil = (CircleImageView) header.findViewById(R.id.img_usuario_menu);

        String nomeUsuario = usuario.nomeUsuario;
        String emailUsuario = usuario.email;
        Picasso.with(this).load(usuario.imagemPerfil).into(mImagePerfil);

        mTextViewNomeUsuario.setText(nomeUsuario);
        mTextViewEmailUsuario.setText(emailUsuario);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @SuppressWarnings("StatementWithEmptyBody")
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();
                        if (id == R.id.menu_anuncios) {

                            Bundle bundle = new Bundle();
                            bundle.putInt("id",usuario.idUsuario);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
                            /*Intent i = MainActivity.this.getIntent();
                            startActivity(i);*/

                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            if (viewPager != null) {
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }
                            MainActivity.super.setTitle("Meus Anuncios");
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewPager);

                        } else if (id == R.id.menu_perfil) {
                            // ABRIR MEU PERFIL
                            Intent intent = new Intent(MainActivity.this, UserPerfilActivity.class);

                            intent.putExtra("arrayUsuario", usuario.toString());

                            startActivity(intent);

                        } else if (id == R.id.menu_calendario) {
                            // ABRIR Agenda
                            Intent intent = new Intent(MainActivity.this, GenderActivity.class);

                            startActivity(intent);

                        } else if (id == R.id.menu_chat) {
                            // ABRIR CHAT

                            Bundle bundle = new Bundle();
                            bundle.putInt("idUsuario",usuario.idUsuario);
                            Intent intent = new Intent(MainActivity.this, ListChatActivity.class);
                            intent.putExtra("bundle", bundle);


                            startActivity(intent);

                        } else if (id == R.id.menu_historico) {
                            // ABRIR HISTORICO
                            Intent intent = new Intent(MainActivity.this, UserPerfilActivity.class);

                            startActivity(intent);

                        } else if (id == R.id.menu_pagina_inicial) {
                            id = 0;
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            Intent intent = MainActivity.this.getIntent();
                            intent.putExtra("bundle", bundle);
                            /*Intent i = MainActivity.this.getIntent();
                            startActivity(i);*/


                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            if (viewPager != null) {
                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }
                            MainActivity.super.setTitle("Befree");
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setupWithViewPager(viewPager);

                        } else if (id == R.id.nav_share) {

                        } else if (id == R.id.nav_send) {

                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logout")
                    .setMessage("Você tem certeza que deseja sair do Befree?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();

            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);


        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    search_advanced_button.setVisibility(View.VISIBLE);
                }else {
                    search_advanced_button.setVisibility(View.GONE);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
            //Busca simples
                buscaSimples = searchQuery;
                busca();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;



        //getMenuInflater().inflate(R.menu.main, menu);
        //return true;
    }

    private void busca() {
        ApiModels api = new ApiModels();
        for (Busca b : searchFragment.results) {
            //Busca por titulo e descrição
            b.mostrar = b.titulo.toLowerCase().contains(buscaSimples.toLowerCase())
                    || b.descricao.toLowerCase().contains(buscaSimples.toLowerCase());

            if (!"Todos".equals(categoriaBuscaAvancada) && b.mostrar){
                b.mostrar = b.mostrar && subCategoriasDaCategoria.contains(b.idSubCategoria);
//                ArrayList<Categoria> categorias = api.getCategorias();
//                int idCategoria = 0;
//                for (Categoria c: categorias) {
//                    if (c.descricao.equals(categoriaBuscaAvancada)) {
//                        idCategoria = c.idCategoria;
//                        break;
//                    }
//                }
//                SubCategoria[] subCategorias = api.getSubCategoriasVetorByIdCategoria(idCategoria);
//                boolean subcategoriaExiste = false;
//                for (int i = 0; i< subCategorias.length; i++)
//                    if (subCategorias[i].idSubCategoria == b.idSubCategoria){
//                        subcategoriaExiste = true;
//                        break;
//                    }
//                b.mostrar = b.mostrar && subcategoriaExiste;
            }

            if (!"Todos".equals(dddBuscaAvancada)){
                b.mostrar = b.mostrar && b.idDDD == idDDDBuscaAvancada;
            }

            if (!"Todos".equals(subcategoriaBuscaAvancada)){
                b.mostrar = b.mostrar && b.idSubCategoria == idSubCategoriaBuscaAvancada;
            }
        }
        searchFragment.setRealizouBusca(true);

        for (Servico s : serviceFragment.results) {
            //Busca por titulo e descrição
            s.mostrar = s.titulo.toLowerCase().contains(buscaSimples.toLowerCase()) || s.descricao.toLowerCase().contains(buscaSimples.toLowerCase());

            if (!"Todos".equals(categoriaBuscaAvancada) && s.mostrar){
                s.mostrar = s.mostrar && subCategoriasDaCategoria.contains(s.idSubCategoria);
            }

            if (!"Todos".equals(dddBuscaAvancada)){
                s.mostrar = s.mostrar && s.idDDD == idDDDBuscaAvancada;
            }

            if (!"Todos".equals(subcategoriaBuscaAvancada)){
                s.mostrar = s.mostrar && s.idSubCategoria == idSubCategoriaBuscaAvancada;
            }
        }
        serviceFragment.setRealizouBusca(true);

        refreshLista();
    }

    private void refreshLista() {
        abaAtual = viewPager.getCurrentItem();
        viewPager = (ViewPager) findViewById(R.id.container);
        if (viewPager != null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mSectionsPagerAdapter);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(abaAtual);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //usuario = new ApiModels().getUsuarioById(id);
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        //SE O DRAWER NAO ABRIR TENTAR RETURN TRUE
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the current view position from the ViewPager by
     * extending SimpleOnPageChangeListener class and adding your method
     */
    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==1) {
                return searchFragment;
            }
            else {
                return serviceFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Serviço";
                case 1:
                    return "Busca";
            }
            return null;
        }
    }

    public void onCheckboxClicked(View view) {
        //CheckBox do Busca Avançada
        //dialog.onCheckboxClicked(view);
    }
}
