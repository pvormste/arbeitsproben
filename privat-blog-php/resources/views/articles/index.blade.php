@extends('app')

@section('content')
    <h1>Artikel</h1>
    <ul>
        @foreach($articles as $article)
            <li><a href="{{ route('articles-show', $article->slug) }}">{{ $article->title }}</a> <small>- geschrieben am {{ $article->published_at }} - [ <a href="{{ route('articles-edit', $article->slug) }}">edit</a> ] [ <a href="{{ route('articles-delete', $article->slug) }}">delete</a> ]</small></li>
        @endforeach
    </ul>
    <p><a href="{{ route('articles-create') }}">Add Article</a></p>
@endsection