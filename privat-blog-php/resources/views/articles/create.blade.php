@extends('app')

@section('content')
    <h2>Add Article <small><a href="{{ route('articles-index') }}">Back</a></small></h2>

    @include('articles._form', [
        'route_name' => 'articles-store',
        'article' => null,
        'buttontext' => 'Add Article',
        'errors' => $errors
    ])
@endsection