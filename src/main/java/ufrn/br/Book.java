package main.java.ufrn.br;

public record Book(
        long id,
        String title,
        String author,
        float score,
        String review
) {
}
