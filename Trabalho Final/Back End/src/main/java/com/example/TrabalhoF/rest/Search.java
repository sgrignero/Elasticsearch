package com.example.TrabalhoF.rest;

import com.example.TrabalhoF.elastic.ElManager;
import com.example.TrabalhoF.resultado.Resultado;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/search")
public class Search {

    @GetMapping("")

    public Resultado calls(@RequestParam(name = "must") String must, @RequestParam(name = "must_not", defaultValue = "") String must_not, @RequestParam(name = "should", defaultValue = "") String should , @RequestParam(name = "page", defaultValue = "1") int  pagina ) throws IOException {
        OpenSearchClient client = new ElManager().conexao();

        SearchResponse<Document> results = client.search(s -> s
                        .index("wikipedia")
                        .query(q -> q
                                .bool(b -> b
                                        .must(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(must)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                        .mustNot(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(must_not)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                        .should(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(should)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                )
                        ).from((pagina - 1) * 10)
                , Document.class
        );

        List<Document> documents = results.hits().hits().stream().map(hit -> hit.source()).collect(Collectors.toList());
        long total = results.hits().total().value();

        return new Resultado(documents, total);
    }

}
