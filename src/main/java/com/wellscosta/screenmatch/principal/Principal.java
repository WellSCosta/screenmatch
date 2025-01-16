package com.wellscosta.screenmatch.principal;

import com.wellscosta.screenmatch.model.*;
import com.wellscosta.screenmatch.repository.SerieRepository;
import com.wellscosta.screenmatch.service.ConsumoApi;
import com.wellscosta.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner sc = new Scanner(System.in);
    
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_APIKEY");

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private List<Serie> series = new ArrayList<>();

    private SerieRepository repository;

    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = 0;
        do {
            var menu = """
                    =-=-=-=-=-=-=-=-=-=-=-=-=-=

                    1  - Buscar séries
                    2  - Buscar episódios
                    3  - Listar séries buscadas
                    4  - Buscar série por título
                    5  - Buscar série por ator
                    6  - Top 5 série
                    7  - Buscar por categoria
                    8  - Filtrar séries
                    9  - Buscar episodio por trecho
                    10 - Top 5 episódios
                    11 - Buscar episódios a partir de uma data
                    
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
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodioPorSerie();
                    break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
            }
        } while (opcao != 0);
    }

    private void buscarEpisodioDepoisDeUmaData() {
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()) {
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento: ");
            var lancamento = sc.nextInt();
            sc.nextLine();

            List<Episodio> episodiosAno = repository.episodiosPorSerieEAno(serie, lancamento);
            episodiosAno.forEach(System.out::println);
        }
    }

    private void topEpisodioPorSerie() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()) {
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repository.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporadas: %s - Episódio: %s - %s - Avaliação: %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("Qual o nome do episodio para busca: ");
        var nomeEpisodio = sc.nextLine();
        List<Episodio> episodiosEncontrados = repository.episodiosPorTrecho(nomeEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporadas: %s - Episódio %s - %s\n",
                e.getSerie().getTitulo(), e.getTemporada(),
                e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void filtrarSeriesPorTemporadaEAvaliacao() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = sc.nextInt();
        sc.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        sc.nextLine();
        List<Serie> filtroSeries = repository.seriePorTemporadaEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.print("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = sc.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);

        List<Serie> serieBuscada = repository.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        serieBuscada.forEach(System.out::println);
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repository.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s -> System.out.println("Título: " + s.getTitulo() + ", avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorAtor() {
        System.out.print("Escolha um ator: ");
        var ator = sc.nextLine();
        var serieBuscada = repository.findByAtoresContainingIgnoreCase(ator);

        System.out.println("Séries em que " + ator + " trabalhou: ");
        serieBuscada.forEach(s -> System.out.println("Título: " + s.getTitulo() + ", avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Escolha uma série pelo nome: ");
        var nomeSerie = sc.nextLine();
        serieBusca = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da Série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas() {
        series = repository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.print("Escolha uma série pelo nome: ");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
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
