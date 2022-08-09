<%----%>
<%@ page import="java.util.List" %>
<%@ page import="uz.pdp.librarymanagementsystem.books.Book" %>
<%@ page import="uz.pdp.librarymanagementsystem.books.BookController" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    .card{
        margin-top: 6%;
        margin-left: 6%;
    }
</style>
<%@include file="navigation.jsp"%>
<section class="container mt-4 ">

    <div class="row justify-content-around">

<% List<Book> bookList = new BookController().getBookList();
    for (Book book : bookList) {%>

<div class="card text-center shadow col-md-3" style="width: 18rem;">
    <img src="images/<%=book.getImgUrl()%>" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title"><%=book.getTitle()%></h5>
        <p class="card-text"><%=book.getAuthors()%></p>
        <a href="#" class="btn btn-primary">About Book</a>
    </div>
</div>
<%}%>
    </div>
</section>
</body>
</html>
