//package main.java.ufrn.br;
//
//import main.java.ufrn.br.annotations.*;
//import org.json.JSONObject;
//
//@RemoteComponent(name = "book_app")
//public class BookLog {
//
//    @Get(route = "book/{id}")
//    public String getBook(@Param long id){
//        System.out.println("Get Book "+ id);
//        return "Book " + id;
//    }
//
//    @Get(route = "books")
//    public String getAllBooks(){
//
//        return "All books";
//    }
//
//    @Post(route = "book")
//    public JSONObject addBook(@RequestBody JSONObject book){
//        System.out.println(book);
//        System.out.println("Book added");
//        return book;
//    }
//
//    // NOTE: Por enquanto os parametros enviados na rota (@Param) devem ser definidos antes dos parametros de objeto (@RequestBody)
//    // Eles devem também estar na mesma ordem em que foi denido em "route".
//    // Isso porque o matcher do regex procura por eles em ordem.
//    // Caso um @Param seja defino após um @ResquestBody haverá um erro de IndexOutOfBoundesExceptio.
//    @Put(route = "book/{id}")
//    public JSONObject updateBook(@Param long id, @RequestBody JSONObject book){
//        System.out.println("Id do livro modificado: " + id);
//        return book;
//    }
//
//    @Delete(route = "book/{id}")
//    public String deleteBook(@Param long id){
//        return "Id do livro removido: " + id;
//    }
//}
