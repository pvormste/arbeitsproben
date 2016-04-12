@extends('app')

@section('content')
    <h2>{{ $article->title }}</h2>
    <p>{{ $article->textbody }}</p>
    <hr>
    <p>{{ $article->published_at }}</p>
    <a href="{{ route('articles-index') }}">Back</a>
@endsection