package main.java.ufrn.br;

import main.java.ufrn.br.annotations.*;

@RemoteComponent(name = "book_app", basePath = "book_app")
public class BookLog {

    // TODO: Enviar o parametro pela
    @Get(route = "/book")
    public void getBook(@Param(name = "id") long id){
        System.out.println("Get Book");
    }

    @Get(route = "/all-books")
    public void getAllBooks(){
        System.out.println("Get all books");
    }

    @Post(route = "/new-book")
    public String addBook(){
        System.out.println("Book added");
        return "Book added";
    }

    @Put(route = "/alt-book")
    public String updateBook(){
        return "Book updated";
    }

    @Delete(route = "/delete-book")
    public String deleteBook(){
        return "Book deleted";
    }
}
