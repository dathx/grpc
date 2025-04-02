package com.grpc.student;

import com.book.Author;
import com.book.Book;
import com.book.EnumRequest;
import com.student.StudentRequest;
import com.student.StudentResponse;
import com.student.StudentServiceGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StudentServer {
    public static void main(String[] args) {
        Author author = Author.newBuilder().setName("Dat").setEnumRequest(EnumRequest.SECOND).build();

        List<Author> authorList = List.of(author);
        Book book = Book.newBuilder().addAllAuthor(authorList).build();
        Author author1 = Author.newBuilder().setName("Jack").setEnumRequest(EnumRequest.THIRD).setEnumRequest(EnumRequest.SECOND).putMap("data","Data values").build();

        log.info("author1: {}", author1);
        log.info("book: {}", book);
        log.info("default: {}", book.getDefaultInstanceForType());
        log.info("Email: {}", author1.getMapCount());
//        log.info("book: {}", book.hasField(book.getDescriptorForType().findFieldByName("author")));
    }


}
