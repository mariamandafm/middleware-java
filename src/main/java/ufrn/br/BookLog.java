package main.java.ufrn.br;

import main.java.ufrn.br.annotations.*;
import org.json.JSONObject;

@RemoteComponent(name = "book_app", basePath = "book_app")
public class BookLog {

    // TODO: Enviar o parametro pela
    @Get(route = "book/{id}")
    public String getBook(@Param(name = "id") long id){
        System.out.println("Get Book "+ id);
        return "Book " + id;
    }

    @Get(route = "all-books")
    public void getAllBooks(){
        System.out.println("Get all books");
    }

    @Post(route = "new-book")
    public String addBook(JSONObject book){
        System.out.println("Book added");
        return "Book added";
    }

    @Put(route = "alt-book")
    public String updateBook(){
        return "Book updated";
    }

    @Delete(route = "delete-book")
    public String deleteBook(){
        return "Book deleted";
    }
}
