package com.wellscosta.screenmatch.principal;

import com.wellscosta.screenmatch.model.DadosSerie;
import com.wellscosta.screenmatch.model.DadosTemporada;
import com.wellscosta.screenmatch.model.Serie;
import com.wellscosta.screenmatch.repository.SerieRepository;
import com.wellscosta.screenmatch.service.ConsumoApi;
import com.wellscosta.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner sc = new Scanner(System.in);
    
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_APIKEY");

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repository;
    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = 0;
        do {
            var menu = """
                    =-=-=-=-=-=-=-=-=-=-=-=-=-=

                    1 - Buscar Séries
                    2 - Buscar Episódios
                    3 - Listar Séries Buscadas

                    0 - Sair

                    =-=-=-=-=-=-=-=-=-=-=-=-=-=
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
            }
        } while (opcao != 0);
    }

    private void listarSeriesBuscadas() {
        List<Serie> series = repository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dados = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var json = consumoApi.obterDados(ENDERECO + dados.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("Digite o nome da série para buscar: ");
        var nomeSerie = sc.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        return converteDados.obterDados(json, DadosSerie.class);
    }
}
