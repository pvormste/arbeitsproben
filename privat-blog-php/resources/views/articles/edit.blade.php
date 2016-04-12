@extends('app')

@section('content')
    <h2>Edit Article <small><a href="{{ route('articles-index') }}">Back</a></small></h2>

    @include('articles._form', [
        'route_name' => 'articles-update',
        'article' => $article,
        'buttontext' => 'Update',
        'errors' => $errors
    ])
@endsection